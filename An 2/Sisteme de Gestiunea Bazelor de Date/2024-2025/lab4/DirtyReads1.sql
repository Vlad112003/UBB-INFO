BEGIN TRANSACTION
UPDATE reducere SET procent = 40 WHERE id = 2
WAITFOR DELAY '00:00:07'
ROLLBACK TRANSACTION
INSERT INTO IstoricTranzactii VALUES ('Update reducere with rollback', GETDATE(), 'No issues');
