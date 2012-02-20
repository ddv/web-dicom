package org.psystems.dicom.pdfview.client;

import java.util.ArrayList;

import org.psystems.dicom.pdfview.dto.ConfigTemplateDto;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface PdfServiceAsync {
	
	void getTemplates(AsyncCallback<ArrayList<ConfigTemplateDto>> callback);
}