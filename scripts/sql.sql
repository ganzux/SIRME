-- --------------------------------------------------------
-- Host:                         firext
-- Versión del servidor:         5.6.15-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             8.1.0.4545
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura para tabla firext.address
CREATE TABLE IF NOT EXISTS `address` (
  `idaddress` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(300) COLLATE latin1_bin NOT NULL DEFAULT '0',
  `idCustomer` bigint(20) NOT NULL DEFAULT '0',
  `prov` varchar(50) COLLATE latin1_bin DEFAULT '0',
  `pobl` varchar(300) COLLATE latin1_bin DEFAULT '0',
  `postalCode` varchar(5) COLLATE latin1_bin DEFAULT '0',
  `other` varchar(300) COLLATE latin1_bin DEFAULT '0',
  `location` varchar(120) COLLATE latin1_bin DEFAULT NULL,
  `monthMask` set('DIC','NOV','OCT','SEP','AGO','JUL','JUN','MAY','ABR','MAR','FEB','ENE') COLLATE latin1_bin DEFAULT NULL,
  `amount` varchar(10) COLLATE latin1_bin DEFAULT NULL,
  PRIMARY KEY (`idaddress`),
  KEY `FK_address_customers` (`idCustomer`),
  CONSTRAINT `FK_address_customers` FOREIGN KEY (`idCustomer`) REFERENCES `customers` (`idCustomer`)
) ENGINE=InnoDB AUTO_INCREMENT=1349 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;


-- Volcando estructura para tabla firext.applications
CREATE TABLE IF NOT EXISTS `applications` (
  `idApplication` int(10) NOT NULL AUTO_INCREMENT,
  `idMainApplication` int(10) DEFAULT NULL,
  `codeApplication` varchar(15) NOT NULL,
  `nameApplication` varchar(50) NOT NULL,
  `levelApplication` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idApplication`),
  UNIQUE KEY `codeApplication` (`codeApplication`),
  KEY `FKApplicatio546142` (`idMainApplication`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla firext.applications: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `applications` DISABLE KEYS */;
INSERT INTO `applications` (`idApplication`, `idMainApplication`, `codeApplication`, `nameApplication`, `levelApplication`) VALUES
	(1, NULL, 'CLIENTES', 'Clientes', 1),
	(2, NULL, 'EQUIPOS', 'Equipos', 1),
	(3, NULL, 'PARTES', 'Partes de Trabajo', 1),
	(4, NULL, 'USUARIOS', 'Usuarios', 1),
	(5, NULL, 'ESTADISTICAS', 'Estadísticas', 1),
	(6, NULL, 'GESTORES', 'Gestores', 1);
/*!40000 ALTER TABLE `applications` ENABLE KEYS */;


-- Volcando estructura para tabla firext.contact
CREATE TABLE IF NOT EXISTS `contact` (
  `idContact` bigint(20) NOT NULL AUTO_INCREMENT,
  `nameContact` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `dataContact` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `idCustomer` bigint(20) NOT NULL,
  PRIMARY KEY (`idContact`),
  KEY `FK_contact_customers` (`idCustomer`),
  CONSTRAINT `FK_contact_customers` FOREIGN KEY (`idCustomer`) REFERENCES `customers` (`idCustomer`)
) ENGINE=InnoDB AUTO_INCREMENT=1705 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;


-- Volcando estructura para tabla firext.customers
CREATE TABLE IF NOT EXISTS `customers` (
  `idCustomer` bigint(20) NOT NULL AUTO_INCREMENT,
  `nameCustomer` varchar(200) COLLATE latin1_bin NOT NULL,
  `cifCustomer` varchar(20) COLLATE latin1_bin NOT NULL,
  `codeCustomer` int(20) NOT NULL,
  `mainAddress` varchar(300) COLLATE latin1_bin DEFAULT NULL,
  `mainProv` varchar(50) COLLATE latin1_bin DEFAULT NULL,
  `mainPobl` varchar(300) COLLATE latin1_bin DEFAULT NULL,
  `mainPostalCode` varchar(5) COLLATE latin1_bin DEFAULT NULL,
  `mainMail` varchar(100) COLLATE latin1_bin DEFAULT NULL,
  `mainPhone` varchar(20) COLLATE latin1_bin DEFAULT NULL,
  `idManager` bigint(20) DEFAULT NULL,
  `idUser` int(6) DEFAULT NULL,
  `typeCustomer` tinyint(1) DEFAULT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idCustomer`),
  UNIQUE KEY `codeCustomer` (`codeCustomer`),
  KEY `FK_customers_manager` (`idManager`),
  KEY `FK_customers_users` (`idUser`),
  CONSTRAINT `FK_customers_manager` FOREIGN KEY (`idManager`) REFERENCES `manager` (`idManager`),
  CONSTRAINT `FK_customers_users` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=1080 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando estructura para tabla firext.customer_report
