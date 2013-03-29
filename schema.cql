
-- Copyright 2013, Matt Brozowski and Eric Evans
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

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
    username  text,
    following text,
    PRIMARY KEY(username, following)
);

-- Tweet storage
CREATE TABLE tweets (tweetid uuid PRIMARY KEY, username text, body text);

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