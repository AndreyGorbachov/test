CREATE DATABASE jtest;
USE jtest;
CREATE TABLE departments (
	id		INT NOT NULL AUTO_INCREMENT,
	name	VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX `name__UNIQUE` (`name` ASC)
)ENGINE=INNODB;

CREATE TABLE employees (
	id					INT NOT NULL AUTO_INCREMENT,
	name 				VARCHAR(50) NOT NULL,
	dob					DATE,
	salary 				INT,
	department_id		INT,
	PRIMARY KEY (id),
    FOREIGN KEY (department_id) REFERENCES departments(id)
)ENGINE=INNODB;