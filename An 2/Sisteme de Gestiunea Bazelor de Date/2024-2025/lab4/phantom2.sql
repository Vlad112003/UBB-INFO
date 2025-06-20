BEGIN TRAN
WAITFOR DELAY '00:00:07'
INSERT INTO Client(Nume, email, Fonduri) values ('Goofer', 'gooferman@gmail.com', 1000);
COMMIT TRAN
INSERT INTO IstoricTranzactii VALUES ('Insert Client cu succes', GETDATE(), 'fara probleme la crearea problemei')


select * from IstoricTranzactii