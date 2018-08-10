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
INSERT INTO item_type_relations(name) VALUES('Blog');
INSERT INTO item_type_relations(name) VALUES('MOOC');
INSERT INTO item_type_relations(name) VALUES('Carnet de recherche (en ligne)');
INSERT INTO item_type_relations(name) VALUES('Autre activité numérique');
INSERT INTO item_type_relations(name) VALUES('Documentaire (gd public)');

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

-- create default tags (needed for certain questions)
INSERT INTO tags(name) VALUES('En France');
INSERT INTO tags(name) VALUES('À l''étranger');

-- create geographical taxonomy
INSERT INTO tax_geo(name) VALUES('100 Géographie');
INSERT INTO tax_geo(name) VALUES('101 Allemagne');
INSERT INTO tax_geo(name) VALUES('102 Amérique du Sud');
INSERT INTO tax_geo(name) VALUES('103 Asie');
INSERT INTO tax_geo(name) VALUES('104 Autriche-Hongrie');
INSERT INTO tax_geo(name) VALUES('105 Balkans');
INSERT INTO tax_geo(name) VALUES('– 105a Albanie');
INSERT INTO tax_geo(name) VALUES('– 105b Bulgarie');
INSERT INTO tax_geo(name) VALUES('– 105c Grèce');
INSERT INTO tax_geo(name) VALUES('–105d Macédoine');
INSERT INTO tax_geo(name) VALUES('–105e Monténégro');
INSERT INTO tax_geo(name) VALUES('–105f Roumanie');
INSERT INTO tax_geo(name) VALUES('–105g Serbie');
INSERT INTO tax_geo(name) VALUES('–105f Yougoslavie');
INSERT INTO tax_geo(name) VALUES('106 Belgique');
INSERT INTO tax_geo(name) VALUES('107 Empire allemand');
INSERT INTO tax_geo(name) VALUES('108 Empire belge');
INSERT INTO tax_geo(name) VALUES('109 Empire britannique');
INSERT INTO tax_geo(name) VALUES('– 109a Canada');
INSERT INTO tax_geo(name) VALUES('– 109b Nouvelle Zélande');
INSERT INTO tax_geo(name) VALUES('– 109c Afrique du Sud');
INSERT INTO tax_geo(name) VALUES('– 109d Australie');
INSERT INTO tax_geo(name) VALUES('– 109e Inde');
INSERT INTO tax_geo(name) VALUES('– 109f Afrique');
INSERT INTO tax_geo(name) VALUES('110 Empire français');
INSERT INTO tax_geo(name) VALUES('111 Empire Ottoman et Moyen-Orient');
INSERT INTO tax_geo(name) VALUES('– 111a Arménie');
INSERT INTO tax_geo(name) VALUES('112 Europe centrale et orientale');
INSERT INTO tax_geo(name) VALUES('– 112a Pologne');
INSERT INTO tax_geo(name) VALUES('– 112b Ukraine');
INSERT INTO tax_geo(name) VALUES('– 112c Tchécoslovaquie');
INSERT INTO tax_geo(name) VALUES('– 112d Hongrie');
INSERT INTO tax_geo(name) VALUES('113 Espagne');
INSERT INTO tax_geo(name) VALUES('114 Etats-Unis');
INSERT INTO tax_geo(name) VALUES('115 France');
INSERT INTO tax_geo(name) VALUES('– 115a France entière');
INSERT INTO tax_geo(name) VALUES('– 115b Étude locale');
INSERT INTO tax_geo(name) VALUES('116 Grande Bretagne');
INSERT INTO tax_geo(name) VALUES('117 Irlande');
INSERT INTO tax_geo(name) VALUES('118 Pays scandinaves');
INSERT INTO tax_geo(name) VALUES('119 Pays Bas');
INSERT INTO tax_geo(name) VALUES('120 Luxembourg');
INSERT INTO tax_geo(name) VALUES('121 Portugal');
INSERT INTO tax_geo(name) VALUES('122 Russie');
INSERT INTO tax_geo(name) VALUES('123 Histoire transnationale/comparative (sans spécification)');
INSERT INTO tax_geo(name) VALUES('124 Histoire transnationale/comparative + pays concernés');

-- create chronological taxonomy
INSERT INTO tax_chrono(name) VALUES('200 Chronologie');
INSERT INTO tax_chrono(name) VALUES('201 Avant-1914');
INSERT INTO tax_chrono(name) VALUES('202 1914-1918');
INSERT INTO tax_chrono(name) VALUES('203 1914');
INSERT INTO tax_chrono(name) VALUES('204 1915');
INSERT INTO tax_chrono(name) VALUES('205 1916');
INSERT INTO tax_chrono(name) VALUES('206 1917');
INSERT INTO tax_chrono(name) VALUES('207 1918');
INSERT INTO tax_chrono(name) VALUES('208 Après-1918');

