/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package si.test.client;

import si.test.client.jsinterop.Blob;
import si.test.client.jsinterop.FormData;
import si.test.client.jsinterop.ProgressBar;
import si.test.client.jsinterop.XMLHttpRequestUpload.ProgressEventListener;
import si.test.client.jsinterop.event.ProgressEvent;
import si.test.client.resources.AppResources;
import si.test.client.ui.MyGwtFileUpload;
import si.test.client.ui.MyGxtFileUpload;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.MyRequestBuilder;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FileUploadField;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyClient implements EntryPoint 
{
	private Button clickMeButton;
	private ProgressBar progressBar;
	private String fixedText = "Selected file: ";
	Label label = new Label(fixedText);
	
	public void onModuleLoad() 
	{
		RootPanel rootPanel = RootPanel.get();
		AppResources resources = GWT.create(AppResources.class);
		resources.css().ensureInjected();

		MyGwtFileUpload fileUpload = new MyGwtFileUpload();
		fileUpload.getElement().getStyle().setDisplay(Display.NONE);
		rootPanel.add(fileUpload);
		Button browser = new Button("Browse...");
		rootPanel.add(browser);
		
		browser.addClickHandler(new ClickHandler() 
		{
			@Override
			public void onClick(ClickEvent event) 
			{
				label.setText(fixedText);
				fileUpload.clear();
				progressBar.setValue(0);
				fileUpload.click();
			}
		});
		
		rootPanel.add(label);

		fileUpload.addChangeHandler(new ChangeHandler() 
		{
			@Override
			public void onChange(ChangeEvent event) 
			{
				if(fileUpload.getBlob() != null)
				{
					label.setText(fixedText + fileUpload.getBlob().getName());
				}
				else
				{
					label.setText(fixedText);
				}
			}
		});
		
		clickMeButton = new Button();
		rootPanel.add(clickMeButton);
		clickMeButton.setText("Upload");
		clickMeButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
//				TestService.Util.getInstance().test(new AsyncCallback<String>() 
//				{
//					@Override
//					public void onSuccess(String result) {
//						Window.alert(result);
//					}
//					
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert(caught.getMessage());
//					}
//				});
				
//				MyRequestBuilder builder = new MyRequestBuilder(RequestBuilder.POST, "UploadServlet");
				MyRequestBuilder builder = new MyRequestBuilder(RequestBuilder.POST, "UploadFormDataServlet");
				builder.setProgressEventListener(new ProgressEventListener() 
				{
					@Override
					public void handleEvent(ProgressEvent progressEvent) 
					{
//						GWT.debugger();
						if (progressEvent.getLengthComputable()) 
						{
							progressBar.setMax(progressEvent.getTotal());
							progressBar.setValue(progressEvent.getLoaded());
							GWT.log("Total: " + progressEvent.getTotal() + "   Loaded: " + progressEvent.getLoaded());
						}
					}
				});
//				builder.setHeader("Content-Type", "multipart/form-data; charset=utf-8");
				try 
				{
					Blob blob = fileUpload.getBlob();
					if(blob == null) 
					{
						Window.alert("Select file first");
						return;
					}
					RequestCallback requestCallback = new RequestCallback()
					{
						@Override
						public void onResponseReceived(Request request, Response response) {
							Window.alert(response.getText());

						}
						
						@Override
						public void onError(Request request, Throwable exception) 
						{
							Window.alert(exception.getMessage());
						}
					};
//					builder.send(blob, requestCallback);
					FormData formData = new FormData();
//					formData.append("file-name", blob.getName()); //èe pri spodnjem dodamo getName, potem moramo mna serverju prebrati filename from parf - glej TestFormDataUpload
					formData.append("file", blob, blob.getName());
					builder.send(formData, requestCallback);
					
				}
				catch (RequestException e) 
				{
					Window.alert(e.getMessage());
				}
			}
		});
		HTMLPanel div = new HTMLPanel("<progress id='progressBar' max='100' value='0'></progress>");
		rootPanel.add(div);
		progressBar = DOM.getElementById("progressBar").cast();		
		
		HTMLPanel hr = new HTMLPanel("<hr style='width='100%';'>");
		rootPanel.add(hr);
		
		MyGxtFileUpload gxtFileUpload = new MyGxtFileUpload();
		rootPanel.add(gxtFileUpload);

		com.sencha.gxt.widget.core.client.ProgressBar gxtProgressBar = new com.sencha.gxt.widget.core.client.ProgressBar();
		rootPanel.add(gxtProgressBar);
		TextButton gxtButton = new TextButton("Upload by GXT");
		rootPanel.add(gxtButton);
		gxtFileUpload.addChangeHandler(new ChangeHandler() 
		{
			@Override
			public void onChange(ChangeEvent event) 
			{
				gxtProgressBar.reset();
			}
		});
		gxtButton.addSelectHandler(new SelectHandler() 
		{
			@Override
			public void onSelect(SelectEvent event) 
			{
				MyRequestBuilder builder = new MyRequestBuilder(RequestBuilder.POST, "UploadFormDataServlet");
				builder.setProgressEventListener(new ProgressEventListener() 
				{
					@Override
					public void handleEvent(ProgressEvent progressEvent) 
					{
//						GWT.debugger();
						if (progressEvent.getLengthComputable()) 
						{
							gxtProgressBar.updateProgress(progressEvent.getLoaded()/progressEvent.getTotal(), "{0}%");
							GWT.log("Total: " + progressEvent.getTotal() + "   Loaded: " + progressEvent.getLoaded() + "   " + progressEvent.getLoaded()/progressEvent.getTotal()*100);
						}
					}
				});
//				builder.setHeader("Content-Type", "multipart/form-data; charset=utf-8");
				try 
				{
					Blob blob = gxtFileUpload.getBlob();
					if(blob == null) 
					{
						Window.alert("Select file first");
						return;
					}
					RequestCallback requestCallback = new RequestCallback()
					{
						@Override
						public void onResponseReceived(Request request, Response response) {
							Window.alert(response.getText());

						}
						
						@Override
						public void onError(Request request, Throwable exception) 
						{
							Window.alert(exception.getMessage());
						}
					};
//					builder.send(blob, requestCallback);
					FormData formData = new FormData();
//					formData.append("file-name", blob.getName()); //èe pri spodnjem dodamo getName, potem moramo mna serverju prebrati filename from parf - glej TestFormDataUpload
					formData.append("file", blob, blob.getName());
					builder.send(formData, requestCallback);
					
				}
				catch (RequestException e) 
				{
					Window.alert(e.getMessage());
				}
			}
		});
	}
}
