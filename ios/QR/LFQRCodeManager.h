#if __has_include(<React/RCTViewManager.h>)
#import <React/RCTViewManager.h>
#elif __has_include("RCTViewManager.h")
#import "RCTViewManager.h"
#elif __has_include("React/RCTViewManager.h")
#import "React/RCTViewManager.h"   // Required when used as a Pod in a Swift project
#endif

@interface LFQRCodeManager : RCTViewManager

@end
