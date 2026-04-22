#include "AskarCxxTurboModule.h"

#ifdef RCT_NEW_ARCH_ENABLED

#include "turboModuleUtility.h"

namespace facebook::react {

AskarCxxTurboModule::AskarCxxTurboModule(const ObjCTurboModule::InitParams &params)
    : NativeAskarSpecJSI(params) {}

jsi::Value AskarCxxTurboModule::get(
    jsi::Runtime &runtime,
    const jsi::PropNameID &propName) {
  const auto name = propName.utf8(runtime);

  if (name == "install") {
    // Return a host function so the bound `jsi::Runtime &rt` on invocation
    // is the real JS runtime — the piece we were previously trying (and
    // failing, under Bridgeless) to pull from `RCTCxxBridge`.
    return jsi::Function::createFromHostFunction(
        runtime,
        propName,
        /* paramCount */ 0,
        [this](
            jsi::Runtime &rt,
            const jsi::Value &,
            const jsi::Value *,
            size_t) -> jsi::Value {
          askarTurboModuleUtility::registerTurboModule(rt, jsInvoker_);
          return jsi::Value(true);
        });
  }

  return NativeAskarSpecJSI::get(runtime, propName);
}

} // namespace facebook::react

#endif // RCT_NEW_ARCH_ENABLED
