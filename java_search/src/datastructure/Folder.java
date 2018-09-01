package datastructure;

import java.util.SortedSet;
import java.util.TreeSet;

public class Folder {
	private String name;
	private SortedSet<File> files =  new TreeSet(new FileComparator());
	
	TreeSet<Folder> folders = new TreeSet(new FolderComparator());
	
	public Folder(String name) {
		this.name = name;
	}
	public String toString() {
		return name +" "+folders;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TreeSet<Folder> getFolders() {
		return folders;
	}
	public void setFolders(TreeSet<Folder> folders) {
		this.folders = folders;
	}
	
	public Folder getFolderByName(String name) {
		for (Folder aFolder: folders) {
			if (aFolder.getName().equalsIgnoreCase(name)) {
				return aFolder;
			}
		}
		return null;
	}
	public SortedSet<File> getFiles() {
		return files;
	}
	public void setFiles(SortedSet<File> files) {
		this.files = files;
	}
}
