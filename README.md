DevoxxFr Back Office
====================

This application use JPA 2, JAX-RS (RestEasy), JAXB, Morphia (MongoDb JPA), ...

Prerequisite
------------

* Need a MongoDb instance (specified in pom.xml)
* Need a MySQL Datasource
* Need a JBoss AS 7 server

Usages
------ 

### Activate CloudBees Profile (tomcat) :
    mvn clean package -Dcloudbees=master

### Activate CloudBees Profile (jee/jboss) :
    mvn clean package -Dcloudbees=jee