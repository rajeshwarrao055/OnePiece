## Description

* https://stackoverflow.com/questions/1000162/what-are-the-use-cases-of-graph-based-databases-http-neo4j-org

### Notes
* Graph Database stores nodes and relationships instead of tables / Documents.
* Existing relational databases can store relationships, however, they navigate them with expensive joins
or cross lookups, often tied to a rigid schema 
* In graph databases, relationships are stored alongside data nodes in a much more flexible format.

### Property Graph Model
Information is stored as `nodes`, `relationships` and `properties`
* **Nodes** -> entities in the graph
  * can be tagged with `labels` (represent their different roles in the domain). e.g. `Person`
  * can hold any number of k-v pairs or **properties** . e.g. `name`
  * node labels may also attach metadata `such as index or constraint information`
* **Relationships** -> provide directed, named connections between two node entities
  * always have a direction(start node -> end node), they can have properties just like nodes 
  * nodes can have any number or type of relationships without impacting performance

### More notes

Graph databases are particularly useful when you need to model and query complex relationships between entities.

Scenarios where using a graph database is advantageous :
* Highly Connected Data : Lots of relationships and dependencies such as social networks, recommendation systems,
or fraud detection 
* Hierarchical or networked data 
* Real-time recommendations and personalization : Graph databases excel at providing real-time recommendations and personalizations
based on user preference and behaviour. They can model user-item interactions, social connections and contextual information to generate
personalized suggestions efficiently

## Understanding Graph DB Queries 
1. Find actor name "Tom Hanks"
```
MATCH (person:Person {name: "Tom Hanks"}) RETURN person
```

node identifier -> person, label : Person 
property : name -> value "Tom Hanks"

2. Find movie with the title "Cloud Atlast"
```
MATCH (movie: Movie {title : "Cloud Atlas"}) RETURN movie
```

3. Find 10 people and return their names
```
MATCH (people : Person) RETURN people.name LIMIT 10
```

4. Find movies released in the 1990s and return their titles 
```
MATCH (movie : Movie) WHERE movie.released >= 1990 AND movie.released <2000 RETURN movie
```

### Notes on Queries 
* MATCH (Describe a data pattern) => `MATCH` clause describes a pattern of graph data. Neo4j will collect all paths within
the graph which match this pattern. Match describes the structure
* WHERE (Filter results) => `WHERE` clause imposes conditions on data within a potentially matching path, filtering the result 
set of a match. 

**Query**
```
MATCH (director:Person)-[:DIRECTED]->(movie)
WHERE director.name = "Steven Spielberg"
RETURN movie.title
```

`MATCH (director:Person)-[:DIRECTED]->(movie)` -> specifies the pattern to match in the graph.
Consists of three parts :
* `(director:Person)` :: identifer director , matches a node with label Person
* `[:DIRECTED]` : matches a relationship with type `DIRECTED`. director node is connected to the next part of 
the pattern via this relationship
* `->(movie)` : matches a node without specifying a label

### More Queries 

1. What movies did Tom Hanks act in ?
```
MATCH (person :Person{name : "Tom Hanks"})-[:ACTED_IN]->(movie :Movie) RETURN person, movie
```

2. Who directed Cloud Atlas ?
```
MATCH(movie : Movie{title : "Cloud Atlas"})<-[:DIRECTED]-(person: Person) RETURN person.name
```

3. Who were Tom Hanks' co actors ?
```
MATCH(person:Person{name : "Tom Hanks"})-[:ACTED_IN]->(movies)<-[:ACTED_IN]-(coActors) RETURN DISTINCT coActors.name
```