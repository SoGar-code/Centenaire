-- To populate the database initially in project Centenaire
-- Latin1 encoding

-- with PostgreSQL (and OS = Windows):
-- \c bdd_centenaire
-- \i path/to/folder/PopulateDataBase.sql

SET client_encoding = 'Latin1';

-- create types of items
INSERT INTO item_type_relations(name) VALUES('Thèse');
INSERT INTO item_type_relations(name) VALUES('HDR');
INSERT INTO item_type_relations(name) VALUES('Mémoire de master');
INSERT INTO item_type_relations(name) VALUES('Article');
INSERT INTO item_type_relations(name) VALUES('Livre');
INSERT INTO item_type_relations(name) VALUES('Numéro de revue');
INSERT INTO item_type_relations(name) VALUES('Chapitre de livre');
INSERT INTO item_type_relations(name) VALUES('Edition de sources');
INSERT INTO item_type_relations(name) VALUES('Article de presse');

-- create types of events
INSERT INTO event_type_relations(name) VALUES('Débat grand public');
INSERT INTO event_type_relations(name) VALUES('Séminaire');
INSERT INTO event_type_relations(name) VALUES('Colloque');
INSERT INTO event_type_relations(name) VALUES('Conférence grand public');
INSERT INTO event_type_relations(name) VALUES('Intervention radio');
INSERT INTO event_type_relations(name) VALUES('Intervention TV');

-- create types of institutions
INSERT INTO institution_type_relations(name) VALUES('Université');
INSERT INTO institution_type_relations(name) VALUES('Laboratoire');
INSERT INTO institution_type_relations(name) VALUES('Association');
INSERT INTO institution_type_relations(name) VALUES('Organisation non-scientifique');

-- create institutional status
INSERT INTO institutional_status(name) VALUES('Collaboration');
INSERT INTO institutional_status(name) VALUES('MCF');
INSERT INTO institutional_status(name) VALUES('Professeur');
INSERT INTO institutional_status(name) VALUES('Post-doctorant');
INSERT INTO institutional_status(name) VALUES('Doctorant');
INSERT INTO institutional_status(name) VALUES('ATER');

-- create localisation_type_relations
INSERT INTO localisation_type_relations(name) VALUES('Financement');
INSERT INTO localisation_type_relations(name) VALUES('Soutien institutionnel');