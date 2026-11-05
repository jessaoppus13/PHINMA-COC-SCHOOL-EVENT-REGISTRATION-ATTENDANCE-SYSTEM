-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 15, 2026 at 08:25 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `event_registration`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_attendance`
--

CREATE TABLE `tbl_attendance` (
  `id` int(11) NOT NULL,
  `registration_id` int(11) NOT NULL,
  `attendance_status` varchar(20) NOT NULL DEFAULT 'Pending',
  `attendance_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_attendance`
--

INSERT INTO `tbl_attendance` (`id`, `registration_id`, `attendance_status`, `attendance_date`) VALUES
(1, 1, 'Present', '2026-10-11 13:04:38'),
(2, 6, 'Present', '2026-10-15 15:52:55'),
(3, 7, 'Present', '2026-10-15 10:26:40'),
(4, 5, 'Absent', '2026-10-15 14:57:45');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_events`
--

CREATE TABLE `tbl_events` (
  `id` int(11) NOT NULL,
  `event_name` varchar(150) NOT NULL,
  `description` text DEFAULT NULL,
  `event_date` date NOT NULL,
  `venue` varchar(150) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Open',
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `participant_limit` int(11) DEFAULT NULL,
  `registration_deadline` date DEFAULT NULL,
  `is_archived` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_events`
--

INSERT INTO `tbl_events` (`id`, `event_name`, `description`, `event_date`, `venue`, `status`, `start_time`, `end_time`, `participant_limit`, `registration_deadline`, `is_archived`) VALUES
(1, 'IT Week', 'Information Technology activities', '2026-11-15', 'Puerto Campus', 'Open', '09:00:00', '16:00:00', 1, '2026-11-10', 0),
(2, 'Student Orientation', 'Orientation for students', '2026-11-20', 'School Auditorium', 'Open', NULL, NULL, NULL, NULL, 0),
(3, 'Campus Seminar', 'Educational seminar for students', '2026-12-05', 'Function Hall', 'Open', NULL, NULL, NULL, NULL, 0),
(4, 'PHINMA COC IT Innovation Day 2026', 'A campus technology event where students showcase IT projects, explore programming and web development, learn about cybersecurity, and participate in interactive activities and an IT quiz competition.', '2026-11-20', 'Puerto Campus', 'Open', '08:00:00', '16:00:00', 100, '2026-11-18', 0),
(5, 'Defense for IT Systems', '2nd year students\' day to defend their systems.', '2026-10-16', 'Puerto Campus - CL1 and CL2', 'Closed', '08:30:00', '19:30:00', 100, '2026-10-15', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_registrations`
--

CREATE TABLE `tbl_registrations` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `registration_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(20) NOT NULL DEFAULT 'Registered'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_registrations`
--

INSERT INTO `tbl_registrations` (`id`, `user_id`, `event_id`, `registration_date`, `status`) VALUES
(1, 17, 1, '2026-10-09 14:39:05', 'Registered'),
(5, 17, 2, '2026-10-13 14:13:45', 'Registered'),
(6, 17, 4, '2026-10-15 06:56:47', 'Registered'),
(7, 18, 2, '2026-10-15 09:40:20', 'Registered');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'Student',
  `full_name` varchar(100) DEFAULT NULL,
  `student_id` varchar(50) DEFAULT NULL,
  `course` varchar(100) DEFAULT NULL,
  `year_level` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`id`, `email`, `password`, `role`, `full_name`, `student_id`, `course`, `year_level`) VALUES
(3, 'jessa@staff.com', '1111', 'Staff', NULL, NULL, NULL, NULL),
(4, 'keith@staff.com', '1111', 'Staff', NULL, NULL, NULL, NULL),
(5, 'shiny@staff.com', '1111', 'Staff', NULL, NULL, NULL, NULL),
(6, 'josh@staff.com', '1111', 'Staff', NULL, NULL, NULL, NULL),
(7, 'lourd@staff.com', '1111', 'Staff', NULL, NULL, NULL, NULL),
(17, 'jepe.oppus.coc@phinmaed.com', '12345', 'Student', 'Jessa Oppus', '02-2526-016506', 'Bachelor of Science in Information Technology', '2nd Year'),
(18, 'keba.gallego.coc@phinmaed.com', '1111', 'Student', 'Keith Lino Gallego', '02-2324-09897', 'Bachelor of Science in Information Technology', '2nd Year');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_attendance`
--
ALTER TABLE `tbl_attendance`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `registration_id` (`registration_id`);

--
-- Indexes for table `tbl_events`
--
ALTER TABLE `tbl_events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_registrations`
--
ALTER TABLE `tbl_registrations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`,`event_id`),
  ADD KEY `event_id` (`event_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `unique_email` (`email`),
  ADD UNIQUE KEY `unique_student_id` (`student_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_attendance`
--
ALTER TABLE `tbl_attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_events`
--
ALTER TABLE `tbl_events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_registrations`
--
ALTER TABLE `tbl_registrations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_attendance`
--
ALTER TABLE `tbl_attendance`
  ADD CONSTRAINT `tbl_attendance_ibfk_1` FOREIGN KEY (`registration_id`) REFERENCES `tbl_registrations` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_registrations`
--
ALTER TABLE `tbl_registrations`
  ADD CONSTRAINT `tbl_registrations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`),
  ADD CONSTRAINT `tbl_registrations_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `tbl_events` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
