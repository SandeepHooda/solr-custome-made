package search;

import java.util.Comparator;

public class SearchResultComparator implements Comparator<SearchResultPosition> {

	@Override
	public int compare(SearchResultPosition o1, SearchResultPosition o2) {
		return   o2.getPosition() - o1.getPosition();
	}

}
