package org.psystems.dicom.daemon;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import junit.framework.TestCase;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

public class StudyTest extends TestCase {

	public StudyTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetInstance_elektron_1() {

		DicomObject dcmObj;
		DicomInputStream din = null;
		String fileName = "test/data/elektron/1_1.2.826.0.1.3680043.2.634.0.19902.201041.165915.1.dcm";
		try {

			File f = new File(fileName);
			din = new DicomInputStream(f);
			dcmObj = din.readDicomObject();
			Study study = Study.getInstance(dcmObj);
			assertEquals(study.getClass().getName(),
					"org.psystems.dicom.daemon.StudyImplElektron");
			assertEquals(study.getStudyInstanceUID(),
					"1.2.826.0.1.3680043.2.634.30.1.94286.20100401164900");
			assertEquals(study.getModality(), "CR");
			assertEquals(study.getStudyID(), "94286");
			assertEquals(study.getPatientBirthDate(), new Date(61, 3, 9));
			assertEquals(study.getPatientName(), "Николаева Татьяна Гордеевна");
			assertEquals(study.getPatientID(), "LAB_ID44770");
			assertEquals(study.getPatientSex(), "F");
			assertEquals(study.getStudyDoctor(), "not defined");
			assertEquals(study.getStudyOperator(), "Шевченко М.Ю.");
			assertEquals(study.getStudyDescription(), "Без диагноза");
			assertEquals(study.getPatientShortName(), "НИКТГ61");
			assertEquals(study.getManufacturerModelName(), "КРТ-Электрон");
			assertEquals(study.getStudyType(), "Флюорография, Прямая передняя");
			assertEquals(study.getStudyResult(), "empty");
			assertEquals(study.getStudyViewProtocol(), "empty");
			assertEquals(study.getStudyViewProtocolDate(), null);
			assertEquals(study.getManufacturerUID(), "empty");
			assertEquals(study.getDcmType(), "empty");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (din != null)
					din.close();
			} catch (IOException ignore) {
			}
		}
	}

	public void testGetInstance_elektron_2() {

		DicomObject dcmObj;
		DicomInputStream din = null;
		String fileName = "test/data/elektron/3_1.2.826.0.1.3680043.2.634.0.28586.201041.17627.1.dcm";
		try {

			File f = new File(fileName);
			din = new DicomInputStream(f);
			dcmObj = din.readDicomObject();
			Study study = Study.getInstance(dcmObj);
			assertEquals(study.getClass().getName(),
					"org.psystems.dicom.daemon.StudyImplElektron");
			assertEquals(study.getStudyInstanceUID(),
					"1.2.826.0.1.3680043.2.634.30.1.94286.20100401164900");
			assertEquals(study.getModality(), "CR");
			assertEquals(study.getStudyID(), "94286");
			assertEquals(study.getPatientBirthDate(), new Date(61, 3, 9));
			assertEquals(study.getPatientName(), "Николаева Татьяна Гордеевна");
			assertEquals(study.getPatientID(), "LAB_ID44770");
			assertEquals(study.getPatientSex(), "F");
			assertEquals(study.getStudyDoctor(), "Девяткова И. А.");
			assertEquals(study.getStudyOperator(), "Шевченко М.Ю.");
			
			//(0010,2000) LO #12 [Без диагноза] Medical Alerts
			//TODO Разобраться почему тег исчез
			//assertEquals(study.getStudyDescription(), "Без диагноза");
			
			assertEquals(study.getPatientShortName(), "НИКТГ61");
			assertEquals(study.getManufacturerModelName(), "КРТ-Электрон");
			assertEquals(study.getStudyType(), "Флюорография, Прямая передняя");
			assertEquals(study.getStudyResult(), "норма");
			assertEquals(study.getStudyViewProtocol(), "Органы грудной полости без видимых патологических изменений (придуманная)\n" +
					"придуманное - не согласие!!!!!!!!!!!");
			assertEquals(study.getStudyViewProtocolDate(), null);
			assertEquals(study.getManufacturerUID(), "empty");
			assertEquals(study.getDcmType(), "empty");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (din != null)
					din.close();
			} catch (IOException ignore) {
			}
		}
	}
	
	public void testGetInstance_renex() {

		
		//Исследование из RENEX 
		DicomObject dcmObj;
		DicomInputStream din = null;
		String fileName = "test/data/pacs/iso.dcm";
		try {

			File f = new File(fileName);
			din = new DicomInputStream(f);
			dcmObj = din.readDicomObject();
			Study study = Study.getInstance(dcmObj);
			assertEquals(study.getClass().getName(),
					"org.psystems.dicom.daemon.Study");
			assertEquals(study.getStudyInstanceUID(),
					"1.871.3.3789295964.39943.18345.1575621271.2728364279.1");
			assertEquals(study.getModality(), "CR");
			assertEquals(study.getStudyID(), "1");
			assertEquals(study.getPatientBirthDate(), new Date(59, 9, 5));//19591005
			assertEquals(study.getPatientName(), "Gaer^Lidiya^Nikolaevna");
			//TODO Разобраться почему ID нету
			assertEquals(study.getPatientID(), null);
			assertEquals(study.getPatientSex(), "F");
			//TODO Разобраться почему ID нету
			assertEquals(study.getStudyDoctor(), "not defined");
			//TODO Разобраться почему ID нету
			assertEquals(study.getStudyOperator(), "empty");
			
			//(0010,2000) LO #12 [Без диагноза] Medical Alerts
			//TODO Разобраться почему тег исчез
			//assertEquals(study.getStudyDescription(), "Без диагноза");
			
			assertEquals(study.getPatientShortName(), "GAELN59");
			assertEquals(study.getManufacturerModelName(), null);
			assertEquals(study.getManufacturer(), "JV HELPIC (MOSCOW)");
			
			assertEquals(study.getStudyType(), null);
			assertEquals(study.getStudyResult(), null);
			assertEquals(study.getStudyViewProtocol(), null);
			assertEquals(study.getStudyViewProtocolDate(), null);
			assertEquals(study.getManufacturerUID(), "empty");
			assertEquals(study.getDcmType(), "empty");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (din != null)
					din.close();
			} catch (IOException ignore) {
			}
		}
	}
	
