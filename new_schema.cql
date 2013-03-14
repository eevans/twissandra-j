
DROP KEYSPACE twissandra;

CREATE KEYSPACE twissandra
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE twissandra;

-- User storage
CREATE TABLE users (username text PRIMARY KEY, password text);

-- Users user is following
CREATE TABLE following (
    username text,
    followed text,
    PRIMARY KEY(username, followed)
);

-- Users who follow user
CREATE TABLE followers (
    username text,
    following text,
    PRIMARY KEY(username, following)
);

-- Tweet storage
CREATE TABLE tweets (id uuid PRIMARY KEY, username text, body text);

-- Materialized view of tweets created by user
CREATE TABLE userline (
    posted_at timeuuid,
    username text,
    body text,
    PRIMARY KEY(username, posted_at)
);

-- Materialized view of tweets created by user, and users she follows
CREATE TABLE timeline (
    username text,
    posted_at timeuuid,
    posted_by text,
    body text,
    PRIMARY KEY(username, posted_at)
);