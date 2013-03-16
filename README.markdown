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
### Build the twissandra-j project

From the top-level twissandra-j directory, build the project:

    $ mvn install

### Setting up Cassandra

Cassandra can be downloaded from: http://cassandra.apache.org/download.

It's beyond the scope of this document to cover the installation of
Cassandra in detail.  See the README.txt in the top-level project directory,
or the [Getting Started](http://wiki.apache.org/cassandra/GettingStarted)
guide.  That said, for a simple test setup it's generally enough to extract
the download, create the data directory, and run it in the foreground:

    $ tar xvf apache-cassandra-x.y.z-bin.tar.gz
    $ sudo mkdir -p /var/log/cassandra
    $ sudo chown -R `whoami` /var/log/cassandra
    $ sudo mkdir -p /var/lib/cassandra
    $ sudo chown -R `whoami` /var/lib/cassandra
    $ cd apache-cassandra-x.y.z
    $ bin/cassandra -f

Since `twissandra-j` uses the CQL native transport, you will need to enable
it.  Open `conf/cassandra.yaml` in your editor of choice and set
`start_native_transport: true`.

Once Cassandra is running you can load the schema using `cqlsh` (ships
with Cassandra):

    $ /path/to/cassandra/bin/cqlsh < /path/to/twissandra-j/schema.cql

### Starting Karaf and twissandra-j

From the top-level directory of your Karaf installation, start karaf with
a console, and install the `twisssandra-cassandra` feature:

    $ bin/karaf
    karaf@root> features:addurl mvn:org.opennms.twissandra/twissandra-cassandra/1.0-SNAPSHOT/xml/features
    karaf@root> features:install twissandra-cassandra

If Cassandra is configured to listen on a host other than localhost, or a
port other than 9042, run the following as necessary in your karaf shell:

    karaf@root> config:propset -p org.opennms.twissandra.persistence.cassandra cassandraHost cass.sample.com
    karaf@root> config:propset -p org.opennms.twissandra.persistence.cassandra cassandraPort 9041