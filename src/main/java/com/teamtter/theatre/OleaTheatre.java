package com.teamtter.theatre;

import static uk.co.caprica.vlcj.player.Logo.logo;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

@Slf4j
public class OleaTheatre {

	public static void main(String[] args) throws Exception {
		// Get config from file & cmdLine in beans *Config
		AppConfig cfg = new AppConfig(args, OleaTheatre.class.getSimpleName());
		MediaToStartWithConfig mediaConfig = cfg.getConfigBean(MediaToStartWithConfig.class);
		GeneralConfig generalConfig = cfg.getConfigBean(GeneralConfig.class);

		boolean vlcLibFound = new NativeDiscovery().discover();
		if (vlcLibFound) {
			System.out.println(LibVlc.INSTANCE.libvlc_get_version());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new OleaTheatre(generalConfig, mediaConfig);
				}
			});
		} else {
			log.error("LibVLC not found. Exiting...");
		}
	}

	private JFrame frame;

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private RemoteControlController remoteControlController;

	public OleaTheatre(GeneralConfig generalConfig, MediaToStartWithConfig mediaConfig) {
		initGui();
		startPlaying(generalConfig, mediaConfig);
		displayLogo(generalConfig);
	}

	private void displayLogo(GeneralConfig generalConfig) {
		String logoPath = generalConfig.getLogoFile().getAbsolutePath();
		logo().file(logoPath).location(0, 0).position(libvlc_logo_position_e.top_left).opacity(255).enable(true)
				.apply(mediaPlayerComponent.getMediaPlayer());
	}

	private void startPlaying(GeneralConfig generalConfig, MediaToStartWithConfig mediaConfig) {
		File fileToPlay = mediaConfig.getFileToPlay();
		if (fileToPlay.exists()) {
			mediaPlayerComponent.getMediaPlayer().playMedia(fileToPlay.getAbsolutePath());
		} else { // default to DVD if no file is specified or does not exist
			String dvdMediaResourceLocation = new DvdMrl()
					.device(generalConfig.getDvdPath())
					.title(mediaConfig.getStartTitle())
					.chapter(mediaConfig.getStartChapter())
					.value();
			mediaPlayerComponent.getMediaPlayer().playMedia(dvdMediaResourceLocation, new String[] {});
		}
	}

	private void initGui() {
		frame = new JFrame("Olea Theatre");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		remoteControlController = new RemoteControlController(mediaPlayerComponent);
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
