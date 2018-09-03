package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datastructure.File;

public class QueryParser {
	
	public List<File> getFilteredFiles(Set<String> searchkeyWords, Map<String, List<File>> keyWordToFileMap){
		List<File> filesWithHighestRank = new ArrayList<>();
		List<File> filesWithSearchRank = new ArrayList<>();
		
   	
		for (String keyWord:searchkeyWords) {
			List<File> filesContainingKeyword = keyWordToFileMap.get(keyWord);
			if (null != filesContainingKeyword) {
				for (File aFile: filesContainingKeyword) {
					if (filesWithSearchRank.contains(aFile)) {
						aFile.setSearchRank(aFile.getSearchRank()+1);
					}else {
						aFile.setSearchRank(1);
						filesWithSearchRank.add(aFile);
					}
					
					
				}
			}
			
		}
		
		
		//if we get the key words at least in one of our files one then we care about which files matches the max search keyword
		if (filesWithSearchRank.size()>1) {
			Collections.sort(filesWithSearchRank, new FileSearchRankComparator());
			int highestRank = filesWithSearchRank.get(0).getSearchRank();
			for (File aFile: filesWithSearchRank) {
				if (aFile.getSearchRank() == highestRank ) {
					filesWithHighestRank.add(aFile);
				}else {
					break;
				}
			}
		}
		return filesWithHighestRank;
	}
	
	/*public List<File> getFilteredFiles(String[] searchkeyWords, List<File> allFiles){
		List<File> filteredFiles = new ArrayList<>();
		List<FileSearchRank> filteredSearchResults = new ArrayList<FileSearchRank>();
		
		
		for (File aFile:allFiles) {
			FileSearchRank fileSearchRank = new FileSearchRank();
			fileSearchRank.setFile(aFile);
			for (String searchKey:searchkeyWords ) {
				if (aFile.getKeyWords().contains(searchKey)) {
					fileSearchRank.setSearchRank(fileSearchRank.getSearchRank()+1);
				}
			}
			if (fileSearchRank.getSearchRank()>0) {
				filteredSearchResults.add(fileSearchRank);
			}
		}
		
	    //if we get the key words at least in one of our files one then we care about which files matches the max search keyword
		if (filteredSearchResults.size()>1) {
			Collections.sort(filteredSearchResults, new SearchRankComparator());
			int maxRank = filteredSearchResults.get(0).getSearchRank();
			for (FileSearchRank filteredSearchResult: filteredSearchResults) {
				if (filteredSearchResult.getSearchRank() == maxRank ) {
					filteredFiles.add(filteredSearchResult.getFile());
				}
			}
		}
		return filteredFiles;
	}*/
	

}
