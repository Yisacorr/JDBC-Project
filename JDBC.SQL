CREATE TABLE WritingGroup (
    Group_Name VARCHAR(50) NOT NULL,
    Head_Writer VARCHAR(50),
    Year_Formed INT,
    SUBJECT VARCHAR(50),
    CONSTRAINT pk_WritingGroup PRIMARY KEY(Group_Name));

CREATE TABLE Publishers(
  Publisher_Name VARCHAR(40)  NOT NULL,
  Publisher_Address VARCHAR(40),
  Publisher_Phone VARCHAR(15),
  Publisher_Email VARCHAR(50),
  CONSTRAINT pk_Publishers PRIMARY KEY (Publisher_Name));

CREATE TABLE Books(
    Group_Name VARCHAR(50) NOT NULL,
    Book_Title VARCHAR(50)  NOT NULL,
    Publisher_Name VARCHAR(40),
    Year_Published INT,
    Number_Pages INT,
    CONSTRAINT pk_Books PRIMARY KEY (Group_Name,Book_Title),
    CONSTRAINT Books_fk FOREIGN KEY (Group_Name) REFERENCES WritingGroup (Group_Name),
    CONSTRAINT Books_fk2 FOREIGN KEY(Publisher_Name) REFERENCES Publishers (Publisher_Name),
    CONSTRAINT Books_CK UNIQUE (Book_Title,Publisher_Name));

INSERT INTO WritingGroup (Group_Name,Head_Writer,Year_Formed,SUBJECT) VALUES
    ('Music','John Lennon',1960,'Music'),

    ('Big Sports Media','Shaquille Oneal',2004,'Sports'),

    ('The Romantics','John Green',2005,'Romance Novels'),

    ('Carpet Fiction','Philip Straub',2008,'Fiction Novels'),

    ('HBO Writing Group','George R.R. Martin',2003,'Fantasy Novels'),

    ('The Horror House','Stephen King', 1970,'Horror Novels'),

    ('Original KB','Dr. Seuss', 1944,'Childrens Books'),

    ('Classics','F Scott Fitzgerald',1932,'Fantasy Novels'),

    ('Plays','Shakespear',1312,'Renaissance');

    
INSERT INTO Publishers (Publisher_Name,Publisher_Address,Publisher_Phone,Publisher_Email) VALUES
    ('Penguin House','111 Sesame Street','323-334-3333','penguinHouse@gmail.com'),

    ('Genious Bar', '455 Regis way', '424-678-2734','geniousBar@yahoo.com'),

    ('Emma', '455 Regis way', '424-678-2734','Emma123@yahoo.com'),

    ('Chatto & Windus', '295 East Milkyway', '984-226-7343','ChattoNWindus@outlook.com'),

    ('Crown Publishing Group', '1395 West Rutherford', '866-665-4231','CrownPublishing@gmail.com'),

    ('J.B. Lippincott & Co.', '130 Main St.', '714-994-3232','JBLippincott@jb.com'),

    ('Bantam Spectra', '133 N. Westerosi Way', '985-111-3234','JohnSnow@GOT.com'),

    ('Viking Press', '2408 Skol Rd.', '985-447-3234','TheVikingPress@vkp.it'),

    ('Harper', '994 E. Web St.', '224-494-3737','HarperProductions@aol.com'),

    ('Bloomsbury', '4 Privet Drive', '987-654-3230','jkRowling@hotmail.com'),
    
    ('Simon & Schuster', '1894 S Western St.', '084-321-7831','simonNschuster@myspace.com');

INSERT INTO Books (Group_Name,Book_Title,Publisher_Name,Year_Published,Number_Pages) VALUES
    ('Original KB','The Cat in the Hat','Penguin House',1957,61),

    ('Original KB','Green Eggs and Ham','Penguin House',1960,72),

    ('Classics','How The Grinch Stole Christmas','Penguin House',1957,69),

    ('The Romantics','Looking For Alaska','Penguin House',2012,280),

    ('The Romantics','The Fault In Our Stars','Penguin House',2012,313),

    ('Carpet Fiction','Maze Runner','Genious Bar',2009,218),

    ('Classics','The Great Gatsby','Chatto & Windus',1925,190),

    ('Classics','To Kill a Mocking Bird','J.B. Lippincott & Co.',1960,210),

    ('HBO Writing Group','Game of Thrones','Bantam Spectra',1996,694),

    ('HBO Writing Group','The Winds of Winter','Bantam Spectra',1996,3000),

    ('HBO Writing Group','A Dance with Dragons','Bantam Spectra',2011,1016),

    ('HBO Writing Group','Assasins Quest','Bantam Spectra',1997,742),

    ('The Horror House','It','Viking Press',1986,1138),

    ('The Horror House','The Shining','Viking Press',1977,447),

    ('The Horror House','The Stand','Viking Press',1978,823),
    
    ('Classics','Charlottes Web','Harper',1952,192),

    ('Carpet Fiction','Harry Potter','Bloomsbury',1997,357),

    ('Plays','Romeo and Juliet','Simon & Schuster',1597,480),

    ('Plays','Hamlet','Simon & Schuster',1597,368),

    ('Original KB','Ferdinand','Penguin House',2000,32);
