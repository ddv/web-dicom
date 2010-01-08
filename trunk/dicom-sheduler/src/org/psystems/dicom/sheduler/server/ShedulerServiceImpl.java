package org.psystems.dicom.sheduler.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.apache.derby.drda.NetworkServerControl;
import org.psystems.dicom.sheduler.client.ShedulerService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ShedulerServiceImpl extends RemoteServiceServlet implements ShedulerService {

	// http://db.apache.org/derby/docs/dev/adminguide/adminguide-single.html#cadminov17524
	// http://db.apache.org/derby/javadoc/publishedapi/jdbc4/org/apache/derby/drda/NetworkServerControl.html
	private String framework = "embedded";
	// private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String driver = "org.apache.derby.jdbc.ClientDriver";
	// private String protocol = "jdbc:derby:ttt/";

	private String protocol = "jdbc:derby://localhost:1527/ttttt/";
	private String rootDicomFilesDir = "/WORK/workspace/dicom-sheduler/test/testdata/2009-12-16/2009-12-16";

	public String greetServer(String input) {
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		startDB();
		loadDriver();
		createDb();

		File f = new File(rootDicomFilesDir);
		if (f.isDirectory()) {

			// filter files for extension *.dcm
			FilenameFilter filter = new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".dcm")) {
						return true;
					}
					return false;
				}

			};
			File[] files = f.listFiles(filter);
			for (int i = 0; i < files.length; i++) {
				System.out.println("FILE=" + files[i]);
				try {

					// DCMUtil.convert(files[i], new
					// File(files[i].getAbsolutePath() + ".jpg"));
//					DCMUtil.printTags(files[i]);
					
					
					DicomObjectWrapper proxy = DCMUtil.getDCMObject(files[i]);
					
					insertData(proxy.getDCM_FILE_NAME(), proxy.getPATIENT_NAME(), 
							proxy.getPATIENT_BIRTH_DATE(), proxy.getSTUDY_DATE());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	@Override
	public String startDB() {
		NetworkServerControl server;
		try {
			server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
			server.start(null);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "STARTED";
	}

	@Override
	public String stopDB() {
		NetworkServerControl server;
		try {
			server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
			server.shutdown();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "stopped";
	}

	private void createDb() {

		try {
			Reader reader = new InputStreamReader(new FileInputStream("db.sql"), "WINDOWS-1251");
			BufferedReader d = new BufferedReader(reader);
			String sql = "";
			String s1 = d.readLine();
			while (s1 != null) {
				s1 = d.readLine();
				if (s1 != null)
					sql += s1 + "\n";
			}

			createSchema(sql);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertData(String DCM_FILE_NAME, String PATIENT_NAME, Date PATIENT_BIRTH_DATE,
			Date STUDY_DATE) {
		Connection conn = null;
		/*
		 * This ArrayList usage may cause a warning when compiling this class
		 * with a compiler for J2SE 5.0 or newer. We are not using generics
		 * because we want the source to support J2SE 1.4.2 environments.
		 */
		// PreparedStatements
		PreparedStatement psInsert = null;
		try {
			Properties props = new Properties(); // connection properties
			props.put("user", "user1");
			props.put("password", "user1");
			String dbName = "derbyDBTEST"; // the name of the database
			conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
			conn.setAutoCommit(false);

			psInsert = conn.prepareStatement("insert into webdicom.dcmfile"
					+ " (DCM_FILE_NAME, PATIENT_BIRTH_DATE, PATIENT_NAME, STUDY_DATE)"
					+ " values (?, ?, ?, ?)");

			psInsert.setString(1, DCM_FILE_NAME);
			psInsert.setDate(2, PATIENT_BIRTH_DATE);
			psInsert.setString(3, PATIENT_NAME);
			psInsert.setDate(4, STUDY_DATE);

			psInsert.executeUpdate();

			conn.commit();
		} catch (SQLException sqle) {
			printSQLException(sqle);
		}

	}

	private void createSchema(String sql) {
		Connection conn = null;
		/*
		 * This ArrayList usage may cause a warning when compiling this class
		 * with a compiler for J2SE 5.0 or newer. We are not using generics
		 * because we want the source to support J2SE 1.4.2 environments.
		 */
		ArrayList statements = new ArrayList(); // list of Statements,
		// PreparedStatements
		PreparedStatement psInsert = null;
		PreparedStatement psUpdate = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			Properties props = new Properties(); // connection properties
			// providing a user name and password is optional in the embedded
			// and derbyclient frameworks
			props.put("user", "user1");
			props.put("password", "user1");

			/*
			 * By default, the schema APP will be used when no username is
			 * provided. Otherwise, the schema name is the same as the user name
			 * (in this case "user1" or USER1.)
			 * 
			 * Note that user authentication is off by default, meaning that any
			 * user can connect to your database using any password. To enable
			 * authentication, see the Derby Developer's Guide.
			 */

			String dbName = "derbyDBTEST"; // the name of the database

			/*
			 * This connection specifies create=true in the connection URL to
			 * cause the database to be created when connecting for the first
			 * time. To remove the database, remove the directory derbyDB (the
			 * same as the database name) and its contents.
			 * 
			 * The directory derbyDB will be created under the directory that
			 * the system property derby.system.home points to, or the current
			 * directory (user.dir) if derby.system.home is not set.
			 */

			conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);

			// conn = DriverManager.getConnection(protocol
			// + ";create=true", props);

			System.out.println("Connected to and created database " + dbName);

			// We want to control transactions manually. Autocommit is on by
			// default in JDBC.
			conn.setAutoCommit(false);

			/*
			 * Creating a statement object that we can use for running various
			 * SQL statements commands against the database.
			 */
			s = conn.createStatement();
			statements.add(s);

			// We create a table...
			// s.execute("create table location(num int, addr varchar(40))");
			s.execute(sql);

			System.out.println("SQL: " + sql);

			conn.commit();
		} catch (SQLException sqle) {
			printSQLException(sqle);
		}

	}

	private void loadDriver() {

		try {
			Class.forName(driver).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("\nUnable to load the JDBC driver " + driver);
			System.err.println("Please check your CLASSPATH.");
			cnfe.printStackTrace(System.err);
		} catch (InstantiationException ie) {
			System.err.println("\nUnable to instantiate the JDBC driver " + driver);
			ie.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			System.err.println("\nNot allowed to access the JDBC driver " + driver);
			iae.printStackTrace(System.err);
		}
	}

	public static void printSQLException(SQLException e) {
		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null) {
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			// for stack traces, refer to derby.log or uncomment this:
			// e.printStackTrace(System.err);
			e = e.getNextException();
		}
	}
}
