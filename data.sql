SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

DROP TABLE IF EXISTS parties;
CREATE TABLE parties(
    id varchar(255) NOT NULL,
    token varchar(255),
    nb_photos int,
    `status` varchar(255),
    score int,
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS photos;
CREATE TABLE photos(
    id varchar(255) not null,
    `desc` varchar(255),
    longitude float,
    latitude float,
    `url` varchar(255),
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS series;
CREATE TABLE series(
    id varchar(255) not null,
    ville varchar(255),
    map_long float,
    map_lat float,
    dist int,
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id varchar(255) not null,
    `login` varchar(255),
    `password` varchar(255),
    token varchar(255),    
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;