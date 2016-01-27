package si.test.client.jsinterop;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative=true, namespace=JsPackage.GLOBAL)
public class FormData 
{
	//verjetno bi bilo bolje, èe bi namesto Blob dat JsObject, vendar to ni sedaj main focus
	public native void append(String name, Blob value, String filename);
	public native void append(String name, Blob value);
	public native void append(String name, String value);
	
	//morda še ostale metode  - kasneje pouporabi Elemental 2.0 namesto tega class
}
