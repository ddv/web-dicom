package org.psystems.dicom.commons.orm.entity;

import java.io.Serializable;
import java.util.Arrays;

import org.psystems.dicom.commons.orm.ORMUtil;
import org.psystems.dicom.commons.orm.Patient;

/**
 * Направление на исследование
 * 
 * @author dima_d
 */
public class Direction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2840335603832244555L;
	private Long id; // Внутренний ID
	private String directionId; // штрих код
	private Employee doctorDirect;// Направивший врач
	private Diagnosis[] diagnosisDirect;// Диагнозы при направлении
	private Service[] servicesDirect;// Услуги при направлении
	private String dateDirection;// Дата направления
	private ManufacturerDevice device;// Аппарат
	private String datePlanned;// Планируемая дата выполнения исследования
	private Employee doctorPerformed;// Врач выполнивший исследование
	private String directionCode;// Идентификатор случая заболевания
	private String directionLocation;// Кабинет
	private Diagnosis[] diagnosisPerformed;// Диагнозы после выполнения
	// исследования
	private Service[] servicesPerformed;// Выполненные услуги
	private String datePerformed;// Дата выполнения исследования
	private Patient patient; // Пациент
	private String dateTimeModified;// Дата модификации
	private String dateTimeRemoved;// Дата удаления

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDirectionId() {
		return directionId;
	}

	public void setDirectionId(String directionId) {
		this.directionId = directionId;
	}

	public Employee getDoctorDirect() {
		return doctorDirect;
	}

	public void setDoctorDirect(Employee doctorDirect) {
		this.doctorDirect = doctorDirect;
	}

	public Diagnosis[] getDiagnosisDirect() {
		return diagnosisDirect;
	}

	public void setDiagnosisDirect(Diagnosis[] diagnosisDirect) {
		this.diagnosisDirect = diagnosisDirect;
	}

	public Service[] getServicesDirect() {
		return servicesDirect;
	}

	public void setServicesDirect(Service[] servicesDirect) {
		this.servicesDirect = servicesDirect;
	}

	public String getDateDirection() {
		return dateDirection;
	}

	public void setDateDirection(String dateDirection) {
		this.dateDirection = dateDirection;
	}

	public ManufacturerDevice getDevice() {
		return device;
	}

	public void setDevice(ManufacturerDevice device) {
		this.device = device;
	}

	public String getDatePlanned() {
		return datePlanned;
	}

	public void setDatePlanned(String datePlanned) {
		this.datePlanned = datePlanned;
	}

	public Employee getDoctorPerformed() {
		return doctorPerformed;
	}

	public void setDoctorPerformed(Employee doctorPerformed) {
		this.doctorPerformed = doctorPerformed;
	}

	public String getDirectionCode() {
		return directionCode;
	}

	public void setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
	}

	public String getDirectionLocation() {
		return directionLocation;
	}

	public void setDirectionLocation(String directionLocation) {
		this.directionLocation = directionLocation;
	}

	public Diagnosis[] getDiagnosisPerformed() {
		return diagnosisPerformed;
	}

	public void setDiagnosisPerformed(Diagnosis[] diagnosisPerformed) {
		this.diagnosisPerformed = diagnosisPerformed;
	}

	public Service[] getServicesPerformed() {
		return servicesPerformed;
	}

	public void setServicesPerformed(Service[] servicesPerformed) {
		this.servicesPerformed = servicesPerformed;
	}

	public String getDatePerformed() {
		return datePerformed;
	}

	public void setDatePerformed(String datePerformed) {
		this.datePerformed = datePerformed;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getDateTimeModified() {
		return dateTimeModified;
	}

	public void setDateTimeModified(String dateTimeModified) {
		this.dateTimeModified = dateTimeModified;
	}

	public String getDateTimeRemoved() {
		return dateTimeRemoved;
	}

	public void setDateTimeRemoved(String dateTimeRemoved) {
		this.dateTimeRemoved = dateTimeRemoved;
	}

	/**
	 * Проверка всех полей.
	 */
	public void chechEntity() {
		String field = null;
		try {
			if (dateDirection != null) {
				field = "dateDirection";
				ORMUtil.dateSQLToUtilDate(dateDirection);
			}
			if (datePlanned != null) {
				field = "datePlanned";
				ORMUtil.dateSQLToUtilDate(datePlanned);
			}
			if (datePerformed != null) {
				field = "datePerformed";
				ORMUtil.dateSQLToUtilDate(datePerformed);
			}
			if (dateTimeModified != null) {
				field = "dateTimeModified";
				ORMUtil.dateTimeSQLToUtilDate(dateTimeModified);
			}
			if (dateTimeRemoved != null) {
				field = "dateTimeRemoved";
				ORMUtil.dateTimeSQLToUtilDate(dateTimeRemoved);
			}

			field = "patient";
			if (patient != null)
				patient.chechEntity();
			field = "doctorDirect";
			if (doctorDirect != null)
				doctorDirect.chechEntity();
			field = "doctorPerformed";
			if (doctorPerformed != null)
				doctorPerformed.chechEntity();

		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Direction field " + field + " "
					+ ex.getMessage(), ex);
		}

	}

	@Override
	public String toString() {
		return "Direction [id=" + id + ", directionId=" + directionId
				+ ", dateDirection=" + dateDirection + ", directionCode="
				+ directionCode + ", patient=" + patient + ", datePerformed="
				+ datePerformed + ", datePlanned=" + datePlanned + ", device="
				+ device + ", directionLocation=" + directionLocation
				+ ", doctorDirect=" + doctorDirect + ", diagnosisDirect="
				+ Arrays.toString(diagnosisDirect) + ", servicesDirect="
				+ Arrays.toString(servicesDirect) + ", doctorPerformed="
				+ doctorPerformed + ", diagnosisPerformed="
				+ Arrays.toString(diagnosisPerformed) + ", servicesPerformed="
				+ Arrays.toString(servicesPerformed) + ", dateTimeModified="
				+ dateTimeModified + ", dateTimeRemoved=" + dateTimeRemoved
				+ "]";
	}

}
