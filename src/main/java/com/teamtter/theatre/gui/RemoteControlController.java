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
		log.info("dvdMediaResourceLocation = {}", dvdMediaResourceLocation);
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

	public void rewindTitle() {
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		int currentTitle = mediaPlayer.getTitle();
		int newTitle = currentTitle - 1;
		if (newTitle <= 0) {
			newTitle = 0;
		}
		log.warn("Will set next title: {}", newTitle);
		mediaPlayer.setTitle(newTitle);
	}

	public void rewindChapter() {
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		int currentChapter = mediaPlayer.getChapter();
		int currentTitle = mediaPlayer.getTitle();

		List<List<String>> allChaptersPerTitle = mediaPlayer.getAllChapterDescriptions();
		int nbChapterInTitle = allChaptersPerTitle.get(currentTitle).size();
		log.info("currentTitle {} - currentChapter {} / {}", currentTitle, currentChapter, nbChapterInTitle);
		// (last chaper seems to always mean 'end of title')
		if (currentChapter <= 0) {
			rewindTitle();
		} else {
			log.info("Will set next chapter: {}", currentChapter-1);
			mediaPlayer.previousChapter();
		}
	}

	public void skipChapter() {
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		int currentChapter = mediaPlayer.getChapter();
		int currentTitle = mediaPlayer.getTitle();

		List<List<String>> allChaptersPerTitle = mediaPlayer.getAllChapterDescriptions();
		int nbChapterInTitle = allChaptersPerTitle.get(currentTitle).size();
		log.info("currentTitle {} - currentChapter {} / {}", currentTitle, currentChapter, nbChapterInTitle);
		// (last chaper seems to always mean 'end of title')
		if (currentChapter >= nbChapterInTitle - 1) {
			skipTitle();
		} else {
			log.info("Will set next chapter: {}", currentChapter+1);
			mediaPlayer.nextChapter();
		}
		// mediaPlayerComponent.getMediaPlayer().skip(10000);
	}


	public void skipTitle() {
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		int currentTitle = mediaPlayer.getTitle();
		List<List<String>> allChaptersPerTitle = mediaPlayer.getAllChapterDescriptions();

		int newTitle = currentTitle + 1;
		if (newTitle >= allChaptersPerTitle.size()) {
			newTitle = 0;
		}
		log.warn("Will set next title: {}", newTitle);
		mediaPlayer.setTitle(newTitle);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
