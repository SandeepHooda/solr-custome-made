package datastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

public class File {
	@Expose(serialize = false, deserialize = false)
	private String rowNum;
	@Expose(serialize = false, deserialize = false)
	private int noOfSerachWordsMatched;
	@Expose()
	private String name;
	@Expose()
	private String id;
	@Expose(serialize = false, deserialize = true)
	private Set<String> keyWords = new HashSet<>();
	@Expose(serialize = false, deserialize = true)
	private List<String> folder = new ArrayList<>();

	public File(String name, String id) {
		this.name = name;
		this.id = id;
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
		this.keyWords = new HashSet<>();
		for (String keyWord: keyWords) {
			this.keyWords.add(keyWord.trim().toLowerCase());
		}
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getFolder() {
		return folder;
	}
	public void setFolder(List<String> folder) {
		this.folder = folder;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	public int getNoOfSerachWordsMatched() {
		return noOfSerachWordsMatched;
	}
	public void setNoOfSerachWordsMatched(int noOfSerachWordsMatched) {
		this.noOfSerachWordsMatched = noOfSerachWordsMatched;
	}

	
}
