package com.rnbarcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

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
	private static final String QR_CODE_PREFIX = "qr_code_";
	private static final long OLD_QR_CODE_CUTOFF = 1000 * 60 * 60 * 24;

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
			File outputFile = new File(getReactApplicationContext().getFilesDir().getAbsolutePath(), QR_CODE_PREFIX + Integer.toHexString(content.hashCode()) + ".png");

			if (outputFile.exists())
			{
				promise.resolve(outputFile.getAbsolutePath());
				return;
			}

			// Delete old qr codes
			for (File file: getReactApplicationContext().getFilesDir().listFiles())
			{
				if (file.getName().startsWith(QR_CODE_PREFIX) && file.getName().endsWith(".png") && (System.currentTimeMillis() - file.lastModified()) > OLD_QR_CODE_CUTOFF)
				{
					file.delete();
				}
			}

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
