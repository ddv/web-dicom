package org.psystems.webdicom2.ws;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.MTOM;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.commons.io.FileUtils;
import org.psystems.webdicom2.ws.dto.Direction;
import org.psystems.webdicom2.ws.dto.RISCode;
import org.psystems.webdicom2.ws.dto.DCM;
import org.psystems.webdicom2.ws.dto.StudyResult;

import javax.imageio.ImageIO;

/**
 * @author dima_d
 * 
 *         http://jax-ws.java.net/
 * 
 *         Типы данных:
 *         http://download.oracle.com/docs/cd/E12840_01/wls/docs103/
 *         webserv/data_types.html
 * 
 *         аттачменты:
 *         http://www.mkyong.com/webservices/jax-ws/jax-ws-attachment-with-mtom/
 */
@MTOM
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public class Gate {

	public static Logger logger = Logger.getLogger(Gate.class.getName());

	@Resource
	private WebServiceContext context;

	String testDrnDataDir = "/tmp/webdicom";
	String testDrnDatafile = "direction.xml";

	// /private static Properties drnProp;

	/**
	 * For Testing
	 * 
	 * @param misId
	 * @return
	 * @throws IOException
	 */
	private Properties loadDrnFromPropFile(String misId) throws IOException {

		Properties drnProp = new Properties();

		try {
			FileInputStream fis = new FileInputStream(testDrnDataDir
					+ File.separator + misId + File.separator + testDrnDatafile);
			drnProp.loadFromXML(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			// empty properties
		}

		return drnProp;

	}

	private void saveDrnToPropFile(Properties drnProp) throws IOException {

		Properties tmp = new Properties() {

			@Override
			public Set<Object> keySet() {
				return Collections.unmodifiableSet(new TreeSet<Object>(super
						.keySet()));
			}

		};

		tmp.putAll(drnProp);

		File theDir = new File(testDrnDataDir);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();

			if (result) {
				// System.out.println("DIR created");
			}
		}

		theDir = new File(testDrnDataDir + File.separator
				+ drnProp.getProperty("misId"));
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();

			if (result) {
				// System.out.println("DIR created");
			}
		}

		FileOutputStream fos = new FileOutputStream(testDrnDataDir
				+ File.separator + drnProp.getProperty("misId")
				+ File.separator + testDrnDatafile);
		tmp.storeToXML(fos, "WebdicomProperties File", "UTF-8");
		fos.close();
	}

	/**
	 * Создание исследования
	 * 
	 * @param drn
	 * @return
	 */
	public Direction sendDirection(@WebParam(name = "direction") Direction drn)
			throws WsException {

		try {

			Properties drnProp = loadDrnFromPropFile(drn.barCode);

			drnProp.put("barCode", drn.barCode);
			drnProp.put("misId", drn.misId);
			drnProp.put("dateBirsday", drn.dateBirsday);
			drnProp.put("dateStudy", drn.dateStudy);
			drnProp.put("modality", drn.modality);
			drnProp.put("patientId", drn.patientId);
			drnProp.put("patientName", drn.patientName);
			drnProp.put("serviceName", drn.serviceName);
			drnProp.put("sex", drn.sex);

			saveDrnToPropFile(drnProp);

		} catch (IOException e) {
			e.printStackTrace();
			throw new WsException(e);
		}

		return drn;
	}

	/**
	 * Удалить направление
	 * 
	 * @param misId
	 *            - ID из МИС
	 * 
	 * @return - количество связанных исследований
	 * @throws WsException
	 * 
	 */
	public int removeDirection(@WebParam(name = "misId") String misId)
			throws WsException {
		File drnDir = new File(testDrnDataDir + File.separator + misId);
		System.out.println("! path=" + drnDir.getAbsolutePath());
		try {
			FileUtils.deleteDirectory(drnDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WsException(e);
		}
		return 0;
	}

	/**
	 * Получение списка выполненных сейрий
	 * 
	 * @param misId
	 * @return
	 */
	public DCM[] getDCM(@WebParam(name = "misId") String misId) {

		File drnDir = new File(testDrnDataDir + File.separator + misId);
		ArrayList<DCM> result = new ArrayList<DCM>();

		File[] files = drnDir.listFiles();
		for (File dcmDir : files) {
			if (dcmDir.isDirectory()) {

				DCM dcmDto = new DCM();

				File[] dataFiles = dcmDir.listFiles();
				for (File datafile : dataFiles) {
					if (datafile.getName().endsWith(".pdf")
							|| datafile.getName().endsWith(".jpg")) {

						// String fileID =
						// datafile.getName().replaceFirst(".pdf", "");
						// fileID = datafile.getName().replaceFirst(".jpg", "");

						if (datafile.getName().endsWith(".pdf"))
							dcmDto.pdfId = dcmDir.getName();
						if (datafile.getName().endsWith(".jpg"))
							dcmDto.imageId = dcmDir.getName();

						dcmDto.dcmId = dcmDir.getName();
						;
						dcmDto.misId = drnDir.getName();
						result.add(dcmDto);

					}
				}
			}
		}

		return result.toArray(new DCM[result.size()]);

	}

	/**
	 * 
	 * DCM-ки по дате
	 * 
	 * @param misId
	 * @param date
	 *            - формат YYYYMMDD
	 * @return
	 */
	public DCM[] getDCMbyDate(@WebParam(name = "misId") String misId,
			@WebParam(name = "date") String date) {
		return null;

	}

	/**
	 * Список тэгов
	 * 
	 * @param dcmId
	 * @return
	 */
	public HashMap<String, String> getDCMTags(
			@WebParam(name = "dcmId") String dcmId) {
		return null;

	}

	/**
	 * 
	 * Получение бинарного контента
	 * 
	 * @param misId
	 * @param contentId
	 * @return
	 * @throws IOException
	 */
	public byte[] getDCMContent(@WebParam(name = "misId") String misId,
			@WebParam(name = "contentId") String contentId) throws WsException {

		File drnDir = new File(testDrnDataDir + File.separator + misId);

		File[] files = drnDir.listFiles();
		for (File studyDir : files) {
			if (studyDir.isDirectory() && studyDir.getName().equals(contentId)) {

				File[] dataFiles = studyDir.listFiles();
				for (File datafile : dataFiles) {
					if (datafile.getName().endsWith(".pdf")
							|| datafile.getName().endsWith(".jpg")) {

						String fileID = datafile.getName().replaceFirst(".pdf",
								"");
						fileID = datafile.getName().replaceFirst(".jpg", "");

						// fileID = "data.jpg";

						// System.out.println("!!!! id="+id);
						// System.out.println("!!!! fileID="+fileID);
						// System.out.println("!!!! datafile="+datafile);

						// try {
						// return ImageIO.read(datafile);
						// } catch (IOException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// throw new WsException(e.getCause());
						// }

						try {
							FileInputStream fis = new FileInputStream(datafile);
							byte[] data = new byte[(int) datafile.length()];
							fis.read(data);
							fis.close();
							return data;
						} catch (IOException e) {
							e.printStackTrace();
							throw new WsException(e.getCause());
						}

					}
				}
			}
		}
		return null;

	}

	/**
	 * @param misId
	 * @return
	 */
	public StudyResult getCompliteStudyResult(
			@WebParam(name = "misId") String misId) {

		StudyResult result = new StudyResult();

		File drnDir = new File(testDrnDataDir + File.separator + misId);
		ArrayList<String> pdfs = new ArrayList<String>();
		ArrayList<String> jpgs = new ArrayList<String>();

		File[] files = drnDir.listFiles();
		for (File dcmDir : files) {
			if (dcmDir.isDirectory()) {
				File[] dataFiles = dcmDir.listFiles();
				for (File datafile : dataFiles) {
					if (datafile.getName().endsWith(".pdf")) {
						pdfs.add(dcmDir.getName());
					} else if (datafile.getName().endsWith(".jpg")) {
						jpgs.add(dcmDir.getName());
					}
				}
			}
		}

		result.imageUrls = jpgs.toArray(new String[jpgs.size()]);
		result.pdfUrls = pdfs.toArray(new String[pdfs.size()]);

		try {

			Properties drnProp = loadDrnFromPropFile(misId);
			result.result = drnProp.getProperty("finalResult");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Создание PDF-ки в исследовании
	 * 
	 * @param misId
	 * @param studyUID
	 * @param content
	 * @return
	 */
	public String sendPdf(@WebParam(name = "misId") String misId,
			@WebParam(name = "content") byte[] content) throws WsException {

		long id = new Date().getTime();
		File theDir = new File(testDrnDataDir + File.separator + misId
				+ File.separator + id);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();

			if (result) {
				// System.out.println("DIR created");
			}
		}
		try {
			// Properties drnProp = loadDrnFromPropFile(barCode);

			String filename = "data.pdf";
			// drnProp.put("pdf." + id, filename);
			FileOutputStream fos = new FileOutputStream(testDrnDataDir
					+ File.separator + misId + File.separator + id
					+ File.separator + filename);
			fos.write(content);
			fos.flush();
			fos.close();
			return "http://localhost:8080/pdf/" + misId + File.separator
					+ filename;
		} catch (IOException e) {
			e.printStackTrace();
			throw new WsException(e);
		}

	}

	public String sendImage(@WebParam(name = "misId") String misId,
			@WebParam(name = "content") byte[] content) throws WsException {

		long id = new Date().getTime();
		File theDir = new File(testDrnDataDir + File.separator + misId
				+ File.separator + id);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();

			if (result) {
				// System.out.println("DIR created");
			}
		}
		try {
			// Properties drnProp = loadDrnFromPropFile(barCode);

			String filename = "data.jpg";
			FileOutputStream fos = new FileOutputStream(testDrnDataDir
					+ File.separator + misId + File.separator + id
					+ File.separator + filename);
			fos.write(content);
			fos.flush();
			fos.close();
			// saveDrnToPropFile(barCode);
			return "http://localhost:8080/img/" + misId + File.separator
					+ filename;
		} catch (IOException e) {
			e.printStackTrace();
			throw new WsException(e);
		}

	}

	/**
	 * Передача окончательного результата
	 * 
	 * @param misId
	 * @param resultStr
	 */
	public String sendFinalResult(@WebParam(name = "misId") String misId,
			@WebParam(name = "resultStr") String resultStr) {

		try {
			Properties drnProp = loadDrnFromPropFile(misId);
			drnProp.put("finalResult", resultStr);
			saveDrnToPropFile(drnProp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Что лучше возвращать?
		return misId + "=" + resultStr;
	}

	/**
	 * Передача ФИО врача узкого специалиста
	 * 
	 * @param misId
	 * @param resultStr
	 */
	public String sendPhysician(@WebParam(name = "misId") String misId,
			@WebParam(name = "fio") String fio) {

		try {
			Properties drnProp = loadDrnFromPropFile(misId);
			drnProp.put("physician", fio);
			saveDrnToPropFile(drnProp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Что лучше возвращать?
		return misId + "=" + fio;
	}

	/**
	 * TODO Не используется
	 * 
	 * @return
	 */
	public RISCode[] getRISCodes() {

		ArrayList<RISCode> result = new ArrayList<RISCode>();

		RISCode code = new RISCode();
		code.modality = "MG";
		code.risCode = "MG00.1";
		code.description = "Исследование молочной железы 1";

		result.add(code);

		code = new RISCode();
		code.modality = "DF";
		code.risCode = "DF00.1";
		code.description = "Рентген 1";

		result.add(code);

		return result.toArray(new RISCode[result.size()]);

	}
}
