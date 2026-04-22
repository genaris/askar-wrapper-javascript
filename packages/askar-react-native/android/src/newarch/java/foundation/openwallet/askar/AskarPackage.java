package foundation.openwallet.askar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.BaseReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * New Architecture package.
 *
 * Extends {@code BaseReactPackage} so that the TurboModuleManager can create
 * the module directly by name without reflection and without requiring the
 * Interop Layer.
 */
public class AskarPackage extends BaseReactPackage {
    @Nullable
    @Override
    public NativeModule getModule(@NonNull String name, @NonNull ReactApplicationContext reactContext) {
        if (AskarModule.NAME.equals(name)) {
            return new AskarModule(reactContext);
        }
        return null;
    }

    @NonNull
    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return () -> {
            Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
            moduleInfos.put(
                    AskarModule.NAME,
                    new ReactModuleInfo(
                            AskarModule.NAME,
                            AskarModule.class.getName(),
                            /* canOverrideExistingModule */ false,
                            /* needsEagerInit */ false,
                            /* isCxxModule */ false,
                            /* isTurboModule */ true
                    )
            );
            return moduleInfos;
        };
    }
}
