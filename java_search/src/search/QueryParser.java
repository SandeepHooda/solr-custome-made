package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import datastructure.File;

public class QueryParser {
	
	public List<File> getFilteredFiles(String[] searchkeyWords, List<File> allFiles){
		List<File> filteredFiles = new ArrayList<>();
		List<SearchResultPosition> filteredResults = new ArrayList<SearchResultPosition>();
		
		//Single threaded
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
		
		//Multi threaded 
		/*List<MatchFinder> allWorkers = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (File aFile:allFiles) {
			MatchFinder worker = new MatchFinder(aFile,searchkeyWords);
			executor.execute(worker);
			allWorkers.add(worker);
		}
		
		executor.shutdown();
        while (!executor.isTerminated()) {
        }
		
        for (MatchFinder matchFinder: allWorkers) {
        	if (matchFinder.getSearchResult() != null) {
        		filteredResults.add(matchFinder.getSearchResult());
        	}
        }*/
        
        
        
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
