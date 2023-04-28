
CREATE TABLE IF NOT EXISTS  groups( id INT NOT NULL,
name VARCHAR (20),
PRIMARY KEY(id))  ;

CREATE TABLE IF NOT EXISTS students(id INT NOT NULL,
   group_id   INT NULL,
   first_name VARCHAR (20) NOT NULL,
   last_name VARCHAR (20)  NOT NULL,
   PRIMARY KEY(id),
   CONSTRAINT fk_students  FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE SET NULL);

CREATE TABLE IF NOT EXISTS  courses(id INT  PRIMARY KEY,
 name VARCHAR (40) NOT NULL,
 description TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS
students_courses (student_id INT NOT NULL,
 course_id INT NOT NULL ,
 PRIMARY KEY(student_id,course_id),
 FOREIGN KEY (student_id) REFERENCES students (id)ON DELETE CASCADE ,
 FOREIGN KEY (course_id) REFERENCES courses (id) );

 CREATE SEQUENCE IF NOT EXISTS courses_seq INCREMENT 10 OWNED BY courses.id;
 CREATE SEQUENCE IF NOT EXISTS groups_seq INCREMENT 10 OWNED BY groups.id;
 CREATE SEQUENCE IF NOT EXISTS students_seq INCREMENT 200 OWNED BY students.id;

