/*
    WEB-DICOM - preserving and providing information to the DICOM devices
	
    Copyright (C) 2009-2010 psystems.org
    Copyright (C) 2009-2010 Dmitry Derenok 

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
    
    The Original Code is part of WEB-DICOM, an implementation hosted at 
    <http://code.google.com/p/web-dicom/>
    
    In the project WEB-DICOM used the library open source project dcm4che
    The Original Code is part of dcm4che, an implementation of DICOM(TM) in
    Java(TM), hosted at http://sourceforge.net/projects/dcm4che.
    
    =======================================================================
    
    WEB-DICOM - Сохранение и предоставление информации с DICOM устройств

    Copyright (C) 2009-2010 psystems.org 
    Copyright (C) 2009-2010 Dmitry Derenok 

    Это программа является свободным программным обеспечением. Вы можете 
    распространять и/или модифицировать её согласно условиям Стандартной 
    Общественной Лицензии GNU, опубликованной Фондом Свободного Программного 
    Обеспечения, версии 3 или, по Вашему желанию, любой более поздней версии. 
    Эта программа распространяется в надежде, что она будет полезной, но
    БЕЗ ВСЯКИХ ГАРАНТИЙ, в том числе подразумеваемых гарантий ТОВАРНОГО СОСТОЯНИЯ ПРИ 
    ПРОДАЖЕ и ГОДНОСТИ ДЛЯ ОПРЕДЕЛЁННОГО ПРИМЕНЕНИЯ. Смотрите Стандартную 
    Общественную Лицензию GNU для получения дополнительной информации. 
    Вы должны были получить копию Стандартной Общественной Лицензии GNU вместе 
    с программой. В случае её отсутствия, посмотрите <http://www.gnu.org/licenses/>
    Русский перевод <http://code.google.com/p/gpl3rus/wiki/LatestRelease>
    
    Оригинальный исходный код WEB-DICOM можно получить на
    <http://code.google.com/p/web-dicom/>
    
    В проекте WEB-DICOM использованы библиотеки открытого проекта dcm4che/
    Оригинальный исходный код проекта dcm4che, и его имплементация DICOM(TM) in
    Java(TM), находится здесь http://sourceforge.net/projects/dcm4che.
    
    
 */
package org.psystems.dicom.browser.client.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * @author dima_d
 * 
 */
public class StudyProxy implements Serializable {

	private static final long serialVersionUID = -7977302129675187420L;

	private long id; // ID
	private String studyUID; // UID
	private String patientName; // ФИО пациента
	private String patientSex; // Пол пациента
	private String patientId; // ID пациента
	private Date patientBirthDate; // Дата рождения пациента
	private String studyId; // ID исследования
	private Date studyDate; // Дата исследования
	private String studyDoctor; // Врач исследования
	private String studyOperator; // Оператор исследования
	private Date studyDescriptionDate;// Дата описания исследования.
	private String studyType;// Вид исследования.
	private String studyDescription;// Описание исследования.
	private String studyViewprotocol;// Протокол осмотра
	private String studyResult;// Результат исследования.
	private String ManufacturerModelName; // Аппарат

	// Файлы связанные с исследованием
	private ArrayList<DcmFileProxy> files = new ArrayList<DcmFileProxy>();

	/**
	 * Инициализация класса
	 * 
	 * @param id
	 * @param studyUID
	 * @param ManufacturerModelName
	 * @param patientName
	 * @param patientSex
	 * @param patientId
	 * @param patientBirthDate
	 * @param studyId
	 * @param studyType
	 * @param studyDate
	 * @param studyDescriptionDate
	 * @param studyDoctor
	 * @param studyOperator
	 * @param studyDescription
	 * @param studyViewprotocol
	 * @param studyResult
	 */
	public void init(long id, String studyUID, String ManufacturerModelName,
			String patientName, String patientSex, String patientId,
			Date patientBirthDate, String studyId, String studyType,
			Date studyDate, Date studyDescriptionDate, String studyDoctor,
			String studyOperator, String studyDescription,
			String studyViewprotocol, String studyResult) {

		this.id = id;
		this.studyUID = studyUID;
		this.ManufacturerModelName = ManufacturerModelName;
		this.patientName = patientName;
		this.patientSex = patientSex;
		this.patientId = patientId;
		this.patientBirthDate = patientBirthDate;
		this.studyId = studyId;
		this.studyType = studyType;
		this.studyDate = studyDate;
		this.studyDescriptionDate = studyDescriptionDate;
		this.studyDoctor = studyDoctor;
		this.studyOperator = studyOperator;
		this.studyDescription = studyDescription;
		this.studyViewprotocol = studyViewprotocol;
		this.studyResult = studyResult;

	}

	public long getId() {
		return id;
	}

	public String getPatientName() {
		return patientName;
	}

	public Date getPatientBirthDate() {
		return patientBirthDate;
	}

	public String getPatientBirthDateAsString(String pattern) {
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(pattern);
		return dateFormat.format(patientBirthDate);
	}

	public String getPatientSex() {
		return patientSex;
	}

	public String getPatientId() {
		return patientId;
	}

	public String getStudyId() {
		return studyId;
	}

	public Date getStudyDate() {
		return studyDate;
	}

	public String getStudyDateAsString(String pattern) {
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(pattern);
		return dateFormat.format(studyDate);
	}

	public String getStudyDoctor() {
		return studyDoctor;
	}

	public String getStudyOperator() {
		return studyOperator;
	}

	public String getStudyDescription() {
		return studyDescription;
	}

	public String getStudyResult() {
		return studyResult;
	}

	public Date getStudyDescriptionDate() {
		return studyDescriptionDate;
	}
	
	public String getStudyDescriptionDateAsString(String pattern) {
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(pattern);
		return dateFormat.format(studyDescriptionDate);
	}

	public String getStudyType() {
		return studyType;
	}

	public String getManufacturerModelName() {
		return ManufacturerModelName;
	}

	public String getStudyViewprotocol() {
		return studyViewprotocol;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<DcmFileProxy> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<DcmFileProxy> files) {
		this.files = files;
	}

	public String getStudyUID() {
		return studyUID;
	}

	@Override
	public String toString() {
		return "StudyProxy " + id + ";" + ";" + patientName + ";"
				+ patientBirthDate + ";" + studyDate + " [" + getStudyResult()
				+ "] images count:" + files.size();
	}

}
