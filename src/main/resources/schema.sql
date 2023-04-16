
CREATE TABLE IF NOT EXISTS  groups( id SERIAL NOT NULL,
name VARCHAR (20),
PRIMARY KEY(id))  ;

CREATE TABLE IF NOT EXISTS students( id SERIAL NOT NULL,
   group_id   INT  NULL DEFAULT NULL,
   first_name VARCHAR (20) NOT NULL,
   last_name VARCHAR (20)  NOT NULL,
   PRIMARY KEY(id),
   CONSTRAINT fk_students  FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE SET NULL);

CREATE TABLE IF NOT EXISTS  courses(id SERIAL  PRIMARY KEY,
 name VARCHAR (40) NOT NULL,
 description TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS
students_courses (student_id INT NOT NULL,
 course_id INT NOT NULL ,
 PRIMARY KEY(student_id,course_id),
 FOREIGN KEY (student_id) REFERENCES students (id)ON DELETE CASCADE ,
 FOREIGN KEY (course_id) REFERENCES courses (id) );