DevoxxFr Back Office
====================

This application use JPA 2, JAX-RS (RestEasy), JAXB, Morphia (MongoDb JPA), ...

Prerequisite
------------

* Need a MongoDb instance (specified in pom.xml)
* Need a MySQL5 Instance + Datasource
* Need a JBoss AS 7

Usages
------ 

### Activate Local Profile (jee/jboss) :
    mvn clean package

### Activate CloudBees Profile (jee/jboss) :
    mvn clean package -Dcloudbees-jee