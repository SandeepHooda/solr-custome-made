package search;

import com.google.gson.annotations.Expose;

import datastructure.Folder;

public class SearchResult {
	@Expose()
	private int matchedFilesCount;
	@Expose()
	private long searchTime;
	@Expose()
	private Folder searchResult;
	public int getMatchedFilesCount() {
		return matchedFilesCount;
	}
	public void setMatchedFilesCount(int matchedFilesCount) {
		this.matchedFilesCount = matchedFilesCount;
	}
	
	public Folder getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(Folder searchResult) {
		this.searchResult = searchResult;
	}
	public long getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}
	
	

}
