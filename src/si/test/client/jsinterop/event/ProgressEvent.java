package si.test.client.jsinterop.event;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative=true, namespace=JsPackage.GLOBAL)
public interface ProgressEvent {
	
	@JsProperty public boolean getLengthComputable();
	@JsProperty public double getLoaded();
	@JsProperty public double getTotal();
}
