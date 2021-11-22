create database Cinema;
use Cinema;


create Table Films(
	FID varchar(10) primary key,
    FName varchar(500) not null unique,
    Genre varchar(150) not null,
    Duration int not null,
    ReleaseDate date,
    Origin varchar(10) not null,
    Lang varchar(150) not null,
    Restriction varchar(3) not null
);

create table Customer(
	CPhone varchar(10) primary key,
    CName varchar(55) not null,
    Age int not null,
    Membership varchar(10) not null
    );

create table Theaters(
	TID varchar(10) primary key,
    TName varchar(100) not null,
    Location varchar(50) not null,
    Hotline int not null,
    Opening varchar(15) not null
    );
    
 create table ShowTime(  
	SID varchar(10) primary key,
    TotalSlot int not null,
    AvailSlot int not null,
    Screen int not null,
    StartTime varchar(10) not null, -- xem các suất chiếu là như nhau giữa các ngày
    FID varchar(10),
    TID varchar(10),
    foreign key (FID) references Films(FID),
    foreign key (TID) references Theaters(TID)
    );
create table Ticket(
	TKID varchar(10) primary key,
    Price int not null,
    Seat int not null unique,
    SeatCat varchar(10),
    TkDay date not null, 
    FID varchar(10),
    TID varchar(10),
    SID varchar(10),
    foreign key (FID) references Films(FID),
    foreign key (TID) references Theaters(TID),
    foreign key (SID) references ShowTime(SID)
    );


insert into Films values('H01', 'The Conjuring', 'Horror', 112, '2021-10-29', 'USA', ' English with Vietnamese subtitles', 'C16');
insert into Films values('H02', 'Wrong Turn: The Foundation', 'Horror', 105, '2021-10-29', 'USA', ' English with Vietnamese subtitles', 'C18');
insert into Films values('HT03', 'Great White', 'Horror, Thriller', 91, '2021-10-29', 'USA', ' English with Vietnamese subtitles', 'C16');
insert into Films values('AF01', 'The Eternals', 'Action, Adventure, Fantasy', 115, '2021-11-19', 'USA', ' English with Vietnamese subtitles', 'C16');
insert into Films values('AF02', 'Venom 2', 'Action, Adventure, Fantasy, Science Fiction', 100, '2021-12-03', 'USA', ' English with Vietnamese subtitles', 'C16');
insert into Films values('A01', 'No Time To Die', 'Action', 120, '2021-11-26', 'USA', ' English with Vietnamese subtitles', 'C18');
insert into Films values('A02', 'Shang-Chi And The Legend Of Ten Rings', 'Action', 112, '2021-11-12', 'USA', ' English with Vietnamese subtitles', 'C16');
insert into Films values('DT01', 'The Guardian', 'Drama, Thriller', 124, '2021-11-05', 'VietNam', ' Vietnamese with English subtitles', 'C18');
insert into Films values('ADT1', 'Those Who Wish Me Dead', 'Action, Drama, Thriller', 100, '2021-11-05', 'USA', ' English with Vietnamese subtitles', 'C18');

select * from Customer;
select * from Ticket;

insert into Customer values('0939215213', 'Nguyen Quoc Viet', 16, 'U22');
insert into Customer values('0915366374', 'Ho Trung Dung', 18, 'U22');
insert into Customer values('0710345875', 'Tran Thi Truc Thu', 23, 'VIP');
insert into Customer values('0546258712', 'Truong Phu Tuan', 20, 'U22');
insert into Customer values('0386547125', 'Nguyen Trung Hau', 25, 'VIP');
    
