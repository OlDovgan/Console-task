DROP TABLE IF  EXISTS courses;
CREATE TABLE courses(course_id SERIAL  PRIMARY KEY ,
 course_name VARCHAR (40) NOT NULL,
 course_description TEXT NOT NULL);
INSERT INTO courses (course_name, course_description) VALUES ('Java', 'Java');
INSERT INTO courses (course_name, course_description) VALUES ('Pascal', 'Pascal');
INSERT INTO courses (course_name, course_description) VALUES ('PHP', 'PHP');