-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sty 27, 2025 at 06:11 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `event_calendar`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `events_type`
--

CREATE TABLE `events_type` (
  `Id_type` int(11) NOT NULL,
  `R_name` varchar(45) DEFAULT NULL,
  `Color` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `events_type`
--

INSERT INTO `events_type` (`Id_type`, `R_name`, `Color`) VALUES
(1, 'Holiday', 'cornflowerblue'),
(2, 'Birthday', 'greenyellow'),
(3, 'other', 'aquamarine');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `role_name`
--

CREATE TABLE `role_name` (
  `Id` int(11) NOT NULL,
  `Id_role` char(1) NOT NULL,
  `R_name` varchar(45) DEFAULT NULL,
  `Active` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `role_name`
--

INSERT INTO `role_name` (`Id`, `Id_role`, `R_name`, `Active`) VALUES
(1, 'U', 'User', 1),
(2, 'E', 'Employee', 1),
(3, 'A', 'Admin', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `role_user`
--

CREATE TABLE `role_user` (
  `Id` int(11) NOT NULL,
  `Id_user` int(11) NOT NULL,
  `Id_role` char(1) NOT NULL,
  `Role_add` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `role_user`
--

INSERT INTO `role_user` (`Id`, `Id_user`, `Id_role`, `Role_add`) VALUES
(3, 8, 'A', '2024-06-02 07:08:23'),
(4, 9, 'U', '2024-06-02 07:08:53'),
(5, 10, 'E', '2024-06-03 02:12:33'),
(6, 11, 'U', '2024-06-03 08:27:38'),
(8, 44, 'U', '2025-01-19 18:56:51');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_data`
--

CREATE TABLE `user_data` (
  `Id_user` int(11) NOT NULL,
  `Username` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Login` varchar(45) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `User_create` varchar(45) DEFAULT NULL,
  `Last_change` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `user_data`
--

INSERT INTO `user_data` (`Id_user`, `Username`, `Email`, `Login`, `Password`, `User_create`, `Last_change`) VALUES
(8, 'Mati', 'mati@vp.pl', 'Mati1', '123', '2024-06-02 07:08:23', '2024-06-10 08:57:07'),
(9, 'Maro', 'Maro@vp.pl', 'Maro1', '12345', '2024-06-02 07:08:53', '2024-06-10 05:47:53'),
(10, 'employee', 'employee@o.pl', 'employee1', '123', '2024-06-03 02:12:33', '2024-06-03 02:12:33'),
(11, 'Cylina', 'Cylina@Cylina.pl', 'Cylina2', '123', '2024-06-03 08:27:38', '2024-06-03 08:27:38'),
(22, 'kacp@vp.pl', 'Kacp', 'Kacp1', '1', '2025-01-15 19:41:09', '2025-01-15 19:41:09'),
(40, 'kamil@vp.pl', 'Kamil', 'kamil1', '123', '2025-01-19 18:01:46', '2025-01-19 18:01:46'),
(44, 'Maciek', 'maciek@vp.pl', 'maciek1', '123', '2025-01-19 18:56:51', '2025-01-25 13:50:28');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_events`
--

CREATE TABLE `user_events` (
  `Id_events` int(11) NOT NULL,
  `Id_user` int(11) NOT NULL,
  `Name_event` varchar(45) DEFAULT NULL,
  `Start_date_event` datetime DEFAULT NULL,
  `End_date_event` datetime DEFAULT NULL,
  `Id_type` int(11) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `user_events`
--

INSERT INTO `user_events` (`Id_events`, `Id_user`, `Name_event`, `Start_date_event`, `End_date_event`, `Id_type`, `Description`) VALUES
(38, 11, 'Melanż w Ciechocimku', '2024-06-09 10:00:00', '2024-06-09 11:00:00', 2, 'Melanż'),
(40, 9, 'Zakupy', '2024-06-16 14:00:00', '2024-06-16 18:00:00', 3, ''),
(43, 9, 'Bieg', '2024-06-27 13:00:00', '2024-06-27 14:00:00', 3, 'Bieg w parku sieleckim.\r\nZe znajomymi.'),
(44, 9, 'ewew', '2024-06-09 15:00:00', '2024-06-09 17:00:00', 3, 'Lech Jaworski Analiza Rynek mediów elektronicznych domaga się zmian Dogonić EuropęNasze prawo medialne nie przystaje do rzeczywistości i hamuje rozwój. Dlatego wymaga gruntownej przebudowy. Politycy z'),
(46, 9, 'Urodziny', '2024-06-09 12:00:00', '2024-06-09 13:00:00', 1, '1'),
(47, 44, 'Nowy rok', '2025-01-01 00:00:00', '2025-01-01 03:00:00', NULL, 'Sylwester'),
(50, 44, 'dwa dni', '2025-01-08 00:00:00', '2025-01-09 01:00:00', NULL, ''),
(52, 44, 'Nowy event caly dzien', '2025-01-02 00:00:00', '2025-01-02 01:00:00', NULL, '');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `events_type`
--
ALTER TABLE `events_type`
  ADD PRIMARY KEY (`Id_type`);

--
-- Indeksy dla tabeli `role_name`
--
ALTER TABLE `role_name`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Id_role` (`Id_role`);

--
-- Indeksy dla tabeli `role_user`
--
ALTER TABLE `role_user`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Id_user` (`Id_user`),
  ADD KEY `Id_role` (`Id_role`);

--
-- Indeksy dla tabeli `user_data`
--
ALTER TABLE `user_data`
  ADD PRIMARY KEY (`Id_user`);

--
-- Indeksy dla tabeli `user_events`
--
ALTER TABLE `user_events`
  ADD PRIMARY KEY (`Id_events`),
  ADD KEY `Id_type` (`Id_type`),
  ADD KEY `Id_user` (`Id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `events_type`
--
ALTER TABLE `events_type`
  MODIFY `Id_type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `role_name`
--
ALTER TABLE `role_name`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `role_user`
--
ALTER TABLE `role_user`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user_data`
--
ALTER TABLE `user_data`
  MODIFY `Id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `user_events`
--
ALTER TABLE `user_events`
  MODIFY `Id_events` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `role_user`
--
ALTER TABLE `role_user`
  ADD CONSTRAINT `fk_role_user_id_user` FOREIGN KEY (`Id_user`) REFERENCES `user_data` (`Id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `role_user_ibfk_1` FOREIGN KEY (`Id_role`) REFERENCES `role_name` (`Id_role`);

--
-- Constraints for table `user_events`
--
ALTER TABLE `user_events`
  ADD CONSTRAINT `user_events_ibfk_1` FOREIGN KEY (`Id_user`) REFERENCES `user_data` (`Id_user`),
  ADD CONSTRAINT `user_events_ibfk_2` FOREIGN KEY (`Id_type`) REFERENCES `events_type` (`Id_type`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
