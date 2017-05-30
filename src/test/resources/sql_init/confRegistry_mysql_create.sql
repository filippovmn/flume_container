CREATE TABLE  IF NOT EXISTS  Units (
	U_Id varchar(36) NOT NULL,
	U_Name varchar(40) NOT NULL UNIQUE,
	U_Comment varchar(100) NOT NULL UNIQUE,
	PRIMARY KEY (U_Id)
);

CREATE TABLE  IF NOT EXISTS  Properties (
	P_Id varchar(36) NOT NULL,
	P_Key varchar(200) NOT NULL,
	P_Value varchar(200) NOT NULL,
	P_U_Id varchar(36) NOT NULL,
	PRIMARY KEY (P_Id),
	CONSTRAINT Properties_fk0 FOREIGN KEY (P_U_Id) REFERENCES Units(U_Id)
);

CREATE TABLE  IF NOT EXISTS  Tags (
	T_Id varchar(36) NOT NULL,
	T_Tag varchar(40) NOT NULL,
	T_U_Id varchar(36) NOT NULL,
	PRIMARY KEY (T_Id),
	CONSTRAINT Tags_fk0 FOREIGN KEY (T_U_Id) REFERENCES Units(U_Id)
);

