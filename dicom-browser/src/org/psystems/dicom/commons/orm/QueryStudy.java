package org.psystems.dicom.commons.orm;

/**
 * Запрос исследований
 * 
 * @author dima_d
 * 
 */
public class QueryStudy {

	private static final long serialVersionUID = -2840335603833444555L;
	private Long id; // Внутренний ID
	private String studyId; // штрих код
	private String studyModality;// модальность
	private String beginStudyDate;// Дата начала интервала поиска по дате
									// исследования
	private String endStudyDate;// Дата окончания интервала поиска по дате
								// исследования

	private String patientId; // ID пациента
	private String patientName; // ФИО пациента
	private String patientSex; // Пол пациента (M/F)
	private String patientBirthDate; // Дата рождения пациента
	private String patientShortName; // код краткого поиска
	
	private String manufacturerModelName; //имя аппарата
	private String studyResult;//TODO странное поле :-( new | old используется
	//для фильтра в worklist типа сделать 
	private String sortOrder;//TODO странное поле :-( идут перечисления полей в БД... переделать..

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getStudyModality() {
		return studyModality;
	}

	public void setStudyModality(String studyModality) {
		this.studyModality = studyModality;
	}

	public String getBeginStudyDate() {
		return beginStudyDate;
	}

	/**
	 * @param beginStudyDate
	 *            Формат SQL Date - "гггг.дд.мм"
	 */
	public void setBeginStudyDate(String beginStudyDate) {
		this.beginStudyDate = beginStudyDate;
	}

	public String getEndStudyDate() {
		return endStudyDate;
	}

	/**
	 * @param endStudyDate
	 *            Формат SQL Date - "гггг.дд.мм"
	 */
	public void setEndStudyDate(String endStudyDate) {
		this.endStudyDate = endStudyDate;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

	public String getPatientBirthDate() {
		return patientBirthDate;
	}

	/**
	 * @param patientBirthDate
	 *            Формат SQL Date - "гггг.дд.мм"
	 */
	public void setPatientBirthDate(String patientBirthDate) {
		this.patientBirthDate = patientBirthDate;
	}

	public String getPatientShortName() {
		return patientShortName;
	}

	public void setPatientShortName(String patientShortName) {
		this.patientShortName = patientShortName;
	}
	
	

	public String getManufacturerModelName() {
		return manufacturerModelName;
	}

	public void setManufacturerModelName(String manufacturerModelName) {
		this.manufacturerModelName = manufacturerModelName;
	}
	
	

	public String getStudyResult() {
		return studyResult;
	}

	public void setStudyResult(String studyResult) {
		this.studyResult = studyResult;
	}

	/**
	 * Проверка всех полей.
	 */
	public void chechEntity() {
		String field = null;
		try {
			if (beginStudyDate != null) {
				field = "beginStudyDate";
				ORMUtil.dateSQLToUtilDate(beginStudyDate);
			}
			if (endStudyDate != null) {
				field = "endStudyDate";
				ORMUtil.dateSQLToUtilDate(endStudyDate);
			}
			if (patientBirthDate != null) {
				field = "patientBirthDate";
				ORMUtil.dateSQLToUtilDate(patientBirthDate);
			}
			
			//TODO Сделать остальные проверки (studyModality итп..)
			//TODO сделать проверку на длинну и корретность строк...
			
//			
//			if (java.sql.Date.valueOf(request.getBeginStudyDate()).getTime() > java.sql.Date
//					.valueOf(request.getEndStudyDate()).getTime()) {
//				throw new DataException(new IllegalArgumentException(
//						"beginStudyDate > endStudyDate"));
//			}
			
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("field " + field + " ", ex);
		}
	}
	
	

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "QueryStudy [beginStudyDate=" + beginStudyDate
				+ ", endStudyDate=" + endStudyDate + ", id=" + id
				+ ", manufacturerModelName=" + manufacturerModelName
				+ ", patientBirthDate=" + patientBirthDate + ", patientId="
				+ patientId + ", patientName=" + patientName + ", patientSex="
				+ patientSex + ", patientShortName=" + patientShortName
				+ ", sortOrder=" + sortOrder + ", studyId=" + studyId
				+ ", studyModality=" + studyModality + ", studyResult="
				+ studyResult + "]";
	}

	

	




}
