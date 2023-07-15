## Description

### Where can we use graph QL ?

GraphQL can be used in various scenarios where you need a flexible and efficient way to retrieve and manipulate data. Here are some common use cases where GraphQL is often utilized:

API Development: GraphQL is commonly used for building APIs that allow clients to fetch and modify data. Its ability to provide a single endpoint that can fetch multiple resources and retrieve only the required data makes it efficient and reduces over-fetching and under-fetching of data.

Mobile Applications: GraphQL's ability to allow clients to specify their data requirements and retrieve only the necessary data makes it well-suited for mobile applications. It helps minimize the amount of data transferred over the network, reduces the number of API calls, and enables clients to work efficiently with limited resources.

Web Applications: GraphQL is also popular for building web applications as it provides a declarative and efficient way to fetch and manipulate data. It allows frontend developers to precisely specify the data they need, reducing the complexity of handling multiple endpoints or excessive data fetching.

Microservices Architecture: In a microservices architecture, where multiple services need to communicate and exchange data, GraphQL can act as a layer of abstraction between the services. It enables clients to retrieve data from multiple services through a single GraphQL endpoint, simplifying the communication process.

Real-time Updates: GraphQL subscriptions allow clients to receive real-time updates from the server. This feature makes it suitable for applications that require real-time data updates, such as chat applications, live dashboards, or collaborative editing tools.

Aggregating Data: GraphQL can be used to aggregate data from multiple sources or APIs into a single unified API. It enables clients to retrieve data from different services and APIs in a consistent and efficient manner, reducing the complexity of managing multiple integrations.

Overall, GraphQL can be used in a wide range of applications where efficient data retrieval, flexibility, and real-time capabilities are desired. Its versatility and ability to adapt to various use cases make it a popular choice among developers.

### References 
Reference 1 - [here](https://www.baeldung.com/spring-graphql)
Reference 2 - [here](https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/) :: Much Better


### Testing Endpoint
GraphQL endpoint - localhost:8080/graphql
Enabled GraphIQL playground - localhost:8080/graphiql