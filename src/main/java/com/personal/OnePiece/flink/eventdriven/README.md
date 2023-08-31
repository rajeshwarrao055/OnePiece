### Event Driven Applications
### Process Functions
* ProcessFunction combines event processing with timers and state. 
* ProcessWindowFunction - requires a window function
```
DataStream<Tuple3<Long, Long, Float>> hourlyTips = fares.keyBy(fare -> fare.driverId)
    .window(TumblingEventTimeWindows.of(Time.hours(1)))
    .process(new AddTips());
```
* ProcessFunction
```
DataStream<Tuple3<Long, Long, Float>> hourlyTips = fares.keyBy(fare -> fare.driverId)
    .process(new PseudoWindo(Time.hours(1)));
```

KeyedProcessFunction called `PseudoWindow` is being applied to keyed stream

```
public static class PseudoWindow extends KeyedProcessFunction<Long, TaxiFare, Tuple3<Long, Long, Float>> {
    private final long durationMsec;
    public PseudoWindow(Time duration) P{
        this.durationMsec = duration.toMilliseconds();
    }
    
    @Override
    public void open(Configuration conf){
        ...
    }
    
    @Override
    public void processElement(
        TaxiFare fare,
        Context ctx,
        Collector<Tuple3<Long, Long, Float>> out
    ) throws Exception {
        ...
    }
    
    @Override
    public void onTimer(long timestamp, OnTimerContext context,
        Collector<Tuple3<Long, Long, Float>> out) throws Exception {
        ...
    }
}
```

* Several types of ProcessFunctions - KeyedProcessFunction, CoProcessFunctions, BroadcastProcessFunctions, etc.
* KeyedProcessFunction is a kind of RichFunction. It has access to the `open` and `getRuntimeContext` methods needed for
working with managed keyed state.
* Two callbacks to implement : `processElement` and `onTimer`.
* `processElement` is called with each incoming event
* `onTimer` is called when timers fire. These can be either event time or processing time timers.

### The `open()` method

```
// Keyed, managed state, with an entry for each window, keyed by window's end time
// separate map state object for each driver

private transient MapState<Long, Float> sumOfTips;

@Override
public void open(Configuration conf) {
    MapStateDescriptor<Long, Float> sumDesc = new MapStateDescriptor<>("sumOfTips", Long.class, Float.class);
    sumOfTips = getRuntimeContext().getMapState(sumDesc);
}
```

* fare events can arrive out of order, it will sometimes be necessary to process events for one hour before
having finished computing the results for the previous hour. 
* If watermarking delay is much longer than the window length, there may be many windows open simultaneously
* This implementation supports this by using a MapState that maps the timestamp for the end of each window to the sum of
tips for that window

#### The `processElement()` method
```
public void processElement(
    TaxiFare fare,
    Context ctx,
    Collector<Tuple3<Long, Long, Float>> out) throws exception {
    
    long eventTime = fare.getEventTime();
    TimerService timerService = ctx.timerService();
    
    if(eventTime <= timerService.currentWatermark()) {
        // event is late, window has already been triggered
    } else {
        // rounding up eventTime to end of window containing this event
        long endOfWindow = (event - (eventTime%durationMsec) + durationMsec - 1);
        
        // schedule a callback for when window has been completed
        timerService.registerEventTimeTimer(endOfWindow);
        
        Float sum = sumOfTips.getOrDefault(endOfWindow, 0.0F);
        sum += fare.Tip;
        sumOfTips.put(endOfWindow, sum);
    }
}
```

* What happens with late events ? 
  * Events that are behind the watermark are being dropped
  * Consider using a side output if needed

#### The `onTimer()` method

```
public void onTimer(long timestamp, OnTimerContext context, Collector<Tuple3<Long,Long,Float>> out) throws Exception {
  long driverId = context.getCurrentKey();
  Float sumOfTipsForTheHour = this.sumOfTips.get(timestamp);
  Tuple3<Long, Long, Float> result = Tuple3.of(driverId, timestamp, sumOfTipsForTheHour);
  out.collect(result);
  this.sumOfTips.remove(timestamp);
}
```

* pseudo-windows are being triggered when current watermark reaches the end of the hours, at which point onTimer is called

##### Performance Considerations
* Flink provides MapState and ListState types that are optimized for RocksDB
* Where possible, these should be used instead of ValueState object holding some sort of collection
* RocksDB state backend can append to ListState without going through serDe
* and for MapState each K-V pair is a separate RocksDB object, so MapState can be efficiently accessed and updated


### Side Outputs
#### Why ?
There are several reasons to have more than one output stream from a Flink operator, such as
* exceptions
* malformed events
* late events
* operational alerts, timed out connections to external services

Side outputs are a convenient way to do this. 

Side outputs are also a good way to implement an n-way split of a stream

#### Code Overview
Side output channel is associated with an `OutputTag<T>`. These tags have generic types that correspond 
to the type of the side output's Datastream
```
private static final OutputTag<TaxiFare> lateFares = new OutputTag<TaxiFare> ("lateFares");
```

above declared lateFares can be referenced both 
* when emitted late events in the processElement method of the `PseudoWindow`
```
if(eventTime <= timerService.currentWatermark()) {
  ctx.output(lateFares, fare);
} else {
  ....
}
```
* when accessing the stream from this side output in the main method of the job
```
SingleOutputStreamOperator hourlyTips = fares.keyBy(fare -> fare.driverId)
  .process(new PseudoWindow(Time.hours(1)));
 
hourlyTips.getSideOutput(lateFares).print();
```

NOTE : If Flink's built-in windowing API meets your needs, use it. But if doing something contorted with Flink's windows,
might be worth using the ProcessFunction
