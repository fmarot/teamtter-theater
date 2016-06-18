package com.teamtter.theatre;

import static uk.co.caprica.vlcj.player.Logo.logo;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.google.common.io.Resources;
import com.teamtter.options.AppConfig;
import com.teamtter.theatre.gui.RemoteControlController;
import com.teamtter.theatre.options.GeneralConfig;
import com.teamtter.theatre.options.MediaToStartWithConfig;

import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_logo_position_e;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.mrl.DvdMrl;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@Slf4j
public class OleaTheatre {

	public static void main(String[] args) throws Exception {
		// Get config from file & cmdLine in beans *Config
		AppConfig cfg = new AppConfig(args, OleaTheatre.class.getSimpleName());
		MediaToStartWithConfig mediaConfig = cfg.getConfigBean(MediaToStartWithConfig.class);
		GeneralConfig generalConfig = cfg.getConfigBean(GeneralConfig.class);

		boolean vlcLibFound = new NativeDiscovery().discover();
		if (vlcLibFound) {
			log.info("LibVlc version : {}", LibVlc.INSTANCE.libvlc_get_version());
			SwingUtilities.invokeLater(() -> new OleaTheatre(generalConfig, mediaConfig));
		} else {
			log.error("LibVLC not found. Exiting...");
		}
	}

	private JFrame frame;

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private RemoteControlController remoteControlController;

	public OleaTheatre(GeneralConfig generalConfig, MediaToStartWithConfig mediaConfig) {
		initGui(generalConfig);
		configure();
		startPlaying(generalConfig, mediaConfig);
		displayLogo();
	}

	private void configure() {
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		// mediaPlayer.addMediaPlayerEventListener(new TheatreListener()); // for debugging purpose

		// Configure fullscreen toggle feature
		mediaPlayer.setEnableMouseInputHandling(false); // must be called for M$ Windows
		mediaPlayer.setEnableKeyInputHandling(false); // must be called for M$ Windows
		mediaPlayer.setFullScreenStrategy(new TheatreFullScreenStrategy(frame));
		Canvas videoSurface = mediaPlayerComponent.getVideoSurface();
		videoSurface.addMouseListener(remoteControlController);
	}

	private void displayLogo() {
		try {
			// extract logo to a real filesystem file
			Path tmpPath = Files.createTempFile("logoOlea", ".png");
			File tmpLogoFile = tmpPath.toFile();
			URL logoInJar = getClass().getResource("/logoOlea.png");
			Resources.asByteSource(logoInJar).copyTo(com.google.common.io.Files.asByteSink(tmpLogoFile));
			tmpLogoFile.deleteOnExit();
			// display the logo after one second (otherwise it is not displayed)
			new Thread(
					() -> {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						logo().file(tmpLogoFile.getAbsolutePath())
								.location(0, 0)
								.position(libvlc_logo_position_e.top_left)
								.opacity(255)
								.enable(true)
								.apply(mediaPlayerComponent.getMediaPlayer());
					}).run();
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private void startPlaying(GeneralConfig generalConfig, MediaToStartWithConfig mediaConfig) {
		File fileToPlay = mediaConfig.getFileToPlay();
		if (fileToPlay.exists()) {
			remoteControlController.playFile(fileToPlay);
		} else { // default to DVD if no file is specified or does not exist
			remoteControlController.playDvd(mediaConfig.getStartTitle(), mediaConfig.getStartChapter());
		}
	}

	private void initGui(GeneralConfig generalConfig) {
		frame = new JFrame("Olea Theatre");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		remoteControlController = new RemoteControlController(mediaPlayerComponent, frame, generalConfig);
		frame.add(mediaPlayerComponent, BorderLayout.CENTER);
		frame.add(remoteControlController.getView(), BorderLayout.SOUTH);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
