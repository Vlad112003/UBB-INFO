-- solutie: SET TRANSACTION ISOLATION LEVEL SERIALIZABLE

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
BEGIN TRANSACTION
SELECT * FROM Client
WAITFOR DELAY '00:00:10'
SELECT * FROM Client
COMMIT TRAN