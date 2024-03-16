USE AstronomicalEvents2
GO

-- verifica daca un string contine doar litere si spatii
CREATE OR ALTER FUNCTION ONLY_LETTERS(@string varchar(100))
	RETURNS INT
AS
BEGIN
	IF PATINDEX('%[^A-Za-z ]%', @string) = 0
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- verifica daca lungimea unui string este >= decat un numar dat
CREATE OR ALTER FUNCTION MINIMUM_LENGTH(@string varchar(100), @min_len INT)
	RETURNS INT
AS
BEGIN
	IF LEN(@string) >= @min_len
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO


-- CRUD - Event
CREATE OR ALTER PROCEDURE CRUD_event
	@name VARCHAR(50),
	@type VARCHAR(30),
	@description VARCHAR(1000),
	@no_of_rows INT = 1
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ONLY_LETTERS(@name) = 1 
		AND dbo.ONLY_LETTERS(@type) = 1
		AND dbo.MINIMUM_LENGTH(@name, 2) = 1
		AND dbo.MINIMUM_LENGTH(@type, 2) = 1
		AND dbo.MINIMUM_LENGTH(@description, 10) = 1)
	BEGIN
		-- CREATE = INSERT
		DECLARE @n INT = 0;
		WHILE (@n < @no_of_rows)
		BEGIN
			INSERT INTO event (name, type, description) VALUES (@name, @type, @description);
			SET @n = @n + 1;
		END

		-- READ = SELECT
		SELECT * FROM event;

		-- UPDATE
		UPDATE event set type='Eclipse'
		WHERE name COLLATE SQL_Latin1_General_CP1_CI_AS LIKE '%Eclipse%';

		SELECT * FROM event WHERE type='Eclipse';

		-- DELETE
		DELETE FROM event WHERE type LIKE 'Eclipse';
		
		SELECT * FROM event;

		PRINT 'CRUD operations for table event';
	END
	ELSE
	BEGIN
		PRINT 'Invalid parameters'
	END

END
GO

SELECT * FROM event
EXEC CRUD_event 'Solar EcliPse', 'Type', 'An amazing summer event';
GO


-- valideaza un URL
CREATE OR ALTER FUNCTION WEBSITE_URL(@url_string VARCHAR(255))
	RETURNS INT
AS
BEGIN
	IF (@url_string IS NOT NULL AND
		(@url_string LIKE 'https://%[a-z,0-9]%.[A-Za-z]%[A-Za-z]%' OR 
		@url_string LIKE 'http://%[a-z,0-9]%.[A-Za-z]%[A-Za-z]%' OR
		@url_string LIKE 'www.[A-Za-z]%[A-Za-z].[A-Za-z]%[A-Za-z]%'
		))
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- CRUD - Observatory
CREATE OR ALTER PROCEDURE CRUD_observatory
	@name VARCHAR(50),
	@website VARCHAR(255),
	@manager varchar(50),
	@no_of_rows INT = 1
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ONLY_LETTERS(@name) = 1 
		AND dbo.ONLY_LETTERS(@manager) = 1
		AND dbo.WEBSITE_URL(@website) = 1)
	BEGIN
		-- CREATE = INSERT
		DECLARE @n INT = 0;
		WHILE (@n < @no_of_rows)
		BEGIN
			INSERT INTO observatory(name, website, manager) VALUES (@name, @website, @manager);
			SET @n = @n + 1;
		END

		-- READ = SELECT
		SELECT * FROM observatory;

		-- UPDATE
		UPDATE observatory set manager='Vacant'
		WHERE name LIKE @name;

		SELECT * FROM observatory WHERE manager='Vacant';

		-- DELETE
		DELETE FROM observatory WHERE manager LIKE 'Vacant';
		
		SELECT * FROM observatory;

		PRINT 'CRUD operations for table observatory';
	END
	ELSE
	BEGIN
		PRINT 'Invalid parameters'
	END

END
GO

SELECT * FROM observatory
EXEC CRUD_observatory 'Royal Observatory', 'https://www.rmg.co.uk/royal-observatory', 'Ina Elina';
GO

