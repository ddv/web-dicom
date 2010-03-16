
/**
 * DicomArchiveCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

    package org.psystems.dicom.webservice;

    /**
     *  DicomArchiveCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class DicomArchiveCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public DicomArchiveCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public DicomArchiveCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getStudy method
            * override this method for handling normal response from getStudy operation
            */
           public void receiveResultgetStudy(
                    org.psystems.dicom.webservice.DicomArchiveStub.GetStudyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getStudy operation
           */
            public void receiveErrorgetStudy(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for findStudies method
            * override this method for handling normal response from findStudies operation
            */
           public void receiveResultfindStudies(
                    org.psystems.dicom.webservice.DicomArchiveStub.FindStudiesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from findStudies operation
           */
            public void receiveErrorfindStudies(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for newStudy method
            * override this method for handling normal response from newStudy operation
            */
           public void receiveResultnewStudy(
                    org.psystems.dicom.webservice.DicomArchiveStub.NewStudyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from newStudy operation
           */
            public void receiveErrornewStudy(java.lang.Exception e) {
            }
                


    }
    