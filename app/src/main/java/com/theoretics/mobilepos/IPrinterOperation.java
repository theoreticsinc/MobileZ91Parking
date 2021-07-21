package com.theoretics.mobilepos;

import android.content.Intent;

import com.android.print.sdk.PrinterInstance;

public interface IPrinterOperation {
	public void open(Intent data);
	public void close();
	public void chooseDevice();
	public PrinterInstance getPrinter();
}
