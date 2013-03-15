twissandra-j
============

Twissandra-J is an OSGI-enabled Java example application created to learn and
demonstrate Cassandra usage.  Running the project will present a website that
has similar functionality to [Twitter](http://twitter.com).

Requirements
------------
 * Java 1.6 (or higher)
 * Karaf 2.3.x (or higher)
 * Maven 2.x
 * Cassandra 1.2.x (or higher)

Installation
------------
From the top-level directory, build the project:

    $ mvn install

Load the schema:

    $ cqlsh < schema.cql

From the top-level directory of your Karaf installation:

    $ features:addurl mvn:org.opennms.twissandra/twissandra-cassandra/1.0-SNAPSHOT/xml/features
    $ features:install twissandra-cassandra