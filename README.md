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
## What is REST API?
A REST API is an application programming interface (API) that uses HTTP requests to retrieve, place, and delete data. REST stands for Representational State Transfer in the context of an API.

Let's start with what an API is. For a website, this is the code that allows two programs to communicate with each other. The API offers developers the correct way to write a program that requests services from an operating system or other application. Simply put, it is a kind of standard that allows programs and applications to understand each other; it is the language of the Internet, which is essential for almost all sites and applications to work.
Another common use case is in the cloud, where a REST API is needed to provide and organize access to web services. The technology allows users to flexibly connect to, manage and interact with cloud services in a distributed environment.

A REST (or RESTful) API is based on representational state transfer, an architectural style, and a communications approach often used when developing web services.
### REST API principles:
* Unified interface:

Resources must be uniquely identified using a single URL and only using basic network protocol methods (DELETE, PUT, GET, HTTP).
* Client-server:

There should be a clear distinction between client and server:
  1. user interface and query collection issues - on the client side.
  2. data access, workload management and security - on the server side.

* Saved State:

All client/server operations must be stateless. Any necessary state management should be done on the client, not on the server.
* Caching:

All resources must enable caching unless explicitly stated that it is not possible.
* Multilevel system:

The REST API allows an architecture that consists of multiple server tiers.
* Code request:

In most cases, the server sends back static representations of resources in XML or JSON format. However, servers can send executable code directly to the client if needed.
## What is Elasticsearch?
Elasticsearch (ES) is a scalable full-text search and analytics utility that allows you to quickly store, search, and analyze large amounts of data in real time. ES provides scale-out search with multithreading support. The system is based on the Apache Lucene library, which is designed for indexing and searching information in any type of documents. All Lucene features are available through JSON and Java APIs. ES allows you to work with GET requests in real time, but does not support distributed transactions.

Some of the key features of Elasticsearch are:
* automatic indexing of new JSON objects that are loaded into the database and immediately become available for search, due to the absence of a schema according to a typical NoSQL concept. This allows you to speed up prototyping of Big Data search solutions.
* support for oriental languages (Chinese, Japanese, Korean);
* flexibility of search filters, including fuzzy search and multitenancy, when several different search engines can be dynamically organized within one ES object;
* the presence of built-in text analyzers allows Elasticsearch to automatically perform tokenization, lemmatization, stemming, and other transformations to solve NLP tasks related to data retrieval.
## Why Elasticsearch over MongoDB?
Hypothetically both store data objects that have key-value pair, and allow querying that body of objects. But both come from 2 different camps and are made for different purposes.
* MongoDB is a general purpose database, Elasticsearch is a distributed text search engine backed by Lucene. 
* ElasticSearch is very efficient for specific task — indexing and searching big datasets.
* Elasticsearch has been developed to make a ‘no SPOF’ (no Single Point Of Failure) engine -i.e. in a cluster of several Elasticsearch even if a node would turn off (for example a server crash) the data would always be available and the service would go on working.
* Elasticsearch has got its own DSL based on a JSON format enabling to make some queries through REST API more easily.
* Elasticsearch has ability to create custom analyzers.
* Elasticsearch has ability to multi match search with custom field weights.
## Instalation guide
1. Download and install Java SDK 17;
2. Download and install Gradle Build Tool 7.3 or later;
3. Download, install and run Docker;
4. In command line get inside project's root folder;
5. In command line run `gradle build -x test` command;
6. In command line run `docker-compose up` command;
