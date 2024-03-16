insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Ubisoft'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Ubisoft Marketing Team'),
        1000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Electronic Arts'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Electronic Arts Marketing Team'),
        5000
    )

--same for the rest of the publishers

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Activision'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Activision Marketing Team'),
        2000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Take-Two Interactive'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Take-Two Interactive Marketing Team'),
        3000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Square Enix'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Square Enix Marketing Team'),
        4000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Warner Bros. Interactive Entertainment'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Warner Bros. Interactive Entertainment Marketing Team'),
        1000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Capcom'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Capcom Marketing Team'),
        2000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Bandai Namco Entertainment'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Bandai Namco Entertainment Marketing Team'),
        3000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Konami'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Konami Marketing Team'),
        4000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'Sega'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'Sega Marketing Team'),
        5000
    )

insert into Marketing_Publisher (Id_Publisher, Id_Marketing_Team, Pret)
VALUES
    (
        (SELECT Id_Publisher FROM Publisher_Joc WHERE Denumire = 'THQ'),
        (SELECT Id_Marketing_Team FROM Marketing_Team WHERE Denumire = 'THQ Marketing Team'),
        4000
    )
