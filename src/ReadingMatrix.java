import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.StyleConstants.ColorConstants;

import Jama.Matrix;

import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

public class ReadingMatrix {

	static double [][] tf_array;
	static double [][]tfidf_array;
	static String path1="src/new_2000.txt";
	static String path2="src/Docs2.txt";
	static int dimensions;
	 static double[][] finalresult=new double[1000][2224];
		static PSOConstants p=new PSOConstants();
		static Cmeans o=new Cmeans();
		static StringToDouble std=new StringToDouble();
		static Fpso f=new Fpso();
		//static  long startTime = System.currentTimeMillis();
		//static  long startTime1 = System.currentTimeMillis();
	public static void main (String[] args) throws IOException
	{	
		
	//Start: Getting TF Matrix	

	
		Cmeans c=new Cmeans();
		
		
		 
		 tfidf_array= std.twoDArrayofStrings(path1);

		//tf_array= std.twoDArrayofStrings(path2);

	//End : Getting Tf Matrix	
		
	//Converting 2d array into Matrix
/*System.out.println("before creating matrix");
		 RealMatrix m1=MatrixUtils.createRealMatrix(tf_array);
		 System.out.println("after creating matrix");
	// Transposing it as LSI considers rows as Words and Columns as Documents	
		RealMatrix m2=m1.transpose();
	//Callig LsiIndexer	
		LsiIndexer lsi=new LsiIndexer();
		 System.out.println("before calling transform");
		RealMatrix lisIndexed_Matrix= lsi.transform(m2);
		 System.out.println("after calling transpose");
		RealMatrix lsi_transposed=lisIndexed_Matrix.transpose();
		
	    finalresult=lsi_transposed.getData();
	  // dimensions= finalresult[0].length;*/
	   PSOConstants p=new PSOConstants();		
		
		 c.doCmeans();
		// long stopTime = System.currentTimeMillis();
		// long elapsedTime = stopTime - startTime;
	     // System.out.println(elapsedTime);
		 writefile_fcm();
		 writefile_fpso();
		new ValidateClusters().doValidation();
		 new Keywordextraction().doExtraction();
		// new Fpso().doPSO();
   }	
	public static void writefile_fcm ()
	{
		int k;
		int [][] cluster=new int[p.clusters][p.Document_number];
		for(int i=0;i<p.clusters;i++)
        {
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		   if(o.check_membership[j][0]==i||o.check_membership[j][1]==i)
     		   {
     			   cluster[i][j]=j;
     		   }
     		   else
     		   {
     			   cluster[i][j]=-1;
     		   }
     	   }
			
        }
		/*for(int i=0;i<p.clusters;i++)
        {
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		  System.out.print(cluster[i][j]+" ");
     	   }
			System.out.println("\n");
        }*/
		File file = new File("c:/result/fuzzycmeans.txt");
		String content = "This is the text content";
		byte[] contentInBytes;
		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			for(int i=0;i<p.clusters;i++)
	        {
				content="\n\n\n\nCluster "+i+" : ";
				contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
	     	   for(int j=0;j<p.Document_number;j++)
	     	   {
	     		   k=cluster[i][j];
	     		   if(k>=0){
	     			   
	     		   
	     		   content=std.documents[k]+" ;";
	     		  contentInBytes = content.getBytes();
	     		 fop.write(contentInBytes);
					fop.flush();
	     	   }
	     	   }
	        }
			
			
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writefile_fpso ()
	{
		int k;
		int [][] cluster=new int[p.clusters][p.Document_number];
		for(int i=0;i<p.clusters;i++)
        {
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		   if(f.check_membership[j][0]==i||f.check_membership[j][1]==i)
     		   {
     			   cluster[i][j]=j;
     		   }
     		   else
     		   {
     			   cluster[i][j]=-1;
     		   }
     	   }
			
        }
		/*for(int i=0;i<p.clusters;i++)
        {
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		  System.out.print(cluster[i][j]+" ");
     	   }
			System.out.println("\n");
        }*/
		File file = new File("c:/result/fpso.txt");
		String content = "This is the text content";
		byte[] contentInBytes;
		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			for(int i=0;i<p.clusters;i++)
	        {
				content="\n\n\n\nCluster "+i+" : ";
				contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
	     	   for(int j=0;j<p.Document_number;j++)
	     	   {
	     		   k=cluster[i][j];
	     		   if(k>=0){
	     			   
	     		   
	     		   content=std.documents[k]+" ;";
	     		  contentInBytes = content.getBytes();
	     		 fop.write(contentInBytes);
					fop.flush();
	     	   }
	     	   }
	        }
			
			
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
