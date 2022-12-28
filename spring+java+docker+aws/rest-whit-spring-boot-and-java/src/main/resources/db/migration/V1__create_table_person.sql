CREATE TABLE `person` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `first_name` varchar(80) NOT NULL,
    `last_name` varchar(80) NOT NULL,
    `address` varchar(100) NOT NULL,
    `gender` varchar(10) DEFAULT NULL,
    PRIMARY KEY (`id`)
);