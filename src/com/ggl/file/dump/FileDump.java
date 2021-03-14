package com.ggl.file.dump;

import javax.swing.SwingUtilities;

import com.ggl.file.dump.model.FileDumpModel;
import com.ggl.file.dump.view.FileDumpFrame;

public class FileDump implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new FileDump());
	}

	@Override
	public void run() {
		new FileDumpFrame(new FileDumpModel());
	}

}
