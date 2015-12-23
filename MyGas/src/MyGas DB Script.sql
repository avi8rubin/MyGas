Drop DATABASE IF exists MyGas;
CREATE DATABASE MyGas;
USE MyGas;

CREATE TABLE IF NOT EXISTS Fuels (
	Fuel_ID INTEGER NOT NULL AUTO_INCREMENT,
    Fuel_Description VARCHAR (25),
    Max_Price FLOAT NOT NULL,
    Current_Price FLOAT,
    PRIMARY KEY (Fuel_ID)
);

CREATE TABLE IF NOT EXISTS Costing_Model (
	Costing_Model_ID INTEGER NOT NULL AUTO_INCREMENT,
    Model_Type_Description VARCHAR (100), 
    PRIMARY KEY (Costing_Model_ID)
);

CREATE TABLE IF NOT EXISTS Gas_Company (
	Gas_Company_ID INTEGER NOT NULL AUTO_INCREMENT,
    Gas_Company_Name VARCHAR (20),
    PRIMARY KEY (Gas_Company_ID)
);

CREATE TABLE IF NOT EXISTS User_Type (
	User_Type_ID INTEGER NOT NULL AUTO_INCREMENT,
    User_Privilege VARCHAR (40),
    PRIMARY KEY (User_Type_Id)
);

CREATE TABLE IF NOT EXISTS Users (
	User_ID INTEGER NOT NULL AUTO_INCREMENT,
    User_Name VARCHAR (20) NOT NULL,
    User_Password VARCHAR (20) NOT NULL,
    User_Type_Id INTEGER NOT NULL,
    Logged_In ENUM('Yes','No','Station') NOT NULL DEFAULT 'No', -- Gas Station can login more then 1 time on line
    PRIMARY KEY (User_Id),
    UNIQUE KEY (User_Name),
    FOREIGN KEY (User_Type_Id) references User_Type(User_Type_Id)
);

CREATE TABLE IF NOT EXISTS Workers (
	Worker_ID INTEGER NOT NULL AUTO_INCREMENT,
    Worker_First_Name VARCHAR (20),
    Worker_Last_Name VARCHAR (20),
    Email VARCHAR (30),
    Organization ENUM ('Management','Station'),
    User_Id INTEGER NOT NULL,
    PRIMARY KEY (Worker_ID),
    UNIQUE KEY (Email),
    FOREIGN KEY (User_Id) references Users(User_Id)
);

CREATE INDEX User_Id ON Workers (User_Id);

CREATE TABLE IF NOT EXISTS Gas_Stations (
	Gas_Station_ID INTEGER NOT NULL AUTO_INCREMENT,
    Station_Name VARCHAR (20),
    Station_Address VARCHAR (60),
    Gas_Station_Manager_ID INTEGER,
    User_Interface_ID INTEGER, -- worker enter to screen
    Gas_Company_ID INTEGER NOT NULL,
    PRIMARY KEY (Gas_Station_ID),
    UNIQUE KEY `Gas_Station_Manager_ID` (`Gas_Station_Manager_ID`),
    FOREIGN KEY (Gas_Station_Manager_ID) references Workers(Worker_ID),
    FOREIGN KEY (User_Interface_ID) references Users(User_ID),
    FOREIGN KEY (Gas_Company_ID) references Gas_Company(Gas_Company_ID)
);

CREATE TABLE IF NOT EXISTS Fuel_Orders (
	Order_ID INTEGER NOT NULL AUTO_INCREMENT,
    Fuel_ID INTEGER NOT NULL,
    Gas_Station_ID INTEGER NOT NULL,
    Amount FLOAT,
    Order_Date DATETIME NOT NULL,
    Order_Confirmation ENUM('Yes','No','Waiting') DEFAULT 'Waiting',
    Showed_To_Manager ENUM('Yes','No') DEFAULT 'No',
    PRIMARY KEY (Order_ID),
    UNIQUE KEY (Fuel_ID,Gas_Station_ID,Order_Date),
    FOREIGN KEY (Fuel_ID) references Fuels(Fuel_ID),
    FOREIGN KEY (Gas_Station_ID) references Gas_Stations(Gas_Station_ID)
);

CREATE INDEX Gas_Station_ID ON Fuel_Orders (Gas_Station_ID);
CREATE INDEX Order_Date ON Fuel_Orders (Order_Date);

CREATE TABLE IF NOT EXISTS Fuel_Per_Station (
	Gas_Station_ID INTEGER,
    Fuel_ID INTEGER,
    Threshold_Limit INTEGER,
	Current_Amount FLOAT,
    Capacity INTEGER NOT NULL,   
    PRIMARY KEY (Fuel_ID,Gas_Station_ID),
    FOREIGN KEY (Fuel_ID) references Fuels(Fuel_ID),
    FOREIGN KEY (Gas_Station_ID) references Gas_Stations(Gas_Station_ID)
);

CREATE TABLE IF NOT EXISTS Purchase_Plan (
	Plan_ID INTEGER NOT NULL AUTO_INCREMENT,
    Plan_Name VARCHAR(100),
    PRIMARY KEY (Plan_ID)
);

CREATE TABLE IF NOT EXISTS Tariff_Update (
	Tariff_Update_ID INTEGER NOT NULL AUTO_INCREMENT,
    Tariff_Update_Date DATETIME NOT NULL,
    Fuel_ID INTEGER,
    Wanted_Price FLOAT NOT NULL,
    Current_Price FLOAT NOT NULL, -- For Hisrory
    CEO_Confirmation ENUM('Yes','No','Waiting') DEFAULT 'Waiting',
    PRIMARY KEY (Tariff_Update_ID,Tariff_Update_Date,Fuel_ID),
    FOREIGN KEY (Fuel_ID) references Fuels(Fuel_ID)
);

CREATE TABLE IF NOT EXISTS Purchase_Plan_For_Gas_Company (
	Gas_Company_ID INTEGER,
    Plan_ID INTEGER,
    PRIMARY KEY (Gas_Company_ID,Plan_ID),
    FOREIGN KEY (Gas_Company_ID) references Gas_Company(Gas_Company_ID),
    FOREIGN KEY (Plan_ID) references Purchase_Plan(Plan_ID)
);

CREATE TABLE IF NOT EXISTS Customers (
	Customers_ID INTEGER NOT NULL,
    Customer_First_Name VARCHAR (20),
    Customer_Last_Name VARCHAR (20),
    Customer_Type ENUM('Private','Commercial'),
    Plan_ID INTEGER,
    User_ID INTEGER,
    Phone_Number VARCHAR(15) NOT NULL,
    Credit_Card VARCHAR(20),
    Email VARCHAR (30),
    Carrent_Rate FLOAT DEFAULT 0,
    IS_Active ENUM('Yes','No') DEFAULT 'Yes',
    PRIMARY KEY (Customers_ID),
    UNIQUE KEY (User_ID,Plan_ID),
    UNIQUE KEY (Email),
    FOREIGN KEY (Plan_ID) references Purchase_Plan(Plan_ID),
    FOREIGN KEY (User_ID) references Users(User_ID)
);