-- create thematic taxonomy
INSERT INTO tax_theme(name) VALUES('300 Thématique');
INSERT INTO tax_theme(name) VALUES('301 Outils et bibliographies');
INSERT INTO tax_theme(name) VALUES('302 Général');
INSERT INTO tax_theme(name) VALUES('303 Avant-Guerre');
INSERT INTO tax_theme(name) VALUES('– 303a période d’avant-guerre');
INSERT INTO tax_theme(name) VALUES('– 303b origines de la guerre');
INSERT INTO tax_theme(name) VALUES('304 Opérations');
INSERT INTO tax_theme(name) VALUES('– 304a Aviation');
INSERT INTO tax_theme(name) VALUES('– 304b Guerre maritime');
INSERT INTO tax_theme(name) VALUES('– 304c Général');
INSERT INTO tax_theme(name) VALUES('– 304d Front de l’Ouest');
INSERT INTO tax_theme(name) VALUES('– 304e Combats en Afrique');
INSERT INTO tax_theme(name) VALUES('– 304f Combats dans les Balkans');
INSERT INTO tax_theme(name) VALUES('– 304g Combats Moyen-Orient');
INSERT INTO tax_theme(name) VALUES('– 304h Front Austro-Italien');
INSERT INTO tax_theme(name) VALUES('– 304i Front oriental');
INSERT INTO tax_theme(name) VALUES('305 Les combattants et le combat');
INSERT INTO tax_theme(name) VALUES('– 305a Expérience de guerre');
INSERT INTO tax_theme(name) VALUES('– 305b Animaux');
INSERT INTO tax_theme(name) VALUES('– 305c Impact sur l’environnement/paysage');
INSERT INTO tax_theme(name) VALUES('– 305d Religion');
INSERT INTO tax_theme(name) VALUES('– 305e Organisation militaire');
INSERT INTO tax_theme(name) VALUES('– 305f Refus et contestation');
INSERT INTO tax_theme(name) VALUES('– 305g Troupes coloniales');
INSERT INTO tax_theme(name) VALUES('– 305h Prisonniers de guerre');
INSERT INTO tax_theme(name) VALUES('– 305i Mémoires publiées et biographies');
INSERT INTO tax_theme(name) VALUES('306 Genre, Familles');
INSERT INTO tax_theme(name) VALUES('– 306a Enfants');
INSERT INTO tax_theme(name) VALUES('– 306b Masculinité');
INSERT INTO tax_theme(name) VALUES('– 306c Femmes');
INSERT INTO tax_theme(name) VALUES('– 306d le couple');
INSERT INTO tax_theme(name) VALUES('307 Sociétés en guerre');
INSERT INTO tax_theme(name) VALUES('– 307a Travail');
INSERT INTO tax_theme(name) VALUES('– 307b Occupations');
INSERT INTO tax_theme(name) VALUES('– 307c Mobilisation de l''Empire');
INSERT INTO tax_theme(name) VALUES('– 307d Religion');
INSERT INTO tax_theme(name) VALUES('– 307e Violences contre les civils');
INSERT INTO tax_theme(name) VALUES('– 307f Vie à l’arrière');
INSERT INTO tax_theme(name) VALUES('308 Économie');
INSERT INTO tax_theme(name) VALUES('309 Culture(s)');
INSERT INTO tax_theme(name) VALUES('– 309a Arts visuels et Arts de la Scène');
INSERT INTO tax_theme(name) VALUES('– 309b Musique');
INSERT INTO tax_theme(name) VALUES('– 309c Presse et presse illustrée');
INSERT INTO tax_theme(name) VALUES('– 309d Littérature');
INSERT INTO tax_theme(name) VALUES('– 309e Intellectuels et la guerre');
INSERT INTO tax_theme(name) VALUES('310 Droit');
INSERT INTO tax_theme(name) VALUES('311 Politique');
INSERT INTO tax_theme(name) VALUES('– 311a Relations internationales pdt guerre');
INSERT INTO tax_theme(name) VALUES('– 311b Biographie/Prosopographie');
INSERT INTO tax_theme(name) VALUES('– 311c Politique intérieure');
INSERT INTO tax_theme(name) VALUES('– 311d Pacifisme');
INSERT INTO tax_theme(name) VALUES('– 311e Traités de paix');
INSERT INTO tax_theme(name) VALUES('312 Sciences, technologies et médecine');
INSERT INTO tax_theme(name) VALUES('– 312a Médecine');
INSERT INTO tax_theme(name) VALUES('– 312b Sciences et Téchnologies');
INSERT INTO tax_theme(name) VALUES('313 Sorties de guerre');
INSERT INTO tax_theme(name) VALUES('314 Mémoire et traces');
INSERT INTO tax_theme(name) VALUES('– 314a Mémoires et commémorations');
INSERT INTO tax_theme(name) VALUES('– 314b Environnement/Paysage');
INSERT INTO tax_theme(name) VALUES('– 314c Archéologie');

-- create default institution
INSERT INTO institutions(name, place, type) VALUES('Inconnu', '-', 2);
