package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import datastructure.File;

public class QueryParser {
	
	public List<File> getFilteredFiles(String[] searchkeyWords, Map<String, List<File>> keyWordToFile){
		List<File> filesBestMacth = new ArrayList<>();
		List<File> filesAnyMacth = new ArrayList<>();
		
   	//Single threaded
		for (String keyWord:searchkeyWords) {
			List<File> filesContainingKeyword = keyWordToFile.get(keyWord);
			if (null != filesContainingKeyword) {
				for (File aFile: filesContainingKeyword) {
					if (filesAnyMacth.contains(aFile)) {
						aFile.setNoOfSerachWordsMatched(aFile.getNoOfSerachWordsMatched()+1);
					}else {
						aFile.setNoOfSerachWordsMatched(1);
						filesAnyMacth.add(aFile);
					}
					
					
				}
			}
			
		}
		
		
		//if we get the key words at least in one of our files one then we care about which files matches the max search keyword
		if (filesAnyMacth.size()>1) {
			Collections.sort(filesAnyMacth, new FileSearchMatchComparator());
			int maxMatch = filesAnyMacth.get(0).getNoOfSerachWordsMatched();
			for (File aFile: filesAnyMacth) {
				if (aFile.getNoOfSerachWordsMatched() == maxMatch ) {
					filesBestMacth.add(aFile);
				}else {
					break;
				}
			}
		}
		return filesBestMacth;
	}
	
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
        
        
        //if we get the key words at least in one of our files one then we care about which files matches the max search keyword
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
