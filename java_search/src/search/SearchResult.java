package search;

import com.google.gson.annotations.Expose;

import datastructure.Folder;

public class SearchResult {
	@Expose()
	private int matchedFilesCount;
	@Expose()
	private long executionTime;
	@Expose()
	private Folder searchResult;
	public int getMatchedFilesCount() {
		return matchedFilesCount;
	}
	public void setMatchedFilesCount(int matchedFilesCount) {
		this.matchedFilesCount = matchedFilesCount;
	}
	public long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	public Folder getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(Folder searchResult) {
		this.searchResult = searchResult;
	}
	
	

}
