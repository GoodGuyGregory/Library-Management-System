-- From the MySQL prompt,execute this file,e.g.
-- SYNTAX:
--     mysql> SOURCE <filename>
-- EXAMPLE:
--     mysql> SOURCE create-company-for-MySQL.sql



-- DROP SCHEMA IF EXISTS Company
CREATE SCHEMA Company9;
USE Company9;

DROP TABLE IF EXISTS DEPARTMENT;
CREATE TABLE DEPARTMENT (
  dnumber    INT      NOT NULL,
  dname      VARCHAR(25) NOT NULL,
  mgrssn     CHAR(9)    NOT NULL,
  mgrstartdate DATE,
  CONSTRAINT pk_Department PRIMARY KEY (dnumber),
  CONSTRAINT uk_dname UNIQUE (dname) 
);

DROP TABLE IF EXISTS EMPLOYEE;
CREATE TABLE EMPLOYEE (
  fname    VARCHAR(15) NOT NULL,
  minit    VARCHAR(1),
  lname    VARCHAR(15) NOT NULL,
  ssn     CHAR(9),
  bdate    DATE,
  address  VARCHAR(50),
  sex     CHAR(1),
  salary   DECIMAL(10,2),
  super_ssn CHAR(9),
  dno     INT,
  CONSTRAINT pk_employee PRIMARY KEY (ssn),
  -- Constraint Will be added later to prevent cyclic referential itegrity violation'
  -- 'CONSTRAINT fk_employee_employee FOREIGN key (super_ssn) references EMPLOYEE(ssn),
  CONSTRAINT fk_employee_department FOREIGN KEY (dno) REFERENCES DEPARTMENT(dnumber)
);

DROP TABLE IF EXISTS DEPENDENT;
CREATE TABLE DEPENDENT (
  essn        CHAR(9),
  dependent_name VARCHAR(15),
  sex        CHAR,
  bdate       DATE,
  relationship  VARCHAR(8),
  CONSTRAINT pk_essn_dependent_name PRIMARY KEY (essn,dependent_name),
  CONSTRAINT fk_dependent_employee FOREIGN KEY (essn) REFERENCES EMPLOYEE(ssn)
);

DROP TABLE IF EXISTS DEPT_LOCATIONS;
CREATE TABLE DEPT_LOCATIONS (
  dnumber  INT,
  dlocation VARCHAR(15),
  CONSTRAINT pk_dept_locations PRIMARY KEY (dnumber,dlocation),
  CONSTRAINT fk_deptlocations_department FOREIGN KEY (dnumber) REFERENCES DEPARTMENT(dnumber)
);

DROP TABLE IF EXISTS PROJECT;
CREATE TABLE PROJECT (
  pnumber   INT,
  pname    VARCHAR(25) NOT NULL,
  plocation  VARCHAR(15),
  dnum     INT      NOT NULL,
  CONSTRAINT ok_project PRIMARY KEY (pnumber),
  CONSTRAINT uc_pnumber unique (pname),
  CONSTRAINT fk_project_department FOREIGN KEY (dnum) REFERENCES DEPARTMENT(dnumber)
);

DROP TABLE IF EXISTS WORKS_ON;
CREATE TABLE WORKS_ON (
  essn  CHAR(9),
  pno   INT,
  hours  DECIMAL(4,1),
  CONSTRAINT pk_worksOn PRIMARY KEY (essn,pno),
  CONSTRAINT fk_workson_employee FOREIGN KEY (essn) references EMPLOYEE(ssn),
  CONSTRAINT fk_workson_project FOREIGN KEY (pno) references PROJECT(pnumber)
);



-- Insert Statements

INSERT INTO DEPARTMENT VALUES 
  ('1','Headquarters','888665555','1971-06-19'),
  ('4','Administration','987654321','1985-01-01'),
  ('5','Research','333445555','1978-05-22'),
  ('6','Software','111111100','1999-05-15'),
  ('7','Hardware','444444400','1998-05-15'),
  ('8','Sales','555555500','1997-01-01');


