package datastructure;

import java.util.ArrayList;
import java.util.List;

public class Library {

	private List<Folder> folders = new ArrayList<Folder>();

	public List<Folder> getFolders() {
		return folders;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}
}
