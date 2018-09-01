package search;

import datastructure.File;

public class SearchResultPosition {

	private int position;//How many search  key words are matched here 
	private File file;
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
