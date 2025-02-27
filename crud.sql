use crudopp;
create table crud(
Id Int Primary Key,
Name Varchar(30),
Mark Int,
City varchar(50)
);
describe crud;
Select*from crud;
Alter table crud modify column Id integer Auto_Increment;
