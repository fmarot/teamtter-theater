//package com.teamtter.theatre;
//
//import lombok.extern.slf4j.Slf4j;
//import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
//import uk.co.caprica.vlcj.player.MediaPlayer;
//import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
//
//@Slf4j
//public class TheatreListener implements MediaPlayerEventListener {
//
//	@Override
//	public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t media, String mrl) {
//		log.info("mediaChanged...");
//	}
//
//	@Override
//	public void opening(MediaPlayer mediaPlayer) {
//		log.info("opening...");
//	}
//
//	@Override
//	public void buffering(MediaPlayer mediaPlayer, float newCache) {
////		log.info("buffering...");
//	}
//
//	@Override
//	public void playing(MediaPlayer mediaPlayer) {
//		log.info("playing...");
//	}
//
//	@Override
//	public void paused(MediaPlayer mediaPlayer) {
//		log.info("paused...");
//	}
//
//	@Override
//	public void stopped(MediaPlayer mediaPlayer) {
//		log.info("stopped...");
//	}
//
//	@Override
//	public void forward(MediaPlayer mediaPlayer) {
//		log.info("forward...");
//	}
//
//	@Override
//	public void backward(MediaPlayer mediaPlayer) {
//		log.info("backward...");
//	}
//
//	@Override
//	public void finished(MediaPlayer mediaPlayer) {
//		log.info("finished...");
//	}
//
//	@Override
//	public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
////		log.info("timeChanged...");
//	}
//
//	@Override
//	public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
////		log.info("positionChanged...");
//	}
//
//	@Override
//	public void seekableChanged(MediaPlayer mediaPlayer, int newSeekable) {
//		log.info("seekableChanged...");
//	}
//
//	@Override
//	public void pausableChanged(MediaPlayer mediaPlayer, int newPausable) {
//		log.info("pausableChanged...");
//	}
//
//	@Override
//	public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
//		log.info("titleChanged...");
//	}
//
//	@Override
//	public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
//		log.info("snapshotTaken...");
//	}
//
//	@Override
//	public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
//		log.info("lengthChanged...");
//	}
//
//	@Override
//	public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
//		log.info("videoOutput...");
//	}
//
//	@Override
//	public void scrambledChanged(MediaPlayer mediaPlayer, int newScrambled) {
//		log.info("scrambledChanged...");
//	}
//
//	@Override
//	public void elementaryStreamAdded(MediaPlayer mediaPlayer, int type, int id) {
//		log.info("elementaryStreamAdded...");
//	}
//
//	@Override
//	public void elementaryStreamDeleted(MediaPlayer mediaPlayer, int type, int id) {
//		log.info("elementaryStreamDeleted...");
//	}
//
//	@Override
//	public void elementaryStreamSelected(MediaPlayer mediaPlayer, int type, int id) {
//		log.info("elementaryStreamSelected...");
//	}
//
//	@Override
//	public void corked(MediaPlayer mediaPlayer, boolean corked) {
//		log.info("corked...");
//	}
//
//	@Override
//	public void muted(MediaPlayer mediaPlayer, boolean muted) {
//		log.info("muted...");
//	}
//
//	@Override
//	public void volumeChanged(MediaPlayer mediaPlayer, float volume) {
//		log.info("volumeChanged...");
//	}
//
//	@Override
//	public void audioDeviceChanged(MediaPlayer mediaPlayer, String audioDevice) {
//		log.info("audioDeviceChanged...");
//	}
//
//	@Override
//	public void chapterChanged(MediaPlayer mediaPlayer, int newChapter) {
//		log.info("chapterChanged...");
//	}
//
//	@Override
//	public void error(MediaPlayer mediaPlayer) {
//		log.info("error...");
//	}
//
//	@Override
//	public void mediaMetaChanged(MediaPlayer mediaPlayer, int metaType) {
//		log.info("mediaMetaChanged...");
//	}
//
//	@Override
//	public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
//		log.info("mediaSubItemAdded...");
//	}
//
//	@Override
//	public void mediaDurationChanged(MediaPlayer mediaPlayer, long newDuration) {
//		log.info("mediaDurationChanged...");
//	}
//
//	@Override
//	public void mediaParsedChanged(MediaPlayer mediaPlayer, int newStatus) {
//		log.info("mediaParsedChanged...");
//	}
//
//	@Override
//	public void mediaFreed(MediaPlayer mediaPlayer) {
//		log.info("mediaFreed...");
//	}
//
//	@Override
//	public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
//		log.info("mediaStateChanged...");
//	}
//
//	@Override
//	public void mediaSubItemTreeAdded(MediaPlayer mediaPlayer, libvlc_media_t item) {
//		log.info("mediaSubItemTreeAdded...");
//	}
//
//	@Override
//	public void newMedia(MediaPlayer mediaPlayer) {
//		log.info("newMedia...");
//	}
//
//	@Override
//	public void subItemPlayed(MediaPlayer mediaPlayer, int subItemIndex) {
//		log.info("subItemPlayed...");
//	}
//
//	@Override
//	public void subItemFinished(MediaPlayer mediaPlayer, int subItemIndex) {
//		log.info("subItemFinished...");
//	}
//
//	@Override
//	public void endOfSubItems(MediaPlayer mediaPlayer) {
//		log.info("endOfSubItems...");
//	}
//
//}
