package si.test.client.ui;

import si.test.client.jsinterop.Blob;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FileUpload;

public class MyGwtFileUpload extends FileUpload 
{
	public final Blob getBlob()
	{
		return getBlob(getElement());
	}
	
	public final native Blob getBlob(Element element) /*-{
		if(element.files.length == 0) return null;
	    return element.files[0];
	  }-*/;
	
	public void clear() 
	{
		clear(getElement());
	}
	public final native void clear(Element element) /*-{
		if(element == null) return;
	    return element.value = '';
	  }-*/;
	
	//morda dodati še getFileList, ki vraèa FileList[] JsType
}
