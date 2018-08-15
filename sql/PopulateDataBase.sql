-- To populate the database initially in project Centenaire
-- Latin1 encoding

-- with PostgreSQL (and OS = Windows):
-- \c bdd_centenaire
-- \i path/to/folder/PopulateDataBase.sql

SET client_encoding = 'Latin1';

-- create types of items
INSERT INTO item_type_relations(name) VALUES('Th�se');
INSERT INTO item_type_relations(name) VALUES('HDR');
INSERT INTO item_type_relations(name) VALUES('M�moire de master');
INSERT INTO item_type_relations(name) VALUES('Article');
INSERT INTO item_type_relations(name) VALUES('Livre');
INSERT INTO item_type_relations(name) VALUES('Num�ro de revue');
INSERT INTO item_type_relations(name) VALUES('Chapitre de livre');
INSERT INTO item_type_relations(name) VALUES('Edition de sources');
INSERT INTO item_type_relations(name) VALUES('Article de presse');
INSERT INTO item_type_relations(name) VALUES('Blog');
INSERT INTO item_type_relations(name) VALUES('MOOC');
INSERT INTO item_type_relations(name) VALUES('Carnet de recherche (en ligne)');
INSERT INTO item_type_relations(name) VALUES('Autre activit� num�rique');
INSERT INTO item_type_relations(name) VALUES('Documentaire (gd public)');

-- create types of events
INSERT INTO event_type_relations(name) VALUES('D�bat grand public');
INSERT INTO event_type_relations(name) VALUES('S�minaire');
INSERT INTO event_type_relations(name) VALUES('Colloque');
INSERT INTO event_type_relations(name) VALUES('Conf�rence grand public');
INSERT INTO event_type_relations(name) VALUES('Intervention radio');
INSERT INTO event_type_relations(name) VALUES('Intervention TV');

-- create types of institutions
INSERT INTO institution_type_relations(name) VALUES('Universit�');
INSERT INTO institution_type_relations(name) VALUES('Laboratoire');
INSERT INTO institution_type_relations(name) VALUES('Mus�e');
INSERT INTO institution_type_relations(name) VALUES('Archive');
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
INSERT INTO tags(name) VALUES('� l''�tranger');

-- create geographical taxonomy
INSERT INTO tax_geo(name) VALUES('100 G�ographie');
INSERT INTO tax_geo(name) VALUES('101 Allemagne');
INSERT INTO tax_geo(name) VALUES('102 Am�rique du Sud');
INSERT INTO tax_geo(name) VALUES('103 Asie');
INSERT INTO tax_geo(name) VALUES('104 Autriche-Hongrie');
INSERT INTO tax_geo(name) VALUES('105 Balkans');
INSERT INTO tax_geo(name) VALUES('� 105a Albanie');
INSERT INTO tax_geo(name) VALUES('� 105b Bulgarie');
INSERT INTO tax_geo(name) VALUES('� 105c Gr�ce');
INSERT INTO tax_geo(name) VALUES('�105d Mac�doine');
INSERT INTO tax_geo(name) VALUES('�105e Mont�n�gro');
INSERT INTO tax_geo(name) VALUES('�105f Roumanie');
INSERT INTO tax_geo(name) VALUES('�105g Serbie');
INSERT INTO tax_geo(name) VALUES('�105f Yougoslavie');
INSERT INTO tax_geo(name) VALUES('106 Belgique');
INSERT INTO tax_geo(name) VALUES('107 Empire allemand');
INSERT INTO tax_geo(name) VALUES('108 Empire belge');
INSERT INTO tax_geo(name) VALUES('109 Empire britannique');
INSERT INTO tax_geo(name) VALUES('� 109a Canada');
INSERT INTO tax_geo(name) VALUES('� 109b Nouvelle Z�lande');
INSERT INTO tax_geo(name) VALUES('� 109c Afrique du Sud');
INSERT INTO tax_geo(name) VALUES('� 109d Australie');
INSERT INTO tax_geo(name) VALUES('� 109e Inde');
INSERT INTO tax_geo(name) VALUES('� 109f Afrique');
INSERT INTO tax_geo(name) VALUES('110 Empire fran�ais');
INSERT INTO tax_geo(name) VALUES('111 Empire Ottoman et Moyen-Orient');
INSERT INTO tax_geo(name) VALUES('� 111a Arm�nie');
INSERT INTO tax_geo(name) VALUES('112 Europe centrale et orientale');
INSERT INTO tax_geo(name) VALUES('� 112a Pologne');
INSERT INTO tax_geo(name) VALUES('� 112b Ukraine');
INSERT INTO tax_geo(name) VALUES('� 112c Tch�coslovaquie');
INSERT INTO tax_geo(name) VALUES('� 112d Hongrie');
INSERT INTO tax_geo(name) VALUES('113 Espagne');
INSERT INTO tax_geo(name) VALUES('114 Etats-Unis');
INSERT INTO tax_geo(name) VALUES('115 France');
INSERT INTO tax_geo(name) VALUES('� 115a France enti�re');
INSERT INTO tax_geo(name) VALUES('� 115b �tude locale');
INSERT INTO tax_geo(name) VALUES('116 Grande Bretagne');
INSERT INTO tax_geo(name) VALUES('117 Irlande');
INSERT INTO tax_geo(name) VALUES('118 Pays scandinaves');
INSERT INTO tax_geo(name) VALUES('119 Pays Bas');
INSERT INTO tax_geo(name) VALUES('120 Luxembourg');
INSERT INTO tax_geo(name) VALUES('121 Portugal');
INSERT INTO tax_geo(name) VALUES('122 Russie');
INSERT INTO tax_geo(name) VALUES('123 Histoire transnationale/comparative (sans sp�cification)');
INSERT INTO tax_geo(name) VALUES('124 Histoire transnationale/comparative + pays concern�s');