-- verifica daca o data nu este mai mare decat cea de azi
CREATE OR ALTER FUNCTION CHECK_DATE(@date DATE)
	RETURNS INT
AS
BEGIN
	IF @date <= GETDATE()
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- verifica daca un planetariu exista in baza de date dupa id
CREATE OR ALTER FUNCTION OBSERVATORY_ID_EXISTS(@observatory_id INT)
	RETURNS INT
AS
BEGIN
	IF EXISTS( SELECT * FROM observatory WHERE @observatory_id = observatory.id)
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- verifica daca un eveniment exista in baza de date dupa id
CREATE OR ALTER FUNCTION EVENT_ID_EXISTS(@event_id INT)
	RETURNS INT
AS
BEGIN
	IF EXISTS( SELECT * FROM event WHERE @event_id = event.id)
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- verifica daca cheia pe care dorim sa o inseram in observatory_id exista deja
CREATE OR ALTER FUNCTION OBSERVATORY_EVENT_KEY_EXISTS(@event_id INT, @observatory_id INT)
	RETURNS INT
AS
BEGIN
	IF EXISTS( SELECT * FROM observatory_event 
				WHERE @event_id = event_id AND @observatory_id = observatory_id)
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- CRUD - Observatory-Event
CREATE OR ALTER PROCEDURE CRUD_observatory_event
	@observatory_id INT,
	@event_id INT,
	@date DATE
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.EVENT_ID_EXISTS(@event_id) = 1 
		AND dbo.OBSERVATORY_ID_EXISTS(@observatory_id) = 1
		AND dbo.CHECK_DATE(@date) = 1)
	BEGIN
		-- CREATE = INSERT
		IF DBO.OBSERVATORY_EVENT_KEY_EXISTS(@event_id, @observatory_id) = 0
		BEGIN
			INSERT INTO observatory_event(event_id, observatory_id, date) 
			VALUES (@event_id, @observatory_id, @date);
		END
		ELSE
		BEGIN
			PRINT 'The key already exists'
		END

		-- READ = SELECT
		SELECT * FROM observatory_event;

		-- UPDATE
		UPDATE observatory_event set date = GETDATE()
		WHERE @event_id = event_id AND observatory_id = @observatory_id;

		SELECT * FROM observatory_event
		WHERE @event_id = event_id AND observatory_id = @observatory_id;

		-- DELETE
		DELETE FROM observatory_event
		WHERE @event_id = event_id AND observatory_id = @observatory_id;
		
		SELECT * FROM observatory_event;

		PRINT 'CRUD operations for table observatory_event';
	END
	ELSE
	BEGIN
		PRINT 'Invalid parameters'
	END

END
GO

SELECT * FROM observatory_event
EXEC CRUD_observatory_event 4, 2, '01.01.1980'
GO

---- VIEWS ----
CREATE OR ALTER VIEW view_event
AS
	SELECT type, COUNT(*) AS no_events
	FROM event 
	GROUP BY type
GO

CREATE OR ALTER VIEW view_observatory
AS
	SELECT name, manager
	FROM observatory
GO

---- INDEXES ----

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'n_idx_event_name_type')
	DROP INDEX n_idx_event_name_type ON event;
GO
CREATE NONCLUSTERED INDEX n_idx_event_name_type ON event(name, type);
GO

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'n_idx_event_name_description')
	DROP INDEX n_idx_event_name_description ON event;
GO
CREATE NONCLUSTERED INDEX n_idx_event_name_description ON event(name, description);


IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'n_idx_observatory_name_manager')
	DROP INDEX n_idx_observatory_name_manager ON observatory;
GO
CREATE NONCLUSTERED INDEX n_idx_observatory_name_manager ON observatory(name, manager);
GO

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'n_idx_observatory_name_website')
	DROP INDEX n_idx_observatory_name_website ON observatory;
GO
CREATE NONCLUSTERED INDEX n_idx_observatory_name_website ON observatory(name, website);
GO

SELECT * FROM view_event ORDER BY no_events

SELECT * FROM view_observatory ORDER BY name
