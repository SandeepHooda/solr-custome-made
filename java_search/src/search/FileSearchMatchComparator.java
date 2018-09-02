package search;

import java.util.Comparator;

import datastructure.File;

public class FileSearchMatchComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		return   o2.getNoOfSerachWordsMatched() - o1.getNoOfSerachWordsMatched();
	}
}
