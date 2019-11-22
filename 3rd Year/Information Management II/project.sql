create table Event(
    event_name varchar(50) NOT NULL,
    category varchar(40) NOT NULL,
    event_date DATE NOT NULL,
    ticket_price decimal(10,2) NOT NULL,
    event_location varchar(100) NOT NULL,
    event_id varchar(10) NOT NULL,
    PRIMARY KEY(event_id)
);

create table Activity(
    activity_name varchar(50) NOT NULL,
    activity_location varchar(100) NOT NULL,
    difficulty_rating varchar(5),
    activity_description varchar (500),
    activity_id varchar(10) NOT NULL,
    PRIMARY KEY (activity_id)
);

create table Places_to_eat{
    place_name varchar(50) NOT NULL,
    average_price_rating varchar(4),
    place_addr varchar(100) NOT NULL,
    cuisine varchar(20) NOT NULL,
    PRIMARY KEY(place_name)
}

