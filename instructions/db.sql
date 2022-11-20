CREATE DATABASE pizzeria;

USE pizzeria;

create table user (id bigint auto_increment primary key,
                   email varchar(40) not null unique,
                   phone_number varchar(20) not null,
                   first_name varchar(30) not null,
                   birthday date not null,
                   address varchar(60) not null,
                   is_admin tinyint(1) default 0,
                   is_banned tinyint(1) default 0,
                   password varchar(100) not null);

create table status (id bigint auto_increment primary key);

create table locale (id bigint auto_increment primary key,
                     name varchar(40) not null,
                     short_name varchar(10) not null);

create table status_locale (id bigint primary key auto_increment,
                            status_id bigint not null,
                            locale_id bigint not null,
                            name varchar(50) not null,
                            foreign key (status_id) references status (id),
                            foreign key (locale_id) references locale (id));

create table product_category (id bigint auto_increment primary key);

create table product_category_locale (id bigint auto_increment primary key,
                                      locale_id bigint not null,
                                      product_category_id bigint not null,
                                      name varchar(15) not null,
                                      foreign key (locale_id) references locale (id),
                                      foreign key (product_category_id) references product_category (id));

create table product (id bigint auto_increment primary key,
                      product_category_id bigint not null,
                      name varchar(100) not null,
                      description varchar(250) not null,
                      image_url varchar(100),
                      price int,
                      is_pizza tinyint(1) default 0,
                      is_active tinyint(1) default 1,
                      foreign key (product_category_id) references product_category (id));

create table additional_ingredient (id bigint auto_increment primary key,
                                    name varchar(30) not null,
                                    image_url varchar(100),
                                    is_active tinyint(1) default 1);

create table product_ingredient_detail (id bigint not null auto_increment primary key,
                                        product_id bigint not null,
                                        ingredient_id bigint not null,
                                        foreign key(product_id) references product(id),
                                        foreign key (ingredient_id) references additional_ingredient (id));

create table additional_ingredient_detail (id bigint auto_increment primary key,
                                           size_id bigint not null,
                                           additional_ingredient_id bigint not null,
                                           price int not null,
                                           foreign key (size_id) references size (id),
                                           foreign key (additional_ingredient_id) references additional_ingredient (id));

create table size (id bigint auto_increment primary key,
                   name varchar(30) not null,
                   size varchar(10) not null);

create table product_size_detail (id bigint auto_increment primary key,
                                  size_id bigint not null,
                                  product_id bigint not null,
                                  price int,
                                  foreign key (size_id) references size (id),
                                  foreign key (product_id) references product (id));

create table basket (id bigint auto_increment primary key,
                     user_id bigint not null,
                     product_id bigint not null,
                     count int not null default 1,
                     size_id bigint default null,
                     foreign key (size_id) references size (id),
                     foreign key (user_id) references user (id),
                     foreign key (product_id) references product (id));

create table orders (id bigint auto_increment primary key,
                     user_id bigint not null,
                     status_id bigint not null,
                     total_price int not null,
                     date_start timestamp not null,
                     ready_in varchar(15),
                     delivery_method_id bigint,
                     foreign key (user_id) references user (id),
                     foreign key (status_id) references status (id),
                     foreign key (delivery_method_id) references delivery_method (id));

create table order_detail (id bigint auto_increment primary key,
                           order_id bigint not null,
                           product_id bigint not null,
                           count int not null,
                           price int not null,
                           size_id bigint not null,
                           foreign key (size_id) references size (id),
                           foreign key (order_id) references orders (id),
                           foreign key (product_id) references product (id));

create table order_ingredient_detail (id bigint not null auto_increment primary key,
                                      order_detail_id bigint not null,
                                      additional_ingredient_detail_id bigint not null,
                                      foreign key (order_detail_id) references order_detail (id),
                                      foreign key (additional_ingredient_detail_id) references additional_ingredient_detail (id));

create table basket_ingredient_detail (id bigint not null auto_increment primary key,
                                       basket_id bigint not null,
                                       additional_ingredient_detail_id bigint not null,
                                       foreign key (basket_id) references basket (id),
                                       foreign key (additional_ingredient_detail_id) references additional_ingredient_detail (id));

create table delivery_method (id bigint not null auto_increment primary key,
                              name varchar(30) not null);

create table delivery_method_locale (id bigint not null auto_increment primary key,
                                     locale_id bigint not null,
                                     name varchar(30) not null,
                                     delivery_method_id bigint not null,
                                     foreign key (locale_id) references locale (id),
                                     foreign key (delivery_method_id) references delivery_method (id));


insert into user (first_name, birthday, phone_number, address, password, is_admin, email, is_banned) values ('admin', '1998-07-01', 87751234567, 'city Melkiy', 'c177bd4348a475e1a02858d01f464512', true, 'admin@mail.com', false);
insert into user (first_name, birthday, phone_number, address, password, is_admin, email, is_banned) values ('user', '1990-08-07', 87757654321, 'city Bolshoy', 'c177bd4348a475e1a02858d01f464512', false, 'user@mail.com', false);
insert into user (first_name, birthday, phone_number, address, password, is_admin, email, is_banned) values ('blocked', '1995-10-07', 87750123456, 'city Nepovezlo', 'c177bd4348a475e1a02858d01f464512', false, 'blocked@mail.com', true);
insert into locale (short_name, name) values ('RU', 'русский'), ('EN', 'english');
insert into size (name, size) values ('Маленькая', '25 см');
insert into size (name, size) values ('Средняя', '30 см');
insert into size (name, size) values ('Большая', '35 см');
insert into status VALUES (null);
insert into status VALUES (null);
insert into status_locale (status_id, locale_id, name) values (1, 1, 'Выполняется'), (1, 2, 'Be in progress'), (2, 1, 'Готов'), (2, 2, 'Ready');
insert into delivery_method (name) values ('Самовывоз'), ('Доставка');
insert into delivery_method_locale (locale_id, delivery_method_id, name) values (1, 1, 'Самовывоз'), (1, 2, 'Доставка'), (2, 1, 'Pickup'), (2, 2, 'Delivery');
