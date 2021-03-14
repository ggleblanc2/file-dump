package com.ggl.file.dump.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import com.ggl.file.dump.model.FileDumpModel;
import com.ggl.file.dump.view.FileDumpFrame;

public class GetFileListener implements ActionListener {
	
	private FileDumpFrame frame;
	
	private FileDumpModel model;

	public GetFileListener(FileDumpFrame frame, FileDumpModel model) {
		this.frame = frame;
		this.model = model;
	}

	/**
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JFileChooser fc = new JFileChooser(model.getInputFile());
		int returnVal = fc.showOpenDialog(frame.getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			model.setInputFile(fc.getSelectedFile());
			String path = model.getInputFile().getAbsolutePath();
			frame.setFileNameText(path);
			new Thread(new ProcessFileRunnable(frame, model)).start();
		}
	}

}
