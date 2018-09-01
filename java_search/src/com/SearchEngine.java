package com;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.google.gson.Gson;

import datastructure.File;
import datastructure.Folder;
public class SearchEngine {

	private static List<Set<String>> synonymsList = new ArrayList<>();;
	public static List<Set<String>> getSynonyms() throws IOException {
	
		String filename = "synonyms.properties";
		InputStream input  = SearchEngine.class.getClassLoader().getResourceAsStream(filename);
		if(input==null){
	            System.out.println("Sorry, unable to find " + filename);
		    return null;
		}else {
			Properties prop = new Properties();
			prop.load(input);
			Set<Object> keys = prop.keySet();
			
			for (Object key: keys) {
				String word = (String)key;
				Set<String> synonyms = new HashSet<String>();
				synonyms.add(word);
				synonyms.addAll(new HashSet<>(Arrays.asList( prop.getProperty(word).split(" "))));
				synonymsList.add(synonyms);
			}
		}
		
		return synonymsList;
	}
	public static void main(String[] args) {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			getSynonyms();
			String filename = "config.properties";
    		input = SearchEngine.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}
    		
    		Folder root = new Folder("root");
    	
    		
    		Gson gson = new Gson();
    	
			prop.load(input);
			
			Set<Object> keys = prop.keySet();
			
			for (Object key: keys) {
				
				String fileName = (String)key;
				String[] foldersAndKeyWords = prop.getProperty(fileName).split("\\$@");
				String[] folders = foldersAndKeyWords[0].split("~");
				
				Folder topFolder = root.getFolderByName(folders[0]);
					if (null == topFolder) {
						topFolder = new Folder(folders[0]);
						root.getFolders().add(topFolder);
					}
					Folder nodeFolder = topFolder;
					for (int i=1;i<folders.length;i++) {//find out where to place this file
						Folder subFolder = nodeFolder.getFolderByName(folders[i]);
						if (null != subFolder) {
							nodeFolder = subFolder;
						}else {
							subFolder = new Folder(folders[i]);
							nodeFolder.getFolders().add(subFolder);
							nodeFolder = subFolder;
						}
					}
					
					File file = new File(fileName);//This is my custom file not java file utility
					String[] keyWords = foldersAndKeyWords[1].split(" ");
					file.getKeyWords().addAll(new HashSet<>(Arrays.asList(keyWords)));
					Set<String> filekeyWords = file.getKeyWords();
					Set<String> filekeyWordsWithSynonyms = new HashSet<>();
					filekeyWordsWithSynonyms.addAll(filekeyWords);
					Iterator<String> fileKeyWordItr = filekeyWords.iterator();
					while(fileKeyWordItr.hasNext()) {
						String keyWord = fileKeyWordItr.next();
						for (Set<String> synonyms: synonymsList) {
							if (synonyms.contains(keyWord)) {
								filekeyWordsWithSynonyms.addAll(synonyms);
							}
						}
					}
					file.setKeyWords(filekeyWordsWithSynonyms);
					nodeFolder.getFiles().add(file);
				
			}
			
			System.out.println(gson.toJson(root));
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	  }
}
