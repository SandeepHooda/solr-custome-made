package search;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.SearchEngine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import datastructure.File;
import datastructure.Folder;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("q");
		Folder root = null;
		long start = System.currentTimeMillis();
		SearchResult searchResult = new SearchResult();
		if (query == null) {
			root = SearchEngine.getRootFolderpopulated(SearchEngine.getAllFiles());
			searchResult.setSearchTime(System.currentTimeMillis() - start);
			searchResult.setMatchedFilesCount(SearchEngine.getAllFiles().size());
		}else {
			QueryParser queryParser = new QueryParser();
			String[] keyWords = query.toLowerCase().split(" ");
			//List<File> filteredFiles = queryParser.getFilteredFiles(keyWords, SearchEngine.getAllFiles());
			List<File> filteredFiles = queryParser.getFilteredFiles(keyWords, SearchEngine.getKeyWordToFile());
			searchResult.setSearchTime(System.currentTimeMillis() - start);
			if (filteredFiles.size()>0) {
				root = SearchEngine.getRootFolderpopulated(filteredFiles);
				searchResult.setMatchedFilesCount(filteredFiles.size());
			}else {//User has not enter any relevent text to narow searhc so show him all records
				root = SearchEngine.getRootFolderpopulated(SearchEngine.getAllFiles());
				searchResult.setMatchedFilesCount(SearchEngine.getAllFiles().size());
			}
			
		}
		
		searchResult.setSearchResult(root);
		response.setContentType("application/json");
		GsonBuilder builder = new GsonBuilder();  
		builder.excludeFieldsWithoutExposeAnnotation();  
		Gson gson = builder.create();  
	
		searchResult.setRequestExecutionTime(System.currentTimeMillis() - start);
		response.getWriter().println(gson.toJson(searchResult));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
