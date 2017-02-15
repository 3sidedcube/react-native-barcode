#import <UIKit/UIKit.h>

#if __has_include(<React/RCTView.h>)
#import <React/RCTView.h>
#elif __has_include("RCTView.h")
#import "RCTView.h"
#elif __has_include("React/RCTView.h")
#import "React/RCTView.h"   // Required when used as a Pod in a Swift project
#endif

@interface LFQRCode : RCTView

@property (nonatomic, copy) NSString *content;

@end
