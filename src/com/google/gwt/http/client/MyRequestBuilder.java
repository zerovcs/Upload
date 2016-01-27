package com.google.gwt.http.client;

import java.util.HashMap;
import java.util.Map;

import si.test.client.jsinterop.Blob;
import si.test.client.jsinterop.FormData;
import si.test.client.jsinterop.XMLHttpRequestUpload.ProgressEventListener;
import si.test.client.xhr.MyXMLHttpRequest;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.xhr.client.ReadyStateChangeHandler;
import com.google.gwt.xhr.client.XMLHttpRequest;

public class MyRequestBuilder extends RequestBuilder
{
	private Map<String, String> headers;
	private ProgressEventListener progressEventListener;
	
	public MyRequestBuilder(Method httpMethod, String url) 
	{
		super(httpMethod, url);
	}

	/**
	 * Returns the value of a header previous set by
	 * {@link #setHeader(String, String)}, or <code>null</code> if no such
	 * header was set.
	 * 
	 * @param header
	 *            the name of the header
	 */
	public String getHeader(String header) {
		if (headers == null) {
			return null;
		}
		return headers.get(header);
	}

	public void setProgressEventListener(ProgressEventListener progressEventListener) 
	{
		this.progressEventListener = progressEventListener;
	}
	
	public void setHeader(String header, String value) {
		StringValidator.throwIfEmptyOrNull("header", header);
		StringValidator.throwIfEmptyOrNull("value", value);

		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.put(header, value);
	}	  
	
	public Request send(Blob blob, RequestCallback callback) throws RequestException 
	{
	    StringValidator.throwIfNull("callback", callback);
		MyRequest request = createXMLHttpRequest(callback);
	    setHeaders(request.getMyXMLHttpRequest());

	    try {
		      request.getMyXMLHttpRequest().send(blob);
		    } catch (JavaScriptException e) {
		      throw new RequestException(e.getMessage());
		    }
	    return request;
	}

	public Request send(FormData formData, RequestCallback callback) throws RequestException 
	{
	    StringValidator.throwIfNull("callback", callback);
		MyRequest request = createXMLHttpRequest(callback);
	    try {
		      request.getMyXMLHttpRequest().send(formData);
		    } catch (JavaScriptException e) {
		      throw new RequestException(e.getMessage());
		    }
	    return request;
	}

	  private MyRequest createXMLHttpRequest(final RequestCallback callback) throws RequestException 
	  {
			MyXMLHttpRequest xmlHttpRequest = XMLHttpRequest.create().cast();

		    try 
		    {
		        xmlHttpRequest.open(super.getHTTPMethod(), super.getUrl());
		    }
		    catch (JavaScriptException e) 
		    {
		      RequestPermissionException requestPermissionException = new RequestPermissionException(super.getUrl());
		      requestPermissionException.initCause(new RequestException(e.getMessage()));
		      throw requestPermissionException;
		    }
			if(progressEventListener != null)
			{
				xmlHttpRequest.upload().setOnprogress(progressEventListener);
			}


		    final MyRequest request = new MyRequest(xmlHttpRequest, super.getTimeoutMillis(), callback);

		    // Must set the onreadystatechange handler before calling send().
		    xmlHttpRequest.setOnReadyStateChange(new ReadyStateChangeHandler() {
		      public void onReadyStateChange(XMLHttpRequest xhr) {
		        if (xhr.getReadyState() == XMLHttpRequest.DONE) {
		          xhr.clearOnReadyStateChange();
		          request.fireOnResponseReceived(callback);
		        }
		      }
		    });
		    return request;
	  }
	  
	private void setHeaders(XMLHttpRequest xmlHttpRequest) throws RequestException 
	{
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				try {
					xmlHttpRequest.setRequestHeader(header.getKey(),
							header.getValue());
				} catch (JavaScriptException e) {
					throw new RequestException(e.getMessage());
				}
			}
		} else {
			xmlHttpRequest.setRequestHeader("Content-Type",
					"text/plain; charset=utf-8");
		}
	}

	private class MyRequest extends Request
	{
		private MyXMLHttpRequest myXMLHttpRequest;
		MyRequest(MyXMLHttpRequest xmlHttpRequest, int timeoutMillis, RequestCallback callback) 
		{
			super(xmlHttpRequest, timeoutMillis, callback);
			this.myXMLHttpRequest = xmlHttpRequest;
		}
		
		public MyXMLHttpRequest getMyXMLHttpRequest() {
			return myXMLHttpRequest;
		}
		
		
	}	
}
