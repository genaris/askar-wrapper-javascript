#ifdef RCT_NEW_ARCH_ENABLED
#if __has_include(<RNAskarSpec/RNAskarSpec.h>)
#import <RNAskarSpec/RNAskarSpec.h>
#elif __has_include(<React-Codegen/RNAskarSpec.h>)
#import <React-Codegen/RNAskarSpec.h>
#endif

@interface Askar : NSObject <NativeAskarSpec>
@end
#else
#import <React/RCTBridgeModule.h>

@interface Askar : NSObject <RCTBridgeModule>
@end
#endif
