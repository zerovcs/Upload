package si.test.client.jsinterop;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative=true, namespace=JsPackage.GLOBAL, name="Blob")
public interface Blob {

	@JsProperty public String getName();
}
