package search;

import datastructure.File;

public class MatchFinder implements Runnable {

	private File aFile;
	private String[] searchkeyWords;
	private SearchResultPosition searchResult;
	public MatchFinder(File aFile, String[] searchkeyWords) {
		this.aFile = aFile;
		this.searchkeyWords = searchkeyWords;
	}
	@Override
	public void run() {
		
			SearchResultPosition searchResult = new SearchResultPosition();
			searchResult.setFile(aFile);
			for (String searchKey:searchkeyWords ) {
				if (aFile.getKeyWords().contains(searchKey)) {
					searchResult.setPosition(searchResult.getPosition()+1);
				}
			}
			if (searchResult.getPosition()>0) {
				this.searchResult = searchResult;
			}
		
		
	}
	public SearchResultPosition getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(SearchResultPosition searchResult) {
		this.searchResult = searchResult;
	}

}
