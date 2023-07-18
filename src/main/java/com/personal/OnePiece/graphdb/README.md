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
