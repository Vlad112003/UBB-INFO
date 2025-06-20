--solutie: set deadlock_priority high/low

set deadlock_priority high
BEGIN TRAN
UPDATE reducere SET procent = 100 
WAITFOR DELAY '00:00:07'
UPDATE Client SET Fonduri = 100
COMMIT TRAN
INSERT INTO IstoricTranzactii VALUES ('DEADLOCK2 transaction completed', GETDATE(), 'No issues');