CREATE INDEX Carrent_Rate ON Customers (Carrent_Rate);

CREATE TABLE IF NOT EXISTS Cars (
	Car_ID INTEGER NOT NULL AUTO_INCREMENT,
    Car_Number VARCHAR (12) NOT NULL,
    Customers_ID INTEGER,
    NFC ENUM('Yes','No') DEFAULT 'No',
    Fuel_ID INTEGER,
    Costing_Model_ID INTEGER,
    PRIMARY KEY (Car_ID,Car_Number,Customers_ID),
    FOREIGN KEY (Fuel_ID) references Fuels(Fuel_ID),
    FOREIGN KEY (Customers_ID) references Customers(Customers_ID),
    FOREIGN KEY (Costing_Model_ID) references Costing_Model(Costing_Model_ID)
);

CREATE INDEX Car_Number ON Cars (Car_Number);
CREATE INDEX Customers_ID ON Cars (Customers_ID);

CREATE TABLE IF NOT EXISTS Campaign_Type (
	Campaign_Type_ID INTEGER NOT NULL AUTO_INCREMENT,
    Calculation_Method VARCHAR(70),
    PRIMARY KEY (Campaign_Type_ID)
);

CREATE TABLE IF NOT EXISTS Campaign_Patterns (
	Campaign_Patterns_ID INTEGER NOT NULL AUTO_INCREMENT,
    Campaign_Description VARCHAR(100),
    Campaign_Type_ID INTEGER,
    Discount_Percentage FLOAT(7,2) NOT NULL, -- Enter to query the discount code from java
    PRIMARY KEY (Campaign_Patterns_ID),
    FOREIGN KEY (Campaign_Type_ID) references Campaign_Type(Campaign_Type_ID)
);

CREATE TABLE IF NOT EXISTS Campaigns (
	Campaign_ID INTEGER NOT NULL AUTO_INCREMENT,
    Campaign_Patterns_ID INTEGER,
    Start_Campaign DATETIME NOT NULL DEFAULT NOW(), -- The start day of the campaign
    End_Campaign DATETIME NOT NULL,					-- The end day of the campaign
	IS_Active ENUM('Yes','No') DEFAULT 'Yes',
    PRIMARY KEY (Campaign_ID),
    FOREIGN KEY (Campaign_Patterns_ID) references Campaign_Patterns(Campaign_Patterns_ID)
);

CREATE INDEX Start_Campaign ON Campaigns (Start_Campaign);

CREATE TABLE IF NOT EXISTS Campaigns_Date (
	Campaign_Patterns_ID INTEGER,
    Date_Description VARCHAR(80),
    PRIMARY KEY (Campaign_Patterns_ID),
    FOREIGN KEY (Campaign_Patterns_ID) references Campaign_Patterns(Campaign_Patterns_ID)
);

CREATE TABLE IF NOT EXISTS Campaigns_Hours (
	Campaign_Patterns_ID INTEGER,
    Start_Hour TIME NOT NULL,
	End_Hour TIME NOT NULL,
    PRIMARY KEY (Campaign_Patterns_ID),
    FOREIGN KEY (Campaign_Patterns_ID) references Campaign_Patterns(Campaign_Patterns_ID)
);

CREATE TABLE IF NOT EXISTS Campaigns_Gas_Type (
	Campaign_Patterns_ID INTEGER,
	Fuel_ID INTEGER,
    PRIMARY KEY (Campaign_Patterns_ID),
    FOREIGN KEY (Campaign_Patterns_ID) references Campaign_Patterns(Campaign_Patterns_ID),
    FOREIGN KEY (Fuel_ID) references Fuels(Fuel_ID)
);

CREATE TABLE IF NOT EXISTS Campaigns_Amount (
	Campaign_Patterns_ID INTEGER,
	Amount INTEGER,
    PRIMARY KEY (Campaign_Patterns_ID),
    FOREIGN KEY (Campaign_Patterns_ID) references Campaign_Patterns(Campaign_Patterns_ID)
);

CREATE TABLE IF NOT EXISTS Campaigns_Gas_Station (
	Campaign_Patterns_ID INTEGER,
	Gas_Station_ID INTEGER,
    PRIMARY KEY (Campaign_Patterns_ID),
    FOREIGN KEY (Campaign_Patterns_ID) references Campaign_Patterns(Campaign_Patterns_ID),
    FOREIGN KEY (Gas_Station_ID) references Gas_Stations(Gas_Station_ID)
);

CREATE TABLE IF NOT EXISTS Campaigns_Rate (
	Campaign_Patterns_ID INTEGER,
	Customer_Rate INTEGER NOT NULL,
    PRIMARY KEY (Campaign_Patterns_ID),
    FOREIGN KEY (Campaign_Patterns_ID) references Campaign_Patterns(Campaign_Patterns_ID)
);

CREATE TABLE IF NOT EXISTS Sales (
	Sales_ID INTEGER NOT NULL AUTO_INCREMENT,
    Fuel_ID INTEGER,
    Sale_Date DATETIME NOT NULL DEFAULT NOW(),
	Fuel_Amount FLOAT,
    Payment FLOAT,
    Customers_ID INTEGER,
    PRIMARY KEY (Sales_ID),
    FOREIGN KEY (Fuel_ID) references Fuels(Fuel_ID),
    FOREIGN KEY (Customers_ID) references Customers(Customers_ID)
);

CREATE INDEX Customers_ID ON Sales (Customers_ID);
CREATE INDEX Sale_Date ON Sales (Sale_Date);

CREATE TABLE IF NOT EXISTS Home_Fuel_Sales (
	Sales_ID INTEGER,
    Delivery_Date DATETIME NOT NULL,
    Address VARCHAR(20),
    Order_Status ENUM('Delivered','Not Delivered') DEFAULT 'Delivered',
    PRIMARY KEY (Sales_ID),
    FOREIGN KEY (Sales_ID) references Sales(Sales_ID)
);

CREATE TABLE IF NOT EXISTS Gas_Stations_Sales (
	Sales_ID INTEGER,
    Driver_Name VARCHAR(20),
    Car_ID INTEGER,
    Gas_Station_ID INTEGER,
    Campaign_ID INTEGER DEFAULT NULL,
    PRIMARY KEY (Sales_ID),
   FOREIGN KEY (Sales_ID) references Sales(Sales_ID),
    FOREIGN KEY (Car_ID) references Cars(Car_ID),
    FOREIGN KEY (Gas_Station_ID) references Gas_Stations(Gas_Station_ID),
    FOREIGN KEY (Campaign_ID) references Campaigns(Campaign_ID)
);

CREATE INDEX Gas_Station_ID ON Gas_Stations_Sales (Gas_Station_ID);

