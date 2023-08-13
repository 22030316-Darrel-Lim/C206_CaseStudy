-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 13, 2023 at 07:02 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `c206_ga`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(10) NOT NULL,
  `admin_profile` varchar(50) NOT NULL DEFAULT 'admin.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `admin_profile`) VALUES
(1, 'admin.png'),
(8, 'admin.png');

-- --------------------------------------------------------

--
-- Table structure for table `child`
--

CREATE TABLE `child` (
  `child_id` int(10) NOT NULL,
  `child_name` varchar(100) NOT NULL,
  `child_allegies` varchar(100) NOT NULL,
  `normal_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `child`
--

INSERT INTO `child` (`child_id`, `child_name`, `child_allegies`, `normal_id`) VALUES
(1, 'child1', '-', 4),
(2, 'child2', 'beef', 4),
(3, 'child3', 'chocolate ', 5),
(4, 'child4', 'nuts ', 5);

-- --------------------------------------------------------

--
-- Table structure for table `has_order`
--

CREATE TABLE `has_order` (
  `order_id` int(10) NOT NULL,
  `order_status` varchar(100) NOT NULL,
  `preference` varchar(100) NOT NULL,
  `child_id` int(10) DEFAULT NULL,
  `school_has_vendor_id` int(10) NOT NULL,
  `item_id` int(10) NOT NULL,
  `payment_id` int(10) NOT NULL,
  `normal_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `item_id` int(10) NOT NULL,
  `vendor_id` int(10) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_qty` int(10) NOT NULL,
  `item_description` varchar(300) NOT NULL,
  `item_dietary` varchar(300) NOT NULL,
  `item_ingredients` varchar(300) NOT NULL,
  `item_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_id`, `vendor_id`, `item_name`, `item_qty`, `item_description`, `item_dietary`, `item_ingredients`, `item_price`) VALUES
(0, 2, 'banana', 5, 'Bananas are long, curved fruits with smooth, yellow, and sometimes slightly green skin', 'NIL', 'Fruit, Tree', 5.5),
(1, 2, 'apple', 1, 'Round fruit with red or green skin and a whitish inside. One variety of apple might be sweet, another sour', 'NIL', 'Fruit, Tree', 6.5),
(2, 3, 'milk', 0, 'Liquid produced by mammals.', 'Dairy, NON Vegan', 'Cow', 4.5),
(3, 3, 'water', 20, 'Water is an inorganic compound with the chemical formula H 2O. It is a transparent, tasteless, odorless, and nearly colorless chemical substance', 'NIL', 'H2O Basic building blocks of life', 1.2),
(4, 3, 'banana milk', 4, 'Beverage made from blended bananas and water', 'Dairy, NON Vegan', 'Cow, Fruit, Tree', 8.95),
(5, 3, 'apple milk', 10, 'The unique thick and fragrant sweet taste is created by inheriting the taste of sweet apple and milk', 'Dairy, NON Vegan', 'Cow, Fruit, Tree', 8.7),
(6, 3, 'Grilled Chicken', 3, 'Grilling infuses the chicken with a smoky flavor from the meat juices that drip during the grilling process', 'Poultry, NON Vegan', 'Chicken, Rosemary, Oregano, Cilantro, Salt, Pepper', 3.4),
(7, 2, 'beef', 50, 'Flesh of a cow, bull, or ox', 'Red Meat, NON Vegan', 'Cow', 5.6),
(8, 2, 'Lemon Tea', 999, 'Yellow liquid that is sweet, it has the tea of leafs', 'No bang bus, Vegan', 'Lemon, Tea', 0.01);

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `menu_id` int(10) NOT NULL,
  `vendor_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`menu_id`, `vendor_id`) VALUES
