create sequence ORDER_SEQUENCE
/

create table BOOKS
(
  ISBN    CHAR(10)      not null
    primary key,
  AUTHOR  VARCHAR2(100) not null,
  TITLE   VARCHAR2(128) not null,
  PRICE   NUMBER(7, 2)  not null,
  SUBJECT VARCHAR2(30)  not null
)
/

create table MEMBERS
(
  FNAME            VARCHAR2(20) not null,
  LNAME            VARCHAR2(20) not null,
  ADDRESS          VARCHAR2(50) not null,
  CITY             VARCHAR2(30) not null,
  STATE            VARCHAR2(20) not null,
  ZIP              NUMBER(5)    not null,
  PHONE            VARCHAR2(12),
  EMAIL            VARCHAR2(40),
  USERID           VARCHAR2(20) not null
    primary key,
  PASSWORD         VARCHAR2(20),
  CREDITCARDTYPE   VARCHAR2(10)
    check (creditcardtype in ('amex', 'discover', 'mc', 'visa')),
  CREDITCARDNUMBER CHAR(16)
)
/

create table ORDERS
(
  USERID      VARCHAR2(20) not null
    references MEMBERS,
  ONO         NUMBER(5)    not null
    primary key,
  RECEIVED    DATE         not null,
  SHIPPED     DATE,
  SHIPADDRESS VARCHAR2(50),
  SHIPCITY    VARCHAR2(30),
  SHIPSTATE   VARCHAR2(20),
  SHIPZIP     NUMBER(5)
)
/

create table ODETAILS
(
  ONO   NUMBER(5)    not null
    references ORDERS,
  ISBN  CHAR(10)     not null
    references BOOKS,
  QTY   NUMBER(5)    not null,
  PRICE NUMBER(7, 2) not null,
  primary key (ONO, ISBN)
)
/

create table CART
(
  USERID VARCHAR2(20) not null
    references MEMBERS,
  ISBN   CHAR(10)     not null
    references BOOKS,
  QTY    NUMBER(5)    not null,
  primary key (USERID, ISBN)
)
/

