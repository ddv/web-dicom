CONNECT 'jdbc:derby://localhost:1527//DICOM/DB/WEBDICOM;create=true';


--
-- Таблица "исследование"
--
CREATE TABLE WEBDICOM.STUDY (
	ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	STUDY_UID VARCHAR(512) NOT NULL, 
	STUDY_ID VARCHAR(512) NOT NULL, 
	STUDY_DATE DATE NOT NULL,
	STUDY_TYPE VARCHAR(512) NOT NULL, 
	STUDY_DESCRIPTION  VARCHAR(512), --
	STUDY_DOCTOR  VARCHAR(512) NOT NULL, -- Referring Physician's Name
	STUDY_OPERATOR  VARCHAR(512) NOT NULL, -- Operators' Name
	STUDY_RESULT  VARCHAR(1024), --
	STUDY_VIEW_PROTOCOL  VARCHAR(1024), -- Протокол осмотра
	-- VIEW_PROTOCOL_BLOB  BLOB(1024), -- На будущее 
	STUDY_VIEW_PROTOCOL_DATE DATE, -- Дата осмотра
	STUDY_MANUFACTURER_MODEL_NAME VARCHAR(512) NOT NULL,
	STUDY_MANUFACTURER_UID VARCHAR(512) NOT NULL,
	PATIENT_ID VARCHAR(512) NOT NULL,
	PATIENT_NAME VARCHAR(512) NOT NULL,
	PATIENT_SHORTNAME CHAR(7) NOT NULL, -- Это КБП. ФФФИОГГ
	PATIENT_BIRTH_DATE DATE NOT NULL,
	PATIENT_SEX VARCHAR(1),
	DATE_MODIFY DATE NOT NULL, -- дата измения
	CONSTRAINT WEBDICOM.PK_STUDY PRIMARY KEY (ID),
	UNIQUE (STUDY_UID)
);

--
-- Таблица атрибутов исследования
-- 
CREATE TABLE WEBDICOM.STUDY_ATTRIBUTE (
	FID_STUDY BIGINT NOT NULL,
	ATTRIBUTE INTEGER NOT NULL,
	VALUE_STRING VARCHAR(1024) NOT NULL,
	CONSTRAINT WEBDICOM.PK_STUDY_ATTRIBUTE PRIMARY KEY (FID_STUDY,ATTRIBUTE),
	CONSTRAINT WEBDICOM.FK_STUDY_ATTRIBUTE_STUDY FOREIGN KEY (FID_STUDY) REFERENCES WEBDICOM.STUDY (ID)	
);

--
-- Таблица DICOM-файл
--
CREATE TABLE WEBDICOM.DCMFILE (
	FID_STUDY BIGINT NOT NULL,
	ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	TYPE VARCHAR(512) NOT NULL, -- Тип файла
	DCM_FILE_NAME VARCHAR(512) NOT NULL, -- Полый путь к файлу
	NAME VARCHAR(512) NOT NULL, -- Имя файла (без дополнительного пути)
	DCM_FILE_SIZE BIGINT NOT NULL,
	IMAGE_FILE_SIZE BIGINT,
	IMAGE_WIDTH INTEGER, -- COLUMNS
	IMAGE_HEIGHT INTEGER, -- ROWS
	DATE_MODIFY DATE NOT NULL, -- дата измения
	CONSTRAINT WEBDICOM.PK_DCMFILE PRIMARY KEY (ID),
	CONSTRAINT WEBDICOM.FK_DCMFILE_STUDY FOREIGN KEY (FID_STUDY) REFERENCES WEBDICOM.STUDY (ID),
	UNIQUE (DCM_FILE_NAME),
	UNIQUE (NAME)
);

--
-- Таблица ТЕГ DICOM-файла
-- 
CREATE TABLE WEBDICOM.DCMFILE_TAG (
	FID_DCMFILE BIGINT NOT NULL,
	TAG INTEGER NOT NULL,
	TAG_TYPE CHAR(2) NOT NULL,
	VALUE_STRING VARCHAR(1024) NOT NULL,
	CONSTRAINT WEBDICOM.PK_DCMFILE_TAG PRIMARY KEY (FID_DCMFILE,TAG),
	CONSTRAINT WEBDICOM.FK_DCMFILE_TAG_DCMFILE FOREIGN KEY (FID_DCMFILE) REFERENCES WEBDICOM.DCMFILE (ID)	
);



--
-- Статистические таблицы
--
CREATE TABLE WEBDICOM.STAT (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	METRIC_NAME VARCHAR(512) NOT NULL,
	METRIC_DATE DATE NOT NULL,
	METRIC_VALUE_LONG BIGINT,
	CONSTRAINT WEBDICOM.PK_STAT PRIMARY KEY (ID),
	UNIQUE (METRIC_NAME,METRIC_DATE)
	--INDEX WEBDICOM.PK_STAT1 (METRIC_VALUE_LONG)
);

CREATE TABLE WEBDICOM.DAYSTAT (
	ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	METRIC_NAME VARCHAR(512) NOT NULL,
	METRIC_DATE DATE NOT NULL,
	METRIC_VALUE_LONG BIGINT,
	CONSTRAINT WEBDICOM.PK_DAYSTAT PRIMARY KEY (ID),
	UNIQUE (METRIC_NAME,METRIC_DATE)
	--INDEX (METRIC_VALUE_LONG)
);



