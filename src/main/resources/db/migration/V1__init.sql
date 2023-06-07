DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id bigserial PRIMARY KEY,
  username varchar(50) NOT NULL UNIQUE,
  password varchar(60) NOT NULL,
  email TEXT NOT NULL UNIQUE,
  mobile TEXT,
  first_name varchar(50) NOT NULL,
  last_name TEXT,
  image_url TEXT,
  enabled BOOLEAN NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

CREATE TABLE authorities (
  id bigserial PRIMARY KEY,
  username varchar(50) REFERENCES users(username),
  authority varchar(50) NOT NULL
);

create unique index ix_auth_username on authorities(username, authority);