package com.teamtter.theatre.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RemoteControlView extends JPanel {
	
	private final JButton pauseButton;

	private final JButton rewindButton;

	private final JButton skipButton;
	private final JButton chooseFileButton;
	private final JButton dvdButton;

	private final RemoteControlController controller;

	public RemoteControlView(final RemoteControlController remoteControlController) {
		this.controller = remoteControlController;
		pauseButton = new JButton("Pause");
		add(pauseButton);
		rewindButton = new JButton("Rewind");
		add(rewindButton);
		skipButton = new JButton("Skip");
		add(skipButton);
		chooseFileButton = new JButton("choose File");
		add(chooseFileButton);
		dvdButton = new JButton("dvd");
		add(dvdButton);

		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.pause();
			}
		});

		rewindButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.rewind();
			}
		});

		skipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.skip();
			}
		});
		
		chooseFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.playFile();
			}
		});
		
		dvdButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.playDvd();
			}
		});
	}

}
