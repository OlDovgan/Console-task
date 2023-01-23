
CREATE TABLE IF NOT EXISTS students( student_id SERIAL  PRIMARY KEY ,
   group_id   INT NULL,
   first_name VARCHAR (20) NOT NULL,
   last_name VARCHAR (20)  NOT NULL);

CREATE TABLE IF NOT EXISTS  groups( group_id SERIAL  PRIMARY KEY , group_name VARCHAR (20) NOT NULL );

CREATE TABLE IF NOT EXISTS  courses(course_id SERIAL  PRIMARY KEY ,
 course_name VARCHAR (40) NOT NULL,
 course_description TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS students_courses (student_id INT NOT NULL REFERENCES students (student_id) ON DELETE CASCADE,
 course_id INT );