#pragma once

#ifdef RCT_NEW_ARCH_ENABLED

#include <ReactCommon/TurboModule.h>
#include <jsi/jsi.h>

// Codegen-generated spec — produced by the RN Codegen step from the TS spec
// at `src/specs/NativeAskar.ts`.
#if __has_include(<RNAskarSpec/RNAskarSpec.h>)
#import <RNAskarSpec/RNAskarSpec.h>
#elif __has_include(<React-Codegen/RNAskarSpec.h>)
#import <React-Codegen/RNAskarSpec.h>
#endif

namespace facebook::react {

/**
 * Bridgeless-safe C++ TurboModule backing `NativeAskarSpec`.
 *
 * The standard Codegen pipeline dispatches `install()` to the platform
 * module (Objective-C / Java), which historically relied on
 * `[RCTBridge currentBridge]`/`RCTCxxBridge` to obtain the `jsi::Runtime*`.
 * Those APIs are absent in Bridgeless Mode.
 *
 * Instead, we subclass the generated `NativeAskarSpecJSI` and override
 * `get()` to intercept the `install` property. When JS calls the function we
 * return, we already have the live `jsi::Runtime&` as an argument, so we can
 * install the Askar HostObject directly with no bridge lookup.
 */
class JSI_EXPORT AskarCxxTurboModule : public NativeAskarSpecJSI {
 public:
  explicit AskarCxxTurboModule(const ObjCTurboModule::InitParams &params);

  facebook::jsi::Value get(
      facebook::jsi::Runtime &runtime,
      const facebook::jsi::PropNameID &propName) override;
};

} // namespace facebook::react

#endif // RCT_NEW_ARCH_ENABLED
