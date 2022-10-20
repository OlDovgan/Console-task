INSERT INTO students ( group_id,first_name, last_name) VALUES
(1,'Alex','Fix'),
(2,'Alex2','Fix2'),
(3,'Alex3','Fix3');
INSERT INTO students (first_name, last_name) VALUES ('Alex4','Fix4');
INSERT INTO courses (course_name, course_description) VALUES
('Maths','Default'),
('English','Default'),
('History','Default'),
('Probability theory','Default');
INSERT INTO students_courses (student_id, course_id) VALUES
(1,1),
(2,1),
(1,3),
(4,4);
INSERT INTO groups (group_name) VALUES
('GH-01'),
('RE-08'),
('HM-45'),
('sE-65');