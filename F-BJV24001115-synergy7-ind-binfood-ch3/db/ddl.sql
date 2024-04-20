create table users (
	id UUID primary key,
	username varchar(255),
	email_address varchar(255),
	password varchar(255)
);


create table merchant (
	id UUID primary key,
	merchant_name varchar(255),
	merchant_location text,
	open boolean
);

create table product (
	id UUID primary key,
	produt_name varchar(255),
	price int,
	merchant_id UUID references merchant(id)
);

create table orders (
	id UUID primary key,
	order_time timestamp,
	destination_address text,
	user_id UUID references users(id),
	completed boolean
);

create table orderdetail (
	id UUID primary key,
	order_id UUID references orders(id),
	product_id UUID references product(id),
	quantity int,
	total_price int
);