package si.test.client.xhr;

import si.test.client.jsinterop.Blob;
import si.test.client.jsinterop.FormData;
import si.test.client.jsinterop.XMLHttpRequestUpload;

import com.google.gwt.xhr.client.XMLHttpRequest;

public class MyXMLHttpRequest extends XMLHttpRequest 
{
	protected MyXMLHttpRequest() {
	}
	  public final native void send(Blob blob) /*-{
	    this.send(blob);
	  }-*/;

	  public final native void send(FormData formData) /*-{
	    this.send(formData);
	  }-*/;

	  public final native XMLHttpRequestUpload upload() /*-{
	    return this.upload;
	  }-*/;

}
