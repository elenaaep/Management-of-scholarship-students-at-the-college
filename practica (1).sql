-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Gazdă: 127.0.0.1
-- Timp de generare: ian. 26, 2024 la 05:58 PM
-- Versiune server: 10.4.28-MariaDB
-- Versiune PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Bază de date: `practica`
--

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `activity_log`
--

CREATE TABLE `activity_log` (
  `log_id` int(11) NOT NULL,
  `user_type` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `action_description` varchar(255) DEFAULT NULL,
  `action_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Eliminarea datelor din tabel `activity_log`
--

INSERT INTO `activity_log` (`log_id`, `user_type`, `username`, `action_description`, `action_time`) VALUES
(1, 'Admin', 'Maria', 'Logare reușită pentru Admin cu numele de utilizator: Maria', '2024-01-05 01:10:30'),
(2, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-05 01:14:32'),
(3, 'Admin', 'Maria', 'Logare reușită pentru Admin cu numele de utilizator: Maria', '2024-01-16 22:30:48'),
(4, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-16 22:31:32'),
(5, 'Admin', 'Maria', 'Logare reușită pentru Admin cu numele de utilizator: Maria', '2024-01-18 06:21:01'),
(6, 'Admin', 'Maria', 'Logare reușită pentru Admin cu numele de utilizator: Maria', '2024-01-18 06:22:03'),
(7, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 06:22:40'),
(8, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:44'),
(9, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:45'),
(10, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:46'),
(11, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:46'),
(12, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:47'),
(13, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:47'),
(14, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:47'),
(15, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:47'),
(16, 'Student', 'Daniel', 'Logare reușită pentru Student cu numele de utilizator: Daniel', '2024-01-18 08:20:48'),
(17, 'Admin', 'Maria', 'Logare reușită pentru Admin cu numele de utilizator: Maria', '2024-01-18 08:20:59'),
(18, 'Admin', 'Maria', 'Logare reușită pentru Admin cu numele de utilizator: Maria', '2024-01-18 08:23:05');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `burse`
--

CREATE TABLE `burse` (
  `idB` varchar(6) NOT NULL,
  `idS` varchar(6) NOT NULL,
  `tip_bursa` varchar(25) NOT NULL,
  `nr_burse` varchar(6) NOT NULL,
  `punctaj` varchar(6) NOT NULL,
  `media_finala` varchar(10) NOT NULL,
  `venit_student` varchar(10) NOT NULL,
  `suma` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Eliminarea datelor din tabel `burse`
--

INSERT INTO `burse` (`idB`, `idS`, `tip_bursa`, `nr_burse`, `punctaj`, `media_finala`, `venit_student`, `suma`) VALUES
('11', '1', 'performanta', '3', '150', '9.5', '', '1200'),
('12', '13', 'performanta', '2', '230', '9.3', '', '1200'),
('13', '13', 'performanta', '45', '210', '9.3', '', '1500'),
('14', '6', 'performanta', '34', '195', '9.2', '', '1500'),
('21', '2', 'sociala', '70', '', '', '3000', '600'),
('22', '3', 'sociala', '56', '', '', '2850', '600'),
('23', '10', 'sociala', '77', '', '', '3200', '900'),
('24', '4', 'sociala', '67', '', '', '2780', '900'),
('31', '2', 'merit', '10', '', '9.13', '', '800'),
('32', '3', 'merit', '11', '', '9.24', '', '800'),
('33', '15', 'merit', '10', '', '9', '', '1100'),
('34', '12', 'merit', '10', '', '8.3', '', '1100');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `facultate`
--

CREATE TABLE `facultate` (
  `idF` varchar(6) NOT NULL,
  `numeF` varchar(25) NOT NULL,
  `adresa` varchar(25) NOT NULL,
  `domeniul` varchar(25) NOT NULL,
  `nr_studenti` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Eliminarea datelor din tabel `facultate`
--

INSERT INTO `facultate` (`idF`, `numeF`, `adresa`, `domeniul`, `nr_studenti`) VALUES
('1000', 'FACIEE', 'Str. Stiintei 2', 'tehnic', '4250'),
('1001', 'UPB', 'Splaiul Independenței 313', 'tehnic', '5000'),
('1002', 'FEAA', 'Str Gării', 'economic', '3800'),
('1003', 'ING', 'Str Domnească 111', 'mecanic', '4500'),
('1004', 'UMF', 'Str Universității 16', 'medical', '5500'),
('1005', 'NAOE', 'Str Domnească 111', 'naval', '1500'),
('1006', 'ASE', 'Piața Romană 6', 'economic', '2700'),
('1007', 'SNSPA', 'Blv Expoziției 30A', 'politic', '6300'),
('1008', 'FEFS', 'Str. Toma Cozma 3', 'sportiv', '1670'),
('1009', 'FFCV', 'Str. Traian Vuia 6', 'famacie', '2500'),
('1010', 'FMV', 'Splaiul Independenței 105', 'medicina veterina', '1500'),
('1011', 'FMPT', 'Str. Remus 14', 'transport', '1760'),
('1012', 'FLIT', 'Blv. Vasile Parvan 4', 'filologie', '2780'),
('1013', 'USAMVB', 'Calea Aradului 119', 'agrigultura', '3170'),
('1014', 'Test', 'Str C. Conachi 3', 'economic', '1200'),
('1015', 'UGAL', 'Str C. Conachi 3', 'POLITIC', '2300'),
('1016', 'UGAL', 'Str C. Conachi 3', 'medicina', '5500');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `student`
--

CREATE TABLE `student` (
  `idS` varchar(6) NOT NULL,
  `idF` varchar(6) NOT NULL,
  `nume` varchar(30) NOT NULL,
  `init_tata` varchar(25) NOT NULL,
  `CNP` varchar(13) NOT NULL,
  `dataN` date NOT NULL,
  `an_studii` varchar(6) NOT NULL,
  `specializare` varchar(30) NOT NULL,
  `media` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Eliminarea datelor din tabel `student`
--

INSERT INTO `student` (`idS`, `idF`, `nume`, `init_tata`, `CNP`, `dataN`, `an_studii`, `specializare`, `media`) VALUES
('1', '1000', 'Popa Daniel', 'O', '6034564524535', '2000-07-30', '4', 'CTI', '8.7'),
('10', '1003', 'Mihai Petrescu', 'O.D', '6040912345678', '2000-12-23', '4', 'AR', '6.7'),
('11', '1003', 'Andreea Dumitrescu', 'V', '5040412345678', '2003-03-23', '2', 'SM', '8.2'),
('12', '1001', 'Maria Stanescu', 'I', '5050519876543', '2003-02-16', '1', 'CTI', '8.3'),
('13', '1006', 'Florin Stoica', 'N', '6051019876543', '2003-11-11', '2', 'AA', '9.3'),
('14', '1008', 'Dimitriovici Stefan', 'O.P', '50203197137', '2002-03-20', '3', 'EFS', '7.03'),
('15', '1004', 'Popescu Ionut', 'S.A', '0877212323134', '2002-03-20', '4', 'CTI', '9'),
('16', '1006', 'Dorian Silviu', 'S', '2147483647', '2002-04-03', '2', 'drept', '9'),
('2', '1004', 'Adam Ioana', 'V', '5078930354534', '2001-06-18', '2', 'Medicina generala', '9'),
('3', '1004', 'Balan Cristina', 'C', '5078930345534', '2001-07-02', '2', 'ETTI', '8.3'),
('4', '1001', 'Ana Maria Popescu', 'G', '5010112345678', '2000-07-30', '3', 'IM', '8.6'),
('5', '1005', 'Andrei Popa', 'E', '6010612345678', '2001-02-01', '3', 'AN', '8.5'),
('6', '1005', 'Elena Ionescu', 'A.L', '5020219876543', '2000-05-10', '5', 'SEN', '9.2'),
('7', '1004', 'Sofia Mihai', 'R', '5030301234567', '2001-09-09', '4', 'AMG', '9.1'),
('8', '1006', 'Alexandru Georgescu', 'V', '6020719876543', '2002-11-22', '2', 'FB', '7.9'),
('9', '1000', 'Ion Radu', 'C', '6030801234567', '2001-02-28', '3', 'AIA', '7.6');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `user`
--

CREATE TABLE `user` (
  `id` int(3) NOT NULL,
  `uname` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `utype` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Eliminarea datelor din tabel `user`
--

INSERT INTO `user` (`id`, `uname`, `password`, `utype`) VALUES
(1, 'Maria', '1234', 'Admin'),
(2, 'Daniel', '4321', 'Student'),
(3, 'Ema', '5678', 'Admin'),
(4, 'Dumitru', '8765', 'Student');

--
-- Indexuri pentru tabele eliminate
--

--
-- Indexuri pentru tabele `activity_log`
--
ALTER TABLE `activity_log`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexuri pentru tabele `burse`
--
ALTER TABLE `burse`
  ADD PRIMARY KEY (`idB`),
  ADD KEY `idS` (`idS`);

--
-- Indexuri pentru tabele `facultate`
--
ALTER TABLE `facultate`
  ADD PRIMARY KEY (`idF`);

--
-- Indexuri pentru tabele `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`idS`),
  ADD UNIQUE KEY `CNP` (`CNP`),
  ADD KEY `idF_fk` (`idF`);

--
-- Indexuri pentru tabele `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pentru tabele eliminate
--

--
-- AUTO_INCREMENT pentru tabele `activity_log`
--
ALTER TABLE `activity_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constrângeri pentru tabele eliminate
--

--
-- Constrângeri pentru tabele `burse`
--
ALTER TABLE `burse`
  ADD CONSTRAINT `burse_ibfk_1` FOREIGN KEY (`idS`) REFERENCES `student` (`idS`);

--
-- Constrângeri pentru tabele `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `idF_fk` FOREIGN KEY (`idF`) REFERENCES `facultate` (`idF`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
