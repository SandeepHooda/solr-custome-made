package com;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.google.gson.Gson;

import datastructure.File;
import datastructure.Folder;
public class SearchEngine {

	private static List<Set<String>> synonymsList = new ArrayList<>();
	private static List<File> allFiles = null;
	private static Map<String, List<File>> keyWordToFileMap = new HashMap<>();//We will use this a index when we search keywords in file
	
	
	/**
	 * This function creates a list having set of synonym words it reads synonyms.properties
	 * @return
	 * @throws IOException
	 */
	public static List<Set<String>> getSynonyms() throws IOException {
		InputStream input  = null;
	try {
		String filename = "synonyms.properties";
		input  = SearchEngine.class.getClassLoader().getResourceAsStream(filename);
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
				synonyms.add(word.toLowerCase().trim());
				String[] similars = prop.getProperty(word).split(" ");
				for (String similar: similars) {
					if (!"".equals(similar.trim())) {
						synonyms.add(similar.toLowerCase().trim());
					}
					
				}
			
				synonymsList.add(synonyms);
			}
		}
		
		return synonymsList;
		
	}finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	}
	
	private static List<File> readAllFilesFromConfig() throws IOException{
		Properties prop = new Properties();
		InputStream input = null;
		Gson gson = new Gson();
		List<File> allFiles = new ArrayList<>();
		try {
			//1. Read the config file where folder hierarchical and file information is stored
			String filename = "config10K.properties";
			input = SearchEngine.class.getClassLoader().getResourceAsStream(filename);
			if(input==null){
		            System.out.println("Sorry, unable to find " + filename);
			    return null;
			}
			prop.load(input);
			
			
			//each line item in the property file contains a file attributes like its name , its id , its key word and which folder/ sub folder it exists
			Set<Object> keys = prop.keySet();
			for (Object key: keys) {
				
				String lineNo = (String)key;
				
				//File attributes like  its name , its id , its key word and folder
				String fileDetailJson = prop.getProperty(lineNo);
				File aFile = gson.fromJson(fileDetailJson,File.class);
				aFile.setRowNum(lineNo);
				
				 
				//Add Synonyms
				Set<String> filekeyWords = aFile.getKeyWords();
				Set<String> filekeyWordsWithSynonyms = new HashSet<>();
				for (String keyWord: filekeyWords) {
					filekeyWordsWithSynonyms.add(keyWord.trim().toLowerCase());
				}
				
				Iterator<String> fileKeyWordItr = filekeyWords.iterator();
				while(fileKeyWordItr.hasNext()) {
					String keyWord = fileKeyWordItr.next();
					for (Set<String> synonyms: synonymsList) {//synonyms set contains synonyms as well as the word itself in lower case 
						if (synonyms.contains(keyWord)) {
							filekeyWordsWithSynonyms.addAll(synonyms);
						}
					}
				}
				aFile.setKeyWords(filekeyWordsWithSynonyms);
				
				//make an index of this file in keyWordToFile 
			    for (String keyWord: filekeyWordsWithSynonyms) {
			    	List<File> filesWithKeyword = keyWordToFileMap.get(keyWord);
			    	if (null == filesWithKeyword) {
			    		filesWithKeyword = new ArrayList<>();
			    		filesWithKeyword.add(aFile);
			    		keyWordToFileMap.put(keyWord, filesWithKeyword);
			    	}else {
			    		filesWithKeyword.add(aFile);
			    	}
			    	
			    }
				
				allFiles.add(aFile);
				
			}
			
			
			return allFiles;
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This function create a hierarchical menu. Think of windows folder. Folder can contain another folder(s) or files(s). 
	 * File here is the actual document that user wants to read. Is is associated with keywords that will make the document search-able
	 * @return
	 * @throws IOException
	 */
	public static Folder getRootFolderpopulated(List<File> filesList) throws IOException {
			//1. Root contains only folder - Folder in root are at Level 1 in the menu
			Folder root = new Folder("root");
		
			//each line item in the property file contains a file attributes like its name , its id , its key word and which folder/ sub folder it exists
			for (File aFile: filesList) {
				
				//Place the file in expected folder
				Folder topFolder = root.getFolderByName(aFile.getFolder().get(0));//The L1 folder where this file will be stored
					if (null == topFolder) {
						topFolder = new Folder(aFile.getFolder().get(0));
						root.getFolders().add(topFolder);
					}
					Folder nodeFolder = topFolder;
					for (int i=1;i<aFile.getFolder().size();i++) {//find out where to place this file
						Folder subFolder = nodeFolder.getFolderByName(aFile.getFolder().get(i));
						if (null != subFolder) {
							nodeFolder = subFolder;
						}else {
							subFolder = new Folder(aFile.getFolder().get(i));
							nodeFolder.getFolders().add(subFolder);
							nodeFolder = subFolder;
						}
					}
					
					nodeFolder.getFiles().add(aFile);
				
			}
			
			
			return root;
		
	
	}
	public static void init() throws IOException {
		getSynonyms();
		allFiles = 	readAllFilesFromConfig();
		/*Folder root =*/ getRootFolderpopulated(allFiles);	
		
		/*GsonBuilder builder = new GsonBuilder();  
		builder.excludeFieldsWithoutExposeAnnotation();  
		Gson gson = builder.create();  ;
		
		System.out.println( gson.toJson(root));*/
	}
	
	/*public static void main(String[] args) throws IOException {
		init();
		
	  }*/

	public static List<File> getAllFiles() throws IOException {
		if (null == allFiles) {
			init();
		}
		return allFiles;
	}

	public static void setAllFiles(List<File> allFiles) {
		SearchEngine.allFiles = allFiles;
	}

	public static Map<String, List<File>> getKeyWordToFileMap() {
		return keyWordToFileMap;
	}

	
}
