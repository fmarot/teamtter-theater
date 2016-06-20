package com.teamtter.theatre.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RemoteControlView extends JPanel {
	
	private static Font font;
	
	static {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, RemoteControlView.class.getResourceAsStream("/fonts/Symbola.ttf"));
			ge.registerFont(font);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
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
			Font defaultFont = getFont();
			Float s = defaultFont.getSize2D();
			s += 9.0f;
//			this.setFont(font.deriveFont(s));
			this.setFont(font.deriveFont(s));
		}
	}

}
