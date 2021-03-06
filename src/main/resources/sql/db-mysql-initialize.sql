INSERT INTO `USERS` (`USERNAME`,`PASSWORD`,`EMAIL`) VALUES ('d_castro','d_castro','diego.castro@sopragroup.com');

INSERT INTO `CONTROLS` (`NAME`,`DESCRIPTION`) VALUES ('PROJECTION_MENSUAL','Comprueba que los usuarios hayan imputado las horas de todo el mes en curso. Envía un email a los que no lo hayan hecho.');
INSERT INTO `CONTROLS` (`NAME`,`DESCRIPTION`) VALUES ('PROJECTION_SEMANAL','Comprueba que los usuarios hayan imputado las horas en el projection hasta un determinado día de la semana. Envía un email a los que no lo hayan hecho.');

INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_MENSUAL','MAIL_FROM','','1','carlos.cordero@sopragroup.com');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_MENSUAL','MAIL_SUBJECT','','1','Projection !!');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_MENSUAL','MAIL_TEMPLATE','','1','projection-mensual.html');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_MENSUAL','SIMULATE','','1','true');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_SEMANAL','MAIL_FROM','','1','carlos.cordero@sopragroup.com');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_SEMANAL','MAIL_SUBJECT','','1','Projection !!');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_SEMANAL','MAIL_TEMPLATE','','1','projection-semanal.html');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_SEMANAL','SIMULATE','','1','true');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_SEMANAL','URL','','1','http://192.168.9.151/projectsion_corp1');
INSERT INTO `CONTROL_PARAMS` (`CONTROL_NAME`,`PARAM_NAME`,`DESCRIPTION`,`REQUIRED`,`DEFAULT_VALUE`) VALUES ('PROJECTION_SEMANAL','TOLERANCE_DAYS','','1','0');

INSERT INTO `USER_CONTROLS` (`USER_USERNAME`,`CONTROL_NAME`) VALUES ('d_castro','PROJECTION_MENSUAL');
INSERT INTO `USER_CONTROLS` (`USER_USERNAME`,`CONTROL_NAME`) VALUES ('d_castro','PROJECTION_SEMANAL');

INSERT INTO `CONTROL_SCHEDULES` (`ID`,`CONTROL_NAME`,`DESCRIPTION`,`START_DATE`,`END_DATE`,`SCHEDULE_STATUS`,`CRON`) VALUES (1,'PROJECTION_MENSUAL','','2014-01-01 12:00:00','2014-12-31 12:00:00','RUNNING','*/10 * * * * ?');
INSERT INTO `CONTROL_SCHEDULES` (`ID`,`CONTROL_NAME`,`DESCRIPTION`,`START_DATE`,`END_DATE`,`SCHEDULE_STATUS`,`CRON`) VALUES (2,'PROJECTION_SEMANAL','','2014-01-01 12:00:00','2014-12-31 12:00:00','PAUSED','*/10 * * * * ?');