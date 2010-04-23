 package org.psystems.dicom.webservice;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.axis2.AxisFault;
import org.psystems.dicom.webservice.DicomArchiveStub.FindStudies;
import org.psystems.dicom.webservice.DicomArchiveStub.FindStudiesByType;
import org.psystems.dicom.webservice.DicomArchiveStub.FindStudiesByTypeResponse;
import org.psystems.dicom.webservice.DicomArchiveStub.FindStudiesResponse;
import org.psystems.dicom.webservice.DicomArchiveStub.GetStudy;
import org.psystems.dicom.webservice.DicomArchiveStub.GetStudyResponse;
import org.psystems.dicom.webservice.DicomArchiveStub.NewStudy;
import org.psystems.dicom.webservice.DicomArchiveStub.NewStudyResponse;
import org.psystems.dicom.webservice.DicomArchiveStub.Study;

public class DicomArchiveClient {

	public static void main(String[] args) {
		try {

			try {
//				testNewStudy();
//				testGetStudy();
//				findStudies();
				String host = "http://localhost:8080";
				if(args.length > 0) {
					host = args[0];
				}
				System.out.println("host is "+host);
				findStudiesByType(host);
			} catch (DicomWebServiceExceptionException0 e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			// DicomDBStub stub = new DicomDBStub();
			//                	
			//                	
			// DicomDBStub.GetStudy getstudy = new GetStudy();
			// getstudy.setI(11);
			// DicomDBStub.GetStudyResponse responce = stub.getStudy(getstudy);
			// Study result = responce.get_return();
			//                	
			// System.out.println("RESULT : "+result.getId()+" = "+result.getDescription());
			//					
			// DicomDBStub.FindStudies findstudies = new FindStudies();
			// findstudies.setS("����� searche");
			// FindStudiesResponse responce1 = stub.findStudies(findstudies);
			// Study[] result1 = responce1.get_return();
			//					
			// for(int i = 0; i<result1.length; i++) {
			// System.out.println(" R : "+result1[i].getId() + " = " +
			// result1[i].getDescription());
			// }
			//					
			// DicomDBStub.StartTransaction transaction = new
			// StartTransaction();
			// Study sendedStudy = new Study();
			// sendedStudy.setId(333);
			// sendedStudy.setDescription("TTT ���� TTT");
			// transaction.setStudy(sendedStudy );
			// StartTransactionResponse transactionResponce =
			// stub.startTransaction(transaction);
			// int resultTrans = transactionResponce.get_return();
			// System.out.println("RESULT : "+resultTrans);
			//					

		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
	
	private static void findStudies() throws AxisFault, RemoteException, DicomWebServiceExceptionException0 {

		DicomArchiveStub stub = new DicomArchiveStub();
		DicomArchiveStub.FindStudies studySearchedString = new FindStudies();
		studySearchedString.setS("������ ������");
		FindStudiesResponse responceOfSearcheStudies = stub.findStudies(studySearchedString);
		Study[] result = responceOfSearcheStudies.get_return();
		
		System.out.println("Finded Studies : " + result);
		
		 for(int i = 0; i<result.length; i++) {
			 printStudy(result[i]);
		 }
	}
	
	private static void findStudiesByType(String host) throws AxisFault, RemoteException, DicomWebServiceExceptionException0 {
		
		
	
		
		DicomArchiveStub stub = new DicomArchiveStub(host+"/dicom-webservice/services/DicomArchive" );
		
		FindStudiesByType query = new FindStudiesByType();
		
		query.setStudyModality("CR");
		
		
		query.setPatientName(null);
		query.setPatientShortName(null);
		query.setPatientSex(null);
		query.setPatientBirthDate("");
//		query.setPatientBirthDate("1978-12-14");
//		
//		
//		Calendar db = Calendar.getInstance();
//		db.set(2010, 1, 25); //2010-02-25
//		
////		db.set(1961, 3, 9); //1961-04-09
////		query.setPatientBirthDate(db);
//		(2010-02-25) (2010-04-01)
//		
//		
		query.setBeginStudyDate("2010-02-24");
		query.setEndStudyDate("2010-02-25");
//		
//		
//		
		query.setPatientName("%");
////		query.setPatientName("������");
//		
		FindStudiesByTypeResponse responce = stub.findStudiesByType(query );
		Study[] result = responce.get_return();
		
		System.out.println("Finded Studies : " + result);
//		System.out.println("Calendar:"+Calendar.getInstance());
		if(result==null) return;
		 for(int i = 0; i<result.length; i++) {
			 printStudy(result[i]);
		 }
	}

	private static void testGetStudy() throws AxisFault, RemoteException, DicomWebServiceExceptionException0 {

		DicomArchiveStub stub = new DicomArchiveStub();
		DicomArchiveStub.GetStudy studySearchedId = new GetStudy();
		studySearchedId.setI(10);
		GetStudyResponse responceOfSearcheStudy = stub
				.getStudy(studySearchedId);
		Study findedStudy = responceOfSearcheStudy.get_return();
		printStudy(findedStudy);

	}

	

	private static void testNewStudy() throws AxisFault, RemoteException, DicomWebServiceExceptionException0 {
		DicomArchiveStub stub = new DicomArchiveStub();

		DicomArchiveStub.NewStudy newStudy = new NewStudy();
		newStudy.setPatientName("������ ����");
		newStudy.setPatientDateBirthday(Calendar.getInstance());
		newStudy.setPatientId("����123");
		newStudy.setPatientSex("M");
		newStudy.setStudyType("��������������");

		NewStudyResponse responceOfNewStudy = stub.newStudy(newStudy);
		int newStudyId = responceOfNewStudy.get_return();
		System.out.println("newStudyId=" + newStudyId);
	}
	
	private static void printStudy(Study findedStudy) {
		
		if(findedStudy == null) return;
		
		SimpleDateFormat formatLevel = new SimpleDateFormat("yyyy-MM-dd_H-m-s.S");
//		String PatientBirthDate = formatLevel.format(calendar.getTime());
		
		
		System.out.println("findedStudy=" + findedStudy.getId() + ";"
				+ findedStudy.getStudyViewprotocol() + ";"
				+ findedStudy.getManufacturerModelName() + ";"
				+ findedStudy.getStudyDoctor() + ";" + findedStudy.getStudyId()
				+ ";" + findedStudy.getPatientName() + ";"
				+ ";" + findedStudy.getPatientShortName() + ";"
				+ findedStudy.getPatientId() + ";" +
				"PatientBirthDate="+findedStudy.getPatientBirthDateAsString() + 
				" --- [" + findedStudy.getPatientBirthDate().getTime() +"] -- "+
				"[" + findedStudy.getPatientBirthDate().getTime().getTime() +"] -- "+
				"{" + findedStudy.getPatientBirthDate() +"};"
				+"Sex="+findedStudy.getPatientSex()+"; "
				+ "StudyDateAsString=" + findedStudy.getStudyDateAsString() + ";"
				+ findedStudy.getStudyResult() + ";"
				+ findedStudy.getStudyType() + ";" + findedStudy.getStudyUrl());
				//+ ";" + findedStudy.getStudyDate());
	}
}