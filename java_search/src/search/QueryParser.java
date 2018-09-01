package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datastructure.File;

public class QueryParser {
	
	public List<File> getFilteredFiles(String[] searchkeyWords, List<File> allFiles){
		List<File> filteredFiles = new ArrayList<>();
		List<SearchResultPosition> filteredResults = new ArrayList<SearchResultPosition>();
		for (File aFile:allFiles) {
			SearchResultPosition searchPos = new SearchResultPosition();
			searchPos.setFile(aFile);
			for (String searchKey:searchkeyWords ) {
				if (aFile.getKeyWords().contains(searchKey)) {
					searchPos.setPosition(searchPos.getPosition()+1);
				}
			}
			if (searchPos.getPosition()>0) {
				filteredResults.add(searchPos);
			}
		}
		
		if (filteredResults.size()>1) {
			Collections.sort(filteredResults, new SearchResultComparator());
			int maxMatch = filteredResults.get(0).getPosition();
			for (SearchResultPosition search: filteredResults) {
				if (search.getPosition() == maxMatch ) {
					filteredFiles.add(search.getFile());
				}
			}
		}
		return filteredFiles;
	}
	

}