insert into Theaters values('HCM1', 'CGV Hung Vuong Plaza', 'Ho Chi Minh City', 18001547, '8h-22h');
insert into Theaters values('HCM2', 'CGV Aeon Tan Phu', 'Ho Chi Minh City', 18002641, '8h-22h');
insert into Theaters values('HCM3', 'CGV Vincom Center Lanmark 81', 'Ho Chi Minh City', 18002519, '8h-22h');
insert into Theaters values('HCM4', 'CGV Giga Mall Thu Duc', 'Ho Chi Minh City', 18004638, '8h-22h');
insert into Theaters values('CT1', 'CGV Sense City', 'Can Tho City', 18006421, '8h-22h');
insert into Theaters values('CT2', 'CGV Vincom Hung Vuong', 'Can Tho City', 18005291, '8h-22h');
insert into Theaters values('CT3', 'CGV Vincom Xuan Khanh', 'Can Tho City', 18001563, '8h-22h');
insert into Theaters values('KG', 'CGV Vincom Rach Gia', 'Kien Giang Provice', 18004285, '8h-22h');
insert into Theaters values('VL', 'CGV Vincom Vinh Long', 'Vinh Long Province', 18002347, '8h-22h');

insert into Showtime values('SAHCM', 120, 120, 7, '8h15', 'H01', 'HCM1');
insert into Showtime values('CHCT', 100, 100, 5, '18h', 'ADT1', 'CT1');
insert into Showtime values('CHCT2', 100, 100, 2, '15h', 'DT01', 'CT3');
insert into Showtime values('SACT', 95, 80, 3, '11h', 'A02', 'CT2');
insert into Showtime values('SACT2', 100, 100, 4, '10h15', 'A02', 'CT1');
insert into Showtime values('CHVL', 80, 50, 4, '12h45', 'AF02', 'VL');
insert into Showtime values('CHVL2', 80, 75, 2, '9h15', 'AF01', 'VL');

INSERT INTO Ticket values('T1', 95000, 25, 'VIP', '2021-11-06', 'ADT1', 'CT1', 'CHCT');
INSERT INTO Ticket values('T3', 90000, 22, 'VIP', '2021-12-06', 'AF01', 'VL', 'CHVL2');
INSERT INTO Ticket values('T4', 65000, 17, 'Normal', '2021-10-29', 'H01', 'HCM1', 'SAHCM');

    
 select * from ticket;   
use Cinema;

-- Add a new film
DELIMITER //
create procedure add_films(pid varchar(10), pname varchar(500), pgenre varchar(150),
				plast int, prelease date, porigin varchar(10), plang varchar(150), plimit varchar(3))
BEGIN 
	insert into Films values(pid, pname, pgenre, prelease, pdate, porigin, plang, plimit);
    commit;
END //

DELIMITER ;

call add_films('AF05', 'Godzilla vs. Kong', 'Action, Fantasy', 113, '2021-03-26', 'USA', ' English with Vietnamese subtitles', 'C13');

-- Add a new customer
DELIMITER //
create procedure add_customer(pcphone varchar(10), pcname varchar(55), p_age int, pmem varchar(10))
BEGIN
	INSERT INTO customer VALUES(pcphone, pcname, p_age, pmem);
    commit;
END //
DELIMITER ;

CALL add_customer('0375481252', 'Vu Minh Duc', 22, 'VIP');

-- Add a Theaters
DELIMITER //
create procedure add_theaters(ptid varchar(10), ptname varchar(100), plocate varchar(50), 
										photline int, popening varchar(15))
BEGIN
	INSERT INTO theaters VALUES(ptid, ptname, plocate, photline, popening);
    commit;
END //
DELIMITER ;

CALL add_theaters('HN1','CGV Vincom Ba Trieu', 'Ha Noi', 18002341, '8h-22h');
-- Add a showtime(show films_id & theaters_id first)
select FID, FName
from films;

DELIMITER //
create procedure city(plocation varchar(50))  -- nhap ten thanh pho de hien thi ma rap chieu phim can them suat chieu truoc
BEGIN
	select TID, TName
	from theaters
	where location = plocation;
END //
DELIMITER ;
call city('Ho Chi Minh City');

DELIMITER //
create procedure add_Showtime(psid varchar(10), pTotSlot int, pAvailSlot int, 
							Screen int, StartTime varchar(10), FID varchar(10), TID varchar(10))
BEGIN
	INSERT INTO Showtime values(psid, pTotSlot, pAvailSlot, Screen, StartTime, FID, TID);
    commit;