CREATE TABLE IF NOT EXISTS Notifications (
	User_ID INTEGER,
    NotificationsDescription VARCHAR(100),
    Notification_Date DATETIME NOT NULL DEFAULT NOW(),
    User_Saw ENUM('Yes','No') DEFAULT 'No',
    PRIMARY KEY (User_ID),
    FOREIGN KEY (User_ID) references Users(User_ID)
);

INSERT INTO Fuels VALUES(null,'95',6.5,6.3);
INSERT INTO Fuels VALUES(null,'Scooters Fuel',6.6,6.4);
INSERT INTO Fuels VALUES(null,'Home Fuel',6.7,6);
INSERT INTO Fuels VALUES(null,'Diesel',6.8,6.2);

INSERT INTO Costing_Model VALUES(null,'Casual Fueling-Max price');
INSERT INTO Costing_Model VALUES(null,'Monthly Fueling 1 car-4% discount price');
INSERT INTO Costing_Model VALUES(null,'Monthly Fueling cars-10% discount price');
INSERT INTO Costing_Model VALUES(null,'Full Monthly Fueling-3% discount price');

INSERT INTO Gas_Company  VALUES(null,'Paz');
INSERT INTO Gas_Company  VALUES(null,'Ten');
INSERT INTO Gas_Company  VALUES(null,'Yellow');
INSERT INTO Gas_Company  VALUES(null,'Sonol');

INSERT INTO User_Type  VALUES(1,'Customer');
INSERT INTO User_Type  VALUES(2,'Stations');
INSERT INTO User_Type  VALUES(3,'Station Manager');
INSERT INTO User_Type  VALUES(4,'CEO');
INSERT INTO User_Type  VALUES(5,'Marketing Manager');
INSERT INTO User_Type  VALUES(6,'Marketing Representative');

INSERT INTO Purchase_Plan VALUES(null,'Level One (Exclusive) - One gas station company allow.');
INSERT INTO Purchase_Plan VALUES(null,'Level Two - Two\Three gas Station companies allow.');
INSERT INTO Purchase_Plan VALUES(null,'Level Three - All Gas Station companies allow.');

INSERT INTO Purchase_Plan_For_Gas_Company VALUES(2,1);
INSERT INTO Purchase_Plan_For_Gas_Company VALUES(1,2);
INSERT INTO Purchase_Plan_For_Gas_Company VALUES(2,2);
INSERT INTO Purchase_Plan_For_Gas_Company VALUES(3,2);
INSERT INTO Purchase_Plan_For_Gas_Company VALUES(1,3);
INSERT INTO Purchase_Plan_For_Gas_Company VALUES(2,3);
INSERT INTO Purchase_Plan_For_Gas_Company VALUES(3,3);
INSERT INTO Purchase_Plan_For_Gas_Company VALUES(4,3);

INSERT INTO Users VALUES(null,'Avi','123',6,2);
INSERT INTO Users VALUES(null,'Ohad','1234',5,2);
INSERT INTO Users VALUES(null,'Adir','121',6,2);
INSERT INTO Users VALUES(null,'Litaf','1212',5,2);
INSERT INTO Users VALUES(null,'CEO','CEO',4,2);
INSERT INTO Users VALUES(null,'Gas Station 1','1',2,3);
INSERT INTO Users VALUES(null,'Gas Station 2','2',2,3);
INSERT INTO Users VALUES(null,'Gas Station 3','3',2,3);
INSERT INTO Users VALUES(null,'Gas Station 4','4',2,3);
INSERT INTO Users VALUES(null,'Gas Station 5','5',2,3);
INSERT INTO Users VALUES(null,'Gas Station 6','6',2,3);
INSERT INTO Users VALUES(null,'Manager1','1',3,2);
INSERT INTO Users VALUES(null,'Manager2','2',3,2);
INSERT INTO Users VALUES(null,'Manager3','2',3,2);
INSERT INTO Users VALUES(null,'Manager4','2',3,2);
INSERT INTO Users VALUES(null,'Manager5','2',3,2);
INSERT INTO Users VALUES(null,'Manager6','2',3,2);
INSERT INTO Users VALUES(null,'David','3',1,2);
INSERT INTO Users VALUES(null,'Itzik','4',1,2);
INSERT INTO Users VALUES(null,'Nisim','5',1,2);
INSERT INTO Users VALUES(null,'Him','3',1,2);
INSERT INTO Users VALUES(null,'Eden','4',1,2);
INSERT INTO Users VALUES(null,'Dror','5',1,2);
INSERT INTO Users VALUES(null,'Eli','3',1,2);
INSERT INTO Users VALUES(null,'Ron','4',1,2);
INSERT INTO Users VALUES(null,'Roni','5',1,2);
INSERT INTO Users VALUES(null,'Rita','3',1,2);
INSERT INTO Users VALUES(null,'Zah','4',1,2);
INSERT INTO Users VALUES(null,'Sheli','5',1,2);
INSERT INTO Users VALUES(null,'Shalom','5',1,2);
INSERT INTO Users VALUES(null,'Niv','3',1,2);
INSERT INTO Users VALUES(null,'Shay','4',1,2);
INSERT INTO Users VALUES(null,'Sali','5',1,2);
INSERT INTO Users VALUES(null,'Nir','3',1,2);
INSERT INTO Users VALUES(null,'Kfir','4',1,2);
INSERT INTO Users VALUES(null,'Or','5',1,2);

INSERT INTO Workers VALUES(1,'Avi','Rubin','avi8rubin@gmail.com','Station',1);
INSERT INTO Workers VALUES(2,'Ohad','Zino','zinoohad@gmail.com','Station',2);
INSERT INTO Workers VALUES(11,'Adir','Notes','adirnotes@gmail.com','Station',3);
INSERT INTO Workers VALUES(3,'Litaf','kupfer','litafkupfer@gmail.com','Station',4);
INSERT INTO Workers VALUES(4,'CEO-Name','CEO-Last Name','CEO@gmail.com','Management',5);
INSERT INTO Workers VALUES(5,'Manager1','Manger lastname','Manger1@gmail.com','Management',12);
INSERT INTO Workers VALUES(6,'Manager2','Manger lastname','Manger2@gmail.com','Management',13);
INSERT INTO Workers VALUES(7,'Manager3','Manger lastname','Manger3@gmail.com','Management',14);
INSERT INTO Workers VALUES(8,'Manager4','Manger lastname','Manger4@gmail.com','Management',15);
INSERT INTO Workers VALUES(9,'Manager5','Manger lastname','Manger5@gmail.com','Management',16);
INSERT INTO Workers VALUES(10,'Manager6','Manger lastname','Manger6@gmail.com','Management',17);

