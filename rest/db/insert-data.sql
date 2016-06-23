USE jtest;

INSERT INTO departments (name) VALUES ("Administration");
INSERT INTO departments (name) VALUES ("Programmers");
INSERT INTO departments (name) VALUES ("Accounting");

INSERT INTO employees (name, dob, salary, department_id) VALUES ("Alex", '1990/01/01', 900, 1);
INSERT INTO employees (name, dob, salary, department_id) VALUES ("Barry", '1994/06/22', 850, 1);
INSERT INTO employees (name, dob, salary, department_id) VALUES ("Den", '1993/05/19', 1000, 1);

INSERT INTO employees (name, dob, salary, department_id) VALUES ("Hari", '1992/02/07', 1200, 2);
INSERT INTO employees (name, dob, salary, department_id) VALUES ("Martin", '1994/10/27', 970, 2);
INSERT INTO employees (name, dob, salary, department_id) VALUES ("Mark", '1991/11/14', 1160, 2);

INSERT INTO employees (name, dob, salary, department_id) VALUES ("Nick", '1991/08/06', 540, 3);
INSERT INTO employees (name, dob, salary, department_id) VALUES ("Rob", '1993/01/10', 820, 3);
INSERT INTO employees (name, dob, salary, department_id) VALUES ("Tim", '1991/07/12', 680, 3);