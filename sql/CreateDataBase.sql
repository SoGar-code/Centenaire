﻿-- PostgreSQL database dump
-- to create the database in project Centenaire
-- Latin1 encoding

-- with PostgreSQL (and OS = Windows):
-- CREATE DATABASE bdd_centenaire;
-- \c bdd_centenaire_test
-- \i path/to/folder/CreateDataBase.sql


SET client_encoding = 'UTF-8';

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
  nb_stud_0 int,
  nb_stud_1 int,
  nb_stud_2 int,
  nb_stud_3 int,
  nb_stud_4 int,
  nb_stud_5 int,
  question_instit_non_sci text,
  question_soc_med_expectation text,
  question_twitter_evolution text,
  twitter_account boolean,
  facebook_account boolean,
  twitter_start_year int,
  facebook_start_year int,
  tweets_per_week real,
  successful_tweet text,
  question_concern text,
  question_comittee text,
  question_contribution text,
  question_dev text
);

-- types de productions
CREATE TABLE item_type_relations(
  id SERIAL PRIMARY KEY,
  name text,
  category integer
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
  name text,
  category integer
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

CREATE TABLE sci_author(
  indiv_id integer REFERENCES Individuals, --FK
  item_id integer REFERENCES Items, --FK
  PRIMARY KEY (indiv_id, item_id)
);

CREATE TABLE outreach_author(
  indiv_id integer REFERENCES Individuals, --FK
  item_id integer REFERENCES Items, --FK
  PRIMARY KEY (indiv_id, item_id)
);

CREATE TABLE dig_author(
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

CREATE TABLE sci_participant(
  event_id integer REFERENCES Events,
  indiv_id integer REFERENCES Individuals,
  PRIMARY KEY (event_id, indiv_id)
);

CREATE TABLE outreach_participant_g(
  event_id integer REFERENCES Events,
  indiv_id integer REFERENCES Individuals,
  PRIMARY KEY (event_id, indiv_id)
);

CREATE TABLE outreach_participant_conf(
  event_id integer REFERENCES Events,
  indiv_id integer REFERENCES Individuals,
  PRIMARY KEY (event_id, indiv_id)
);

CREATE TABLE affiliation(
  item_id integer REFERENCES Items,
  instit_id integer REFERENCES Institutions,
  PRIMARY KEY (item_id, instit_id)
);

-- expertise productions
CREATE TABLE expert_item(
  indiv_id integer REFERENCES Individuals,
  item_id integer REFERENCES Items,
  PRIMARY KEY (indiv_id, item_id)
);

-- expertise événements
CREATE TABLE expert_event(
  indiv_id integer REFERENCES Individuals,
  event_id integer REFERENCES Events,
  PRIMARY KEY (indiv_id, event_id)
);

-- expertise institution
CREATE TABLE expert_institutions(
  indiv_id integer REFERENCES Individuals,
  instit_id integer REFERENCES Institutions,
  PRIMARY KEY (indiv_id, instit_id)
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

-- Item chronological taxonomy relation
CREATE TABLE item_tax_chrono_relations(
  item_id integer REFERENCES Items,
  tax_chrono_id integer REFERENCES tax_chrono,
  PRIMARY KEY (item_id, tax_chrono_id)
);

-- Item geographical taxonomy relation
CREATE TABLE item_tax_geo_relations(
  item_id integer REFERENCES Items,
  tax_geo_id integer REFERENCES tax_geo,
  PRIMARY KEY (item_id, tax_geo_id)
);

-- Item thematic taxonomy relation
CREATE TABLE item_tax_theme_relations(
  item_id integer REFERENCES Items,
  tax_theme_id integer REFERENCES tax_theme,
  PRIMARY KEY (item_id, tax_theme_id)
);

-- Event chronological taxonomy relation
CREATE TABLE event_tax_chrono_relations(
  event_id integer REFERENCES Events,
  tax_chrono_id integer REFERENCES tax_chrono,
  PRIMARY KEY (event_id, tax_chrono_id)
);

-- Event geographical taxonomy relation
CREATE TABLE event_tax_geo_relations(
  event_id integer REFERENCES Events,
  tax_geo_id integer REFERENCES tax_theme,
  PRIMARY KEY (event_id, tax_geo_id)
);

-- Event thematic taxonomy relation
CREATE TABLE event_tax_theme_relations(
  event_id integer REFERENCES Events,
  tax_theme_id integer REFERENCES tax_theme,
  PRIMARY KEY (event_id, tax_theme_id)
);
