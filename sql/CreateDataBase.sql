-- PostgreSQL database dump
-- to create the database in project Centenaire
-- Latin1 encoding

-- with PostgreSQL (and OS = Windows):
-- CREATE DATABASE bdd_centenaire;
-- \c bdd_centenaire_test
-- \i path/to/folder/CreateDataBase.sql


SET client_encoding = 'Latin1';

-- list 'départements'
CREATE TABLE departements(
  id SERIAL PRIMARY KEY,
  name text
);

-- list countries
CREATE TABLE countries(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE institution_type_relations(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE institutions(
  id SERIAL PRIMARY KEY,
  name text,
  place text,
  id_dept integer references departements,
  id_country integer references countries,
  type integer references institution_type_relations
);

-- Personne
CREATE TABLE individuals(
  id SERIAL PRIMARY KEY,
  first_name text,
  last_name text,
  birth_year integer,
  id_lab integer references institutions,

  -- extra variables for free text answers
  phd_defense_year integer,
  phd_on_great_war boolean,
  habilitation_on_great_war boolean,
  question_one text,
  question_two text,
  question_three text,
  question_concern text,
  question_comittee text,
  question_contribution text,
  question_dev text
);

-- types de productions
CREATE TABLE item_type_relations(
  id SERIAL PRIMARY KEY,
  name text
);

-- Productions
CREATE TABLE items(
  id SERIAL PRIMARY KEY,
  title text,
  type integer references item_type_relations,
  start_date date,
  end_date date
);

CREATE TABLE event_type_relations(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE events(
  id SERIAL PRIMARY KEY,
  full_name text,
  short_name text,
  place text,
  id_dept integer references departements,
  id_country integer references countries,
  start_date date,
  end_date date,
  type integer references event_type_relations
);

CREATE TABLE tags(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE disciplines(
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE institutional_status(
 id SERIAL PRIMARY KEY,
 name text
);

-- primary key given by the two ids
-- so at most one such link per person and institution
CREATE TABLE individual_institution_relations(
 indiv_id integer REFERENCES individuals, --FK
 instit_id integer REFERENCES institutions,--FK
 instit_status integer REFERENCES institutional_status,
 PRIMARY KEY (indiv_id, instit_id)
);

CREATE TABLE individual_discipline_relations(
 indiv_id integer REFERENCES Individuals, --FK
 disc_id integer REFERENCES Disciplines,--FK
 PRIMARY KEY (indiv_id, disc_id)
);

CREATE TABLE individual_tag_relations(
 indiv_id integer REFERENCES Individuals, --FK
 tag_id integer REFERENCES Tags,--FK
 PRIMARY KEY (indiv_id, tag_id)
);

CREATE TABLE items_tag_relations(
 item_id integer REFERENCES Items, --FK
 tag_id integer REFERENCES Tags, --FK
 PRIMARY KEY (item_id, tag_id)
);

CREATE TABLE event_tag_relations(
 event_id integer REFERENCES Events,
 tag_id integer REFERENCES Tags,
 PRIMARY KEY (event_id, tag_id)
);

CREATE TABLE author(
  indiv_id integer REFERENCES Individuals, --FK
  item_id integer REFERENCES Items, --FK
  PRIMARY KEY (indiv_id, item_id)
);

CREATE TABLE direction(
  item_id integer REFERENCES items, --FK
  indiv_id integer REFERENCES individuals, --FK
  PRIMARY KEY (indiv_id, item_id)
);

CREATE TABLE organizer(
  event_id integer REFERENCES Events,
  indiv_id integer REFERENCES Individuals,
  PRIMARY KEY (event_id, indiv_id)
);

CREATE TABLE participant(
  event_id integer REFERENCES Events,
  indiv_id integer REFERENCES Individuals,
  PRIMARY KEY (event_id, indiv_id)
);

CREATE TABLE affiliation(
  item_id integer REFERENCES Items,
  instit_id integer REFERENCES Institutions,
  PRIMARY KEY (item_id, instit_id)
);

-- two possible values: "financement", "soutien institutionnel"
CREATE TABLE localisation_type_relations(
  id SERIAL PRIMARY KEY,
  name text
);

-- "Localisation"
CREATE TABLE Localisations(
  event_id integer REFERENCES Events,
  instit_id integer REFERENCES Institutions,
  loc_type integer references Localisation_Type_relations,
  PRIMARY KEY (event_id, instit_id)
);

-- taxinomie chronologique
CREATE TABLE tax_chrono(
  id SERIAL PRIMARY KEY,
  name text
);

-- taxinomie géographique
CREATE TABLE tax_geo(
  id SERIAL PRIMARY KEY,
  name text
);

-- taxinomie thématique
CREATE TABLE tax_theme(
  id SERIAL PRIMARY KEY,
  name text
);
