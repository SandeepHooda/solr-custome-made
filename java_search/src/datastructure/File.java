package datastructure;

import java.util.HashSet;
import java.util.Set;

public class File {
	private String name;
	private Set<String> keyWords = new HashSet<>();

	public File(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(Set<String> keyWords) {
		this.keyWords = keyWords;
	}

	
}
