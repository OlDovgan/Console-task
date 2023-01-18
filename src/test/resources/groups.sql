DROP TABLE IF  EXISTS groups;
CREATE TABLE groups( group_id SERIAL  PRIMARY KEY , group_name VARCHAR (20) NOT NULL);

INSERT INTO groups (group_name) VALUES ('Ui-01');
INSERT INTO groups (group_name) VALUES ('uI-02');
INSERT INTO groups (group_name) VALUES ('UI-03');