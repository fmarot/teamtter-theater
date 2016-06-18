package com.teamtter.theatre.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RemoteControlView extends JPanel {
	
	private final JButton pauseButton;

	private final JButton rewindTitleButton;
	private final JButton rewindChapterButton;

	private final JButton skipChapterButton;
	private final JButton skipTitleButton;
	private final JButton chooseFileButton;
	private final JButton dvdButton;

	private final RemoteControlController controller;

	public RemoteControlView(final RemoteControlController remoteControlController) {
		this.controller = remoteControlController;
		
		rewindTitleButton = new RemoteControlButton("⏮ ");
		rewindTitleButton.addActionListener(ae -> controller.rewindTitle());
		add(rewindTitleButton);
		
		rewindChapterButton = new RemoteControlButton("⏪");
		rewindChapterButton.addActionListener(ae -> controller.rewindChapter());
		add(rewindChapterButton);
		
		pauseButton = new RemoteControlButton("⏸");
		pauseButton.addActionListener(ae -> controller.pause() );
		add(pauseButton);
		
		skipChapterButton = new RemoteControlButton("⏩");
		skipChapterButton.addActionListener(ae -> controller.skipChapter());
		add(skipChapterButton);
		
		skipTitleButton = new RemoteControlButton("⏭ ");
		skipTitleButton.addActionListener(ae -> controller.skipTitle());
		add(skipTitleButton);
		
		chooseFileButton = new RemoteControlButton("📂");
		chooseFileButton.addActionListener(ae -> controller.playFile());
		add(chooseFileButton);
		
		dvdButton = new RemoteControlButton("📀");
		dvdButton.addActionListener(ae -> controller.playDvd());
		add(dvdButton);
	}
	
	/** Simply sets bigger font than default buttons */
	class RemoteControlButton extends JButton {
		public RemoteControlButton(String text) {
			super(text);
			Font font = getFont();
			Float s = font.getSize2D();
			s += 9.0f;
			this.setFont(font.deriveFont(s));
		}
	}

}
