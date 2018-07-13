-- To populate the database initially in project GestionBilicence
-- UTF-8 encoding

-- with PostgreSQL:
-- \c testdb (to connect to testdb)
-- \i path\to\folder\PopulateDataBase.sql

INSERT INTO students(stud_firstname, stud_lastname) VALUES('Marc','Dupont');
INSERT INTO students(stud_firstname, stud_lastname) VALUES('Jean','Martin');
INSERT INTO students(stud_firstname, stud_lastname) VALUES('Marie','Richard');

INSERT INTO stud_num(id_stud,stud_number) VALUES(1,7510);
INSERT INTO stud_num(id_stud,stud_number) VALUES(2,7511);

INSERT INTO semesters(semester_name) VALUES('L1S1 2017-2018');
INSERT INTO semesters(semester_name) VALUES('L1S2 2016-2017');
INSERT INTO semesters(semester_name) VALUES('L2S1 2015-2016');
INSERT INTO semesters(semester_name) VALUES('L2S2 2016-2017');
INSERT INTO semesters(semester_name) VALUES('APB 2017');

INSERT INTO acad_years(ay_name) VALUES('2017-2018');
INSERT INTO acad_years(ay_name) VALUES('2016-2017');

INSERT INTO subjects(subject_name) VALUES('English');
INSERT INTO subjects(subject_name) VALUES('History');

INSERT INTO exams(name, id_semester) VALUES('Test 1', 1);
INSERT INTO exams(name, id_semester) VALUES('Test 2', 1);
INSERT INTO exams(name, id_semester) VALUES('Test 3', 2);

INSERT INTO marks(id_exam, id_stud, mark) VALUES(1, 1, 10);
INSERT INTO marks(id_exam, id_stud, mark) VALUES(1, 2, 5);
INSERT INTO marks(id_exam, id_stud, mark) VALUES(1, 3, 15);

INSERT INTO marks(id_exam, id_stud, mark) VALUES(2, 1, 7.5);
INSERT INTO marks(id_exam, id_stud, mark) VALUES(2, 2, 8);
INSERT INTO marks(id_exam, id_stud, mark) VALUES(3, 3, 18);
