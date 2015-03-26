INSERT INTO applications (idApplication, idMainApplication, codeApplication, nameApplication, levelApplication) VALUES (1, NULL, 'CLIENTES', 'Clientes', 1);
INSERT INTO applications (idApplication, idMainApplication, codeApplication, nameApplication, levelApplication) VALUES (2, NULL, 'EQUIPOS', 'Equipos', 1);
INSERT INTO applications (idApplication, idMainApplication, codeApplication, nameApplication, levelApplication) VALUES (3, NULL, 'PARTES', 'Partes de Trabajo', 1);
INSERT INTO applications (idApplication, idMainApplication, codeApplication, nameApplication, levelApplication) VALUES (4, NULL, 'USUARIOS', 'Usuarios', 1);
INSERT INTO applications (idApplication, idMainApplication, codeApplication, nameApplication, levelApplication) VALUES (5, NULL, 'ESTADISTICAS', 'Estadísticas', 1);
INSERT INTO applications (idApplication, idMainApplication, codeApplication, nameApplication, levelApplication) VALUES (6, NULL, 'GESTORES', 'Gestores', 1);

INSERT INTO permissions (idPermission, codePermission, descriptionPermission, idApplication) VALUES (1, 'VIEW_CLIENTES', 'Acceso a Clientes', 1);
INSERT INTO permissions (idPermission, codePermission, descriptionPermission, idApplication) VALUES (2, 'VIEW_EQUIPOS', 'Acceso a Equipos', 2);
INSERT INTO permissions (idPermission, codePermission, descriptionPermission, idApplication) VALUES (3, 'VIEW_PARTES', 'Acceso a Partes', 3);
INSERT INTO permissions (idPermission, codePermission, descriptionPermission, idApplication) VALUES (4, 'VIEW_USUARIOS', 'Acceso a Usuarios', 4);
INSERT INTO permissions (idPermission, codePermission, descriptionPermission, idApplication) VALUES (6, 'VIEW_GESTORES', 'Acceso a Gestores', 6);

INSERT INTO roles (idRole, codeRole, descriptionRole, URLSuccessLogin) VALUES (1, 'ADM', 'Administrador', 'general');
INSERT INTO roles (idRole, codeRole, descriptionRole, URLSuccessLogin) VALUES (2, 'TCN', 'Técnico', 'general');
INSERT INTO roles (idRole, codeRole, descriptionRole, URLSuccessLogin) VALUES (3, 'FCT', 'Faturación', 'general');
INSERT INTO roles (idRole, codeRole, descriptionRole, URLSuccessLogin) VALUES (4, 'CM', 'Comercial', 'general');

INSERT INTO roles_permissions (idRole, idPermission) VALUES (1, 1);
INSERT INTO roles_permissions (idRole, idPermission) VALUES (1, 2);
INSERT INTO roles_permissions (idRole, idPermission) VALUES (1, 3);
INSERT INTO roles_permissions (idRole, idPermission) VALUES (1, 4);
INSERT INTO roles_permissions (idRole, idPermission) VALUES (1, 6);
INSERT INTO roles_permissions (idRole, idPermission) VALUES (3, 1);
INSERT INTO roles_permissions (idRole, idPermission) VALUES (3, 2);
INSERT INTO roles_permissions (idRole, idPermission) VALUES (3, 3);

INSERT INTO replytype (idReplyType, nameReplyType) VALUES (1, 'Texto');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (2, 'Número Entero');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (3, 'Fecha');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (4, 'Sí/No');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (5, 'Bien/Mal');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (6, 'Número Decimal');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (7, 'Mes/Año');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (8, 'Año');
INSERT INTO replytype (idReplyType, nameReplyType) VALUES (9, 'Automatico');

INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (1, 'Extintores', 'CERTIFICADO DE REVISIÓN DE EXTINTORES', 'extintores');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (2, 'B.I.E.S.', 'CERTIFICADO DE REVISIÓN DE BOCAS DE INCENDIO EQUIPADAS (B.I.E.S.)', 'bies');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (3, 'Alumbrado de Emergencia', 'CERTIFICADO DE REVISIÓN DE ALUMBRADO DE EMERGENCIA', 'alumbrado');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (4, 'Sistemas de Humos y Calor', 'CERTIFICADO DE REVISIÓN DE SISTEMAS DE CONTROL DE HUMOS Y DE CALOR', 'humos');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (5, 'Detección de Incendios', 'CERTIFICADO REVISIÓN SISTEMA DE DETECCIÓN DE INCENDIOS', 'incendios');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (6, 'Detección de Monóxido', 'CERTIFICADO REVISIÓN SISTEMA DE DETECCIÓN DE MONÓXIDO', 'monoxido');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (7, 'Rociadores', 'CERTIFICADO DE REVISIÓN DE PUESTO CONTROL ROCIADORES', 'rociadores');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (8, 'Hidrantes', 'CERTIFICADO DE REVISIÓN DE HIDRANTES', 'hidrantes');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (9, 'Columna Seca', 'CERTIFICADO DE REVISIÓN DE COLUMNA SECA', 'columna');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (10,'Extinción de Incendios', 'CERTIFICADO REVISIÓN SISTEMA DE DETECCIÓN Y EXTINCIÓN DE INCENDIOS', 'extincion');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (11,'Campanas de Cocina', 'CERTIFICADO DE REVISIÓN DE SISTEMA DE EXTINCIÓN AUTOMÁTICA EN CAMPANAS DE COCINA', 'campanas');
INSERT INTO report (idReport, nameReport, titleReport, fileReport) VALUES (12,'Grupo de Presión', 'CERTIFICADO REVISIÓN DE GRUPO DE PRESIÓN SANITARIO', 'presion');

INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (1,  1, 'Extintor');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (2,  2, 'Elemento');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (3,  3, 'Luz');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (4,  4, 'Elemento');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (5,  5, 'Características de la Central');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (6,  5, 'Elementos del Sistema y Distribución');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (7,  5, 'Elementos');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (8,  6, 'Características de la Central');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (9,  6, 'Elementos del Sistema y Distribución');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (10, 6, 'Elementos');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (11, 7, 'Puesto de Control');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (12, 8, 'Hidrantes');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (13, 8, 'Casetas');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (14, 9, 'General');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (15, 9, 'Elementos');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (16, 10, 'Características de la central');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (17, 10, 'Elementos del sistema y distribución');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (18, 10, 'Detectores');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (19, 10, 'Datos de válvulas');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (20, 10, 'Cilindros');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (21, 11, 'General');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (22, 11, 'Preguntas');
INSERT INTO questiongroup (idQuestionGroup, idReport, nameQuestionGroup) VALUES (23, 12, 'Columna');

INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (1, 'Número de referencia', 1, 9, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (2, 'Número de Placa', 1, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (3, 'Ubicación', 1, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (4, 'Fabricación', 1, 8, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (5, 'Último Ph', 1, 8, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (6, 'Caducidad', 1, 8, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (7, 'Tipo / Eficacia', 1, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (8, 'Peso CO2 Correcto', 1, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (9, 'Acceso', 1, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (10, 'Señalización', 1, 5, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (11, 'Estado Exterior', 1,  5, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (12, 'Necesario Ph', 1, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (13, 'Necesario Recarga', 1, 4, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (14, 'Desaparecido', 1, 4, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (15, 'Caducado', 1, 4, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (16, 'Fuera Norma', 1, 4, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (17, 'Operación', 1, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (18, 'Retimbrado', 1, 4, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (19, 'Número de Referencia', 2, 9, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (20, 'Tipo', 2, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (21, 'Tramo', 2, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (22, 'Presión', 2, 6, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (23, 'F.Fabric', 2, 8, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (24, 'Último PH', 2, 8, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (25, 'Situación', 2, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (26, 'Acceso', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (27, 'Señalización', 2, 5, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (28, 'Devan', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (29, 'Válvula', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (30, 'Manómetro', 2, 5, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (31, 'Junta', 2, 5, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (32, 'Lanza', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (33, 'Puerta', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (34, 'Cristal/Medida', 2, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (35, 'Armario', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (36, 'Manguera', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (37, 'Cerradura', 2, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (38, 'Retimbrar', 2, 4, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (39, 'Operación', 2, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (40, 'Número', 3, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (41, 'Ubicación', 3, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (42, 'Tipo de Luz', 3, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (43, 'Nº de Luces', 3, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (44, 'Modelo', 3, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (45, 'Estado componentes bloques', 3, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (46, 'Activación de luces', 3, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (47, 'Intensidad de la luz', 3, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (48, 'Bombillas Fundidas', 3, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (49, 'Carcasas Deterioradas', 3, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (50, 'Otras Anomalías', 3, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (51, 'Observaciones', 3, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (52, 'Cambiar Elemento', 3, 4, 1);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (53, 'Número', 4, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (54, 'Ubicación', 4, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (55, 'Equipo', 4, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (56, 'Marca', 4, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (57, 'Modelo', 4, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (58, 'Funcionamiento Manual', 4, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (59, 'Funcionamiento Automático', 4, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (60, 'F. Alimentación y Batería', 4, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (61, 'Estado Aparente', 4, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (62, 'Observaciones', 4, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (64, 'Ubicación', 5, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (65, 'Marca', 5, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (66, 'Modelo', 5, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (67, 'Nº Zona', 5, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (68, 'Red', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (69, 'Pilotos', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (70, 'Relés', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (71, 'Zona Reserva', 5, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (72, 'Control', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (73, 'Zumbador', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (74, 'Líneas Cableado', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (75, 'Nº Baterías', 5, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (76, 'Tipo Baterías', 5, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (77, 'Estado Baterías', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (78, 'Módulos', 5, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (79, 'Marca Detectores', 6, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (80, 'Tipo', 6, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (81, 'Modelo', 6,  1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (82, 'Ubicación', 7, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (83, 'Nº Zona', 7, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (84, 'Nº Detector', 7, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (85, 'Estado Detector', 7, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (86, 'Nº Pulsador', 7, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (87, 'Estado Pulsador', 7, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (88, 'Nº Indicador', 7, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (89, 'Estado Indicador', 7, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (90, 'Nº Sirena', 7, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (91, 'Estado Sirena', 7, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (92, 'Nº Retenedor', 7, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (93, 'Estado Retenedor', 7, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (94, 'Otros', 7, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (95, 'Ubicación', 8, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (96, 'Marca', 8, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (97, 'Modelo', 8, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (98, 'Nº Zonas', 8, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (99, 'Zona Reserva', 8, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (100, 'Arranque Manual', 8, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (101, 'Arranque Automático', 8, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (102, 'Nivel de Concentración', 8, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (103, 'Pilotos', 8, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (104, 'Marca Detectores', 9, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (105, 'Modelo', 9, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (106, 'Nº Detectores Caducados', 9, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (107, 'Fecha Fabricación', 9, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (108, 'Fecha último cambio Filtro', 9, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (109, 'Fecha próximo cambio Filtro', 9, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (110, 'Ubicación', 10, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (111, 'Nº Zona', 10, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (112, 'Nº Detector', 10, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (113, 'Estado Detector', 10, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (114, 'Nº Cuadro', 10, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (115, 'Estado Cuadro', 10, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (116, 'Nº Motor', 10, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (117, 'Estado Motor', 10, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (118, 'Nº Compuerta', 10, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (119, 'Estado Compuerta', 10, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (120, 'Otros', 10, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (121, 'Nº Referencia', 11, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (122, 'Ubicación', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (123, 'Marca', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (124, 'Gong', 11, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (125, 'Válvula de Prueba', 11, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (126, 'Interruptor de Flujo', 11, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (127, 'Pulgadas', 11, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (128, 'Punto de Prueba', 11, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (129, 'Desagüe', 11, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (130, 'Conducido', 11, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (131, 'Estado General de las válvulas', 11, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (132, 'Solenoide', 11, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (133, 'Punto de Prueba', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (134, 'Línea de Rociadores', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (135, 'Línea de Acometida', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (136, 'Tipo de Instalación', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (137, 'Tipo de Rociadores', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (138, 'Temperatura', 11, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (139, 'Riesgo a cubrir', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (140, 'Observaciones', 11, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (141, 'Ubicación', 12, 1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (142, 'Diámetro (m/m)', 12, 2, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (143, 'Accesibilidad', 12, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (144, 'Señalización', 12, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (145, 'Estanqueidad del Conjunto', 12, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (146, 'Engrase de Rosca', 12, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (147, 'Estado de Juntas', 12, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (148, 'Estado de Racores', 12, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (149, 'Funcionamiento de la válvula Principal', 12,5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (150, 'Funcionamiento del Sistema de Drenaje', 12, 5, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (151, 'Ubicación', 13,  1, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (152, 'Manguera 70', 13,  4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (153, 'Fecha Fabricación', 13,  3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (154, 'Fecha Retimbrado', 13, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (155, 'Manguera 45', 13, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (156, 'Fecha Fabricación', 13, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (157, 'Fecha Retimbrado', 13, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (158, 'Manguera 45', 13, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (159, 'Fecha Fabricación', 13, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (160, 'Fecha Retimbrado', 13, 3, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (161, 'Bifurcación 45', 13, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (162, 'Racor Barcelona 70', 13, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (163, 'Lanza 45 (2 Unidades)', 13, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (164, 'Lanza 70', 13, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (165, 'Reducción 70 x 45', 13, 4, NULL);
INSERT INTO question (idQuestion, question, idQuestionGroup, idReplyType, totalize) VALUES (166, 'Tubería de Acero Galvanizado de 3"', 14,  1, NULL);

INSERT INTO users (idUser, codeUser, nameUser, firstSurnameUser, secondSurnameUser, passwordUser, phoneNumberUser, emailUser, addedDateUser, expirationDateUser, expirationDateUserPassword, locked, enabled, lastAccess, outOpen) VALUES (1, 'user1', 'Name 1', '1st Sur 1', '2nd Sur 1', 'cypherPass 1', 'phone 1', 'mail1', '2014-12-30', NULL, NULL, FALSE, TRUE, NULL, FALSE);
INSERT INTO users (idUser, codeUser, nameUser, firstSurnameUser, secondSurnameUser, passwordUser, phoneNumberUser, emailUser, addedDateUser, expirationDateUser, expirationDateUserPassword, locked, enabled, lastAccess, outOpen) VALUES (2, 'user2', 'Name 2', '1st Sur 2', '2nd Sur 2', 'cypherPass 2', 'phone 2', 'mail2', '2014-12-31', NULL, NULL, FALSE, TRUE, NULL, TRUE);
INSERT INTO users (idUser, codeUser, nameUser, firstSurnameUser, secondSurnameUser, passwordUser, phoneNumberUser, emailUser, addedDateUser, expirationDateUser, expirationDateUserPassword, locked, enabled, lastAccess, outOpen) VALUES (3, 'user3', 'Name 3', '1st Sur 3', '2nd Sur 3', 'cypherPass 3', 'phone 3', 'mail3', '2013-12-31', NULL, NULL, FALSE, TRUE, NULL, FALSE);

INSERT INTO team (idTeam, nameTeam, enabled, canUploadPhotos, phoneNumber, password) VALUES (1, 'team1', 1, 0, '1', 'pass 1');
INSERT INTO team (idTeam, nameTeam, enabled, canUploadPhotos, phoneNumber, password) VALUES (2, 'team2', 1, 0, '2', 'pass 2');
INSERT INTO team (idTeam, nameTeam, enabled, canUploadPhotos, phoneNumber, password) VALUES (3, 'team3', 1, 0, '3', 'pass 3');
INSERT INTO team (idTeam, nameTeam, enabled, canUploadPhotos, phoneNumber, password) VALUES (4, 'team4', 1, 0, '4', 'pass 4');
INSERT INTO team (idTeam, nameTeam, enabled, canUploadPhotos, phoneNumber, password) VALUES (5, 'team5', 1, 0, '5', 'pass 5');

INSERT INTO team_user (idTeam, idUser) VALUES (1, 1);

INSERT INTO team_user (idTeam, idUser) VALUES (2, 1);
INSERT INTO team_user (idTeam, idUser) VALUES (2, 2);

INSERT INTO users_roles (IdUser, IdRole) VALUES (1, 1);

INSERT INTO customers (idCustomer, nameCustomer, cifCustomer, codeCustomer, mainAddress, mainProv, mainPobl, mainPostalCode, mainMail, mainPhone, idManager, idUser, typeCustomer, dateCreated, active) VALUES (1, 'nameCustomer', 'cifCustomer', 1, 'mainAddress', 'mainProv', 'mainPobl', 28020, 'mail', 'mainPhone', NULL, NULL, 1, '2014-05-31', 1);
INSERT INTO address (idaddress, address, idCustomer, prov, pobl, postalCode, other, location, monthMask, amount) VALUES (1, 'CL. Camarillas, 11 ', 1, 'Madrid ', 'Torrejon de Ardoz', 28850, '', 'Comunidad de Propietarios', 'MAY', '');
INSERT INTO work (idWork, year, albaran, idTeam, idAddress, memo, data, status, typeWork, dateCreated, signpath, signName) VALUES (1, 2015, 1, 1, 1, 'HA SALTADO LA ALARMA ', 'averia', 1, 1, '2014-05-27', NULL, NULL);

INSERT INTO customers (idCustomer, nameCustomer, cifCustomer, codeCustomer, mainAddress, mainProv, mainPobl, mainPostalCode, mainMail, mainPhone, idManager, idUser, typeCustomer, dateCreated, active) VALUES (2, 'name2', 'cifr2', 2, 'mainAddress', 'mainProv', 'mainPobl', 28020, 'mail', 'mainPhone', NULL, NULL, 1, '2014-05-31', 1);
INSERT INTO address (idaddress, address, idCustomer, prov, pobl, postalCode, other, location, monthMask, amount) VALUES (2, 'CL. Camarillas, 22 ', 2, 'Madrid ', 'Torrejon de Ardoz', 28850, '', 'Comunidad de Propietarios', 'MAY', '');








