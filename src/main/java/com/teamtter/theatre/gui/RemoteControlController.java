package com.teamtter.theatre.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@Slf4j
public class RemoteControlController implements MouseListener {

	@Getter
	private RemoteControlView view;
	
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;

	public RemoteControlController(EmbeddedMediaPlayerComponent mediaPlayerComponent) {
		this.mediaPlayerComponent = mediaPlayerComponent;
		view = new RemoteControlView(this);
	}

	public void pause() {
		mediaPlayerComponent.getMediaPlayer().pause();
	}

	public void rewind() {
		mediaPlayerComponent.getMediaPlayer().skip(-10000);
	}

	public void skip() {
		mediaPlayerComponent.getMediaPlayer().skip(10000);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		log.info("mouseClicked...");
		if (e.getClickCount() == 2) {
			EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
			boolean isFullScreen = mediaPlayer.isFullScreen();
			mediaPlayer.setFullScreen(!isFullScreen);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		log.info("mouseEntered...");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		log.info("mouseExited...");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		log.info("mousePressed...");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		log.info("mouseReleased...");		
	}

}