INSERT INTO Gas_Stations VALUES(null,'Carmiel','snonit 53',5,6,1);
INSERT INTO Gas_Stations VALUES(null,'Tel Aviv','st.hashalom',6,7,1);
INSERT INTO Gas_Stations VALUES(null,'Jerusalem','stav 5',7,8,2);
INSERT INTO Gas_Stations VALUES(null,'Holon','aviv 7',8,9,3);
INSERT INTO Gas_Stations VALUES(null,'Haifa','hadas 59',9,10,4);
INSERT INTO Gas_Stations VALUES(null,'Natania','Or 777',10,11,4);

INSERT INTO Fuel_Orders VALUES(null,1,1,600,'2015-1-1 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,2,2,200,'2015-2-2 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,2,3,400,'2015-3-7 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,1,1,100,'2015-3-23 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,2,2,400,'2015-4-24 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,1,5,500,'2015-5-25 12:12:00',1,1);
INSERT INTO Fuel_Orders VALUES(null,4,6,400,'2015-6-17 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,4,4,30,'2015-7-12 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,2,5,300,'2014-8-11 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,4,2,200,'2014-12-16 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,4,5,100,'2014-11-17 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,1,6,600,'2014-10-10 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,2,1,800,'2014-9-7 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,1,2,600,'2014-5-8 12:12:00',1,1);
INSERT INTO Fuel_Orders VALUES(null,1,4,700,'2014-5-7 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,2,5,900,'2011-5-9 12:12:00',1,1);
INSERT INTO Fuel_Orders VALUES(null,4,3,500,'2011-5-5 12:12:00',1,1);
INSERT INTO Fuel_Orders VALUES(null,4,4,700,'2011-3-7 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,1,5,700,'2011-8-11 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,2,6,800,'2011-7-27 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,2,2,600,'2011-9-26 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,1,4,650,'2011-10-24 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,4,4,640,'2015-12-4 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,4,6,630,'2015-5-8 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,2,2,620,'2015-3-14 12:12:00',2,1);
INSERT INTO Fuel_Orders VALUES(null,1,4,610,'2015-2-17 12:12:00',1,1);
INSERT INTO Fuel_Orders VALUES(null,2,6,680,'2015-2-21 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,4,2,690,'2015-12-13 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,2,4,640,'2015-11-14 12:12:00',3,2);
INSERT INTO Fuel_Orders VALUES(null,1,3,620,'2015-10-6 12:12:00',1,1);
INSERT INTO Fuel_Orders VALUES(null,1,5,650,'2015-9-7 12:12:00',2,1);

INSERT INTO Fuel_Per_Station VALUES(1,1,200,650,1600);
INSERT INTO Fuel_Per_Station VALUES(1,2,200,690,1700);
INSERT INTO Fuel_Per_Station VALUES(1,4,200,850,1900);
INSERT INTO Fuel_Per_Station VALUES(2,1,200,250,1600);
INSERT INTO Fuel_Per_Station VALUES(2,2,400,450,1800);
INSERT INTO Fuel_Per_Station VALUES(2,4,300,350,2000);
INSERT INTO Fuel_Per_Station VALUES(3,1,300,350,2100);
INSERT INTO Fuel_Per_Station VALUES(3,2,300,450,2600);
INSERT INTO Fuel_Per_Station VALUES(3,4,300,350,3600);
INSERT INTO Fuel_Per_Station VALUES(4,1,300,350,1900);
INSERT INTO Fuel_Per_Station VALUES(4,2,200,250,1500);
INSERT INTO Fuel_Per_Station VALUES(4,4,400,450,2600);
INSERT INTO Fuel_Per_Station VALUES(5,1,400,450,3600);
INSERT INTO Fuel_Per_Station VALUES(5,2,400,450,2600);
INSERT INTO Fuel_Per_Station VALUES(5,4,400,450,2600);
INSERT INTO Fuel_Per_Station VALUES(6,4,200,250,2300);
INSERT INTO Fuel_Per_Station VALUES(6,1,200,350,1600);
INSERT INTO Fuel_Per_Station VALUES(6,2,100,350,1775);

INSERT INTO Tariff_Update VALUES(null,'2015-9-7 12:1:00',1,4.5,7.7,3);
INSERT INTO Tariff_Update VALUES(null,'2015-7-23 12:2:00',2,4.8,7.7,3);
INSERT INTO Tariff_Update VALUES(null,'2015-6-24 11:12:00',4,4.7,6.9,3);
INSERT INTO Tariff_Update VALUES(null,'2015-4-7 10:14:00',2,5.5,7.6,2);
INSERT INTO Tariff_Update VALUES(null,'2015-3-7 9:22:00',1,4.8,8.6,2);
INSERT INTO Tariff_Update VALUES(null,'2015-2-27 8:20:00',2,4.2,8.6,2);
INSERT INTO Tariff_Update VALUES(null,'2014-9-7 12:22:00',1,4.3,7.6,1);
INSERT INTO Tariff_Update VALUES(null,'2014-4-17 7:11:00',1,4.4,7.6,1);
INSERT INTO Tariff_Update VALUES(null,'2014-8-7 5:17:00',2,4.7,8.6,2);
INSERT INTO Tariff_Update VALUES(null,'2014-5-16 3:14:00',4,4.9,7.6,1);
INSERT INTO Tariff_Update VALUES(null,'2014-4-14 12:16:00',4,3.5,6.4,2);
INSERT INTO Tariff_Update VALUES(null,'2013-1-13 8:14:00',2,3.5,6.9,1);
INSERT INTO Tariff_Update VALUES(null,'2013-2-11 9:19:00',1,3.5,7.6,1);
INSERT INTO Tariff_Update VALUES(null,'2013-9-6 5:18:00',4,3.5,7.6,2);
INSERT INTO Tariff_Update VALUES(null,'2013-9-9 7:22:00',1,3.8,6.7,2);
INSERT INTO Tariff_Update VALUES(null,'2013-9-12 8:2:00',2,3.5,9.6,1);

INSERT INTO Customers  VALUES(956321568,'David','Shall','Private',1,18,'052-4233658','2356-6589-6589-7852','David@gmail.com',2,1);
INSERT INTO Customers  VALUES(853629458,'Itzik','Lav','Commercial',2,19,'054-2659847','','Itzik@gmail.com',8,1);
INSERT INTO Customers  VALUES(302548695,'Nisim','Zok','Private',3,20,'052-4233658','2345-3456-6589-8520','Nisim@gmail.com',5,1);
INSERT INTO Customers  VALUES(302541654,'Him','Aesh','Commercial',1,21,'054-6598520','5528-9632-7418-2583','Him@gmail.com',3,1);
INSERT INTO Customers  VALUES(984884621,'Eden','Fali','Private',3,22,'057-2315468','7538-6589-9852-7852','Eden@gmail.com',5,2);
INSERT INTO Customers  VALUES(984651535,'Dror','Vir','Private',2,23,'054-8542031','2356-4521-6589-3547','Dror@gmail.com',2,1);
INSERT INTO Customers  VALUES(513813213,'Eli','Sol','Commercial',2,24,'054-8569521','','Eli@gmail.com',1,1);
INSERT INTO Customers  VALUES(681135135,'Ron','Van','Private',1,25,'052-3265874','6589-315-6589-8520','Ron@gmail.com',0,1);
INSERT INTO Customers  VALUES(026065065,'Roni','Israeli','Commercial',1,26,'052-6953268','','Roni@gmail.com',7,1);
INSERT INTO Customers  VALUES(065161615,'Rita','Itan','Commercial',3,27,'052-0231546','2356-6589-6589-7852','Rita@gmail.com',4,2);
INSERT INTO Customers  VALUES(329090511,'Zah','Levi','Private',1,28,'052-5216487','2356-6589-6589-7852','Zah@gmail.com',5,1);
INSERT INTO Customers  VALUES(302929161,'Sheli','Shaked','Private',2,29,'052-9587462','2356-6589-6589-7852','Sheli@gmail.com',6,1);
INSERT INTO Customers  VALUES(302298198,'Shalom','Sal','Private',2,30,'054-8520134','2356-1234-6589-3547','Shalom@gmail.com',2,1);
INSERT INTO Customers  VALUES(302926815,'Niv','Bor','Commercial',2,31,'054-9513265','','Niv@gmail.com',1,1);
INSERT INTO Customers  VALUES(302296816,'Shay','Kill','Private',1,32,'052-7894561','6589-3145-5423-8520','Shay@gmail.com',0,1);
INSERT INTO Customers  VALUES(302561566,'Sali','Funny','Commercial',1,33,'052-2563148','','Sali@gmail.com',7,1);
INSERT INTO Customers  VALUES(306216168,'Nir','Lod','Commercial',3,34,'052-0210225','2356-6589-7644-7852','Nir@gmail.com',4,2);
INSERT INTO Customers  VALUES(359681680,'Kfir','Bill','Private',1,35,'052-3355996','2356-6589-4444-7852','Kfir@gmail.com',5,1);
INSERT INTO Customers  VALUES(326594906,'Or','Levi','Private',2,36,'052-3266585','2345-6589-3333-7852','Or@gmail.com',6,1);

INSERT INTO Cars VALUES(null,'85-659-85',956321568,Default,1,1);
INSERT INTO Cars VALUES(null,'21-659-85',853629458,'No',2,1);
INSERT INTO Cars VALUES(null,'85-652-32',302548695,'Yes',1,2);
INSERT INTO Cars VALUES(null,'74-856-02',302541654,'No',4,3);
INSERT INTO Cars VALUES(null,'32-958-74',984884621,'No',2,4);
INSERT INTO Cars VALUES(null,'89-654-21',984651535,'No',1,1);
INSERT INTO Cars VALUES(null,'11-555-54',513813213,'No',2,2);
INSERT INTO Cars VALUES(null,'78-153-34',681135135,'No',4,1);
INSERT INTO Cars VALUES(null,'87-563-55',026065065,'No',1,2);
INSERT INTO Cars VALUES(null,'95-846-54',065161615,'No',1,1);
INSERT INTO Cars VALUES(null,'12-234-45',329090511,'No',2,2);
INSERT INTO Cars VALUES(null,'55-874-95',302929161,'Yes',4,3);
INSERT INTO Cars VALUES(null,'21-035-95',302298198,'Yes',2,4);
INSERT INTO Cars VALUES(null,'98-000-32',302926815,'No',2,1);
INSERT INTO Cars VALUES(null,'78-555-96',302296816,'Yes',1,2);
INSERT INTO Cars VALUES(null,'21-236-50',302561566,'Yes',4,3);
INSERT INTO Cars VALUES(null,'12-985-44',306216168,'Yes',2,4);
INSERT INTO Cars VALUES(null,'42-635-95',359681680,'No',1,1);
INSERT INTO Cars VALUES(null,'78-542-63',326594906,'Yes',2,2);
INSERT INTO Cars VALUES(null,'12-035-62',956321568,'No',4,1);
INSERT INTO Cars VALUES(null,'78-953-20',853629458,'Yes',1,2);
INSERT INTO Cars VALUES(null,'02-352-65',302548695,'No',1,1);
INSERT INTO Cars VALUES(null,'45-652-35',302541654,'Yes',2,2);
INSERT INTO Cars VALUES(null,'98-785-65',984884621,'No',4,3);
INSERT INTO Cars VALUES(null,'25-632-50',984651535,'No',2,4);
INSERT INTO Cars VALUES(null,'31-264-95',513813213,'No',2,1);
INSERT INTO Cars VALUES(null,'32-659-84',681135135,'Yes',1,2);
INSERT INTO Cars VALUES(null,'12-320-65',026065065,'No',4,3);
INSERT INTO Cars VALUES(null,'98-632-95',065161615,'No',2,4);
INSERT INTO Cars VALUES(null,'23-654-85',329090511,'No',1,1);
INSERT INTO Cars VALUES(null,'32-456-85',302929161,'No',2,2);
INSERT INTO Cars VALUES(null,'10-963-52',302298198,'No',4,1);
INSERT INTO Cars VALUES(null,'10-842-95',302926815,'No',1,2);
INSERT INTO Cars VALUES(null,'95-642-32',302296816,'No',1,1);
INSERT INTO Cars VALUES(null,'12-632-85',302561566,'Yes',2,2);
INSERT INTO Cars VALUES(null,'10-845-98',306216168,'Yes',4,3);
INSERT INTO Cars VALUES(null,'23-468-95',359681680,'Yes',2,4);
INSERT INTO Cars VALUES(null,'10-456-85',326594906,'No',2,1);
INSERT INTO Cars VALUES(null,'20-352-61',956321568,'Yes',1,2);
INSERT INTO Cars VALUES(null,'10-623-98',853629458,'No',4,3);
INSERT INTO Cars VALUES(null,'79-852-30',302548695,'No',2,4);
INSERT INTO Cars VALUES(null,'84-951-32',302541654,'No',1,1);
INSERT INTO Cars VALUES(null,'75-842-91',984884621,'No',2,2);
INSERT INTO Cars VALUES(null,'42-956-35',984651535,'No',4,1);
INSERT INTO Cars VALUES(null,'20-951-86',513813213,'No',1,2);
INSERT INTO Cars VALUES(null,'32-654-82',681135135,'Yes',1,1);
INSERT INTO Cars VALUES(null,'12-030-95',026065065,'No',2,2);
INSERT INTO Cars VALUES(null,'12-005-78',065161615,'Yes',4,3);
INSERT INTO Cars VALUES(null,'32-000-65',329090511,'No',2,4);

INSERT INTO Campaign_Type VALUES(null,'Campaighn - Discount betweem hours.');
INSERT INTO Campaign_Type VALUES(null,'Campaighn - Discount betweem days (Date).');
INSERT INTO Campaign_Type VALUES(null,'Campaighn - Discount on spasific type of fuel.');
INSERT INTO Campaign_Type VALUES(null,'Campaighn - Discount on gas above spasific amount.');
INSERT INTO Campaign_Type VALUES(null,'Campaighn - Discount in gas station.');
INSERT INTO Campaign_Type VALUES(null,'Campaighn - Discount on customer rate.');

INSERT INTO Campaign_Patterns VALUES(null,'Discount of 10% between 2:00 - 4:00.',1,'0.1');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 15% between 4:00 - 5:30',1,'0.15');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 5% in Hanuka.',2,'0.05'); 
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 3% on Scooters Fuel.',3,'0.03');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 5% on 95 Fuel',3,'0.05');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 5% on diesel.',3,'0.05');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 10% on refueling over 60 liter.',4,'0.1');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 20% on refueling over 150 liter',4,'0.2');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 10% on refueling in Tel Aviv gas station.',5,'0.1');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 10% on refueling in Jerusalem gas station',5,'0.1');
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 7% in Rosh Hashana.',2,'0.07'); 
INSERT INTO Campaign_Patterns VALUES(null,'Discount of 20% for customers that have rating above 7.',6,'0.2'); 

