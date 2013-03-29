Twissandra-J
============

Twissandra-J is an OSGi-enabled Java example application created to learn and
demonstrate [Cassandra](http://cassandra.apache.org) usage, and the features
of CQL v3+.  Running the project will present a website that has similar
functionality to [Twitter](http://twitter.com).

To jump straight to the data model-specific code, see the
[CassandraTweetRepository class](https://github.com/eevans/twissandra-j/blob/master/persistence-cassandra/src/main/java/org/opennms/twissandra/persistence/cassandra/internal/CassandraTweetRepository.java).
To see the CQL schema, check out the [Schema Layout](#schema-layout) section of
this document.

Twissandra-J is the Java cousin to
[Twissandra](http://github.com/eevans/twissandra).  If your preferred language
is Python, you may want to check it out instead.


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

### Starting Karaf and Twissandra-J

In order to be able to use the webapp correctly you must change the OSGi
implementation in your Karaf distribution. To do this, set the `karaf.framework`
property in `<KARAF-HOME>/etc/config.properties` from `felix` to `equinox`.

From the top-level directory of your Karaf installation, start karaf with
a console, and install the `twisssandra-cassandra` feature:

    $ bin/karaf
    karaf@root> features:addurl mvn:org.opennms.twissandra/twissandra-cassandra/1.0-SNAPSHOT/xml/features
    karaf@root> features:install twissandra-cassandra

If Cassandra is configured to listen on a host other than localhost, or a
port other than 9042, run the following as necessary in your karaf shell:

    karaf@root> config:propset -p org.opennms.twissandra.persistence.cassandra cassandraHost cass.sample.com
    karaf@root> config:propset -p org.opennms.twissandra.persistence.cassandra cassandraPort 9041

If you would like to generate some random test data, run the following from
the karaf shell:

    karaf@root> twissandra:generate

### Profit

If you've successfully reached this point, open up your browser and visit:
http://localhost:8181/twissandra


Schema Layout
-------------

In Cassandra, the way that your data is structured is very closely tied to how
how it will be retrieved.  Let's start with the user table. The key is a
username, and the columns are the properties on the user:

    -- User storage
    CREATE TABLE users (username text PRIMARY KEY, password text);

Friends and followers are keyed by the username in the `following` and
`followers` tables respectively.  The use of a compound PRIMARY KEY like
this allows us to setup a one to many relationship between a user and the
people they are following, or the people following them.
    
    -- Users user is following
    CREATE TABLE following (
        username text,
        followed text,
        PRIMARY KEY(username, followed)
    );
    
    -- Users who follow user
    CREATE TABLE followers (
        username  text,
        following text,
        PRIMARY KEY(username, following)
    );

Tweets are stored with a UUID for the key.

    -- Tweet storage
    CREATE TABLE tweets (tweetid uuid PRIMARY KEY, username text, body text);

The `timeline` and `userline` tables keep track of which tweets should
appear, and in what order.  To that effect, the partition key is the
username, with columns for the time each was posted, and the text of the
tweet.

The `timeline` table has an additional column for storing the user who
authored the tweet.  This is because the timeline stores a materialized
view of the tweets a user is interested in; tweets created by others:

    -- Materialized view of tweets created by user
    CREATE TABLE userline (
        tweetid  timeuuid,
        username text,
        body     text,
        PRIMARY KEY(username, tweetid)
    );

    -- Materialized view of tweets created by user, and users she follows
    CREATE TABLE timeline (
        username  text,
        tweetid   timeuuid,
        posted_by text,
        body      text,
        PRIMARY KEY(username, tweetid)
    );
