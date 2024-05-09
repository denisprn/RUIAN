# RÚIAN REST API
The Register of Territorial Identification, Addresses and Real Estate, also RÚIAN (in czech stands for Registr územní identifikace, adres a nemovitostí), is one of the four basic registers of the Czech Republic. RÚIAN contains data on basic territorial elements.
## General info
It is a JSON RESTful API, that once a month download archive with CSV files with addresses from real estate cadastre and imports them to Elasticsearch database.
API allows a retrieval of individual items according to RÚIAN ID and fuzzy search in the list of municipalities, parts of municipalities, streets, postal codes and entire address points.
## Technologies
* JDK 17
* Spring Boot 2.6.4
* Gradle Build Tool 7.3.3
* Elasticsearch 7.17.1
