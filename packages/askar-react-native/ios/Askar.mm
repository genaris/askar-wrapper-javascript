#import "Askar.h"

#import <jsi/jsi.h>
#import <ReactCommon/RCTTurboModule.h>
#import <askar/turboModuleUtility.h>

#ifdef RCT_NEW_ARCH_ENABLED
#import "AskarCxxTurboModule.h"
#else
#import <React/RCTBridge+Private.h>
#import <React/RCTUtils.h>
#endif

using namespace facebook;

@implementation Askar

RCT_EXPORT_MODULE()

#ifdef RCT_NEW_ARCH_ENABLED

// New Architecture / Bridgeless-safe path.
// The Codegen-generated `NativeAskarSpecJSI` forwards JS calls to this class.
// We override `getTurboModule:` so that the runtime-accessing `install`
// method is handled by our C++ subclass, which receives the `jsi::Runtime&`
// directly — no `[RCTBridge currentBridge]` / `RCTCxxBridge` required.
- (std::shared_ptr<facebook::react::TurboModule>)
    getTurboModule:(const facebook::react::ObjCTurboModule::InitParams &)params
{
  return std::make_shared<facebook::react::AskarCxxTurboModule>(params);
}

// Still needs to be declared to satisfy the NativeAskarSpec protocol, but in
// practice it is never invoked because `AskarCxxTurboModule::get` intercepts
// `install` before it is dispatched to Objective-C.
- (NSNumber *)install
{
  return @NO;
}

#else

// Old Architecture path — unchanged behaviour, kept for RN < 0.74 /
// `newArchEnabled=false` consumers.
RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(install)
{
  RCTBridge *bridge = [RCTBridge currentBridge];
  RCTCxxBridge *cxxBridge = (RCTCxxBridge *)bridge;
  if (cxxBridge == nil) {
    return @false;
  }

  jsi::Runtime *jsiRuntime = (jsi::Runtime *)cxxBridge.runtime;
  if (jsiRuntime == nil) {
    return @false;
  }

  auto callInvoker = bridge.jsCallInvoker;
  askarTurboModuleUtility::registerTurboModule(*jsiRuntime, callInvoker);
  return @true;
}

#endif

@end
