import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.lang.model.type.PrimitiveType;

public class Keywordextraction {

	static PSOConstants p=new PSOConstants();
	static StringToDouble std=new StringToDouble();
	static int dimensions=p.dimensions;
		//static int dimensions=2224;
	
	static Fpso f=new Fpso();
	
	static ReadingMatrix r=new ReadingMatrix();
	static StringToDouble s=new StringToDouble();
	static double [][] cluster_document=new double[p.clusters][p.Document_number];
	static double [][] temp=new double[p.clusters][p.Document_number];
	static double [] temp2=new double[dimensions];
	 static int [][] document_index=new int[p.clusters][p.Document_number];
	  HashMap<Integer,Double> hm=new HashMap<Integer,Double>();  
	  HashMap<Integer,Integer> hm1=new HashMap<Integer,Integer>();  
	 double[] tf_idf_value=new double[dimensions];
	static int[] dimension_index=new int[dimensions];
	
	 int x=0;
	// int x1=((p.Document_number/10)*2)*2+((p.Document_number/10*2)*2)/10+p.clusters+(p.Document_number/10);
	 int x1=p.Document_number*3;
	 String nullString = null;
//	String[] content = new String[((p.Document_number/10)*2)*2+((p.Document_number/10*2)*2)/10+p.clusters+(p.Document_number/10)];
	String[] content = new String[p.Document_number*3];
    public void doExtraction()
	{
		calculate_closest();
    	sort();
    	extract_keywords();//top 10 percent documents
    	System.out.println("i a out");
    	
	}
	public void extract_keywords()
	{
		
		int term=0;
		for(int i=0;i<p.clusters;i++)
		{
			content[x++]="\n\n\n\nCluster "+i+" : ";
			System.out.println("in loop "+i);
			
				term=f.cluster[i]/10;
				for(int k=0;k<dimensions;k++)
				{
					tf_idf_value[k]=0;
					temp2[k]=0;
					
				}
			
			for(int j=0;j<term;j++)
			{
				
				
				for(int k=0;k<dimensions;k++)
				{
					tf_idf_value[k]=r.tfidf_array[document_index[i][j]][k];
					temp2[k]=r.tfidf_array[document_index[i][j]][k];
					
				}
			/*	for(int k=0;k<10;k++)
				{
					System.out.println(document_index[i][k]);
					
					
				}*/
				Arrays.sort(tf_idf_value);
			//	System.out.println(std.documents[document_index[i][j]]);
				
				for(int k=dimensions-1;k>=0;k--)
				{
					for(int d=0;d<dimensions;d++)
					{
						if(tf_idf_value[k]==temp2[d])
						{
							dimension_index[dimensions-k-1]=d;
						}

					}
					
					
				}
				
				//PrimitiveType.sort(tf_idf_value, (d1, d2) -> Double.compare(d2, d1), false);
				/*for(int j1=0;j1<p.dimensions-1;j1++)
				{
					 for (int d = 0; d < p.dimensions - j1- 1; d++) {
					        if (tf_idf_value[d]>tf_idf_value[d+1]) 
					        {
					          
					         
					          dimension_index[d]=d+1;
					         dimension_index[d+1]=d;
					        }
					      }
					
					
					 
				}*/
				 
				
			//	byte[] contentInBytes;
				
				
				
						
						//contentInBytes = content.getBytes();
						//fop.write(contentInBytes);
						//fop.flush();
				//String nullString = null;
				       content[x++]=std.documents[document_index[i][j]]+" : ";
						for(int h=0;h<5;h++)
				        {
					
						//	 System.out.print(s.words[dimension_index[h]]+";");
							if(s.words[dimension_index[h]]==null )
							{
				     		 
							}
							else
							{
								content[x++]=s.words[dimension_index[h]]+";";
							}
				        }
						
						// System.out.println("\n");
					

						//System.out.println("Done");
						content[x++]="\n";
					
				
				
			}
			//System.out.println(term);
			
		}
		//System.out.println(x);
		File file = new File("c:/result/keywords.txt");
		String content1 = "This is the text content";
		byte[] contentInBytes;
		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
				
	     	   for(int v=0;v<x1;v++)
	     	   {
	     		  
	     		   
	     		   content1=content[v];
	     		   if(content1!=null){
	     		  contentInBytes = content1.getBytes();
	     		 fop.write(contentInBytes);
					fop.flush();}
	     	   
	     	   }
	        
			
			
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void sort(){
		double swap;
		int index_swap;
		for (int i=0;i<p.clusters;i++)
		{
		/*	for(int j=0;j<p.Document_number-1;j++)
			{
				 for (int d = 0; d < p.Document_number - i- 1; d++) {
				        if (cluster_document[i][d] > cluster_document[i][d+1]) 
				        {
				          swap       = cluster_document[i][d];
				          cluster_document[i][d]   = cluster_document[i][d+1];
				          cluster_document[i][d+1] = swap;
				         
				          document_index[i][d]=d+1;
				          document_index[i][d+1]=d;
				        }
				      }
				
			}*/
			Arrays.sort(cluster_document[i]);
			for(int j=0;j<p.Document_number;j++)
			{
			
				for(int l=0;l<p.Document_number;l++)
				{
					if(cluster_document[i][j]==temp[i][l])
					{
						document_index[i][j]=l;
					}
				}
			}
		}
		
		
	}
	
	
	public void calculate_closest()
	{
		
		int [][] cluster=new int[p.clusters][p.Document_number];
		for(int i=0;i<p.clusters;i++)
        {
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		   if(f.check_membership[j][0]==i||f.check_membership[j][0]==i)
     		   {
     			   cluster[i][j]=j;
     		   }
     		   else
     		   {
     			   cluster[i][j]=-1;
     		   }
     	   }
			
        }
		
		int[] k=new int[p.Document_number];
		double sum_distance;
		//double sum;
		
		for(int i=0;i<p.clusters;i++)
		{
			//sum=0.0;
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		   
     		   k[j]=cluster[i][j];
     		   sum_distance=0.0;
     		   if(k[j]>=0){
     			   
     		   for(int l=0;l<dimensions;l++)
     		   {
     			  sum_distance+=Math.pow(r.tfidf_array[k[j]][l]-f.particles[f.pos][i][l],2);
     			// sum_distance+=Math.pow(r.tfidf_array[k][l]-c.cluster_centers[t][l],2);
    			   
     		   }
     		   sum_distance=Math.sqrt(sum_distance);
     		  // hm.put(k, sum_distance);
     		  // hm1.put(i, k);
     		   cluster_document[i][k[j]]=sum_distance;
     		   temp[i][k[j]]=sum_distance;
     		  // sum+=sum_distance;
     		 //  System.out.println(sum_distance);
     	   }
     		  /* else
     		   {
     			   cluster_document[i][k]=999999999999999999999999999999999999999999999999999.0;
     			 //hm.put(k, 999999999999999999999999999999999999999999999999999999999999999.0);
     		    // hm1.put(i, k);
     	   }*/
     		   
     		   int status;
     		   for(int o=0;o<p.Document_number;o++)
     		   {
     			   status=0;
     			  for(int a=0;a<p.Document_number;a++)
     			  {
     				  if(o==k[a])
     				  {
     					  status=1;
     				  }
     			  }
     			  if(status!=1){
     				 cluster_document[i][o]=999999999999999999999999999999999999999999999999999.0;
     				temp[i][0]=sum_distance;
     			  }
     		   }
		}
		
		
	}
	
	
	
	
	
	
	}

}