CREATE TABLE IF NOT EXISTS `customer_report` (
  `idCustomer` bigint(20) NOT NULL,
  `idReport` smallint(6) NOT NULL,
  PRIMARY KEY (`idCustomer`,`idReport`),
  KEY `FK_customer_report_report` (`idReport`),
  CONSTRAINT `FK_customer_report_customers` FOREIGN KEY (`idCustomer`) REFERENCES `customers` (`idCustomer`),
  CONSTRAINT `FK_customer_report_report` FOREIGN KEY (`idReport`) REFERENCES `report` (`idReport`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando estructura para tabla firext.manager
CREATE TABLE IF NOT EXISTS `manager` (
  `idManager` bigint(20) NOT NULL AUTO_INCREMENT,
  `nameManager` varchar(200) COLLATE latin1_bin NOT NULL DEFAULT '0',
  `phoneManager` varchar(200) COLLATE latin1_bin DEFAULT '0',
  `mailManager` varchar(200) COLLATE latin1_bin DEFAULT '0',
  PRIMARY KEY (`idManager`)
) ENGINE=InnoDB AUTO_INCREMENT=811 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando estructura para tabla firext.managercontact
CREATE TABLE IF NOT EXISTS `managercontact` (
  `idManagerContact` bigint(20) NOT NULL AUTO_INCREMENT,
  `nameContact` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `dataContact` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `idManager` bigint(20) NOT NULL,
  PRIMARY KEY (`idManagerContact`),
  KEY `FK_contact_manager` (`idManager`),
  CONSTRAINT `FK_contact_manager` FOREIGN KEY (`idManager`) REFERENCES `manager` (`idManager`)
) ENGINE=InnoDB AUTO_INCREMENT=1930 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando estructura para tabla firext.permissions
CREATE TABLE IF NOT EXISTS `permissions` (
  `idPermission` int(6) NOT NULL AUTO_INCREMENT,
  `codePermission` varchar(50) NOT NULL,
  `descriptionPermission` varchar(255) DEFAULT NULL,
  `idApplication` int(10) NOT NULL,
  PRIMARY KEY (`idPermission`),
  UNIQUE KEY `idPermission` (`idPermission`),
  UNIQUE KEY `codePermission` (`codePermission`),
  KEY `idApplication` (`idApplication`),
  CONSTRAINT `FK_permissions_applications` FOREIGN KEY (`idApplication`) REFERENCES `applications` (`idApplication`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla firext.permissions: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` (`idPermission`, `codePermission`, `descriptionPermission`, `idApplication`) VALUES
	(1, 'VIEW_CLIENTES', 'Acceso a Clientes', 1),
	(2, 'VIEW_EQUIPOS', 'Acceso a Equipos', 2),
	(3, 'VIEW_PARTES', 'Acceso a Partes', 3),
	(4, 'VIEW_USUARIOS', 'Acceso a Usuarios', 4),
	(6, 'VIEW_GESTORES', 'Acceso a Gestores', 6);
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;


-- Volcando estructura para tabla firext.photos
CREATE TABLE IF NOT EXISTS `photos` (
  `idPhoto` bigint(12) NOT NULL AUTO_INCREMENT,
  `idWork` bigint(12) NOT NULL,
  `path` varchar(255) COLLATE latin1_bin NOT NULL,
  `comments` varchar(1024) COLLATE latin1_bin DEFAULT NULL,
  `dateCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idPhoto`),
  UNIQUE KEY `path` (`path`),
  KEY `FK_photos_work` (`idWork`),
  CONSTRAINT `FK_photos_work` FOREIGN KEY (`idWork`) REFERENCES `work` (`idWork`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando estructura para tabla firext.profiles
CREATE TABLE IF NOT EXISTS `profiles` (
  `idProfile` int(6) NOT NULL AUTO_INCREMENT,
  `codeProfile` varchar(50) NOT NULL,
  `descriptionProfile` varchar(255) DEFAULT NULL,
  `URLSuccessLogin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProfile`),
  UNIQUE KEY `idProfile` (`idProfile`),
  UNIQUE KEY `codeProfile` (`codeProfile`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Volcando estructura para tabla firext.question
CREATE TABLE IF NOT EXISTS `question` (
  `idQuestion` bigint(20) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) COLLATE latin1_bin NOT NULL,
  `idQuestionGroup` bigint(20) NOT NULL,
  `order` tinyint(3) DEFAULT '0',
  `idReplyType` smallint(6) DEFAULT NULL,
  `totalize` tinyint(1) DEFAULT NULL,
  `search` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idQuestion`),
  KEY `FK__report` (`idQuestionGroup`),
  KEY `FK_question_replytype` (`idReplyType`),
  CONSTRAINT `FK_question_questiongroup` FOREIGN KEY (`idQuestionGroup`) REFERENCES `questiongroup` (`idQuestionGroup`),
  CONSTRAINT `FK_question_replytype` FOREIGN KEY (`idReplyType`) REFERENCES `replytype` (`idReplyType`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando datos para la tabla firext.question: ~310 rows (aproximadamente)
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` (`idQuestion`, `question`, `idQuestionGroup`, `order`, `idReplyType`, `totalize`, `search`) VALUES
	(1, 'Número de referencia', 1, 1, 9, NULL, 1),
	(2, 'Número de Placa', 1, 2, 2, NULL, 1),
	(3, 'Ubicación', 1, 3, 1, NULL, NULL),
	(4, 'Fabricación', 1, 4, 8, NULL, NULL),
	(5, 'Último Ph', 1, 5, 8, NULL, NULL),
	(6, 'Caducidad', 1, 6, 8, NULL, NULL),
	(7, 'Tipo / Eficacia', 1, 7, 1, NULL, NULL),
	(8, 'Peso CO2 Correcto', 1, 8, 4, NULL, NULL),
	(9, 'Acceso', 1, 9, 5, NULL, NULL),
	(10, 'Señalización', 1, 10, 5, 1, NULL),
	(11, 'Estado Exterior', 1, 11, 5, 1, NULL),
	(12, 'Necesario Ph', 1, 12, 4, NULL, NULL),
	(13, 'Necesario Recarga', 1, 13, 4, 1, NULL),
	(14, 'Desaparecido', 1, 14, 4, 1, NULL),
	(15, 'Caducado', 1, 15, 4, 1, NULL),
	(16, 'Fuera Norma', 1, 16, 4, 1, NULL),
	(17, 'Operación', 1, 18, 1, NULL, NULL),
	(18, 'Retimbrado', 1, 17, 4, 1, NULL),
	(19, 'Número de Referencia', 2, 1, 9, NULL, 1),
	(20, 'Tipo', 2, 2, 2, NULL, NULL),
	(21, 'Tramo', 2, 3, 1, NULL, NULL),
	(22, 'Presión', 2, 4, 6, NULL, NULL),
	(23, 'F.Fabric', 2, 5, 8, NULL, NULL),
	(24, 'Último PH', 2, 6, 8, NULL, NULL),
	(25, 'Situación', 2, 7, 1, NULL, NULL),
	(26, 'Acceso', 2, 8, 5, NULL, NULL),
	(27, 'Señalización', 2, 9, 5, 1, NULL),
	(28, 'Devan', 2, 10, 5, NULL, NULL),
	(29, 'Válvula', 2, 11, 5, NULL, NULL),
	(30, 'Manómetro', 2, 12, 5, 1, NULL),
	(31, 'Junta', 2, 13, 5, 1, NULL),
	(32, 'Lanza', 2, 14, 5, NULL, NULL),
	(33, 'Puerta', 2, 15, 5, NULL, NULL),
	(34, 'Cristal/Medida', 2, 16, 1, NULL, NULL),
	(35, 'Armario', 2, 17, 5, NULL, NULL),
	(36, 'Manguera', 2, 18, 5, NULL, NULL),
	(37, 'Cerradura', 2, 19, 5, NULL, NULL),
	(38, 'Retimbrar', 2, 20, 4, 1, NULL),
	(39, 'Operación', 2, 21, 1, NULL, NULL),
	(40, 'Número', 3, 1, 2, NULL, NULL),
	(41, 'Ubicación', 3, 2, 1, NULL, NULL),
	(42, 'Tipo de Luz', 3, 3, 1, NULL, NULL),
	(43, 'Nº de Luces', 3, 4, 2, NULL, NULL),
	(44, 'Modelo', 3, 5, 1, NULL, NULL),
	(45, 'Estado componentes bloques', 3, 6, 5, NULL, NULL),
	(46, 'Activación de luces', 3, 7, 5, NULL, NULL),
	(47, 'Intensidad de la luz', 3, 8, 5, NULL, NULL),
	(48, 'Bombillas Fundidas', 3, 9, 4, NULL, NULL),
	(49, 'Carcasas Deterioradas', 3, 10, 4, NULL, NULL),
	(50, 'Otras Anomalías', 3, 11, 1, NULL, NULL),
	(51, 'Observaciones', 3, 12, 1, NULL, NULL),
	(52, 'Cambiar Elemento', 3, 13, 4, 1, NULL),
	(53, 'Número', 4, 1, 2, NULL, NULL),
	(54, 'Ubicación', 4, 2, 1, NULL, NULL),
	(55, 'Equipo', 4, 3, 1, NULL, NULL),
	(56, 'Marca', 4, 4, 1, NULL, NULL),
	(57, 'Modelo', 4, 5, 1, NULL, NULL),
	(58, 'Funcionamiento Manual', 4, 6, 5, NULL, NULL),
	(59, 'Funcionamiento Automático', 4, 7, 5, NULL, NULL),
	(60, 'F. Alimentación y Batería', 4, 8, 5, NULL, NULL),
	(61, 'Estado Aparente', 4, 9, 5, NULL, NULL),
	(62, 'Observaciones', 4, 10, 1, NULL, NULL),
	(64, 'Ubicación', 5, 1, 1, NULL, NULL),
	(65, 'Marca', 5, 2, 1, NULL, NULL),
	(66, 'Modelo', 5, 3, 1, NULL, NULL),
	(67, 'Nº Zona', 5, 4, 2, NULL, NULL),
	(68, 'Red', 5, 5, 5, NULL, NULL),
	(69, 'Pilotos', 5, 6, 5, NULL, NULL),
	(70, 'Relés', 5, 7, 5, NULL, NULL),
	(71, 'Zona Reserva', 5, 8, 2, NULL, NULL),
	(72, 'Control', 5, 9, 5, NULL, NULL),
	(73, 'Zumbador', 5, 10, 5, NULL, NULL),
	(74, 'Líneas Cableado', 5, 11, 5, NULL, NULL),
	(75, 'Nº Baterías', 5, 12, 2, NULL, NULL),
	(76, 'Tipo Baterías', 5, 13, 1, NULL, NULL),
	(77, 'Estado Baterías', 5, 14, 5, NULL, NULL),
	(78, 'Módulos', 5, 15, 5, NULL, NULL),
	(79, 'Marca Detectores', 6, 1, 1, NULL, NULL),
	(80, 'Tipo', 6, 2, 1, NULL, NULL),
	(81, 'Modelo', 6, 3, 1, NULL, NULL),
	(82, 'Ubicación', 7, 1, 1, NULL, NULL),
	(83, 'Nº Zona', 7, 2, 2, NULL, NULL),
	(84, 'Nº Detector', 7, 3, 2, NULL, NULL),
	(85, 'Estado Detector', 7, 4, 5, NULL, NULL),
	(86, 'Nº Pulsador', 7, 5, 2, NULL, NULL),
	(87, 'Estado Pulsador', 7, 6, 5, NULL, NULL),
	(88, 'Nº Indicador', 7, 7, 2, NULL, NULL),
	(89, 'Estado Indicador', 7, 8, 5, NULL, NULL),
	(90, 'Nº Sirena', 7, 9, 2, NULL, NULL),
	(91, 'Estado Sirena', 7, 10, 5, NULL, NULL),
	(92, 'Nº Retenedor', 7, 11, 2, NULL, NULL),
	(93, 'Estado Retenedor', 7, 12, 5, NULL, NULL),
	(94, 'Otros', 7, 13, 1, NULL, NULL),
	(95, 'Ubicación', 8, 1, 1, NULL, NULL),
	(96, 'Marca', 8, 2, 1, NULL, NULL),
	(97, 'Modelo', 8, 3, 1, NULL, NULL),
	(98, 'Nº Zonas', 8, 4, 2, NULL, NULL),
	(99, 'Zona Reserva', 8, 5, 1, NULL, NULL),
	(100, 'Arranque Manual', 8, 6, 5, NULL, NULL),
	(101, 'Arranque Automático', 8, 7, 5, NULL, NULL),
	(102, 'Nivel de Concentración', 8, 8, 1, NULL, NULL),
	(103, 'Pilotos', 8, 9, 5, NULL, NULL),
	(104, 'Marca Detectores', 9, 1, 1, NULL, NULL),
	(105, 'Modelo', 9, 2, 1, NULL, NULL),
	(106, 'Nº Detectores Caducados', 9, 3, 2, NULL, NULL),
	(107, 'Fecha Fabricación', 9, 4, 3, NULL, NULL),
	(108, 'Fecha último cambio Filtro', 9, 5, 3, NULL, NULL),
	(109, 'Fecha próximo cambio Filtro', 9, 6, 3, NULL, NULL),
	(110, 'Ubicación', 10, 1, 1, NULL, NULL),
	(111, 'Nº Zona', 10, 2, 2, NULL, NULL),
	(112, 'Nº Detector', 10, 3, 2, NULL, NULL),
	(113, 'Estado Detector', 10, 4, 5, NULL, NULL),
	(114, 'Nº Cuadro', 10, 5, 2, NULL, NULL),
	(115, 'Estado Cuadro', 10, 6, 5, NULL, NULL),
	(116, 'Nº Motor', 10, 7, 2, NULL, NULL),
	(117, 'Estado Motor', 10, 8, 5, NULL, NULL),
	(118, 'Nº Compuerta', 10, 9, 2, NULL, NULL),
	(119, 'Estado Compuerta', 10, 10, 5, NULL, NULL),
	(120, 'Otros', 10, 11, 1, NULL, NULL),
	(121, 'Nº Referencia', 11, 1, 2, NULL, NULL),
	(122, 'Ubicación', 11, 2, 1, NULL, NULL),
	(123, 'Marca', 11, 3, 1, NULL, NULL),
	(124, 'Gong', 11, 4, 5, NULL, NULL),
	(125, 'Válvula de Prueba', 11, 5, 5, NULL, NULL),
	(126, 'Interruptor de Flujo', 11, 6, 5, NULL, NULL),
	(127, 'Pulgadas', 11, 7, 2, NULL, NULL),
	(128, 'Punto de Prueba', 11, 8, 4, NULL, NULL),
	(129, 'Desagüe', 11, 9, 4, NULL, NULL),
	(130, 'Conducido', 11, 10, 4, NULL, NULL),
	(131, 'Estado General de las válvulas', 11, 11, 5, NULL, NULL),
	(132, 'Solenoide', 11, 12, 5, NULL, NULL),
	(133, 'Punto de Prueba', 11, 13, 1, NULL, NULL),
	(134, 'Línea de Rociadores', 11, 14, 1, NULL, NULL),
	(135, 'Línea de Acometida', 11, 15, 1, NULL, NULL),
	(136, 'Tipo de Instalación', 11, 16, 1, NULL, NULL),
	(137, 'Tipo de Rociadores', 11, 17, 1, NULL, NULL),
	(138, 'Temperatura', 11, 18, 2, NULL, NULL),
	(139, 'Riesgo a cubrir', 11, 19, 1, NULL, NULL),
	(140, 'Observaciones', 11, 20, 1, NULL, NULL),
	(141, 'Ubicación', 12, 1, 1, NULL, NULL),
	(142, 'Diámetro (m/m)', 12, 2, 2, NULL, NULL),
	(143, 'Accesibilidad', 12, 3, 5, NULL, NULL),
	(144, 'Señalización', 12, 4, 5, NULL, NULL),
	(145, 'Estanqueidad del Conjunto', 12, 5, 5, NULL, NULL),
	(146, 'Engrase de Rosca', 12, 6, 5, NULL, NULL),
	(147, 'Estado de Juntas', 12, 7, 5, NULL, NULL),
	(148, 'Estado de Racores', 12, 8, 5, NULL, NULL),
	(149, 'Funcionamiento de la válvula Principal', 12, 9, 5, NULL, NULL),
	(150, 'Funcionamiento del Sistema de Drenaje', 12, 10, 5, NULL, NULL),
	(151, 'Ubicación', 13, 1, 1, NULL, NULL),
	(152, 'Manguera 70', 13, 2, 4, NULL, NULL),
	(153, 'Fecha Fabricación', 13, 3, 3, NULL, NULL),
	(154, 'Fecha Retimbrado', 13, 4, 3, NULL, NULL),
	(155, 'Manguera 45', 13, 5, 4, NULL, NULL),
	(156, 'Fecha Fabricación', 13, 6, 3, NULL, NULL),
	(157, 'Fecha Retimbrado', 13, 7, 3, NULL, NULL),
	(158, 'Manguera 45', 13, 8, 4, NULL, NULL),
	(159, 'Fecha Fabricación', 13, 9, 3, NULL, NULL),
	(160, 'Fecha Retimbrado', 13, 10, 3, NULL, NULL),
	(161, 'Bifurcación 45', 13, 11, 4, NULL, NULL),
	(162, 'Racor Barcelona 70', 13, 12, 4, NULL, NULL),
	(163, 'Lanza 45 (2 Unidades)', 13, 13, 4, NULL, NULL),
	(164, 'Lanza 70', 13, 14, 4, NULL, NULL),
	(165, 'Reducción 70 x 45', 13, 15, 4, NULL, NULL),
	(166, 'Tubería de Acero Galvanizado de 3"', 14, 1, 1, NULL, NULL),
	(167, 'Salidas en plantas pares hasta la 8ª y en todas a partir de esta', 14, 2, 4, NULL, NULL),
	(168, 'Válvula de Aireación', 14, 3, 4, NULL, NULL),
	(169, 'Llave de Purga', 14, 4, 4, NULL, NULL),
	(170, 'Ubicación', 15, 1, 1, NULL, NULL),
	(171, 'Accesibilidad Toma Calle', 15, 2, 5, NULL, NULL),
	(172, 'Accesibilidad Toma Pisos', 15, 3, 1, NULL, NULL),
	(173, 'Funcionamiento tapas y cierres', 15, 4, 1, NULL, NULL),
	(174, 'Llaves siamesas cerradas', 15, 5, 4, NULL, NULL),
	(175, 'Llaves de seccionamiento abiertas', 15, 6, 4, NULL, NULL),
	(176, 'Tapas de Racores colocadas', 15, 7, 4, NULL, NULL),
	(177, 'Marca', 16, 1, 1, NULL, NULL),
	(178, 'Modelo', 16, 2, 1, NULL, NULL),
	(179, 'Nº Zonas', 16, 3, 2, NULL, NULL),
	(180, 'Ubicación', 16, 4, 1, NULL, NULL),
	(181, 'Red', 16, 5, 5, NULL, NULL),
	(182, 'Pilotos', 16, 6, 5, NULL, NULL),
	(183, 'Relés', 16, 7, 5, NULL, NULL),
	(184, 'Zonas Reserva', 16, 8, 2, NULL, NULL),
	(185, 'Nº Baterías', 16, 9, 2, NULL, NULL),
	(186, 'Tipo Baterías', 16, 10, 1, NULL, NULL),
	(187, 'Estado Baterías', 16, 11, 5, NULL, NULL),
	(188, 'Control', 16, 12, 5, NULL, NULL),
	(189, 'Zumbador', 16, 13, 5, NULL, NULL),
	(190, 'Líneas de Cableado', 16, 14, 5, NULL, NULL),
	(191, 'Marca Detectores', 17, 1, 1, NULL, NULL),
	(192, 'Tipo', 17, 2, 1, NULL, NULL),
	(193, 'Modelo', 17, 3, 1, NULL, NULL),
	(194, 'Ubicación', 18, 1, 1, NULL, NULL),
	(195, 'Nº Zona', 18, 2, 2, NULL, NULL),
	(196, 'Nº Detector', 18, 3, 2, NULL, NULL),
	(197, 'Estado Detector', 18, 4, 5, NULL, NULL),
	(198, 'Nº Pulsadores Bloqueo', 18, 5, 2, NULL, NULL),
	(199, 'Estado Pulsadores Bloqueo', 18, 6, 5, NULL, NULL),
	(200, 'Nº Pulsadores Disparo', 18, 7, 2, NULL, NULL),
	(201, 'Estado Pulsadoes Disparo', 18, 8, 5, NULL, NULL),
	(202, 'Nº Cartel', 18, 9, 2, NULL, NULL),
	(203, 'Estado Cartel', 18, 10, 5, NULL, NULL),
	(204, 'Nº Sirenas', 18, 11, 2, NULL, NULL),
	(205, 'Estado Sirenas', 18, 12, 5, NULL, NULL),
	(206, 'Otros', 18, 13, 1, NULL, NULL),
	(207, 'Extinción con Gas', 19, 1, 1, NULL, NULL),
	(208, 'Protección válvula descarga', 19, 2, 4, NULL, NULL),
	(209, 'Sistema de pesaje', 19, 3, 4, NULL, NULL),
	(210, 'Marca Valvulería', 19, 4, 1, NULL, NULL),
	(211, 'Tapón protector', 19, 5, 4, NULL, NULL),
	(212, 'Soportación', 19, 6, 4, NULL, NULL),
	(213, 'Ubicación', 20, 1, 1, NULL, NULL),
	(214, 'Nº Cilindro', 20, 2, 2, NULL, NULL),
	(215, 'Volumen (litros)', 20, 3, 6, NULL, NULL),
	(216, 'Carga (kilos)', 20, 4, 6, NULL, NULL),
	(217, 'Tara (kilos)', 20, 5, 6, NULL, NULL),
	(218, 'Peso total (kilos)', 20, 6, 6, NULL, NULL),
	(219, 'Presión (Kg / cm2)', 20, 7, 6, NULL, NULL),
	(220, 'Fecha Fabricación', 20, 8, 7, NULL, NULL),
	(221, 'Fecha Carga', 20, 9, 7, NULL, NULL),
	(222, 'Fecha Retimbrado', 20, 10, 7, NULL, NULL),
	(223, 'Tipo Disparo', 20, 11, 1, NULL, NULL),
	(224, 'Ubicación del sistema', 21, 1, 1, NULL, NULL),
	(225, 'Nº de difusores', 21, 2, 2, NULL, NULL),
	(226, 'Nº de fusibles', 21, 3, 2, NULL, NULL),
	(227, 'Fabricante del sistema', 21, 4, 1, NULL, NULL),
	(228, 'Nº Botellas', 21, 5, 2, NULL, NULL),
	(229, 'Fecha de instalación', 21, 6, 3, NULL, NULL),
	(230, 'Kg de gas/botellas', 21, 7, 6, NULL, NULL),
	(231, 'Agente: Bicarbonato potásico', 21, 8, 4, NULL, NULL),
	(232, 'Agente: Fosfato monoamónico', 21, 9, 4, NULL, NULL),
	(233, 'Agente: Bicarbonato sódico', 21, 10, 4, NULL, NULL),
	(234, 'Agente: Cloruro potásico', 21, 11, 4, NULL, NULL),
	(235, 'Agente: Urea bicarbonato potásico', 21, 12, 4, NULL, NULL),
	(236, 'Agente: Otros', 21, 13, 1, NULL, NULL),
	(237, 'Equipo protegido: Freidoras', 21, 14, 4, NULL, NULL),
	(239, 'Equipo protegido: Planchas', 21, 15, 4, NULL, NULL),
	(240, 'Equipo protegido: Parrillas', 21, 16, 4, NULL, NULL),
	(241, 'Equipo protegido: Quemadores', 21, 17, 4, NULL, NULL),
	(242, 'Equipo protegido: Conductos', 21, 18, 4, NULL, NULL),
	(243, 'Equipo protegido: Otros', 21, 19, 1, NULL, NULL),
	(244, '¿Se dispone de cartucho propelente de gas?', 21, 20, 4, NULL, NULL),
	(245, '¿Está conectado al sistema de alarma?', 21, 21, 4, NULL, NULL),
	(246, '¿Corte automático del equipo de cocina?', 21, 22, 4, NULL, NULL),
	(247, 'Corte automático del equipo de cocina', 21, 23, 1, NULL, NULL),
	(248, 'Fecha de la última prueba hidrostática', 21, 24, 3, NULL, NULL),
	(249, 'Fecha de la prueba de funcionamiento', 21, 25, 3, NULL, NULL),
	(250, '¿Hay corrosión en algún componente del sistema de detección?', 22, 1, 4, NULL, NULL),
	(251, '¿La unidad de descarga ha sido alterada?', 22, 2, 4, NULL, NULL),
	(252, '¿Los sellos de inspección visual se encuentran en su sitio y en buen estado?', 22, 3, 4, NULL, NULL),
	(253, '¿Hay tubos sueltos?', 22, 4, 4, NULL, NULL),
	(254, '¿Faltan las tapas de las boquillas?', 22, 5, 4, NULL, NULL),
	(255, '¿Las boquillas están orientadas sobre la superficie que protegen?', 22, 6, 4, NULL, NULL),
	(256, '¿Las tapas de las boquillas están cubiertas de grasa?', 22, 7, 4, NULL, NULL),
	(257, '¿Se ha quitado la tapa de la boquilla, comprobado que no se encuentre quebradiza, y vuelto a ajustar en la boquilla?', 22, 8, 4, NULL, NULL),
	(258, '¿La rótula metálica de escape puede girar libremente sobre la boquilla?', 22, 9, 4, NULL, NULL),
	(259, '¿El indicador visual en la unidad de descarga está correcto?', 22, 10, 4, NULL, NULL),
	(260, '¿El sistema está conectado?', 22, 11, 4, NULL, NULL),
	(261, '¿La estación de activación manual está obstruida o alterada?', 22, 12, 4, NULL, NULL),
	(262, '¿La etiqueta o certificado de mantenimiento está en su lugar?', 22, 13, 4, NULL, NULL),
	(263, '¿Cada unidad de tanque y descarga están instaladas en áreas cuyo rango de temperaturas sea de 0-54 ºC?', 22, 14, 4, NULL, NULL),
	(264, '¿El tanque de almacenamiento del agente extintor está en un lugar donde la temperatura pueda subir a más de 54 ºC o donde se pueda calentar a una temperatura mayor de 54 ºC a causa de la conductividad a través de los tubos de descarga recalentados?', 22, 15, 4, NULL, NULL),
	(265, 'Grupo ElectroBomba 1', 23, 1, 1, NULL, NULL),
	(266, 'Comprobación Fugas por juntas, Prensa', 23, 2, 5, NULL, NULL),
	(267, 'Anotación Intensidad Fase R', 23, 3, 6, NULL, NULL),
	(268, 'Anotación Intensidad Fase S', 23, 4, 6, NULL, NULL),
	(269, 'Anotación Intensidad Fase T', 23, 5, 6, NULL, NULL),
	(270, 'Control de Ruido', 23, 6, 5, NULL, NULL),
	(271, 'Control de curva de Presión', 23, 7, 5, NULL, NULL),
	(272, 'Grupo ElectroBomba 2', 23, 8, 1, NULL, NULL),
	(273, 'Comprobación Fugas por juntas, Prensa', 23, 9, 5, NULL, NULL),
	(274, 'Anotación Intensidad Fase R', 23, 10, 6, NULL, NULL),
	(275, 'Anotación Intensidad Fase S', 23, 11, 6, NULL, NULL),
	(276, 'Anotación Intensidad Fase T', 23, 12, 6, NULL, NULL),
	(277, 'Control de Ruido', 23, 13, 5, NULL, NULL),
	(278, 'Control de curva de Presión', 23, 14, 5, NULL, NULL),
	(279, 'Cuadro eléctrico', 23, 15, 1, NULL, NULL),
	(280, 'Comprobación de Interruptores', 23, 16, 5, NULL, NULL),
	(281, 'Comprobación de Diferenciales (Si existen)', 23, 17, 5, NULL, NULL),
	(282, 'Comprobación de automáticos de Prtecc', 23, 18, 5, NULL, NULL),
	(283, 'Comprobación de contadores', 23, 19, 5, NULL, NULL),
	(284, 'Comprobación térmicos de protección', 23, 20, 5, NULL, NULL),
	(285, 'Comprobación de Alternancia', 23, 21, 5, NULL, NULL),
	(286, 'Instalación', 23, 22, 1, NULL, NULL),
	(287, 'Comprobación de manómetro', 23, 23, 5, NULL, NULL),
	(288, 'Comprobación visual fugas en válvula', 23, 24, 5, NULL, NULL),
	(289, 'Comprobación visual fugas en tubería', 23, 25, 5, NULL, NULL),
	(290, 'Depósito de llenado', 23, 26, 1, NULL, NULL),
	(291, 'Comprobación de funcionamiento', 23, 27, 5, NULL, NULL),
	(292, 'Comprobación de estanqueidad', 23, 28, 5, NULL, NULL),
	(293, 'Hidrobox', 23, 29, 1, NULL, NULL),
	(294, 'Inspección de fugas por juntas y racores', 23, 30, 5, NULL, NULL),
	(295, 'Comprobación de cámare de aire y reposición', 23, 31, 5, NULL, NULL),
	(296, 'Presostatos', 23, 32, 1, NULL, NULL),
	(297, 'Inspección de fugas por juntas y racores', 23, 33, 5, NULL, NULL),
	(298, 'Comprobación de regulación', 23, 34, 5, NULL, NULL),
	(299, 'Electroválvulas', 23, 35, 1, NULL, NULL),
	(300, 'Inspección de fugas por juntas y racores', 23, 36, 5, NULL, NULL),
	(301, 'Comprobación de funcionamiento', 23, 37, 5, NULL, NULL),
	(302, 'Relojes', 23, 38, 1, NULL, NULL),
	(303, 'Comprobación de funcionamiento', 23, 39, 5, NULL, NULL),
	(304, 'Puesta en hora si procede', 23, 40, 5, NULL, NULL),
	(305, 'Dispositivo de seguridad por falta de agua', 23, 41, 1, NULL, NULL),
	(306, 'Comprobación de funcionamiento', 23, 42, 5, NULL, NULL),
	(307, 'Depósito receptor', 23, 43, 1, NULL, NULL),
	(308, 'Comprobación del estado de la limpieza', 23, 44, 5, NULL, NULL),
	(309, 'Comprobación de ausencia de fugas', 23, 45, 5, NULL, NULL),
	(310, 'Vaciado, retirada de lodos y limpieza del depósito sin desinfecciñon', 23, 46, 5, NULL, NULL),
	(311, 'Desinfección contra legionelosis', 23, 47, 1, NULL, NULL),
	(312, 'Número de Difusores', 19, 7, 2, NULL, NULL);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;


-- Volcando estructura para tabla firext.questiongroup
CREATE TABLE IF NOT EXISTS `questiongroup` (
  `idQuestionGroup` bigint(20) NOT NULL AUTO_INCREMENT,
  `idReport` smallint(6) NOT NULL DEFAULT '0',
  `nameQuestionGroup` varchar(100) COLLATE latin1_bin NOT NULL,
  `times` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idQuestionGroup`),
  KEY `FK_questiongroup_report` (`idReport`),
  CONSTRAINT `FK_questiongroup_report` FOREIGN KEY (`idReport`) REFERENCES `report` (`idReport`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando datos para la tabla firext.questiongroup: ~23 rows (aproximadamente)
/*!40000 ALTER TABLE `questiongroup` DISABLE KEYS */;
INSERT INTO `questiongroup` (`idQuestionGroup`, `idReport`, `nameQuestionGroup`, `times`) VALUES
	(1, 1, 'Extintor', -1),
	(2, 2, 'Elemento', -1),
	(3, 3, 'Luz', -1),
	(4, 4, 'Elemento', -1),
	(5, 5, 'Características de la Central', 1),
	(6, 5, 'Elementos del Sistema y Distribución', 1),
	(7, 5, 'Elementos', -1),
	(8, 6, 'Características de la Central', 1),
	(9, 6, 'Elementos del Sistema y Distribución', 1),
	(10, 6, 'Elementos', -1),
	(11, 7, 'Puesto de Control', -1),
	(12, 8, 'Hidrantes', -1),
	(13, 8, 'Casetas', -1),
	(14, 9, 'General', 1),
	(15, 9, 'Elementos', -1),
	(16, 10, 'Características de la central', 1),
	(17, 10, 'Elementos del sistema y distribución', 1),
	(18, 10, 'Detectores', -1),
	(19, 10, 'Datos de válvulas', 1),
	(20, 10, 'Cilindros', -1),
	(21, 11, 'General', 1),
	(22, 11, 'Preguntas', 1),
	(23, 12, 'Columna', -1);
/*!40000 ALTER TABLE `questiongroup` ENABLE KEYS */;


-- Volcando estructura para tabla firext.reply
CREATE TABLE IF NOT EXISTS `reply` (
  `idReply` bigint(20) NOT NULL AUTO_INCREMENT,
  `idQuestion` bigint(20) DEFAULT NULL,
  `reply` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `idReplyGroup` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idReply`),
  KEY `FK_reply_question` (`idQuestion`),
  KEY `FK_reply_replygroup` (`idReplyGroup`),
  CONSTRAINT `FK_reply_question` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`),
  CONSTRAINT `FK_reply_replygroup` FOREIGN KEY (`idReplyGroup`) REFERENCES `replygroup` (`idReplyGroup`)
) ENGINE=InnoDB AUTO_INCREMENT=388553 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;


-- Volcando estructura para tabla firext.replygroup
CREATE TABLE IF NOT EXISTS `replygroup` (
  `idReplyGroup` bigint(20) NOT NULL AUTO_INCREMENT,
  `nameReplyGroup` varchar(100) COLLATE latin1_bin NOT NULL,
  `idQuestionGroup` bigint(20) NOT NULL,
  `idWork` bigint(20) NOT NULL,
  `idReport` smallint(6) NOT NULL,
  PRIMARY KEY (`idReplyGroup`),
  KEY `FK__questiongroup` (`idQuestionGroup`),
  KEY `FK_replygroup_work` (`idWork`),
  KEY `FK_replygroup_report` (`idReport`),
  CONSTRAINT `FK_replygroup_report` FOREIGN KEY (`idReport`) REFERENCES `report` (`idReport`),
  CONSTRAINT `FK_replygroup_work` FOREIGN KEY (`idWork`) REFERENCES `work` (`idWork`),
  CONSTRAINT `FK__questiongroup` FOREIGN KEY (`idQuestionGroup`) REFERENCES `questiongroup` (`idQuestionGroup`)
) ENGINE=InnoDB AUTO_INCREMENT=22911 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;


-- Volcando estructura para tabla firext.replytype
CREATE TABLE IF NOT EXISTS `replytype` (
  `idReplyType` smallint(6) NOT NULL AUTO_INCREMENT,
  `nameReplyType` varchar(20) COLLATE latin1_bin NOT NULL DEFAULT '0',
  PRIMARY KEY (`idReplyType`),
  UNIQUE KEY `nameReplyType` (`nameReplyType`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando datos para la tabla firext.replytype: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `replytype` DISABLE KEYS */;
INSERT INTO `replytype` (`idReplyType`, `nameReplyType`) VALUES
	(9, 'Automático'),
	(8, 'Año'),
	(5, 'Bien/Mal'),
	(3, 'Fecha'),
	(7, 'Mes/Año'),
	(6, 'Número Decimal'),
	(2, 'Número Entero'),
	(4, 'Sí/No'),
	(1, 'Texto');
/*!40000 ALTER TABLE `replytype` ENABLE KEYS */;


-- Volcando estructura para tabla firext.report
CREATE TABLE IF NOT EXISTS `report` (
  `idReport` smallint(6) NOT NULL AUTO_INCREMENT,
  `nameReport` varchar(50) COLLATE latin1_bin NOT NULL,
  `titleReport` varchar(250) COLLATE latin1_bin NOT NULL,
  `fileReport` varchar(20) COLLATE latin1_bin NOT NULL,
  UNIQUE KEY `nameReport` (`nameReport`),
  KEY `idReport` (`idReport`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando datos para la tabla firext.report: ~12 rows (aproximadamente)
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` (`idReport`, `nameReport`, `titleReport`, `fileReport`) VALUES
	(3, 'Alumbrado de Emergencia', 'CERTIFICADO DE REVISIÓN DE ALUMBRADO DE EMERGENCIA', 'alumbrado'),
	(2, 'B.I.E.S.', 'CERTIFICADO DE REVISIÓN DE BOCAS DE INCENDIO EQUIPADAS (B.I.E.S.)', 'bies'),
	(11, 'Campanas de Cocina', 'CERTIFICADO DE REVISIÓN DE SISTEMA DE EXTINCIÓN AUTOMÁTICA EN CAMPANAS DE COCINA', 'campanas'),
	(9, 'Columna Seca', 'CERTIFICADO DE REVISIÓN DE COLUMNA SECA', 'columna'),
	(5, 'Detección de Incendios', 'CERTIFICADO REVISIÓN SISTEMA DE DETECCIÓN DE INCENDIOS', 'incendios'),
	(6, 'Detección de Monóxido', 'CERTIFICADO REVISIÓN SISTEMA DE DETECCIÓN DE MONÓXIDO', 'monoxido'),
	(10, 'Extinción de Incendios', 'CERTIFICADO REVISIÓN SISTEMA DE DETECCIÓN Y EXTINCIÓN DE INCENDIOS', 'extincion'),
	(1, 'Extintores', 'CERTIFICADO DE REVISIÓN DE EXTINTORES', 'extintores'),
	(12, 'Grupo de Presión', 'CERTIFICADO REVISIÓN DE GRUPO DE PRESIÓN SANITARIO', 'presion'),
	(8, 'Hidrantes', 'CERTIFICADO DE REVISIÓN DE HIDRANTES', 'hidrantes'),
	(7, 'Rociadores', 'CERTIFICADO DE REVISIÓN DE PUESTO CONTROL ROCIADORES', 'rociadores'),
	(4, 'Sistemas de Humos y Calor', 'CERTIFICADO DE REVISIÓN DE SISTEMAS DE CONTROL DE HUMOS Y DE CALOR', 'humos');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;


-- Volcando estructura para tabla firext.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `idRole` int(6) NOT NULL AUTO_INCREMENT,
  `codeRole` varchar(50) NOT NULL,
  `descriptionRole` varchar(255) DEFAULT NULL,
  `URLSuccessLogin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idRole`),
  UNIQUE KEY `idRole` (`idRole`),
  UNIQUE KEY `codeRole` (`codeRole`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla firext.roles: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`idRole`, `codeRole`, `descriptionRole`, `URLSuccessLogin`) VALUES
	(1, 'ADM', 'Administrador', 'general'),
	(2, 'TCN', 'Técnico', 'general'),
	(3, 'FCT', 'Faturación', 'general'),
	(4, 'CM', 'Comercial', 'general');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


-- Volcando estructura para tabla firext.roles_permissions
CREATE TABLE IF NOT EXISTS `roles_permissions` (
  `idRole` int(6) NOT NULL,
  `idPermission` int(6) NOT NULL,
  PRIMARY KEY (`idRole`,`idPermission`),
  KEY `roles_permissions_1` (`idRole`),
  KEY `roles_permissions_2` (`idPermission`),
  CONSTRAINT `FK_roles_permissions_permissions` FOREIGN KEY (`idPermission`) REFERENCES `permissions` (`idPermission`),
  CONSTRAINT `FK_roles_permissions_roles` FOREIGN KEY (`idRole`) REFERENCES `roles` (`idRole`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla firext.roles_permissions: ~8 rows (aproximadamente)
/*!40000 ALTER TABLE `roles_permissions` DISABLE KEYS */;
INSERT INTO `roles_permissions` (`idRole`, `idPermission`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 6),
	(3, 1),
	(3, 2),
	(3, 3);
/*!40000 ALTER TABLE `roles_permissions` ENABLE KEYS */;


-- Volcando estructura para tabla firext.team
CREATE TABLE IF NOT EXISTS `team` (
  `idTeam` int(6) NOT NULL AUTO_INCREMENT,
  `nameTeam` varchar(50) COLLATE latin1_bin NOT NULL,
  `enabled` int(1) NOT NULL DEFAULT '1',
  `canUploadPhotos` int(1) NOT NULL DEFAULT '0',
  `phoneNumber` varchar(50) COLLATE latin1_bin NOT NULL,
  `password` varchar(50) COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`idTeam`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando datos para la tabla firext.team: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` (`idTeam`, `nameTeam`, `enabled`, `canUploadPhotos`, `phoneNumber`, `password`) VALUES
	(1, 'alvaro', 0, 0, '629737616', '38b9068af81e65939d840b9b3d0a7be8');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;


-- Volcando estructura para tabla firext.team_user
CREATE TABLE IF NOT EXISTS `team_user` (
  `idTeam` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  PRIMARY KEY (`idTeam`,`idUser`),
  KEY `Índice 1` (`idTeam`),
  KEY `Índice 2` (`idUser`),
  CONSTRAINT `FK_team` FOREIGN KEY (`idTeam`) REFERENCES `team` (`idTeam`),
  CONSTRAINT `FK__users` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Volcando datos para la tabla firext.team_user: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `team_user` DISABLE KEYS */;
INSERT INTO `team_user` (`idTeam`, `idUser`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `team_user` ENABLE KEYS */;


-- Volcando estructura para tabla firext.users
CREATE TABLE IF NOT EXISTS `users` (
  `idUser` int(6) NOT NULL AUTO_INCREMENT,
  `codeUser` varchar(10) NOT NULL,
  `nameUser` varchar(50) NOT NULL,
  `firstSurnameUser` varchar(100) DEFAULT NULL,
  `secondSurnameUser` varchar(100) DEFAULT NULL,
  `passwordUser` varchar(32) NOT NULL,
  `phoneNumberUser` varchar(11) DEFAULT NULL,
  `emailUser` varchar(100) DEFAULT NULL,
  `addedDateUser` date NOT NULL,
  `expirationDateUser` date DEFAULT NULL,
  `expirationDateUserPassword` date DEFAULT NULL,
  `locked` int(1) NOT NULL DEFAULT '0',
  `enabled` int(1) NOT NULL DEFAULT '1',
  `outOpen` int(1) NOT NULL DEFAULT '0',
  `lastAccess` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `IdUser` (`idUser`),
  UNIQUE KEY `CodeUser` (`codeUser`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla firext.users: ~14 rows (aproximadamente)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`idUser`, `codeUser`, `nameUser`, `firstSurnameUser`, `secondSurnameUser`, `passwordUser`, `phoneNumberUser`, `emailUser`, `addedDateUser`, `expirationDateUser`, `expirationDateUserPassword`, `locked`, `enabled`, `outOpen`, `lastAccess`) VALUES
	(1, 'alvaro', 'Álvaro', 'Alcedo', 'Moreno', '0f61f7efc354468f1df2bab87bb1b671', NULL, NULL, '2013-11-13', NULL, NULL, 0, 1, 0, '2015-07-14 12:19:52');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;


-- Volcando estructura para tabla firext.users_profiles
CREATE TABLE IF NOT EXISTS `users_profiles` (
  `IdUser` int(6) NOT NULL,
  `IdProfile` int(6) NOT NULL,
  PRIMARY KEY (`IdProfile`,`IdUser`),
  KEY `FK__profiles_users` (`IdProfile`),
  KEY `FK__users_profiles` (`IdUser`),
  CONSTRAINT `FK_users_profiles_profiles` FOREIGN KEY (`IdProfile`) REFERENCES `profiles` (`idProfile`),
  CONSTRAINT `FK_users_profiles_users` FOREIGN KEY (`IdUser`) REFERENCES `users` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando estructura para tabla firext.users_roles
CREATE TABLE IF NOT EXISTS `users_roles` (
  `IdUser` int(6) NOT NULL,
  `IdRole` int(6) NOT NULL,
  PRIMARY KEY (`IdUser`,`IdRole`),
  KEY `FK__users_roles_1` (`IdRole`),
  KEY `FK__users_roles_2` (`IdUser`),
  CONSTRAINT `FK_users_roles_roles` FOREIGN KEY (`IdRole`) REFERENCES `roles` (`idRole`),
  CONSTRAINT `FK_users_roles_users` FOREIGN KEY (`IdUser`) REFERENCES `users` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla firext.users_roles: ~24 rows (aproximadamente)
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`IdUser`, `IdRole`) VALUES
	(1, 1),
	(1, 2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;


-- Volcando estructura para tabla firext.usualreply
CREATE TABLE IF NOT EXISTS `usualreply` (
  `idUsualReply` bigint(20) NOT NULL,
  `usualReply` bigint(20) NOT NULL,
  `idQuestion` bigint(20) NOT NULL,
  PRIMARY KEY (`idUsualReply`),
  KEY `FK__question` (`idQuestion`),
  CONSTRAINT `FK__question` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;


-- Volcando estructura para tabla firext.work
CREATE TABLE IF NOT EXISTS `work` (
  `idWork` bigint(20) NOT NULL AUTO_INCREMENT,
  `year` smallint(6) NOT NULL,
  `albaran` int(11) NOT NULL DEFAULT '0',
  `idTeam` int(11) DEFAULT NULL,
  `idAddress` bigint(20) NOT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `dateReceived` timestamp NULL DEFAULT NULL,
  `memo` text CHARACTER SET utf8 COLLATE utf8_bin,
  `data` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `typeWork` tinyint(1) NOT NULL DEFAULT '1',
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `signpath` varchar(256) COLLATE latin1_bin DEFAULT NULL,
  `signName` varchar(256) COLLATE latin1_bin DEFAULT NULL,
  PRIMARY KEY (`idWork`),
  UNIQUE KEY `year_albaran` (`year`,`albaran`),
  KEY `FK_Work_team` (`idTeam`),
  KEY `FK_Work_customers` (`idAddress`),
  CONSTRAINT `FK_work_address` FOREIGN KEY (`idAddress`) REFERENCES `address` (`idaddress`),
  CONSTRAINT `FK_Work_team` FOREIGN KEY (`idTeam`) REFERENCES `team` (`idTeam`)
) ENGINE=InnoDB AUTO_INCREMENT=662 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
