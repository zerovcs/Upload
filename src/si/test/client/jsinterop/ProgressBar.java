package si.test.client.jsinterop;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative=true, namespace=JsPackage.GLOBAL, name="Progress")
public interface ProgressBar {
	@JsProperty public void setValue(double value);
    @JsProperty public void setMax(double max);
}
