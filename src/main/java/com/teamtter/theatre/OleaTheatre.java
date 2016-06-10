package com.teamtter.theatre;

import static uk.co.caprica.vlcj.player.Logo.logo;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_logo_position_e;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.mrl.DvdMrl;

@Slf4j
public class OleaTheatre {

	public static void main(String[] args) {
		boolean found = new NativeDiscovery().discover();
		if (found) {
			System.out.println(LibVlc.INSTANCE.libvlc_get_version());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new OleaTheatre();
				}
			});
		} else {
			log.error("LibVLC not found. Exiting...");
		}
	}

	private JFrame frame;

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private RemoteControlController remoteControlController;

	private AppConfig appConfig;

	public OleaTheatre() {
		appConfig = new AppConfig();
		initGui();
		startPlaying();
		displayLogo();
	}

	private void displayLogo() {
		String logoPath = "/media/vg1-data/dev/sources/olea-theatre/src/main/resources/logoOlea.png";
		logo().file(logoPath).location(0, 0).position(libvlc_logo_position_e.top_left).opacity(255).enable(true)
				.apply(mediaPlayerComponent.getMediaPlayer());
	}

	private void startPlaying() {
		File fileToPlay = new File(appConfig.get(AppConfig.FILE_TO_PLAY));
		if (fileToPlay.exists()) {
			mediaPlayerComponent.getMediaPlayer().playMedia(fileToPlay.getAbsolutePath());
		} else {	// default to DVD if no file is specified or does not exist
			String dvdDrivePath = appConfig.get(AppConfig.DVD_PATH);
			int title = Integer.parseInt(appConfig.get(AppConfig.DVD_TITLE));
			int chapter = Integer.parseInt(appConfig.get(AppConfig.DVD_CHAPTER));
			String dvdMediaResourceLocation = new DvdMrl().device(dvdDrivePath).title(title).chapter(chapter).value();
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
