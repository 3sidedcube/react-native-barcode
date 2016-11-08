package com.rnbarcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ReactNativeBarcodeModule extends ReactContextBaseJavaModule
{
	private final ReactApplicationContext reactContext;

	public ReactNativeBarcodeModule(ReactApplicationContext reactContext)
	{
		super(reactContext);
		this.reactContext = reactContext;
	}

	@Override
	public String getName()
	{
		return "RNBarcode";
	}

	@ReactMethod
	public void generateQrCode(String content, int size, final Promise promise)
	{
		QRCodeWriter writer = new QRCodeWriter();
		try
		{
			BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < height; y++)
				{
					bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
				}
			}

			File outputFile = new File(getReactApplicationContext().getFilesDir().getAbsolutePath(), "qr_code.png");
			OutputStream out = null;

			try
			{
				out = new FileOutputStream(outputFile);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
			}
			finally
			{
				if (out != null)
				{
					out.close();
				}
			}

			promise.resolve(outputFile.getAbsolutePath());
		}
		catch (WriterException | IOException e)
		{
			promise.reject(e);
		}
	}
}
