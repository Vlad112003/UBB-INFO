CREATE DATABASE SGBDIR
GO
USE SGBDIR
Create Table Cadouri(
cod_cadou int primary key identity,
descriere varchar(100),
posesor varchar(100),
pret REAL
);