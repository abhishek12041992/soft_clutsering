package Tfidf;
//DocumentParser.java



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String []> termsDocsArray = new ArrayList<>();
    private List<String> allTerms = new ArrayList<>(); //to hold all terms
    private List<double []> tfidfDocsVector = new ArrayList<>();
 
    public void parseFiles(String filePath) throws FileNotFoundException, IOException 
    {
        File[] allfiles = new File(filePath).listFiles();
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
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
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
        /**System.out.println("allTerms: ");
        System.out.println(allTerms.size());
        for (String term : allTerms){
        	System.out.println(term+" ");
        	
        }*/

    }
    
    
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term frequency inverse document frequency        
        for(String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
        
        for(double[] tfidfdocvector :tfidfDocsVector){
        	for(double terms : tfidfdocvector){
        		DecimalFormat df = new DecimalFormat();
        		df.applyPattern(".000");
        		System.out.print(df.format(terms)+"\t");
        	}
        	System.out.println();
        }
    }
 
    /**
    public void getCosineSimilarity() {
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
                System.out.println("between " + i + " and " + j + "  =  "
                                   + new CosineSimilarity().cosineSimilarity
                                       (
                                         tfidfDocsVector.get(i), 
                                         tfidfDocsVector.get(j)
                                       )
                                  );
            }
        }
    }*/
}