END //
DELIMITER ;
CALL add_Showtime('SAHN', 110, 50, 5, '15h', 'DT01', 'HN1');
-- Add a ticket
select fid, fname from films;

select tid, tname from theaters;

select Sid, TotalSlot, AvailSlot, fname, f.fid, Screen, StartTime
from films f join showtime s on f.fid=s.fid;

-- raise error if sid not linked to fname
DELIMITER //
create procedure add_tkt(p_tkt varchar(10), p_tkday date, p_fid varchar(10), p_tid varchar(10), 
									p_sid varchar(10), p_seat int, p_SeatCat varchar(10), p_price int)
begin
	insert into Ticket (tkid, TkDay, FID, TID, SID, seat, seatcat, price) values 
								(p_tkt, p_tkday, p_fid, p_tid, p_sid, p_seat, p_SeatCat, p_price);
end //
DELIMITER ;

call add_tkt('T2', '2021-12-01', 'DT01', 'CT3', 'CHCT2', 40, 'Normal', 65000);


use Cinema;
-- Show list of films: by Genre
DELIMITER //
CREATE PROCEDURE genre(pgenre varchar(10)) 
BEGIN   
    SELECT * FROM films WHERE genre like pgenre; 
END; //
DELIMITER ;

CALL GENRE('%Action%');


-- Show list of films: by Release month
DELIMITER //
CREATE PROCEDURE rmonth(pmonth int) 
BEGIN   
    SELECT * FROM films WHERE extract(month from ReleaseDate) = pmonth; 
END; //
DELIMITER ;

CALL rmonth(11);

-- Show list of films: by Restriction <not done yet>
DELIMITER $$
CREATE PROCEDURE agelim(p_age int) 
BEGIN   
	IF p_age >= 18 THEN
		SELECT * FROM films; 
    ELSEIF p_age >= 16 AND p_age < 18 THEN
		SELECT * FROM films where restriction != 'C18';
	ELSEIF p_age >= 13 AND p_age < 16 THEN
		SELECT * FROM films where restriction != 'C18' AND restriction != 'C16';
	ELSE
		SELECT * from films where restriction = 'P';
	END IF;
END $$
DELIMITER ;

CALL agelim(17);

use Cinema;
-- Show list of showtimes(input Theaters first) 
delimiter //
create procedure city(plocation varchar(50))
begin
	select tname, tid
    from theaters
    where location = plocation;
end //
delimiter ;

call city('Ho Chi Minh City'); -- nhap vi tri rap chieu phim muon xem

DELIMITER //
create procedure showtime_info(ptid varchar(10)) 
BEGIN
	select fname, totalslot, availSlot, screen, s.starttime 
    from showtime s join films f on s.fid=f.fid
					join theaters t on s.tid = t.tid
    where s.tid = ptid; -- he thong tra ve ma rap chieu phim, ng dung nhap ma nay de xem suat chieu
END //
DELIMITER ;
drop procedure showtime_info;
call showtime_info('HCM1');

select * from showtime;

-- Show ticket info
DELIMITER //
create procedure tk_info(p_tkid varchar(10))
BEGIN
	select tkid, Fname, seat, seatcat, price, screen, starttime 
    from ticket t join showtime s on t.sid = s.sid
				  join films f on s.fid = f.fid
	where TKID = p_tkid;
END //
DELIMITER ;

CALL tk_info('T2');

select * from ticket;
-- Calculate revenue by day
delimiter //
create procedure drevenue(pdate date)
begin
	select sum(price) as DateRevenue
    from ticket
    where tkday = pdate; 
end //
delimiter ;

call drevenue('2021-12-01');

-- Calculate revenue by month
delimiter //
create procedure mrevenue(pmonth int, pyear int)
begin
	select sum(price) as Monthrevenue
    from ticket
    where month(tkday) = pmonth and year(tkday) = pyear; 
end //
delimiter ;
drop procedure mrevenue;

call mrevenue(12, 2021);



