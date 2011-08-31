
package org.psystems.dicom.webservice.clientimg;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "UserManager", targetNamespace = "http://webservice.dicom.psystems.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UserManager {


    /**
     * 
     * @param userName
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addUser", targetNamespace = "http://webservice.dicom.psystems.org/", className = "org.psystems.dicom.webservice.clientimg.AddUser")
    @ResponseWrapper(localName = "addUserResponse", targetNamespace = "http://webservice.dicom.psystems.org/", className = "org.psystems.dicom.webservice.clientimg.AddUserResponse")
    public boolean addUser(
        @WebParam(name = "userName", targetNamespace = "")
        String userName);

    /**
     * 
     * @param arg0
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "downloadImage", targetNamespace = "http://webservice.dicom.psystems.org/", className = "org.psystems.dicom.webservice.clientimg.DownloadImage")
    @ResponseWrapper(localName = "downloadImageResponse", targetNamespace = "http://webservice.dicom.psystems.org/", className = "org.psystems.dicom.webservice.clientimg.DownloadImageResponse")
    public byte[] downloadImage(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "uploadImage", targetNamespace = "http://webservice.dicom.psystems.org/", className = "org.psystems.dicom.webservice.clientimg.UploadImage")
    @ResponseWrapper(localName = "uploadImageResponse", targetNamespace = "http://webservice.dicom.psystems.org/", className = "org.psystems.dicom.webservice.clientimg.UploadImageResponse")
    public String uploadImage(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0);

}
