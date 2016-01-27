package si.test.client.ui;

import si.test.client.jsinterop.Blob;

import com.google.gwt.dom.client.InputElement;
import com.sencha.gxt.widget.core.client.form.FileUploadField;

public class MyGxtFileUpload extends FileUploadField 
{
	public final Blob getBlob()
	{
		return getBlob(super.getFileInput());
	}
	
	public final native Blob getBlob(InputElement element) /*-{
		if(element.files.length == 0) return null;
	    return element.files[0];
	  }-*/;
}