-- create chronological taxonomy
INSERT INTO tax_chrono(name) VALUES('200 Chronologie');
INSERT INTO tax_chrono(name) VALUES('201 Avant-1914');
INSERT INTO tax_chrono(name) VALUES('202 1914-1918');
INSERT INTO tax_chrono(name) VALUES('203 1914');
INSERT INTO tax_chrono(name) VALUES('204 1915');
INSERT INTO tax_chrono(name) VALUES('205 1916');
INSERT INTO tax_chrono(name) VALUES('206 1917');
INSERT INTO tax_chrono(name) VALUES('207 1918');
INSERT INTO tax_chrono(name) VALUES('208 Apr�s-1918');

-- create thematic taxonomy
INSERT INTO tax_theme(name) VALUES('300 Th�matique');
INSERT INTO tax_theme(name) VALUES('301 Outils et bibliographies');
INSERT INTO tax_theme(name) VALUES('302 G�n�ral');
INSERT INTO tax_theme(name) VALUES('303 Avant-Guerre');
INSERT INTO tax_theme(name) VALUES('� 303a p�riode d�avant-guerre');
INSERT INTO tax_theme(name) VALUES('� 303b origines de la guerre');
INSERT INTO tax_theme(name) VALUES('304 Op�rations');
INSERT INTO tax_theme(name) VALUES('� 304a Aviation');
INSERT INTO tax_theme(name) VALUES('� 304b Guerre maritime');
INSERT INTO tax_theme(name) VALUES('� 304c G�n�ral');
INSERT INTO tax_theme(name) VALUES('� 304d Front de l�Ouest');
INSERT INTO tax_theme(name) VALUES('� 304e Combats en Afrique');
INSERT INTO tax_theme(name) VALUES('� 304f Combats dans les Balkans');
INSERT INTO tax_theme(name) VALUES('� 304g Combats Moyen-Orient');
INSERT INTO tax_theme(name) VALUES('� 304h Front Austro-Italien');
INSERT INTO tax_theme(name) VALUES('� 304i Front oriental');
INSERT INTO tax_theme(name) VALUES('305 Les combattants et le combat');
INSERT INTO tax_theme(name) VALUES('� 305a Exp�rience de guerre');
INSERT INTO tax_theme(name) VALUES('� 305b Animaux');
INSERT INTO tax_theme(name) VALUES('� 305c Impact sur l�environnement/paysage');
INSERT INTO tax_theme(name) VALUES('� 305d Religion');
INSERT INTO tax_theme(name) VALUES('� 305e Organisation militaire');
INSERT INTO tax_theme(name) VALUES('� 305f Refus et contestation');
INSERT INTO tax_theme(name) VALUES('� 305g Troupes coloniales');
INSERT INTO tax_theme(name) VALUES('� 305h Prisonniers de guerre');
INSERT INTO tax_theme(name) VALUES('� 305i M�moires publi�es et biographies');
INSERT INTO tax_theme(name) VALUES('306 Genre, Familles');
INSERT INTO tax_theme(name) VALUES('� 306a Enfants');
INSERT INTO tax_theme(name) VALUES('� 306b Masculinit�');
INSERT INTO tax_theme(name) VALUES('� 306c Femmes');
INSERT INTO tax_theme(name) VALUES('� 306d le couple');
INSERT INTO tax_theme(name) VALUES('307 Soci�t�s en guerre');
INSERT INTO tax_theme(name) VALUES('� 307a Travail');
INSERT INTO tax_theme(name) VALUES('� 307b Occupations');
INSERT INTO tax_theme(name) VALUES('� 307c Mobilisation de l''Empire');
INSERT INTO tax_theme(name) VALUES('� 307d Religion');
INSERT INTO tax_theme(name) VALUES('� 307e Violences contre les civils');
INSERT INTO tax_theme(name) VALUES('� 307f Vie � l�arri�re');
INSERT INTO tax_theme(name) VALUES('308 �conomie');
INSERT INTO tax_theme(name) VALUES('309 Culture(s)');
INSERT INTO tax_theme(name) VALUES('� 309a Arts visuels et Arts de la Sc�ne');
INSERT INTO tax_theme(name) VALUES('� 309b Musique');
INSERT INTO tax_theme(name) VALUES('� 309c Presse et presse illustr�e');
INSERT INTO tax_theme(name) VALUES('� 309d Litt�rature');
INSERT INTO tax_theme(name) VALUES('� 309e Intellectuels et la guerre');
INSERT INTO tax_theme(name) VALUES('310 Droit');
INSERT INTO tax_theme(name) VALUES('311 Politique');
INSERT INTO tax_theme(name) VALUES('� 311a Relations internationales pdt guerre');
INSERT INTO tax_theme(name) VALUES('� 311b Biographie/Prosopographie');
INSERT INTO tax_theme(name) VALUES('� 311c Politique int�rieure');
INSERT INTO tax_theme(name) VALUES('� 311d Pacifisme');
INSERT INTO tax_theme(name) VALUES('� 311e Trait�s de paix');
INSERT INTO tax_theme(name) VALUES('312 Sciences, technologies et m�decine');
INSERT INTO tax_theme(name) VALUES('� 312a M�decine');
INSERT INTO tax_theme(name) VALUES('� 312b Sciences et T�chnologies');
INSERT INTO tax_theme(name) VALUES('313 Sorties de guerre');
INSERT INTO tax_theme(name) VALUES('314 M�moire et traces');
INSERT INTO tax_theme(name) VALUES('� 314a M�moires et comm�morations');
INSERT INTO tax_theme(name) VALUES('� 314b Environnement/Paysage');
INSERT INTO tax_theme(name) VALUES('� 314c Arch�ologie');

