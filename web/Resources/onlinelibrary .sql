-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 26, 2016 at 10:44 AM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `onlinelibrary`
--
CREATE DATABASE IF NOT EXISTS `onlinelibrary` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `onlinelibrary`;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `filepath` varchar(100) NOT NULL,
  `title` varchar(20) NOT NULL,
  `editura` varchar(20) NOT NULL,
  `autor` varchar(30) NOT NULL,
  `owner` varchar(20) NOT NULL,
  `category` varchar(20) NOT NULL,
  `color` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`filepath`, `title`, `editura`, `autor`, `owner`, `category`, `color`) VALUES
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/6333e959-b312-42c5-bb4a-2421cf18d413.png', 'admintest', 'admintest', 'admintest', 'Available', 'ItBooks', 'yellowgreen'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/5be33e09-e77b-4aa8-b67b-721796629cee.png', 'asdasdasdasd', 'asdasasd', 'asdasd', 'admin', 'ItBooks', 'red'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/cd428d22-dadf-47f9-b401-36e07ffbf5ff.png', 'book', 'book', 'book', 'Available', 'book', 'yellowgreen'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/012a9856-350b-4333-9ba9-abbf82ba173f.png', 'carte', 'carte', 'carte', 'admin', 'Sport', 'red'),
('web/Resources/book.png', 'Not set', 'Not set', 'Not set', 'Available', 'Not set', 'yellowgreen'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/6becc054-dfcc-4f22-9997-4af635e97db9.png', 'original', 'original', 'original', 'Available', 'original', 'yellowgreen'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/45a0b395-38a9-46c1-befa-7a61f5f46fd0.png', 'test', 'test', 'test', 'Available', 'ItBooks', 'yellowgreen'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/9ce92f6c-ce61-41c1-a281-cb626db04d72.png', 'testing', 'testing', 'testing', 'Available', 'itbooks', 'yellowgreen'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/be41526f-a057-464a-92ac-687373e7b686.png', 'testing7', 'testing7', 'testing7', 'admin', 'Sport', 'red'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/2960fcef-33ea-4513-8574-84b987c5440e.png', 'testing8q', 'testing7q', 'testing7q', 'testing7', 'other', 'yellowgreen'),
('C:/___===DRIVE D===___/OnlineLibrary/build/web/Resources/2a2ea039-e9a4-4be5-b1cb-987c26ff821f.png', 'textimage', 'textimage', 'textimage', 'Available', 'textimage', 'yellowgreen');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` varchar(20) NOT NULL,
  `pass` varchar(20) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `pass`, `admin`) VALUES
('admin', 'admin', 1),
('sorin12', 'sorin12', 1),
('sorin123', 'sorin12', 1),
('sorinKRK', 'sorinKRK', 1),
('testing', 'testing', 0),
('testing1234', 'testing1234', 1),
('testing1234567', 'admin123', 1),
('user1', 'user1', 1),
('user12', 'user12', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`title`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
