
/*
CREATE DATABASE Magazin_Jocuri
GO
*/



USE Magazin_Jocuri
GO





-- CREATE TABLE Publisher_Joc
    CREATE TABLE Publisher_Joc(
        Id_Publisher INT PRIMARY KEY IDENTITY,
        Denumire nvarchar(100)
    );
	

	/*
-- CREATE TABLE Developer_Joc


   CREATE TABLE Developer_Joc(
        Id_Developer INT PRIMARY KEY IDENTITY,
        Denumire nvarchar(100)
    );
	

-- CREATE TABLE Gen_Joc
   CREATE TABLE Gen_Joc(
        Id_Gen INT PRIMARY KEY IDENTITY,
        Denumire nvarchar(100)
    );
	
	
	
	

	
-- CREATE TABLE Joc
    CREATE TABLE Joc(
        Id_Joc INT PRIMARY KEY IDENTITY,
        Denumire nvarchar(100),
        Id_Gen INT FOREIGN KEY REFERENCES Gen_Joc(Id_Gen),
        Id_Publisher INT FOREIGN KEY REFERENCES Publisher_Joc(Id_Publisher),
        Id_Developer INT FOREIGN KEY REFERENCES Developer_Joc(Id_Developer),
		--Id_Platforma INT FOREIGN KEY REFERENCES Platforme(Id_Platforma),
        An_Aparitie INT,
		Pret FLOAT
		
    );

	CREATE TABLE Platforme(
	Id_Platforma INT PRIMARY KEY IDENTITY,
	Id_Joc INT FOREIGN KEY REFERENCES Joc(Id_Joc),
	Denumire nvarchar(100)
	);	
	
	
	
	CREATE TABLE Client(
	Id_Client INT PRIMARY KEY IDENTITY,
	Nume nvarchar(100),
	email nvarchar(100),
	Fonduri INT
	);
	

	
	CREATE TABLE Client_Joc(
	Id_Client INT FOREIGN KEY REFERENCES Client(Id_Client),
	Id_Joc INT FOREIGN KEY REFERENCES Joc(Id_Joc),
	CONSTRAINT Id_Ownership PRIMARY KEY (Id_Client, Id_Joc),
	Achievementuri INT,
	Timp_jucat FLOAT
	);
	


	
	CREATE TABLE Contract_Developer_Publisher(
	Id_Contract INT PRIMARY KEY IDENTITY,
	Id_Developer INT FOREIGN KEY REFERENCES Developer_Joc(Id_Developer),
	Id_Publisher INT FOREIGN KEY REFERENCES Publisher_Joc(Id_Publisher)
	);
	
	
	CREATE TABLE Marketing_Team(
	Id_Marketing_Team INT PRIMARY KEY IDENTITY,
	--Id_Publisher INT FOREIGN KEY REFERENCES Marketing_Publisher(Id_Publisher),
	Id_Joc INT FOREIGN KEY REFERENCES Joc(Id_Joc),
	Denumire nvarchar(100)
	);
	

	
	CREATE TABLE Marketing_Publisher(
	Id_Publisher INT FOREIGN KEY REFERENCES Publisher_Joc(Id_Publisher),
	Id_Marketing_Team INT FOREIGN KEY REFERENCES Marketing_Team(Id_Marketing_Team),
	CONSTRAINT Id_Contract PRIMARY KEY (Id_Publisher, Id_Marketing_Team),
	Pret INT
	);
	

	

	
	CREATE TABLE Client_Platforma(
	Id_Client INT FOREIGN KEY REFERENCES Client(Id_Client),
	Id_Platforma INT FOREIGN KEY REFERENCES Platforme(Id_Platforma),
	Constraint Id_Client_Platforma PRIMARY KEY (Id_Client, Id_Platforma)
	);
	*/





	
	

	

