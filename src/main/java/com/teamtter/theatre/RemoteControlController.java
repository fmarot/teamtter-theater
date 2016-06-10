package com.teamtter.theatre;

import lombok.Getter;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class RemoteControlController {

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

}
