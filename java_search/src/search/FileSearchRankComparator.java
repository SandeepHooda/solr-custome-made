package search;

import java.util.Comparator;

import datastructure.File;

public class FileSearchRankComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		return   o2.getSearchRank() - o1.getSearchRank();
	}
}
