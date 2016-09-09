
public class ValidateClusters {

	static PSOConstants p=new PSOConstants();
    static Fpso f=new Fpso();
     static Cmeans c=new Cmeans();
     static ReadingMatrix r=new ReadingMatrix();
	 double pso_pe=0.0;
	 double fcm_pe=0.0;
	 double pso_pc=0.0;
	 double fcm_pc=0.0;
	 double sum=0.0;
	 double diff=0.0;
	int dimensions=p.dimensions;
	 //int dimensions=2224;
	 double[]term=new double[p.Document_number];
	 double[]sum_clusters=new double[p.Document_number];
	 double[][] cluster_membership=new double[p.Document_number][p.clusters];
	 double[][] cluster_distance=new double[p.clusters][p.clusters];
	 double[][] cluster_distance_f=new double[p.clusters][p.clusters];
	 
	public void doValidation()
	{
		
		
		

		for(int i=0;i<p.Document_number;i++)
		{
			sum=0.0;
			  for(int j=0;j<p.clusters;j++)
			  {
				 
				 //pso_pe+=(f.cluster_membership[j][i]) *Math.log(f.cluster_membership[j][i]);
				
				
				 sum+=f.cluster_membership[i][j];
				  
			  }
			  sum_clusters[i]=sum;
			  term[i]=sum_clusters[i]-1.0;
			  
		}
		for(int i=0;i<p.Document_number;i++)
		{
			  for(int j=0;j<p.clusters;j++)
			  {
				 
				 //pso_pe+=(f.cluster_membership[j][i]) *Math.log(f.cluster_membership[j][i]);
				  cluster_membership[i][j]=f.cluster_membership[i][j]-((f.cluster_membership[i][j]/sum_clusters[i])*term[i]);
				//System.out.print(" "+cluster_membership[i][j]+" "+f.cluster_membership[i][j]);
			
				  
			  }
			//System.out.println("\n");
		}
	/*	for(int i=0;i<p.Document_number;i++)
		{
			  for(int j=0;j<p.clusters;j++)
			  {
				 
				 //pso_pe+=(f.cluster_membership[j][i]) *Math.log(f.cluster_membership[j][i]);
			//	System.out.print(" "+ cluster_membership[i][j]);
				
			
				  
			  }
			//System.out.println("\n");
		}*/
		
		partitionEntropy();
		
		compute_distance();
		Db();
		
		
		
		
		
		
	}
	public void partitionEntropy()
	{

		for(int i=0;i<p.clusters;i++)
		{
			  for(int j=0;j<p.Document_number;j++)
			  {
				 fcm_pe+=(c.degreeOfMembership[j][i])*Math.log(c.degreeOfMembership[j][i]);
				 pso_pe+=(cluster_membership[j][i]) *Math.log(cluster_membership[j][i]);
				 
				 fcm_pc+=(c.degreeOfMembership[j][i])*(c.degreeOfMembership[j][i]);
				 pso_pc+=(cluster_membership[j][i]) *(cluster_membership[j][i]);
				 
				  
			  }
		}
	/*	for(int i=0;i<p.clusters;i++)
		{
			for (int j=0;j<p.dimensions;j++)
			{
				System.out.print(" "+f.particles[f.pos][i][j]);
			}
			System.out.println("\n");
		}*/
	/*	for(int i=0;i<p.clusters;i++)
		{
			for (int j=0;j<p.dimensions;j++)
			{
				System.out.print(" "+c.cluster_centers[i][j]);
			}
			System.out.println("\n");
		}*/
		fcm_pe=-fcm_pe/p.Document_number;
		pso_pe=-pso_pe/p.Document_number;

		fcm_pc=fcm_pc/p.Document_number;
		pso_pc=pso_pc/p.Document_number;
	
		System.out.println("the Partiton entropy for fuzzy cmeans is :"+fcm_pe);
		System.out.println("the Partiton entropy for PSO is :"+pso_pe);
		

		System.out.println("the Partiton  for fuzzy cmeans is :"+fcm_pc);
		System.out.println("the Partiton  for PSO is :"+pso_pc);
		
		
		
		
		
		
		
		
		
	}
	