public void testGetInstance_renex_lookinside() {

		
		//Исследование из RENEX 
		DicomObject dcmObj;
		DicomInputStream din = null;
		String fileName = "test/data/pacs/opis.dcm";
		try {

			File f = new File(fileName);
			din = new DicomInputStream(f);
			dcmObj = din.readDicomObject();
			Study study = Study.getInstance(dcmObj);
			assertEquals(study.getClass().getName(),
					"org.psystems.dicom.daemon.StudyImpLookInside");
			assertEquals(study.getStudyInstanceUID(),
					"1.871.3.3789295964.39943.18345.1575621271.2728364279.1");
			assertEquals(study.getModality(), "CR");
			//TODO Куда исчез тег?
			assertEquals(study.getStudyID(), "empty");
			
			assertEquals(study.getPatientBirthDate(), new Date(59, 9, 5));//1959-10-05
			
			assertEquals(study.getPatientName(), "Гаер Лидия Николаевна");
			//TODO Разобраться почему ID нету
			assertEquals(study.getPatientID(), null);
			assertEquals(study.getPatientSex(), "F");
			//TODO Разобраться почему ID нету
			assertEquals(study.getStudyDoctor(), "not defined");
			//TODO Разобраться почему ID нету
			assertEquals(study.getStudyOperator(), "empty");
			
			//(0010,2000) LO #12 [Без диагноза] Medical Alerts
			//TODO Разобраться почему тег исчез
			//assertEquals(study.getStudyDescription(), "Без диагноза");
			
			assertEquals(study.getPatientShortName(), "GAELN59");
			assertEquals(study.getManufacturerModelName(), null);
			assertEquals(study.getManufacturer(), "JV HELPIC (MOSCOW)");
			
			assertEquals(study.getStudyType(), null);
			assertEquals(study.getStudyResult(), null);
			assertEquals(study.getStudyViewProtocol(), null);
			assertEquals(study.getStudyViewProtocolDate(), null);
			assertEquals(study.getManufacturerUID(), "empty");
			assertEquals(study.getDcmType(), "empty");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (din != null)
					din.close();
			} catch (IOException ignore) {
			}
		}
	}


}