INSERT INTO EMPLOYEE VALUES 
  ('Bob','B','Bender','666666600','1968-04-17','8794 Garfield,Chicago,IL','M','96000.00',NULL,'8' ),
  ('Kim','C','Grace','333333300','1970-10-23','6677 Mills Ave,Sacramento,CA','F','79000.00',NULL,'6'),
  ('James','E','Borg','888665555','1927-11-10','450 Stone,Houston,TX','M','55000.00',NULL,'1'),
  ('Alex','D','Freed','444444400','1950-10-09','4333 Pillsbury,Milwaukee,WI','M','89000.00',NULL,'7'),
  ('Evan','E','Wallis','222222200','1958-01-16','134 Pelham,Milwaukee,WI','M','92000.00',NULL,'7'),
  ('Jared','D','James','111111100','1966-10-10','123 Peachtree,Atlanta,GA','M','85000.00',NULL,'6'),
  ('John','C','James','555555500','1975-06-30','7676 Bloomington,Sacramento,CA','M','81000.00',NULL,'6'),
  ('Andy','C','Vile','222222202','1944-06-21','1967 Jordan,Milwaukee,WI','M','53000.00','222222200','7'),
  ('Brad','C','Knight','111111103','1968-02-13','176 Main St.,Atlanta,GA','M','44000.00','111111100','6'),
  ('Josh','U','Zell','222222201','1954-05-22','266 McGrady,Milwaukee,WI','M','56000.00','222222200','7'),
  ('Justin','n','Mark','111111102','1966-01-12','2342 May,Atlanta,GA','M','40000.00','111111100','6'),
  ('Jon','C','Jones','111111101','1967-11-14','111 Allgood,Atlanta,GA','M','45000.00','111111100','6'),
  ('Ahmad','V','Jabbar','987987987','1959-03-29','980 Dallas,Houston,TX','M','25000.00','987654321','4'),
  ('Joyce','A','English','453453453','1962-07-31','5631 Rice,Houston,TX','F','25000.00','333445555','5'),
  ('Ramesh','K','Narayan','666884444','1952-09-15','971 Fire Oak,Humble,TX','M','38000.00','333445555','5'),
  ('Alicia','J','Zelaya','999887777','1958-07-19','3321 Castle,Spring,TX','F','25000.00','987654321','4'),
  ('John','B','Smith','123456789','1955-01-09','731 Fondren,Houston,TX','M','30000.00','333445555','5'),
  ('Jennifer','S','Wallace','987654321','1931-06-20','291 Berry,Bellaire,TX','F','43000.00','888665555','4'),
  ('Franklin','T','Wong','333445555','1945-12-08','638 Voss,Houston,TX','M','40000.00','888665555','5'),
  ('Tom','G','Brand','222222203','1966-12-16','112 Third St,Milwaukee,WI','M','62500.00','222222200','7'),
  ('Jenny','F','Vos','222222204','1967-11-11','263 Mayberry,Milwaukee,WI','F','61000.00','222222201','7'),
  ('Chris','A','Carter','222222205','1960-03-21','565 Jordan,Milwaukee,WI','F','43000.00','222222201','7'),
  ('Jeff','H','Chase','333333301','1970-01-07','145 Bradbury,Sacramento,CA','M','44000.00','333333300','6'),
  ('Bonnie','S','Bays','444444401','1956-06-19','111 Hollow,Milwaukee,WI','F','70000.00','444444400','7'),
  ('Alec','C','Best','444444402','1966-06-18','233 Solid,Milwaukee,WI','M','60000.00','444444400','7'),
  ('Sam','S','Snedden','444444403','1977-07-31','987 Windy St,Milwaukee,WI','M','48000.00','444444400','7'),
  ('Nandita','K','Ball','555555501','1969-04-16','222 Howard,Sacramento,CA','M','62000.00','555555500','6'),
  ('Jill','J','Jarvis','666666601','1966-01-14','6234 Lincoln,Chicago,IL','F','36000.00','666666600','8'),
  ('Kate','W','King','666666602','1966-04-16','1976 Boone Trace,Chicago,IL','F','44000.00','666666600','8'),
  ('Lyle','G','Leslie','666666603','1963-06-09','417 Hancock Ave,Chicago,IL','M','41000.00','666666601','8'),
  ('Billie','J','King','666666604','1960-01-01','556 Washington,Chicago,IL','F','38000.00','666666603','8'),
  ('Jon','A','Kramer','666666605','1964-08-22','1988 Windy Creek,Seattle,WA','M','41500.00','666666603','8'),
  ('Ray','H','King','666666606','1949-08-16','213 Delk Road,Seattle,WA','M','44500.00','666666604','8'),
  ('Gerald','D','Small','666666607','1962-05-15','122 Ball Street,Dallas,TX','M','29000.00','666666602','8'),
  ('Arnold','A','Head','666666608','1967-05-19','233 Spring St,Dallas,TX','M','33000.00','666666602','8'),
  ('Helga','C','Pataki','666666609','1969-03-11','101 Holyoke St,Dallas,TX','F','32000.00','666666602','8'),
  ('Naveen','B','Drew','666666610','1970-05-23','198 Elm St,Philadelphia,PA','M','34000.00','666666607','8'),
  ('Carl','E','Reedy','666666611','1977-06-21','213 Ball St,Philadelphia,PA','M','32000.00','666666610','8'),
  ('Sammy','G','Hall','666666612','1970-01-11','433 Main Street,Miami,FL','M','37000.00','666666611','8'),
  ('Red','A','Bacher','666666613','1980-05-21','196 Elm Street,Miami,FL','M','33500.00','666666612','8'); 


