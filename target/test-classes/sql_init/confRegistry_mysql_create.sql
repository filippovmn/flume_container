CREATE TABLE Units (
	U_Id varchar(36) NOT NULL,
	U_Name varchar(40) NOT NULL UNIQUE,
	U_Comment varchar(100) NOT NULL UNIQUE,
	PRIMARY KEY (U_Id)
);

CREATE TABLE Properties (
	P_Id varchar(36) NOT NULL,
	P_Key varchar(200) NOT NULL,
	P_Value varchar(200) NOT NULL,
	P_U_Id varchar(36) NOT NULL,
	PRIMARY KEY (P_Id)
);

CREATE TABLE Tags (
	T_Id varchar(36) NOT NULL,
	T_Tag varchar(40) NOT NULL,
	T_U_Id varchar(36) NOT NULL,
	PRIMARY KEY (T_Id)
);

ALTER TABLE Properties ADD CONSTRAINT Properties_fk0 FOREIGN KEY (P_U_Id) REFERENCES Units(U_Id);

ALTER TABLE Tags ADD CONSTRAINT Tags_fk0 FOREIGN KEY (T_U_Id) REFERENCES Units(U_Id);

