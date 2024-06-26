create table Account_type(
acc_type_id int not null primary key  AUTO_INCREMENT,
acc_type_name varchar(20) not null
);
create table Users(
user_id int primary key NOT NULL AUTO_INCREMENT,
email varchar(70) not null unique,
pass varchar(64) DEFAULT NULL,
acc_image blob DEFAULT NULL,
verified boolean DEFAULT FALSE,
secuity_token varchar(30)  DEFAULT NULL,
security_code int DEFAULT NULL,
security_date timestamp DEFAULT CURRENT_TIMESTAMP,
phone varchar(10) DEFAULT NULL,
banovan boolean default true,
acc_type_id int not null,
Foreign key (acc_type_id) references Account_type(acc_type_id)
);
create table  Amenities(
amenity_id int primary key NOT NULL AUTO_INCREMENT,
amenity_name varchar(20) not null,
amenity_desc text not null,
banovan boolean default true,
amenity_image blob not null
);
create table country(
county_id int primary key NOT NULL AUTO_INCREMENT,
country_name varchar(30) not null,
banovan boolean default true
);
CREATE TABLE city (
    city_id INT PRIMARY KEY AUTO_INCREMENT,
    city_name VARCHAR(40) NOT NULL,
    country_id INT NOT NULL,
    banovan BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (country_id) REFERENCES country(county_id)
);
create table Hotel(
hotel_id int primary key NOT NULL AUTO_INCREMENT,
hotel_name varchar(20) not null,
city_id  INT not null,
adress text not null,
banovan boolean default true,
stars int not null,
hotel_main_ing blob not null,
hotel_desc text DEFAULT NULL,
 FOREIGN KEY (city_id) REFERENCES city(city_id)
);
create table Room_type(
room_type_id int primary key NOT NULL AUTO_INCREMENT,
no_beds int not null,
room_type_name varchar(20) not null,
desribe text DEFAULT NULL,
hotel_id int not null,
foreign key (hotel_id) references Hotel(hotel_id),
banovan boolean default true
);
create table Room_type_Image(
room_img_id int primary key NOT NULL AUTO_INCREMENT,
room_type_id int not null,
image blob not null,
banovan boolean default true,
foreign key (room_type_id) references Room_type(room_type_id)
);
create table Room(
room_id int primary key NOT NULL AUTO_INCREMENT,
room_number int not null,
room_name varchar(30) DEFAULT NULL,
banovan boolean default true,
room_type_id int not null,
price int not null,
foreign key (room_type_id) references Room_type(room_type_id)
);
create table Hotem_amenities(
hotel_id int not null,
amenity_id int not null,
foreign key (hotel_id) references Hotel(hotel_id),
primary key (hotel_id,amenity_id)
);
create table Room_amenity(
room_type_id int not null primary key,
amenity_id int not null,
foreign key (room_type_id) references Room_type(room_type_id)
);
create table HotelRewiev(
hotel_id int not null,
user_id int not null,
stars int not null,
review text not null,
foreign key (user_id) references Users(user_id),
primary key (hotel_id,user_id)
);
create table Reservations(
reservation_id int primary key NOT NULL AUTO_INCREMENT,
room_id int not null,
user_id int not null,
price int not null,
dateFrom DAte not null,
dateTo DATE not null,
foreign key (room_id) references Room(room_id),
foreign key (user_id) references Users(user_id)
);
CREATE TABLE HotelImage(
hotel_id int not null,
hotel_img_id int not null primary key auto_increment,
image blob not null,
foreign key (hotel_id) references Hotel(hotel_id)
);
insert into Account_type(acc_type_name) VALUES ('User');
insert into Account_type(acc_type_name) VALUES ('Manager');
insert into Account_type(acc_type_name) VALUES ('Admin');

insert into Users(email,pass,phone,verified,acc_type_id) VALUES('aki.velimirovic',PASSWORD('aleksa'),'0643472608',true,3);
Select * from Users where email = 'aki.velimirovic' and pass = Password('aleksa');
Select * from Users where user_id =2;
ALTER TABLE Hotel
ADD userId int;
ALTER TABLE Hotel
ADD foreign key (userId) references Users(user_id)
AlTER TABLE ROOM
ADD PRICE INT
AlTER TABLE ROOM_TYPE
aDD PRICE INT