-- populate departements
INSERT INTO departements(name) VALUES('01 - Ain');
INSERT INTO departements(name) VALUES('02 - Aisne');
INSERT INTO departements(name) VALUES('03 - Allier');
INSERT INTO departements(name) VALUES('05 - Hautes-Alpes');
INSERT INTO departements(name) VALUES('04 - Alpes-de-Haute-Provence');
INSERT INTO departements(name) VALUES('06 - Alpes-Maritimes');
INSERT INTO departements(name) VALUES('07 - Ard�che');
INSERT INTO departements(name) VALUES('08 - Ardennes');
INSERT INTO departements(name) VALUES('09 - Ari�ge');
INSERT INTO departements(name) VALUES('10 - Aube');
INSERT INTO departements(name) VALUES('11 - Aude');
INSERT INTO departements(name) VALUES('12 - Aveyron');
INSERT INTO departements(name) VALUES('13 - Bouches-du-Rh�ne');
INSERT INTO departements(name) VALUES('14 - Calvados');
INSERT INTO departements(name) VALUES('15 - Cantal');
INSERT INTO departements(name) VALUES('16 - Charente');
INSERT INTO departements(name) VALUES('17 - Charente-Maritime');
INSERT INTO departements(name) VALUES('18 - Cher');
INSERT INTO departements(name) VALUES('19 - Corr�ze');
INSERT INTO departements(name) VALUES('2a - Corse-du-sud');
INSERT INTO departements(name) VALUES('2b - Haute-corse');
INSERT INTO departements(name) VALUES('21 - C�te-d''or');
INSERT INTO departements(name) VALUES('22 - C�tes-d''armor');
INSERT INTO departements(name) VALUES('23 - Creuse');
INSERT INTO departements(name) VALUES('24 - Dordogne');
INSERT INTO departements(name) VALUES('25 - Doubs');
INSERT INTO departements(name) VALUES('26 - Dr�me');
INSERT INTO departements(name) VALUES('27 - Eure');
INSERT INTO departements(name) VALUES('28 - Eure-et-Loir');
INSERT INTO departements(name) VALUES('29 - Finist�re');
INSERT INTO departements(name) VALUES('30 - Gard');
INSERT INTO departements(name) VALUES('31 - Haute-Garonne');
INSERT INTO departements(name) VALUES('32 - Gers');
INSERT INTO departements(name) VALUES('33 - Gironde');
INSERT INTO departements(name) VALUES('34 - H�rault');
INSERT INTO departements(name) VALUES('35 - Ile-et-Vilaine');
INSERT INTO departements(name) VALUES('36 - Indre');
INSERT INTO departements(name) VALUES('37 - Indre-et-Loire');
INSERT INTO departements(name) VALUES('38 - Is�re');
INSERT INTO departements(name) VALUES('39 - Jura');
INSERT INTO departements(name) VALUES('40 - Landes');
INSERT INTO departements(name) VALUES('41 - Loir-et-Cher');
INSERT INTO departements(name) VALUES('42 - Loire');
INSERT INTO departements(name) VALUES('43 - Haute-Loire');
INSERT INTO departements(name) VALUES('44 - Loire-Atlantique');
INSERT INTO departements(name) VALUES('45 - Loiret');
INSERT INTO departements(name) VALUES('46 - Lot');
INSERT INTO departements(name) VALUES('47 - Lot-et-Garonne');
INSERT INTO departements(name) VALUES('48 - Loz�re');
INSERT INTO departements(name) VALUES('49 - Maine-et-Loire');
INSERT INTO departements(name) VALUES('50 - Manche');
INSERT INTO departements(name) VALUES('51 - Marne');
INSERT INTO departements(name) VALUES('52 - Haute-Marne');
INSERT INTO departements(name) VALUES('53 - Mayenne');
INSERT INTO departements(name) VALUES('54 - Meurthe-et-Moselle');
INSERT INTO departements(name) VALUES('55 - Meuse');
INSERT INTO departements(name) VALUES('56 - Morbihan');
INSERT INTO departements(name) VALUES('57 - Moselle');
INSERT INTO departements(name) VALUES('58 - Ni�vre');
INSERT INTO departements(name) VALUES('59 - Nord');
INSERT INTO departements(name) VALUES('60 - Oise');
INSERT INTO departements(name) VALUES('61 - Orne');
INSERT INTO departements(name) VALUES('62 - Pas-de-Calais');
INSERT INTO departements(name) VALUES('63 - Puy-de-D�me');
INSERT INTO departements(name) VALUES('64 - Pyr�n�es-Atlantiques');
INSERT INTO departements(name) VALUES('65 - Hautes-Pyr�n�es');
INSERT INTO departements(name) VALUES('66 - Pyr�n�es-Orientales');
INSERT INTO departements(name) VALUES('67 - Bas-Rhin');
INSERT INTO departements(name) VALUES('68 - Haut-Rhin');
INSERT INTO departements(name) VALUES('69 - Rh�ne');
INSERT INTO departements(name) VALUES('70 - Haute-Sa�ne');
INSERT INTO departements(name) VALUES('71 - Sa�ne-et-Loire');
INSERT INTO departements(name) VALUES('72 - Sarthe');
INSERT INTO departements(name) VALUES('73 - Savoie');
INSERT INTO departements(name) VALUES('74 - Haute-Savoie');
INSERT INTO departements(name) VALUES('75 - Paris');
INSERT INTO departements(name) VALUES('76 - Seine-Maritime');
INSERT INTO departements(name) VALUES('77 - Seine-et-Marne');
INSERT INTO departements(name) VALUES('78 - Yvelines');
INSERT INTO departements(name) VALUES('79 - Deux-S�vres');
INSERT INTO departements(name) VALUES('80 - Somme');
INSERT INTO departements(name) VALUES('81 - Tarn');
INSERT INTO departements(name) VALUES('82 - Tarn-et-Garonne');
INSERT INTO departements(name) VALUES('83 - Var');
INSERT INTO departements(name) VALUES('84 - Vaucluse');
INSERT INTO departements(name) VALUES('85 - Vend�e');
INSERT INTO departements(name) VALUES('86 - Vienne');
INSERT INTO departements(name) VALUES('87 - Haute-Vienne');
INSERT INTO departements(name) VALUES('88 - Vosges');
INSERT INTO departements(name) VALUES('89 - Yonne');
INSERT INTO departements(name) VALUES('90 - Territoire de Belfort');
INSERT INTO departements(name) VALUES('91 - Essonne');
INSERT INTO departements(name) VALUES('92 - Hauts-de-Seine');
INSERT INTO departements(name) VALUES('93 - Seine-Saint-Denis');
INSERT INTO departements(name) VALUES('94 - Val-de-Marne');
INSERT INTO departements(name) VALUES('95 - Val-d''oise');
INSERT INTO departements(name) VALUES('99 - Etranger');

-- populate countries
INSERT INTO countries(name) VALUES('France');
INSERT INTO countries(name) VALUES('Belgique');

-- create default institution
INSERT INTO institutions(name, place, id_dept, id_country, type) VALUES('Inconnu', '-', 1, 1, 2);
