create database STUDENTS
go

create table Student (
	StudentID varchar(8) not null,
	FirstName nvarchar(25) not null,
	LastName nvarchar(50) not null,
	DoB DATE,
	Address nvarchar(100)

	CONSTRAINT PK_Student PRIMARY KEY (StudentID),
)