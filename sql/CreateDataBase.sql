-- PostgreSQL database dump

SET client_encoding = 'UTF8';

-- Personne
CREATE TABLE Individuals(
  id SERIAL PRIMARY KEY,
  first_name text,
  last_name text,
  birth_year integer
);

-- types de productions
CREATE TABLE Item_Types(
  id SERIAL PRIMARY KEY,
  name text
);

-- Productions
CREATE TABLE Items(
  id SERIAL PRIMARY KEY,
  title text,
  type integer references Item_types,
  start_date date,
  end_date date
);

CREATE TABLE Event_Types(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE Events(
  id SERIAL PRIMARY KEY,
  name text,
  place text,
  start_date date,
  end_date date,
  type integer references Event_types
);

CREATE TABLE Institution_Types(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE Institutions(
  id SERIAL PRIMARY KEY,
  name text,
  place text,
  type integer references Institution_types
);

CREATE TABLE Tags(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE Disciplines(
  id SERIAL PRIMARY KEY,
  name text
);

-- primary key given by the two ids
-- so at most one such link per person and institution
CREATE TABLE Individuals_Institutions(
 indiv_id integer REFERENCES Individuals, --FK
 instit_id integer REFERENCES Institutions,--FK
 PRIMARY KEY (indiv_id, instit_id)
);

CREATE TABLE Individuals_Disciplines(
 indiv_id integer REFERENCES Individuals, --FK
 disc_id integer REFERENCES Disciplines,--FK
 PRIMARY KEY (indiv_id, disc_id)
);

CREATE TABLE Individuals_Tags(
 indiv_id integer REFERENCES Individuals, --FK
 tag_id integer REFERENCES Tags,--FK
 PRIMARY KEY (indiv_id, tag_id)
);

CREATE TABLE Items_Tags(
 item_id integer REFERENCES Items, --FK
 tag_id integer REFERENCES Tags, --FK
 PRIMARY KEY (item_id, tag_id)
);

CREATE TABLE Events_Tags(
 event_id integer REFERENCES Events,
 tag_id integer REFERENCES Tags,
 PRIMARY KEY (event_id, tag_id)
);

CREATE TABLE Author(
  indiv_id integer REFERENCES Individuals, --FK
  item_id integer REFERENCES Items, --FK
  PRIMARY KEY (indiv_id, item_id)
);

CREATE TABLE Direction(
  item_id integer REFERENCES Items, --FK
  indiv_id integer REFERENCES Individuals, --FK
  PRIMARY KEY (indiv_id, item_id)
);

CREATE TABLE Organizer(
  event_id integer REFERENCES Events,
  indiv_id integer REFERENCES Individuals,
  PRIMARY KEY (event_id, indiv_id)
);

CREATE TABLE Participant(
  event_id integer REFERENCES Events,
  indiv_id integer REFERENCES Individuals,
  PRIMARY KEY (event_id, indiv_id)
);

CREATE TABLE Affliation(
  item_id integer REFERENCES Items,
  instit_id integer REFERENCES Institutions,
  PRIMARY KEY (event_id, indiv_id)
);

-- two possible values: "financement", "soutien institutionnel"
CREATE TABLE Localisation_Types(
  id SERIAL PRIMARY KEY,
  name text
);

-- "Localisation"
CREATE TABLE Localisations(
  event_id integer REFERENCES Events,
  instit_id integer REFERENCES Institutions,
  loc_type integer references Localisation_Types,
  PRIMARY KEY (event_id, instit_id)
);