INSERT INTO Campaigns VALUES(null,3,'2015-12-7','2015-12-14','Yes');
INSERT INTO Campaigns VALUES(null,11,'2014-12-7','2014-12-14','No');
INSERT INTO Campaigns VALUES(null,1,'2015-11-18','2015-12-14','Yes');
INSERT INTO Campaigns VALUES(null,2,'2015-11-21','2015-11-30','Yes');
INSERT INTO Campaigns VALUES(null,4,'2015-9-7','2015-12-14','Yes');
INSERT INTO Campaigns VALUES(null,5,'2015-9-3','2015-9-17','No');
INSERT INTO Campaigns VALUES(null,6,'2015-4-16','2015-5-1','Yes');
INSERT INTO Campaigns VALUES(null,7,'2015-11-17','2015-12-1','Yes');
INSERT INTO Campaigns VALUES(null,8,'2015-8-23','2014-8-28','No');
INSERT INTO Campaigns VALUES(null,9,'2015-10-7','2014-11-29','Yes');
INSERT INTO Campaigns VALUES(null,10,'2014-11-26','2014-12-29','No');
INSERT INTO Campaigns VALUES(null,12,'2014-9-26','2014-10-13','No');
INSERT INTO Campaigns VALUES(null,12,'2014-2-26','2014-4-13','No');

