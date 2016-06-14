package com.teamtter.theatre;

import java.awt.Window;

import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;

public class TheatreFullScreenStrategy extends DefaultAdaptiveRuntimeFullScreenStrategy {

	public TheatreFullScreenStrategy(Window window) {
		super(window);
	}

	@Override
	protected void beforeEnterFullScreen() {
//		controlsPane.setVisible(false);
//		statusBar.setVisible(false);
	}

	@Override
	protected void afterExitFullScreen() {
//		controlsPane.setVisible(true);
//		statusBar.setVisible(true);
	}

}
