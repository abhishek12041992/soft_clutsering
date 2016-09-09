package Tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountWords {

	 private List<String []> termsDocsArray = new ArrayList<>();
	    private List<String> allTerms = new ArrayList<>(); //to hold all terms
	    private List<double []> tfidfDocsVector = new ArrayList<>();
	 
	    public void parseFiles() throws FileNotFoundException, IOException 
	    {
	        File[] allfiles = new File("src/textFiles").listFiles();
	        BufferedReader in = null;
	        System.out.println(allfiles.length);
	        for (File f : allfiles) 
	        {
	            if (f.getName().endsWith(".txt")) 
	            {
	                in = new BufferedReader(new FileReader(f));
	                StringBuilder sb = new StringBuilder();
	                String s = null;
	                while ((s = in.readLine()) != null) 
	                {
	                    sb.append(s);
	                }
	                String[] tokenizedTerms = sb.toString().split(";");   //to get individual terms
	                for (String term : tokenizedTerms) 
	                {
	                	if (!allTerms.contains(term)) 
	                	{  //avoid duplicate entry
	                        allTerms.add(term);
	                    }
	                }
	                termsDocsArray.add(tokenizedTerms);
	            }
	        }
	        
	        System.out.println(allTerms.size());
	    }
	
	    public static void main(String[] args) throws FileNotFoundException, IOException
	    {
	    	
	    	CountWords c1=new CountWords();
	    	c1.parseFiles();
	    	
	    }
	    
}
