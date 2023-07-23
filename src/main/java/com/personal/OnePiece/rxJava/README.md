## Description
* popular library for composing asynchronous and event-based programs using observable sequences.
* allows to handle streams of data and events with ease

### Key Concepts

1. *Observable* : Represents a stream of data or events that can be observed by subscribers. It emits items to its subscribers, and it can be finite or infinite
2. *Subscriber/Observer* : Listens to items emitted from an observable. It has methods to handle emitted items, errors and completion signals
3. *Operators* : Transform/Filter/Combine/Observables to create new streams of data. RxJava provides a wide range of operators to perform various tasks
4. *Schedulers* : Control execution thread for observables and subscribers
5. *Subscription* : Relationship between observable and subscriber

### Mono v/s Flux
`Mono` and `Flux` are the two main types used for representing async, reactive streams of data.
Both provide a set of operators to transform, combine or handle the emitted elements or errors


1. **MONO**
* Reactive type that represents a single, possible empty result or error.
* It can emit at most one element - container for 0/1 element
* Can completely successfully or with an error, *not both*
* represents a single async value or operation

2. **FLUX**
* represents a stream of zero or more elements, similar to Java's `Iterable`
* it is asynchronous and non blocking - container for multiple elements
* potentially infinite stream of asynchronous values/operations

### Reactive REST APIs v/s Traditional REST APIs
Traditional REST APIs typically use frameworks like Spring MVC. These APIs handle requests by blocking the process thread 
until operation is complete which can lead to thread congestion under heavy loads.

Benefits of Traditional Synchronous REST APIs:
* Easier to develop and understand
* Familiarity with synchronous programming paradigms

Reactive REST API allow your application to efficiently handle multiple concurrent requests w/o blocking threads. Well suited
for I/O bound operations such as network calls and database queries.

Benefits of Reactive REST APIs:
* Non Blocking : Reactive APIs use async operations, allowing server to handle many concurrent requests with fewer threads
* Scalability : can efficiently use server resources and scale well to handle high loads
* Responsiveness : since server is non bocking, can quickly respond to requests even during heavy traffic
* Resource efficiency : use fewer threads