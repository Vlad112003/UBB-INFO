-- marketing team, publisher joc si marketing_publisher

--         Creaţi o procedură stocată ce inserează date pentru entităţi ce se află într-o relaţie m-n.
-- Dacă o operaţie de inserare eşuează, trebuie făcut roll-back pe întreaga procedură
-- stocată. (nota: 6)
--         Creaţi o procedură stocată ce inserează date pentru entităţi ce se află într-o relaţie m-n.
-- Dacă o operaţie de inserare eşuează va trebui să se păstreze cât mai mult posibil din ceea
-- ce s-a modificat până în acel moment. De exemplu, dacă se încearcă inserarea unei cărţi
-- şi a autorilor acesteia, iar autorii au fost inseraţi cu succes însă apare o problemă la
-- inserarea cărţii, atunci să se facă roll-back la inserarea de carte însă autorii acesteia să
-- rămână în baza de date. (nota: 10)

-- Observaţie: Ca notă generală, nu se va transmite niciun ID ca parametru de intrare a unei
-- proceduri stocate şi toţi parametrii trebuie să fie validaţi (utilizaţi funcţii acolo unde este nevoie).
-- De asemenea, pentru toate scenariile trebuie să stabiliţi un sistem de logare ce vă va permite să
-- memoraţi istoricul acţiunilor executate. Pentru detectarea erorilor se recomandă folosirea clauzei
-- try-catch

--         Pentru prezentarea laboratorului pregătiţi teste ce acoperă scenarii de succes şi cu erori. Pregătiţi
-- explicaţii detaliate ale scenariilor şi implementării.

create or alter function validate_name(@name nvarchar(100)) returns int as
begin
    declare @result int = 1
    if @name is null or len(@name) = 0
        set @result = 0
    return @result
end

--verificare
select dbo.validate_name('Penguin Random House') --1
select dbo.validate_name('') --0

-- insert pe rand chiar daca una nu e ok

create or alter procedure insert_publisher_and_marketing_team
    @publisher_name nvarchar(100),
    @marketing_team_name nvarchar(100),
    @marketing_publisher_pret int
as
begin
    begin try
        begin transaction
        if dbo.validate_name(@publisher_name) = 0
        begin
            insert into LogTable(TypeOperation, TableOperation, ExecutionDate) values ('Insert', 'Publisher_Joc', getdate())
            raiserror('Publisher name is invalid', 16, 1)
        end
        if dbo.validate_name(@marketing_team_name) = 0
        begin
            insert into LogTable(TypeOperation, TableOperation, ExecutionDate) values ('Insert', 'Marketing_Team', getdate())
            raiserror('Marketing team name is invalid', 16, 1)
        end
        insert into Publisher_Joc(Denumire) values(@publisher_name)
        insert into Marketing_Team(Denumire) values(@marketing_team_name)
        insert into marketing_publisher(id_publisher, id_marketing_team, pret) values((select Publisher_Joc.Id_Publisher from Publisher_Joc where Denumire = @publisher_name), (select Marketing_Team.Id_Marketing_Team from marketing_team where Denumire = @marketing_team_name), @marketing_publisher_pret)
        insert into LogTable(TypeOperation, TableOperation, ExecutionDate) values ('Insert', 'marketing_publisher', getdate())
            commit
    end try
    begin catch
        if @@trancount > 0
            rollback
        begin
            raiserror ('An error occured', 16, 1)
        end
    end catch
end

--insert pe rand chiar daca una nu e ok

create or alter procedure insert_publisher_and_marketing_team2
    @publisher_name nvarchar(100),
    @marketing_team_name nvarchar(100),
    @marketing_publisher_pret int
    as
begin
    --publisher
    begin try
        begin transaction
        if dbo.validate_name(@publisher_name) = 0
        begin
            raiserror('Publisher name is invalid', 16, 1)
        end
        insert into Publisher_Joc(Denumire) values(@publisher_name)
        insert into LogTable(TypeOperation, TableOperation, ExecutionDate) values ('Insert', 'Publisher_Joc', getdate())
        commit
    end try
    begin catch
        if @@trancount > 0
            rollback
        print('An error occured.')
    end catch

    --marketing team

    begin try
        begin transaction
        if dbo.validate_name(@marketing_team_name) = 0
        begin
            raiserror('Marketing team name is invalid', 16, 1)
        end
        insert into Marketing_Team(Denumire) values(@marketing_team_name)
        insert into LogTable(TypeOperation, TableOperation, ExecutionDate) values ('Insert', 'Marketing_Team', getdate())
        commit
    end try
    begin catch
        if @@trancount > 0
            rollback
        print('An error occured.')
    end catch

    --marketing publisher

    begin try
        begin transaction
        insert into marketing_publisher(id_publisher, id_marketing_team, pret) values((select Publisher_Joc.Id_Publisher from Publisher_Joc where Denumire = @publisher_name), (select Marketing_Team.Id_Marketing_Team from marketing_team where Denumire = @marketing_team_name), @marketing_publisher_pret)
        insert into LogTable(TypeOperation, TableOperation, ExecutionDate) values ('Insert', 'marketing_publisher', getdate())
        commit
    end try
    begin catch
        if @@trancount > 0
            rollback
        print('An error occured.')
    end catch
end

--testare
--1. adaugare impreuna
--succes
exec insert_publisher_and_marketing_team 'Penguin Random House', 'EA Sports', 1000

    select * from Publisher_Joc, Marketing_Team, marketing_publisher where Publisher_Joc.Id_Publisher = marketing_publisher.id_publisher and Marketing_Team.Id_Marketing_Team = marketing_publisher.id_marketing_team
    select * from LogTable
--esec
exec insert_publisher_and_marketing_team '', 'EA Sports', 1000

--2. adaugare pe rand
--succes
exec insert_publisher_and_marketing_team2 'Penguin', 'EA Kids', 1500

select * from Publisher_Joc, Marketing_Team, marketing_publisher where Publisher_Joc.Id_Publisher = marketing_publisher.id_publisher and Marketing_Team.Id_Marketing_Team = marketing_publisher.id_marketing_team

--esec pe publisher, si prin urmare si pe marketing_publisher
exec insert_publisher_and_marketing_team2 '', 'EA Duplo', 1500