package datastructure;

public class FolderComparator implements java.util.Comparator<Folder> {

	@Override
	public int compare(Folder o1, Folder o2) {
		if( o1.getName().compareTo(o2.getName()) >0) {
			return 1;
		}else {
			return -1;
		}
	}

}
