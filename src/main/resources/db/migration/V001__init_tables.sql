CREATE SEQUENCE users_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE clients_id_seq START WITH 1 INCREMENT BY 50;

--
--CREATE TYPE authentication_method AS ENUM ('client_secret_basic', 'client_secret_jwt');
--
--
CREATE TABLE users (
  user_id INTEGER DEFAULT nextval('users_id_seq') PRIMARY KEY,
  username VARCHAR(12),
  email VARCHAR(32),
  password VARCHAR(100),
  CONSTRAINT check_username_or_email_non_empty
  CHECK ((username IS NOT NULL AND username <> '') OR
        (email IS NOT NULL AND email <> ''))
);

CREATE TABLE clients (
  cid INTEGER DEFAULT nextval('clients_id_seq') PRIMARY KEY,
  client_id VARCHAR(16) NOT NULL UNIQUE,
  secret VARCHAR(16) NOT NULL,
  redirect_uri VARCHAR(100) NOT NULL,
  scope VARCHAR(40),
  auth_method VARCHAR(30) NOT NULL,
  grant_type VARCHAR(10) NOT NULL
);

CREATE TABLE authorities (
 authority_id SERIAL PRIMARY KEY,
 name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE roles (
  role_id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_roles (
  user_id INTEGER NOT NULL,
  role_id INTEGER NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE
);

CREATE TABLE roles_authorities (
  role_id INTEGER NOT NULL,
  authority_id INTEGER NOT NULL,
  PRIMARY KEY (role_id, authority_id),
  FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE,
  FOREIGN KEY (authority_id) REFERENCES authorities (authority_id) ON DELETE CASCADE
);