INSERT INTO Campaigns_Date VALUES(3,'Hanuka.');
INSERT INTO Campaigns_Date VALUES(11,'Rosh Hashana.');
INSERT INTO Campaigns_Hours VALUES(1,'2:00:00','4:00:00');
INSERT INTO Campaigns_Hours VALUES(2,'4:00:01','5:30:00');
INSERT INTO Campaigns_Gas_Type VALUES(4,2);
INSERT INTO Campaigns_Gas_Type VALUES(5,1);
INSERT INTO Campaigns_Gas_Type VALUES(6,4);
INSERT INTO Campaigns_Amount VALUES(7,60);
INSERT INTO Campaigns_Amount VALUES(8,150);
INSERT INTO Campaigns_Gas_Station VALUES(9,2);
INSERT INTO Campaigns_Gas_Station VALUES(10,3);
INSERT INTO Campaigns_Rate VALUES(12,7);


INSERT INTO Sales VALUES(null,1,'2015-12-7 10:14:00',50,203.5,956321568);
INSERT INTO Sales VALUES(null,1,'2015-3-17 11:34:00',34,106.5,956321568);
INSERT INTO Sales VALUES(null,1,'2015-2-13 12:23:00',32,103.5,853629458);
INSERT INTO Sales VALUES(null,1,'2015-5-17 2:32:00',34,106.5,853629458);
INSERT INTO Sales VALUES(null,1,'2015-7-7 3:41:00',52,123.5,302548695);
INSERT INTO Sales VALUES(null,1,'2015-8-7 4:12:00',41,143.5,302548695);
INSERT INTO Sales VALUES(null,1,'2015-4-27 5:53:00',14,132.5,302541654);
INSERT INTO Sales VALUES(null,1,'2015-8-23 6:34:00',41,165.5,302541654);
INSERT INTO Sales VALUES(null,1,'2015-10-7 2:23:00',60,123.5,984884621);
INSERT INTO Sales VALUES(null,1,'2015-2-27 3:51:00',34,274.5,984884621);
INSERT INTO Sales VALUES(null,1,'2015-5-5 3:35:00',54,132.5,302926815);
INSERT INTO Sales VALUES(null,1,'2015-1-4 1:39:00',45,123.5,302929161);
INSERT INTO Sales VALUES(null,2,'2015-1-18 9:23:00',34,265.5,984651535);
INSERT INTO Sales VALUES(null,2,'2015-9-18 8:41:00',40,234.5,984651535);
INSERT INTO Sales VALUES(null,2,'2015-1-7 7:11:00',41,298.5,513813213);
INSERT INTO Sales VALUES(null,2,'2015-4-19 6:2:00',31,231.5,513813213);
INSERT INTO Sales VALUES(null,2,'2015-8-21 8:32:00',23,142,681135135);
INSERT INTO Sales VALUES(null,2,'2015-5-20 7:4:00',31,123.5,681135135);
INSERT INTO Sales VALUES(null,2,'2015-6-26 9:51:00',22,321.5,026065065);
INSERT INTO Sales VALUES(null,2,'2015-7-17 12:16:00',11,234,026065065);
INSERT INTO Sales VALUES(null,2,'2015-8-18 11:29:00',12,123.5,065161615);
INSERT INTO Sales VALUES(null,2,'2015-9-28 11:28:00',41,253.5,065161615);
INSERT INTO Sales VALUES(null,2,'2015-12-26 10:26:00',23,135,329090511);
INSERT INTO Sales VALUES(null,2,'2015-7-4 3:49:00',46,153.5,302296816);
INSERT INTO Sales VALUES(null,3,'2015-11-27 10:27:00',100,678.5,329090511);
INSERT INTO Sales VALUES(null,3,'2015-12-3 22:38:00',134,654.5,302929161);
INSERT INTO Sales VALUES(null,3,'2015-2-5 4:37:00',196,643,302298198);
INSERT INTO Sales VALUES(null,3,'2015-4-7 6:36:00',132,623.5,302926815);
INSERT INTO Sales VALUES(null,3,'2015-6-8 7:48:00',111,623.5,302296816);
INSERT INTO Sales VALUES(null,4,'2015-3-6 5:33:00',45,143.5,302298198);
INSERT INTO Sales VALUES(null,4,'2015-8-2 3:47:00',23,165.5,302561566);
INSERT INTO Sales VALUES(null,4,'2015-9-10 3:46:00',42,198.5,302561566);
INSERT INTO Sales VALUES(null,4,'2015-10-19 8:43:00',52,103,306216168);
INSERT INTO Sales VALUES(null,4,'2015-11-17 7:42:00',40,123.5,306216168);
INSERT INTO Sales VALUES(null,4,'2015-12-16 6:41:00',30,109,359681680);
INSERT INTO Sales VALUES(null,4,'2015-6-15 9:27:00',37,190.5,359681680);
INSERT INTO Sales VALUES(null,4,'2015-4-14 5:26:00',36,194,326594906);
INSERT INTO Sales VALUES(null,4,'2015-10-30 6:38:00',38,201.5,326594906);

