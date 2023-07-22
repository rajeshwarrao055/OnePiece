## Description
Components of a prometheus stack : Prometheus, exporters and Grafana

### Prometheus
* Core component responsible for collecting, storing and querying TSD (metrics) from various
targets such as a spring boot application
* Periodically scrapes metrics endpoints exposed by the targets (like a spring boot app) to gather metric data

### Exporters 
* Prometheus relies on exporters to collect metrics from different services/systems
* For a spring boot application, we use a prometheus-compatiable exporter like `micrometer` to expose application metrics
* **Micrometer**
  * metrics instrumentation library that integrates with Spring boot and acts as a bridge b/w application and prometheus
  * provides a unified API to capture metrics and export them to various monitoring systems

### Grafana 
* Data visualization and monitoring tool that integrates with Prometheus
* Can be used for alerting and notification based on metric thresholds

## References
* https://grafana.com/docs/grafana-cloud/quickstart/docker-compose-linux/