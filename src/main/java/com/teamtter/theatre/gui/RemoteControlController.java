package com.teamtter.theatre.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.teamtter.theatre.options.GeneralConfig;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.mrl.DvdMrl;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@Slf4j
public class RemoteControlController extends MouseAdapter {

	@Getter
	private RemoteControlView view;

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;

	final JFileChooser fileChooser = new JFileChooser();

	private JFrame frame;

	private GeneralConfig generalConfig;

	public RemoteControlController(EmbeddedMediaPlayerComponent mediaPlayerComponent, JFrame frame, GeneralConfig generalConfig) {
		this.mediaPlayerComponent = mediaPlayerComponent;
		this.frame = frame;
		this.generalConfig = generalConfig;
		view = new RemoteControlView(this);
	}

	public void playDvd() {
		playDvd(0, 0);
	}

	public void playDvd(int title, int chapter) {
		String dvdMediaResourceLocation = new DvdMrl()
				.device(generalConfig.getDvdPath())
				.title(title)
				.chapter(chapter)
				.value();
		mediaPlayerComponent.getMediaPlayer().playMedia(dvdMediaResourceLocation, new String[] {});
	}

	public void playFile() {
		int fileChooserResult = fileChooser.showOpenDialog(frame);
		if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			playFile(file);
		}
	}

	public void pause() {
		mediaPlayerComponent.getMediaPlayer().pause();
	}

	public void rewind() {
//		mediaPlayerComponent.getMediaPlayer().skip(-10000);
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		int chapter = mediaPlayer.getChapter();
		int title = mediaPlayer.getTitle();
		log.info("title {} - chapter {}", chapter, title);
		mediaPlayer.previousChapter();
	}

	public void skip() {
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		int chapter = mediaPlayer.getChapter();
		int title = mediaPlayer.getTitle();
		log.info("title {} - chapter {}", chapter, title);
		
		List<List<String>> allChapterDescriptions = mediaPlayer.getAllChapterDescriptions();
		mediaPlayer.nextChapter();
		
		int newChapter = chapter;
		if (newChapter == chapter) {
			int titleCount = mediaPlayer.getTitleCount();
			int newTitle = title + 1;
			if (newTitle >= titleCount) {
				newTitle = 0;
			}
			mediaPlayer.setTitle(title);
		}
//		mediaPlayerComponent.getMediaPlayer().skip(10000);
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

	public void playFile(File file) {
		mediaPlayerComponent.getMediaPlayer().playMedia(file.getAbsolutePath());
	}

}
