-- Define database for project GestionBilicence
-- UTF-8 encoding

-- with PostgreSQL:
-- create database testdb;
-- \c testdb (to connect to testdb)
-- \i path\to\folder\CreateDataBase.sql

------------------------------
-- part 1, minimum working model
------------------------------

create table students(
	id_stud serial primary key
	, stud_firstname text
	, stud_lastname text
    	);

-- semesters, including academic year, like 'L1S1 2017-2018', 'L2S3 2016-2017', 'APB', ...
create table semesters(
	id_semester serial primary key
	, semester_name text
    	);

create table exams(
	id_exam serial primary key
	, name text
	, id_semester integer references semesters
	, coefficient integer default 1
    	);

create table marks(
	id_mark serial primary key
	, id_exam integer references exams
	, id_stud integer references students
	, mark integer
	);

------------------------------
-- part 2, additional info
------------------------------

create table stud_num(
	id_stud_num serial primary key
	, id_stud integer references students
	, stud_number integer
	);

-- subjects like English, History, ...
create table subjects(
	id_subject serial primary key
	, subject_name text
	);

create table exam_subject(
	id_e_subject serial primary key
	, id_exam integer references exams
	, id_subject integer references subjects
	);

-- academic years like 2017-2018, 2015-2016, ...
create table acad_years(
	id_ay serial primary key
	, ay_name text
    	);

create table semester_ay(
	id_semester_ay serial primary key
	, id_semester integer references semesters
	, id_ay integer references acad_years
	);
