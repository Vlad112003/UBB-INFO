BEGIN TRAN
UPDATE Client SET Fonduri = 100 
WAITFOR DELAY '00:00:10'
UPDATE reducere SET procent = 90
COMMIT TRAN
INSERT INTO IstoricTranzactii VALUES ('DEADLOCK1 transaction completed', GETDATE(), 'No issues');