INSERT INTO Gas_Stations_Sales VALUES(1,'David',1,1,1);
INSERT INTO Gas_Stations_Sales VALUES(2,'David',1,2,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(3,'Itzik',2,3,6);
INSERT INTO Gas_Stations_Sales VALUES(4,'Itzik',2,4,1);
INSERT INTO Gas_Stations_Sales VALUES(5,'Nisim',3,5,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(6,'Nisim',3,6,4);
INSERT INTO Gas_Stations_Sales VALUES(7,'Him',4,1,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(8,'Him',4,2,2);
INSERT INTO Gas_Stations_Sales VALUES(9,'Eden',5,3,6);
INSERT INTO Gas_Stations_Sales VALUES(10,'Eden',5,4,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(11,'Niv',14,5,4);
INSERT INTO Gas_Stations_Sales VALUES(12,'Sheli',16,6,3);
INSERT INTO Gas_Stations_Sales VALUES(13,'Dror',6,1,7);
INSERT INTO Gas_Stations_Sales VALUES(14,'Dror',6,2,4);
INSERT INTO Gas_Stations_Sales VALUES(15,'Eli',7,3,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(16,'Eli',7,4,4);
INSERT INTO Gas_Stations_Sales VALUES(17,'Ron',8,5,5);
INSERT INTO Gas_Stations_Sales VALUES(18,'Ron',8,6,3);
INSERT INTO Gas_Stations_Sales VALUES(19,'Roni',9,1,6);
INSERT INTO Gas_Stations_Sales VALUES(20,'Roni',9,2,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(21,'Rita',10,3,4);
INSERT INTO Gas_Stations_Sales VALUES(22,'Rita',10,4,7);
INSERT INTO Gas_Stations_Sales VALUES(23,'Zah',11,5,8);
INSERT INTO Gas_Stations_Sales VALUES(24,'Shay',15,6,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(30,'Shalom',13,1,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(31,'Sali',16,2,5);
INSERT INTO Gas_Stations_Sales VALUES(32,'Sali',16,3,3);
INSERT INTO Gas_Stations_Sales VALUES(33,'Nir',17,4,11);
INSERT INTO Gas_Stations_Sales VALUES(34,'Nir',17,5,9);
INSERT INTO Gas_Stations_Sales VALUES(35,'Kfir',18,6,DEFAULT);
INSERT INTO Gas_Stations_Sales VALUES(36,'Kfir',18,1,10);
INSERT INTO Gas_Stations_Sales VALUES(37,'Or',19,2,10);
INSERT INTO Gas_Stations_Sales VALUES(38,'Or',19,3,DEFAULT);

INSERT INTO Home_Fuel_Sales VALUES(25,'2015-12-5 12:16:00','Hneviem 8','Not Delivered');
INSERT INTO Home_Fuel_Sales VALUES(26,'2015-1-2 15:23:00','Israeli 34','Delivered');
INSERT INTO Home_Fuel_Sales VALUES(27,'2015-1-7 23:42:00','Balfur 6','Delivered');
INSERT INTO Home_Fuel_Sales VALUES(28,'2015-5-22 11:34:00','Hadar 12','Delivered');
INSERT INTO Home_Fuel_Sales VALUES(29,'2015-8-2 19:45:00','Sharon 34','Delivered');

CREATE VIEW Customer_Detailes AS
SELECT A.User_ID
	,A.Customers_ID
    ,A.Customer_First_Name
    ,A.Customer_Last_Name
    ,A.Customer_Type
    ,C.Plan_Name
    ,A.Phone_Number
    ,A.Credit_Card
    ,A.Email
    ,A.Carrent_Rate
    ,A.IS_Active
    ,B.User_Name
    ,B.User_Password
    ,D.User_Type_ID
    ,D.User_Privilege
    ,B.Logged_In
FROM Customers A
LEFT OUTER JOIN Users B ON B.User_ID = A.User_ID
LEFT OUTER JOIN Purchase_Plan C ON A.Plan_ID=C.Plan_ID
LEFT OUTER JOIN User_Type D ON B.User_Type_Id=D.User_Type_Id
;

CREATE VIEW All_Campaign_On_System AS
SELECT C.Campaign_ID
	, C.Start_Campaign
	, C.End_Campaign
	, C.IS_Active
	, A.Campaign_Description
    , B.Calculation_Method
    , A.Discount_Percentage
    , A.Campaign_Patterns_ID
FROM Campaigns C
LEFT OUTER JOIN Campaign_Patterns A ON A.Campaign_Patterns_ID=C.Campaign_Patterns_ID
LEFT OUTER JOIN Campaign_Type B ON A.Campaign_Type_ID=B.Campaign_Type_ID
;

/*Gas Stations Sale */
CREATE VIEW All_Gas_Stations_Sales AS
SELECT A.Sales_ID
,A.Sale_Date
,A.Fuel_Amount
,A.Payment
,E.Fuel_Description
,B.Driver_Name
,B.Campaign_ID
,I.Campaign_Description
,G.Gas_Station_ID
,G.Station_Name
,G.Station_Address
,J.Gas_Company_Name
,F.Car_Number
,D.Plan_Name
,C.Customers_ID
,C.Customer_First_Name
,C.Customer_Last_Name
,C.User_ID
FROM Gas_Stations_Sales B
LEFT OUTER JOIN Sales A ON A.Sales_ID=B.Sales_ID
LEFT OUTER JOIN Customers C ON A.Customers_ID=C.Customers_ID
LEFT OUTER JOIN Purchase_Plan D ON C.Plan_ID=D.Plan_ID
LEFT OUTER JOIN Fuels E ON E.Fuel_ID=A.Fuel_ID
LEFT OUTER JOIN Cars F ON B.Car_ID=F.Car_ID
LEFT OUTER JOIN Gas_Stations G ON B.Gas_Station_ID=G.Gas_Station_ID
LEFT OUTER JOIN Campaigns H ON B.Campaign_ID=H.Campaign_ID
LEFT OUTER JOIN Campaign_Patterns I ON H.Campaign_Patterns_ID=I.Campaign_Patterns_ID
LEFT OUTER JOIN Gas_Company J ON G.Gas_Company_ID=J.Gas_Company_ID
-- WHERE Customers_ID = /// User_ID = 
;

/*Sales Home Fuel*/
CREATE VIEW All_Home_Fuel_Sales AS
SELECT A.Sales_ID
,A.Sale_Date
,A.Fuel_Amount
,A.Payment
,E.Fuel_Description
,B.Delivery_Date
,B.Address
,B.Order_Status
,D.Plan_Name
,C.Customers_ID
,C.Customer_First_Name
,C.Customer_Last_Name
,C.User_ID
FROM Home_Fuel_Sales B
LEFT OUTER JOIN Sales A ON A.Sales_ID=B.Sales_ID
LEFT OUTER JOIN Customers C ON A.Customers_ID=C.Customers_ID
LEFT OUTER JOIN Purchase_Plan D ON C.Plan_ID=D.Plan_ID
LEFT OUTER JOIN Fuels E ON E.Fuel_ID=A.Fuel_ID
-- WHERE Customers_ID = /// User_ID = 
;

/*All_Users_Detaile*/
CREATE VIEW All_Users_Detailes AS
SELECT A.*
,B.User_Privilege
,C.Customer_First_Name
,C.Customer_Last_Name
,C.Email
FROM Users A
LEFT OUTER JOIN User_Type B ON A.User_Type_Id=B.User_Type_Id
RIGHT OUTER JOIN Customers C ON A.User_ID=C.User_ID

UNION

SELECT A.*
,B.User_Privilege
,C.Worker_First_Name
,C.Worker_Last_Name
,C.Email
FROM Users A
LEFT OUTER JOIN User_Type B ON A.User_Type_Id=B.User_Type_Id
RIGHT OUTER JOIN Workers C ON A.User_ID=C.User_ID

UNION

SELECT A.*
,B.User_Privilege
,CONCAT('To ',C.Station_Name) AS Station_Name
,'Station' AS Worker_Last_Name
,' ' AS Email
FROM Users A
LEFT OUTER JOIN User_Type B ON A.User_Type_Id=B.User_Type_Id
RIGHT OUTER JOIN Gas_Stations C ON A.User_ID = C.User_Interface_ID
;
/*
CREATE VIEW Customers_Purchase_By_Fuel_Company AS
SELECT DISTINCT A.Customers_ID
, A.Customer_First_Name
, A.Customer_Last_Name
, IFNULL(B.Paz,0) AS Paz
, IFNULL(C.Sonol,0) AS Sonol
, IFNULL(D.Yellow,0) AS Yellow
, IFNULL(E.Ten,0) AS Ten
, IFNULL(F.Total,0) AS Total
FROM All_Gas_Stations_Sales A
LEFT OUTER JOIN (
	SELECT  Customers_ID
	,COUNT(*) AS Paz
	FROM All_Gas_Stations_Sales 
	WHERE Gas_Company_Name='Paz'
    GROUP BY Customers_ID
) B ON A.Customers_ID=B.Customers_ID

LEFT OUTER JOIN (
	SELECT  Customers_ID
	,COUNT(*) AS Sonol
	FROM All_Gas_Stations_Sales 
	WHERE Gas_Company_Name='Sonol'
    GROUP BY Customers_ID
) C ON A.Customers_ID=C.Customers_ID

LEFT OUTER JOIN (
	SELECT  Customers_ID
	,COUNT(*) AS Yellow
	FROM All_Gas_Stations_Sales
	WHERE Gas_Company_Name='Yellow'
    GROUP BY Customers_ID
) D ON A.Customers_ID=D.Customers_ID

LEFT OUTER JOIN (
	SELECT  Customers_ID
	,COUNT(*) AS Ten
	FROM All_Gas_Stations_Sales
	WHERE Gas_Company_Name='Ten'
    GROUP BY Customers_ID
) E ON A.Customers_ID=E.Customers_ID

LEFT OUTER JOIN (
	SELECT  Customers_ID
	,COUNT(*) AS Total
	FROM All_Gas_Stations_Sales
    GROUP BY Customers_ID
) F ON A.Customers_ID=F.Customers_ID
ORDER BY F.Total DESC
;
*/
CREATE VIEW Fuel_Orders_For_Stations AS
SELECT A.Order_ID
, A.Fuel_ID
, B.Fuel_Description
, A.Gas_Station_ID
, A.Amount AS Amount_To_Order
, E.Current_Amount
, A.Order_Date
, A.Order_Confirmation
, A.Showed_To_Manager
, C.Station_Name
, C.Gas_Station_Manager_ID
, D.Gas_Company_Name
FROM Fuel_Orders A
LEFT OUTER JOIN Fuels B ON A.Fuel_ID=B.Fuel_ID
LEFT OUTER JOIN Gas_Stations C ON A.Gas_Station_ID=C.Gas_Station_ID
LEFT OUTER JOIN Gas_Company D ON C.Gas_Company_ID=D.Gas_Company_ID
LEFT OUTER JOIN Fuel_Per_Station E ON A.Gas_Station_ID=E.Gas_Station_ID AND A.Fuel_ID=E.Fuel_ID
ORDER BY A.Order_ID
;

CREATE VIEW All_Patterns_On_System AS
SELECT A.Campaign_Patterns_ID
	, A.Campaign_Description
	, A.Campaign_Type_ID
	, A.Discount_Percentage
    , B.Calculation_Method
    , D.Amount
    , E.Date_Description
    , H.Station_Name
    , I.Fuel_Description
    , J.Start_Hour
    , J.End_Hour
    , K.Customer_Rate
    
FROM Campaign_Patterns A
LEFT OUTER JOIN Campaign_Type B ON A.Campaign_Type_ID=B.Campaign_Type_ID
LEFT OUTER JOIN Campaigns_Amount D ON A.Campaign_Patterns_ID=D.Campaign_Patterns_ID
LEFT OUTER JOIN Campaigns_Date E ON A.Campaign_Patterns_ID=E.Campaign_Patterns_ID
LEFT OUTER JOIN Campaigns_Gas_Station F ON A.Campaign_Patterns_ID=F.Campaign_Patterns_ID
LEFT OUTER JOIN Gas_Stations H ON F.Gas_Station_ID=H.Gas_Station_ID
LEFT OUTER JOIN Campaigns_Gas_Type G ON A.Campaign_Patterns_ID=G.Campaign_Patterns_ID
LEFT OUTER JOIN Fuels I ON I.Fuel_ID=G.Fuel_ID
LEFT OUTER JOIN Campaigns_Hours J ON A.Campaign_Patterns_ID=J.Campaign_Patterns_ID
LEFT OUTER JOIN Campaigns_Rate K ON A.Campaign_Patterns_ID=K.Campaign_Patterns_ID
;

CREATE VIEW Fuel_For_Gas_Station AS
SELECT A.Gas_Station_ID
, A.Fuel_ID
, A.Threshold_Limit
, A.Current_Amount
, A.Capacity
, B.Fuel_Description
, B.Max_Price
, B.Current_Price
FROM Fuel_Per_Station A
LEFT OUTER JOIN Fuels B ON A.Fuel_ID=B.Fuel_ID
;