	public void Db()
	{
		compute_distance();
		double largest;
		double largest_f;
		double temp;
		double temp_f;
		double sum=0.0;
		double sum_f=0.0;
		for(int i=0;i<p.clusters;i++)
		{
			largest=0.0;
			temp=0.0;
			largest_f=0.0;
			temp_f=0.0;
			for(int j=0;j<p.clusters;j++)
			{
				
				if (j!=i)
				{
					temp=(calculateS(i)+calculateS(j))/(cluster_distance[i][j]);
					temp_f=(calculateS_f(i)+calculateS_f(j))/(cluster_distance_f[i][j]);
					System.out.println("temp:"+temp);
					System.out.println("temp_f:"+temp_f);
					if(temp>largest)
					{
						
						largest=temp;
					}
					if(temp_f>largest_f)
					{
						
						largest_f=temp_f;
					}
					
					
				}
				
			 }
			sum+=largest;
	        sum_f+=largest_f;
	}
		
		System.out.println(+sum);
		System.out.println(+sum_f);
		sum=sum/p.clusters;
		sum_f=sum_f/p.clusters;
		System.out.println("the db for fuzzyCmeans:"+sum);
		System.out.println("the db for PSO:"+sum_f);
	}
	
	
	
	
	public void compute_distance()
	{
		double sum_distance=0.0;
		double sum_distance_f=0.0;
		for (int i=0;i<p.clusters;i++)
		{
			for(int j=0;j<dimensions;j++)
			{
				
				System.out.println(f.particles[f.pos][i][j]);
			}
		}
		for (int i=0;i<p.clusters;i++)
		{
			for(int j=0;j<p.clusters;j++)
			{
				sum_distance=0.0;
				 sum_distance_f=0.0;
				for(int k=0;k<dimensions;k++)
				{
					
					sum_distance+=Math.pow(c.cluster_centers[i][k]-c.cluster_centers[j][k],2);
					System.out.println(" fuzzy "+Math.pow(c.cluster_centers[i][k]-c.cluster_centers[j][k],2));
					sum_distance_f+=Math.pow(f.particles[f.pos][i][k]-f.particles[f.pos][j][k],2);
					System.out.println(" pso"+Math.pow(f.particles[f.pos][i][k]-f.particles[f.pos][j][k],2));
					//sum_distance_f+=Math.pow(c.cluster_centers[i][k]-c.cluster_centers[j][k],2);
				}
				
				cluster_distance[i][j]=Math.sqrt(sum_distance);
				cluster_distance_f[i][j]=Math.sqrt(sum_distance_f);
				
			//	System.out.println(" fuzzy "+cluster_distance[i][j]);
			//	System.out.println(" pso "+cluster_distance_f[i][j]);
			}
			
			
			
			
			
		}
		
		
		
		
	}
	
	
	
	public double calculateS(int t)
	{
		double sum=0.0;
	int [][] cluster=new int [p.clusters][p.Document_number];
		
		for(int i=0;i<p.clusters;i++)
        {
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		   if(c.check_membership[j][0]==i||c.check_membership[j][1]==i)
     		   {
     			   cluster[i][j]=j;
     		   }
     		   else
     		   {
     			   cluster[i][j]=-1;
     		   }
     	   }
			
        }
		int k;
		double sum_distance;
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		   
     		   k=cluster[t][j];
     		   sum_distance=0.0;
     		   if(k>=0){
     			   
     		   for(int l=0;l<dimensions;l++)
     		   {
     			  sum_distance+=Math.pow(r.tfidf_array[k][l]-c.cluster_centers[t][l],2);
     			   
     		   }
     		   sum_distance=Math.sqrt(sum_distance);
     		   sum+=sum_distance;
     		   
     	   }
     		   
     	   }
     	 //System.out.println(" fcm"+sum/c.cluster[t]);
		return sum/c.cluster[t];
		
	}
	
	public double calculateS_f(int t)
	{
		double sum=0.0;
int [][] cluster=new int [p.clusters][p.Document_number];
		
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
		int k;
		double sum_distance;
     	   for(int j=0;j<p.Document_number;j++)
     	   {
     		   
     		   k=cluster[t][j];
     		   sum_distance=0.0;
     		   if(k>=0){
     			   
     		   for(int l=0;l<dimensions;l++)
     		   {
     			  sum_distance+=Math.pow(r.tfidf_array[k][l]-f.particles[f.pos][t][l],2);
     			// sum_distance+=Math.pow(r.tfidf_array[k][l]-c.cluster_centers[t][l],2);
    			   
     		   }
     		   sum_distance=Math.sqrt(sum_distance);
     		   sum+=sum_distance;
     		   
     	   }
     		   
     	   }
      //  System.out.println(" pso:"+sum/f.cluster[t]);
		return sum/f.cluster[t];
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
