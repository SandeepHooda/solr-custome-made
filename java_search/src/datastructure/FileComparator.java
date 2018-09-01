package datastructure;

public class FileComparator implements java.util.Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		// TODO Auto-generated method stub
		if( o1.getName().compareTo(o2.getName()) >0) {
			return 1;
		}else {
			return -1;
		}
	}

}