package com.teamtter.theatre.options;

import java.io.File;

import lombok.Data;

@Data
public class MediaToStartWithConfig {
	private File fileToPlay = new File("");
	private Integer startTitle;
	private Integer startChapter;
	
}
