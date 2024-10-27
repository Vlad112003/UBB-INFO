INSERT INTO Client(Nume,email,Fonduri) VALUES ('Goofy', 'goofyaah@gmail.com', 1000)

Begin Tran
WAITFOR DELAY '00:00:07'
DELETE FROM Client WHERE Nume = 'Goofy'
COMMIT TRAN
INSERT INTO IstoricTranzactii VALUES ('Delete Client cu succes', GETDATE(), 'fara probleme la crearea problemei')


