DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students( student_id SERIAL  PRIMARY KEY ,
   group_id   INT NULL,
   first_name VARCHAR (20) NOT NULL,
   last_name VARCHAR (20)  NOT NULL);
DROP TABLE IF  EXISTS groups;
CREATE TABLE groups( group_id SERIAL  PRIMARY KEY , group_name VARCHAR (20) NOT NULL);
DROP TABLE IF  EXISTS courses;
CREATE TABLE courses(course_id SERIAL  PRIMARY KEY ,
 course_name VARCHAR (40) NOT NULL,
 course_description TEXT NOT NULL);
DROP TABLE IF  EXISTS students_courses;
CREATE TABLE students_courses (student_id INT NOT NULL REFERENCES students (student_id) ON DELETE CASCADE,
 course_id INT );