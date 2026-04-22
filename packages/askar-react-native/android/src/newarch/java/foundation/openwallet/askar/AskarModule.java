package foundation.openwallet.askar;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.turbomodule.core.CallInvokerHolderImpl;

/**
 * New Architecture implementation of the Askar TurboModule.
 *
 * Extends the Codegen-generated `NativeAskarSpec` abstract class (produced
 * from `src/specs/NativeAskar.ts` during the RN Gradle Codegen step), which
 * itself extends `ReactContextBaseJavaModule`.
 *
 * Unlike the legacy module, this implementation intentionally avoids
 * `ReactContext#getCatalystInstance()` (which is unavailable under
 * Bridgeless Mode) and uses the public, Bridgeless-safe accessors on
 * `ReactContext` directly.
 */
@Keep
@DoNotStrip
public class AskarModule extends NativeAskarSpec {
    static {
        System.loadLibrary("askarreactnative");
    }

    public static final String NAME = "Askar";
    private static final String TAG = NAME;

    public AskarModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    private static native void installNative(
            long jsiRuntimePointer,
            CallInvokerHolderImpl jsCallInvokerHolder);

    @Override
    public boolean install() {
        try {
            ReactContext context = getReactApplicationContext();

            JavaScriptContextHolder runtimeHolder = context.getJavaScriptContextHolder();
            if (runtimeHolder == null) {
                Log.e(TAG, "JavaScriptContextHolder is null — cannot install JSI bindings.");
                return false;
            }

            // Bridgeless-safe: available on ReactApplicationContext directly.
            // Do NOT route through `context.getCatalystInstance()`, which is
            // removed in Bridgeless Mode.
            CallInvokerHolderImpl holder =
                    (CallInvokerHolderImpl) context.getJSCallInvokerHolder();
            if (holder == null) {
                Log.e(TAG, "JSCallInvokerHolder is null — cannot install JSI bindings.");
                return false;
            }

            installNative(runtimeHolder.get(), holder);
            return true;
        } catch (Exception exception) {
            Log.e(TAG, "Failed to install JSI Bindings!", exception);
            return false;
        }
    }
}
