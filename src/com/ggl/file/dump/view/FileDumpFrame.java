package com.ggl.file.dump.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ggl.file.dump.controller.GetFileListener;
import com.ggl.file.dump.model.FileDumpModel;

public class FileDumpFrame {
	
	private FileDumpModel model;
	
	private JFrame frame;
	
	private JProgressBar progressBar;
	
	private JTextArea textArea;
	
	private JTextField fileNameField;
	
	public FileDumpFrame(FileDumpModel model) {
		this.model = model;
		createMainFrame();
	}
	
	private void createMainFrame() {
		int fontSize = 12;
		
		frame = new JFrame("File Dump");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(createFileNamePanel(fontSize), 
				BorderLayout.BEFORE_FIRST_LINE);
		frame.add(createTextPanel(fontSize), 
				BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationByPlatform(true);
//		System.out.println(frame.getSize());
		frame.setVisible(true);
	}
	
	private JPanel createFileNamePanel(int fontSize) {
		JPanel panel = new JPanel(new FlowLayout());
		Font font = panel.getFont().deriveFont(
				(float) fontSize);
		
		JLabel label = new JLabel("File Name: ");
		label.setFont(font);
		panel.add(label);
		
		fileNameField = new JTextField(50);
		fileNameField.setEditable(false);
		fileNameField.setFont(font);
		panel.add(fileNameField);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setFont(font);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		
		JButton button = new JButton("Get File");
		button.addActionListener(new GetFileListener(
				this, model));
		button.setFont(font);
		panel.add(button);
		
		return panel;
	}
	
	private JPanel createTextPanel(int fontSize) {
		JPanel panel = new JPanel(new BorderLayout());
		
		textArea = new JTextArea(30, 120);
		textArea.setEditable(false);
		textArea.setFont(new Font("Courier New", Font.BOLD, 
				fontSize));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		return panel;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setProgressBar(int value) {
		progressBar.setValue(value);
	}

	public void setFileNameText(String fileName) {
		fileNameField.setText(fileName);
	}
	
	public void clearTextArea() {
		textArea.setText("");
	}
	
	public void setTextArea(String text) {
		textArea.setText(text);
	}
	
	public void setCaretPosition() {
		textArea.setCaretPosition(0);
	}

}