(1, 2),
(2, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `menu_item`
--

CREATE TABLE `menu_item` (
  `menu_item_id` int(10) NOT NULL,
  `item_id` int(10) NOT NULL,
  `menu_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_item`
--

INSERT INTO `menu_item` (`menu_item_id`, `item_id`, `menu_id`) VALUES
(0, 0, 1),
(1, 1, 1),
(2, 7, 1),
(3, 8, 1),
(4, 0, 2),
(5, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `normal`
--

CREATE TABLE `normal` (
  `normal_id` int(10) NOT NULL,
  `normal_phoneNumber` int(10) NOT NULL,
  `normal_address` varchar(100) NOT NULL,
  `normal_profile` varchar(100) NOT NULL DEFAULT 'normal.png',
  `normal_allegies` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `normal`
--

INSERT INTO `normal` (`normal_id`, `normal_phoneNumber`, `normal_address`, `normal_profile`, `normal_allegies`) VALUES
(4, 999, 'Street 1', 'normal.png', 'nil'),
(5, 999, 'Street 2', 'normal.png', 'potato'),
(6, 999, 'Street 3', 'normal.png', 'liquid');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_id` int(10) NOT NULL,
  `payment_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`payment_id`, `payment_name`) VALUES
(1, 'Visa'),
(2, 'Paylah');

-- --------------------------------------------------------

--
-- Table structure for table `school`
--

CREATE TABLE `school` (
  `school_id` int(10) NOT NULL,
  `school_name` varchar(100) NOT NULL,
  `school_address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `school`
--

INSERT INTO `school` (`school_id`, `school_name`, `school_address`) VALUES
(1, 'Republic Polytechnic ', '9 Woodlands Ave 9, Singapore 738964'),
(2, 'Temasek Polytechnic', 'Temasek Polytechnic, 21 Tampines Ave 1, Singapore 529757');

-- --------------------------------------------------------

--
-- Table structure for table `school_has_vendor`
--

CREATE TABLE `school_has_vendor` (
  `school_has_vendor_id` int(11) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `school_has_vendor`
--

INSERT INTO `school_has_vendor` (`school_has_vendor_id`, `vendor_id`, `school_id`) VALUES
(1, 2, 1),
(2, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `ACCESS_TYPE` varchar(100) NOT NULL,
  `LAST_LOGIN` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_email`, `user_password`, `ACCESS_TYPE`, `LAST_LOGIN`) VALUES
(1, 'admin2', 'admin2@admin2', '315f166c5aca63a157f7d41007675cb44a948b33', 'admin', '0000-00-00 00:00:00.000000'),
(2, 'vendor1', 'vendor1@vendor1', '4b804508e5998fbf09fb1d2a4f5bbcc383db920e', 'vendor', '2023-08-12 16:46:29.000000'),
(3, 'vendor2', 'vendor2@vendor2', '9d737ae503ed092cd1b5e2fec61d8474be13a9c1', 'vendor', '2023-08-12 15:39:27.000000'),
(4, 'normal1', 'normal1@normal1', 'feb3c5bb1c85509f6b9fa5d7be536c092a26d1dd', 'normal', '2023-08-12 15:20:27.000000'),
(5, 'normal2', 'normal2@normal2', 'b5fd3d974c0c792f83f08f5386ee468e004cf100', 'normal', '2023-07-31 13:46:41.000000'),
(6, 'normal3', 'normal3@normal3', 'ef9135171318c4abd8cbe007503b25a50a972012', 'normal', '0000-00-00 00:00:00.000000'),
(8, 'admin1', 'admin1@admin1', 'admin1', 'admin', '0000-00-00 00:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `vendor_id` int(10) NOT NULL,
  `vendor_phoneNumber` int(10) NOT NULL,
  `vendor_companyName` varchar(100) NOT NULL,
  `vendor_profile` varchar(100) NOT NULL DEFAULT 'vendor.png',
  `vendor_address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`vendor_id`, `vendor_phoneNumber`, `vendor_companyName`, `vendor_profile`, `vendor_address`) VALUES
(2, 999, 'Company 1', 'vendor.png', 'Street 1'),
(3, 999, 'Company 2', 'vendor.png', 'Street 2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `child`
--
ALTER TABLE `child`
  ADD PRIMARY KEY (`child_id`),
  ADD KEY `normal_id` (`normal_id`);

--
-- Indexes for table `has_order`
--
ALTER TABLE `has_order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `child_id` (`child_id`),
  ADD KEY `normal_id` (`school_has_vendor_id`),
  ADD KEY `item_id` (`item_id`),
  ADD KEY `payment_id` (`payment_id`),
  ADD KEY `normal_id_2` (`normal_id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `vendor_id` (`vendor_id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menu_id`),
  ADD KEY `vendor_id` (`vendor_id`);

--
-- Indexes for table `menu_item`
--
ALTER TABLE `menu_item`
  ADD PRIMARY KEY (`menu_item_id`),
  ADD KEY `item_id` (`item_id`),
  ADD KEY `menu_id` (`menu_id`);

--
-- Indexes for table `normal`
--
ALTER TABLE `normal`
  ADD PRIMARY KEY (`normal_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD UNIQUE KEY `payment_id` (`payment_id`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`school_id`);

--
-- Indexes for table `school_has_vendor`
--
ALTER TABLE `school_has_vendor`
  ADD PRIMARY KEY (`school_has_vendor_id`),
  ADD KEY `vendor_id` (`vendor_id`),
  ADD KEY `school_id` (`school_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`vendor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `item_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `menu_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `menu_item`
--
ALTER TABLE `menu_item`
  MODIFY `menu_item_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `normal`
--
ALTER TABLE `normal`
  MODIFY `normal_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `school`
--
ALTER TABLE `school`
  MODIFY `school_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `vendor_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `child`
--
ALTER TABLE `child`
  ADD CONSTRAINT `child_ibfk_1` FOREIGN KEY (`normal_id`) REFERENCES `normal` (`normal_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `has_order`
--
ALTER TABLE `has_order`
  ADD CONSTRAINT `has_order_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `has_order_ibfk_5` FOREIGN KEY (`school_has_vendor_id`) REFERENCES `school_has_vendor` (`school_has_vendor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `has_order_ibfk_6` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `has_order_ibfk_7` FOREIGN KEY (`item_id`) REFERENCES `menu_item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `has_order_ibfk_8` FOREIGN KEY (`normal_id`) REFERENCES `normal` (`normal_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `menu`
--
ALTER TABLE `menu`
  ADD CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `menu_item`
--
ALTER TABLE `menu_item`
  ADD CONSTRAINT `menu_item_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `menu_item_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `normal`
--
ALTER TABLE `normal`
  ADD CONSTRAINT `normal_ibfk_1` FOREIGN KEY (`normal_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `school_has_vendor`
--
ALTER TABLE `school_has_vendor`
  ADD CONSTRAINT `school_has_vendor_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `school_has_vendor_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vendor`
--
ALTER TABLE `vendor`
  ADD CONSTRAINT `vendor_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
