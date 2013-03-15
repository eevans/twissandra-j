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
From the top-level twissandra-j directory, build the project:

    $ mvn install

Load the schema using `cqlsh` (ships with Cassandra):

    $ cqlsh < schema.cql

From the top-level directory of your Karaf installation, start karaf with
a console, and install the `twisssandra-cassandra` feature:

    $ bin/karaf
    karaf@root> features:addurl mvn:org.opennms.twissandra/twissandra-cassandra/1.0-SNAPSHOT/xml/features
    karaf@root> features:install twissandra-cassandra

If Cassandra is listening on a host other than localhost, or a port other
than 9042, run the following as necessary in your karaf shell:

    karaf@root> config:propset -p org.opennms.twissandra.persistence.cassandra cassandraHost cass.sample.com
    karaf@root> config:propset -p org.opennms.twissandra.persistence.cassandra cassandraPort 9041