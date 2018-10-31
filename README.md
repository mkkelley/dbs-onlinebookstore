Online Bookstore Application
============================

First: This source code is provided for reference and proof, but I do not
recommend trying to build and run it. I will provide instructions, but there
are many variables and I will not be able to account for all of them.

Instead, please point your browser to http://dbs.minthe.net/ . Proof that I
own the domain: https://whois.icann.org/en/lookup?name=minthe.net .

There is a user already created `john_smith` password `js` with a few orders
as test data, but please feel free to create users and input as many orders
as you would like.

File Hierarchy
==============

All Java code can be found in `/src/main/java/net/minthe/dbsbookshop`.

Each package contains classes relevant to a particular part of the domain.
There are a few types of class suffixes that Spring uses:

- Service: Contains code that provides an interface between business logic and
persistence. For example, the `net.minthe.cart.CartService` gets information
about a user's cart from the database, instantiates a domain type
(`ShoppingCart`), and calls methods on the domain model. When it has finished
its operations, it persists the changes to the database.
- Controller: Receives HTTP requests, hands off processing to domain logic
classes, and returns a view (HTML).
- Repository: Handles database connection and entity persistence. The Spring
Boot JPA library automatically handles connection and simple queries by
inference from the method names. For example, in
`net.minthe.cart.CartRepository`, the `findByUserid` method has no associated
query, but the Spring Data JPA framework automatically generates the
appropriate query. In contrast, the `getTotalForMember` method deals with
the domain logic, so a custom query is necessary.
- Form: An object used to hold unsanitized data from form submissions or
other network requests.
- Validator: Does what it sounds like. The error messages from the validation
can be found in `/src/main/resources/messages.properties`.

Other classes deal with domain logic or modelling domain objects.

View templates can be found in `/src/main/resources`.

Running the Application
=======================

In addition to the database schema specified in the project description, an
additional sequence is needed `CREATE SEQUENCE ORDER_SEQUENCE START WITH 1`

If you would like to run the application on a local machine and you are using
Oracle 12c as the database, you can use the included JAR file to run the
application. Edit application.properties to set the connection settings
appropriate to your database (connection string, username, password), and then
`java -jar dbsbookshop_oracle12c.jar`.

If you are not using Oracle 12c, you will have to make some changes. See the
next section.

Building the Application
========================

Database Driver
---------------

The provided JAR includes the driver for Oracle 12c, but this will not work if
you want to build the application. If you are using Oracle 12c, download the
thin driver from Oracle and add it to your local Maven repository. Then edit
pom.xml and make sure the version, artifact id, and group id all match what
you have installed (under dependency `com.oracle.ojdbc8`).

If you are using a different version of Oracle, you will have to follow the
same steps and also change the property `spring.jpa.database-platform` in
`application.properties` to the appropriate value.

If you are using a different database altogether, you will have to include the
driver as a dependency in `pom.xml`, change `spring.jpa.database-platform`, and
`spring.datasource.driver-class-name` in `application.properties`. I have only
tested this application under Oracle 12c and PostgreSQL 10.

Building
--------

`mvn compile`

To run the application, `mvn spring-boot:run`. To build a JAR, `mvn package`.
You may have to disable tests to build the JAR.

Notes
=====

If I have forgotten anything here, I apologize. Please feel free to contact me
for any clarification that is necessary.
