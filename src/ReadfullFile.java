import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadfullFile {
	List<String []> termsDocsArray = new ArrayList<>();
    List<String> allTerms = new ArrayList<>(); //to hold all terms
    List<double []> tfidfDocsVector = new ArrayList<>();
	void readfullfile() throws IOException
	{
		
		
	   // File[] allfiles = new File("src/TextFiles").listFiles();
		
		 //The java.io.File.listFiles() returns the array of abstract pathnames defining the files in the directory denoted by this abstract pathname.
		BufferedReader in = in = new BufferedReader(new FileReader("src/TextFiles/keywords.txt"));
	   //System.out.println(allfiles.length);
	   String s=null;
	  
	  
		   
	   while ((s = in.readLine()) != null)
	   {
	       
	          
	           StringBuilder sb = new StringBuilder();
	           int i;
	           while ((i = in.read()) != (int)'\n') 
	           {
	               sb.append((char)i);
	           }
	           String[] tokenizedTerms = sb.toString().split(";");
	           //String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
	           ///The java.lang.String.split(String regex, int limit) method splits this string around matches of the given regular expression.
	           //The array returned by this method contains each substring of this string that is terminated by another substring that matches the 
	           //given expression or is terminated by the end of the string.
	           for (String term : tokenizedTerms) 
	           {
	           	if (!allTerms.contains(term) ) 
	           	{  //avoid duplicate entry
	                   allTerms.add(term);
	               }
	           }
	           termsDocsArray.add(tokenizedTerms);
	       }
	   in.close();
	   
	   System.out.println(allTerms.size());
	   for (int i = 0; i < allTerms.size(); i++) 
	   {
		   //System.out.println("jhjgv"+allTerms.size());
		    String value = allTerms.get(i);
		    System.out.println( value);
		}
	 }
		
}