INSERT INTO DEPENDENT VALUES 
  ('333445555','Alice','F','1976-04-05','Daughter'),
  ('333445555','Theodore','M','1973-10-25','Son'),
  ('333445555','Joy','F','1948-05-03','Spouse'),
  ('987654321','Abner','M','1932-02-29','Spouse'),
  ('123456789','Michael','M','1978-01-01','Son'),
  ('123456789','Alice','F','1978-12-31','Daughter'),
  ('123456789','Elizabeth','F',Null,'Spouse'),-- Converted to Null as '0000-00-00' is an invalid date in SQL SERVER
  ('444444400','Johnny','M','1997-04-04','Son'),
  ('444444400','Tommy','M','1999-06-07','Son'),
  ('444444401','Chris','M','1969-04-19','Spouse'),
  ('444444402','Sam','M','1964-02-14','Spouse');

INSERT INTO DEPT_LOCATIONS VALUES 
  ('1','Houston'),
  ('4','Stafford'),
  ('5','Bellaire'),
  ('5','Houston'),
  ('5','Sugarland'),
  ('6','Atlanta'),
  ('6','Sacramento'),
  ('7','Milwaukee'),
  ('8','Chicago'),
  ('8','Dallas'),
  ('8','Miami'),
  ('8','Philadephia'),
  ('8','Seattle'); 


INSERT INTO PROJECT VALUES
  ('1','ProductX','Bellaire','5'),
  ('2','ProductY','Sugarland','5'),
  ('3','ProductZ','Houston','5'),
  ('10','Computerization','Stafford','4'),
  ('20','Reorganization','Houston','1'),
  ('30','Newbenefits','Stafford','4'),
  ('61','OperatingSystems','Jacksonville','6'),
  ('62','DatabaseSystems','Birmingham','6'),
  ('63','Middleware','Jackson','6'),
  ('91','InkjetPrinters','Phoenix','7'),
  ('92','LaserPrinters','LasVegas','7'); 


INSERT INTO WORKS_ON VALUES
  ('123456789','1','32.5'),
  ('123456789','2','7.5'),
  ('666884444','3','40.0'),
  ('453453453','1','20.0'),
  ('453453453','2','20.0'),
  ('333445555','2','10.0'),
  ('333445555','3','10.0'),
  ('333445555','10','10.0'),
  ('333445555','20','10.0'),
  ('999887777','30','30.0'),
  ('999887777','10','10.0'),
  ('987987987','10','35.0'),
  ('987987987','30','5.0'),
  ('987654321','30','20.0'),
  ('987654321','20','15.0'),
  ('888665555','20','0.0'),
  ('111111100','61','40.0'),
  ('111111101','61','40.0'),
  ('111111102','61','40.0'),
  ('111111103','61','40.0'),
  ('222222200','62','40.0'),
  ('222222201','62','48.0'),
  ('222222202','62','40.0'),
  ('222222203','62','40.0'),
  ('222222204','62','40.0'),
  ('222222205','62','40.0'),
  ('333333300','63','40.0'),
  ('333333301','63','46.0'),
  ('444444400','91','40.0'),
  ('444444401','91','40.0'),
  ('444444402','91','40.0'),
  ('444444403','91','40.0'),
  ('555555500','92','40.0'),
  ('555555501','92','44.0'),
  ('666666601','91','40.0'),
  ('666666603','91','40.0'),
  ('666666604','91','40.0'),
  ('666666605','92','40.0'),
  ('666666606','91','40.0'),
  ('666666607','61','40.0'),
  ('666666608','62','40.0'),
  ('666666609','63','40.0'),
  ('666666610','61','40.0'),
  ('666666611','61','40.0'),
  ('666666612','61','40.0'),
  ('666666613','61','30.0'),
  ('666666613','62','10.0'),
  ('666666613','63','10.0'); 



-- Adding constraint after loading data into system
Alter table EMPLOYEE
ADD CONSTRAINT fk_employee_employee FOREIGN key (super_ssn) references EMPLOYEE(ssn);



-- Select Staements for all the tables
SELECT * FROM DEPARTMENT;
SELECT * FROM DEPENDENT;
SELECT * FROM DEPT_LOCATIONS;
SELECT * FROM EMPLOYEE;
SELECT * FROM PROJECT;
SELECT * FROM WORKS_ON;
