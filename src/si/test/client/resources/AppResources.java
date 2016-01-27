package si.test.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface AppResources extends ClientBundle 
{
	interface Style extends CssResource
	{
		
	}

	@Source("css/style.gss")
	public Style css();
}
