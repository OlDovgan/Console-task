DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students( studentId SERIAL  PRIMARY KEY ,
   groupId   INT NULL,
   firstName VARCHAR (20) NOT NULL,
   lastName VARCHAR (20)  NOT NULL);
DROP TABLE IF  EXISTS groups;
CREATE TABLE groups( groupId SERIAL  PRIMARY KEY , groupName VARCHAR (20) NOT NULL);
DROP TABLE IF  EXISTS courses;
CREATE TABLE courses(courseId SERIAL  PRIMARY KEY ,
 courseName VARCHAR (40) NOT NULL,
 courseDescription TEXT NOT NULL);
DROP TABLE IF  EXISTS students_courses;
CREATE TABLE studentsCourses (studentId INT NOT NULL REFERENCES students (studentId) ON DELETE CASCADE,
 courseId INT );