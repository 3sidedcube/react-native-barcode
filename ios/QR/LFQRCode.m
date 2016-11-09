#import "LFQRCode.h"
#import "RCTConvert.h"
#import <UIKit/UIKit.h>
#import <CoreImage/CoreImage.h>
#import <CoreGraphics/CoreGraphics.h>

@interface LFQRCode ()

@property (nonatomic, strong) UIImageView *imageView;

@property (nonatomic, strong) CIImage *qrImage;

@end

@implementation LFQRCode

- (instancetype)init
{
    if (self = [super init]) {
        
        self.imageView = [UIImageView new];
        [self addSubview:self.imageView];
    }
    
    return self;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        
        self.imageView = [UIImageView new];
        [self addSubview:self.imageView];
    }
    
    return self;
}

- (void)setContent:(NSString *)content
{
    NSData *data = [content dataUsingEncoding:NSISOLatin1StringEncoding allowLossyConversion:false];
    
    CIFilter *filter = [CIFilter filterWithName:@"CIQRCodeGenerator"];
    [filter setValue:data forKey:@"inputMessage"];
    
    self.qrImage = [filter outputImage];
}

- (void)setQrImage:(CIImage *)qrImage
{
    _qrImage = qrImage;
    [self updateImage];
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    [self updateImage];
}

- (void)updateImage
{
    double scaleX = self.bounds.size.width / self.qrImage.extent.size.width;
    double scaleY = self.bounds.size.height / self.qrImage.extent.size.height;
    
    CIImage *transformedImage = [self.qrImage imageByApplyingTransform:CGAffineTransformMakeScale(scaleX, scaleY)];
    
    self.imageView.frame = self.bounds;
    self.imageView.image = [UIImage imageWithCIImage:transformedImage];
}

@end
