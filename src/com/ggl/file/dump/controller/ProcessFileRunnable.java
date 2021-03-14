package com.ggl.file.dump.controller;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.SwingUtilities;

import com.ggl.file.dump.model.FileDumpModel;
import com.ggl.file.dump.view.FileDumpFrame;

public class ProcessFileRunnable implements Runnable {
	
	private static final boolean DEBUG = false;
	
	private FileDumpFrame frame;
	
	private FileDumpModel model;

	public ProcessFileRunnable(FileDumpFrame frame, FileDumpModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void run() {
		try {
			processFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processFile() throws IOException {
		StringBuilder output = new StringBuilder();
		clearTextArea();
		
		char[] cbuf = new char[4096];
		long fileSize = model.getInputFile().length();
		int lineLength = 16;
		int address = 0;
		String line = "";
		boolean oneTimeFlag = true;
		
		FileReader reader = new FileReader(model.getInputFile());
		int length = reader.read(cbuf, 0, cbuf.length);
		
		while (length > 0) {
			StringBuilder builder = new StringBuilder();
			if (DEBUG && (length < cbuf.length)) {
				System.out.println(address + " " + cbuf.length + " " + length);
			}
			
			for (int start = lineLength; start < length; start += lineLength) {
				line = createLine(address, cbuf, start, lineLength);
				address += lineLength;
				
				if (DEBUG && oneTimeFlag) {
					System.out.println("Line length: " + line.length());
					oneTimeFlag =  false;
				}
				
				builder.append(line);
			}
			
			int value = (int) (100L * address / fileSize);
			setProgressBar(value);
			
			output.append(builder.toString());
			length = reader.read(cbuf, 0, cbuf.length);
		}
		
		setTextArea(output.toString());
		setCaretPosition();
		setProgressBar(100);
		
		reader.close();
		
//		setProgressBar(0);
	}
	
	private String createLine(int address, char[] cbuf, int start, int length) {
		StringBuilder output = new StringBuilder();
		
		String addressString = String.format("%08X", address);
		output.append(addressString).append("   ");
		
		StringBuilder hex = new StringBuilder();
		StringBuilder chars = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i > 0) {
				hex.append(" ");
			}
			
			if (start + i < cbuf.length) {
				int value = cbuf[start + i];
				hex.append(String.format("%04X", value));
				if ((value < 0x20) || (value > 0x7FF)) {
					chars.append(".");
				} else {
					chars.append(cbuf[start + i]);
				}
			} else {
				hex.append("0000");
				chars.append(".");
			}
		}
		
		output.append(hex).append("   ").append(chars);
		return output.append(System.lineSeparator()).toString();
	}
	
	private void setProgressBar(int value) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setProgressBar(value);
			}
		});
	}
	
	private void clearTextArea() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setProgressBar(0);
				frame.clearTextArea();
			}
		});
	}
	
	private void setTextArea(String text) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setTextArea(text);
			}
		});
	}
	
	private void setCaretPosition() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setCaretPosition();
			}
		});
	}

}
