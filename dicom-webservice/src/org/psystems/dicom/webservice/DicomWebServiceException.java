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
    
 */
package org.psystems.dicom.webservice;

/**
 * @author dima_d
 * 
 */
public class DicomWebServiceException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7529959696848010201L;

	public DicomWebServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DicomWebServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DicomWebServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DicomWebServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
