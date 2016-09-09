import java.util.Random;

public class Fpso {

	
	
   static PSOConstants constants=new PSOConstants();
   static Cmeans cm=new Cmeans();
  static int clusters=constants.clusters;
  static int document_number=constants.Document_number;
 static  int swarm_size= constants.SWARM_SIZE;
  static  int dimensions=constants.dimensions;
 // static  int dimensions=2224;
   static double [][][] particles=new double[swarm_size][clusters][dimensions];
   double[][][] degreeOfMembership=new double[swarm_size][clusters][document_number];
   double [][] membership_transpose=new double[clusters][document_number];
   static ReadingMatrix m=new ReadingMatrix();
	static double numerator;
	static double denominator;
	static double numerator1;
	static double denominator1;
	static double fuziness=constants.fuziness;
	 double[][] pow=new double[clusters][document_number];
	 double[][] pow1=new double[clusters][document_number];
	 double[][] pow_transpose=new double[document_number][clusters];
	 double[][] pow_transpose1=new double[document_number][clusters];
	 double [][][] personal_best=new double[swarm_size][clusters][dimensions];
	 double [][] global_best=new double[clusters][dimensions];
	 double gbest=0.0;
	 double[][][] bestvector=new double[swarm_size][clusters][dimensions];
	 double[][] globalvector=new double[clusters][dimensions];
	 double [][][] velocity=new double [swarm_size][clusters][dimensions];
	 double [][][] position=new double [swarm_size][clusters][dimensions];
	static double [][] cluster_membership=new double[document_number][clusters];
	 int iterations=constants.iterations;
	 static int pos=0;
	 int choice;
	 int second_highest;
	 double[][][] temp=new double[swarm_size][document_number][clusters];
	 static int[] cluster=new int[clusters];
	static  double [][] check_membership=new double[document_number][2];
    Random r1= new Random();
    //double phi1=r1.nextDouble();
    //double phi2=r1.nextDouble();
    double phi1=0.2;
    double phi2=0.2;
    void doPSO()
    {
    	transpose();
    	
       PSOinitialize();
    	
    	clustering();
    	
    	
    	
    	
    }
	
    public void PSOinitialize()
    {
    	double max_diff;
		int l=0;
		initializeParticles();
    	initializemembershipmatrix();
    /*	for(int i=0;i<clusters;i++)
		{
			for (int j=0;j<document_number;j++)
			{
			System.out.print("	"+degreeOfMembership[3][i][j]);
			}
			System.out.println("\n");
		}
    	for (int i=1;i<swarm_size;i++)
    	{
    	calculateCenterVectors(i);
    	}*/
    	double[] fitness=new double[swarm_size];
    	double[] pbest=new double[swarm_size];
		for(int i=0;i<iterations;i++)
		{
			System.out.println("iteration: "+i);
			
    		for(int j=0;j<swarm_size;j++)
    		{
    			
    		    
    			fitness[j]=findfitnessfunction(j);
    			if(fitness[j]<pbest[j])
    			{
    				pbest[j]=fitness[j];
    				for (int c=0;c<clusters;c++)
    				{
    					for (int d=0;d<dimensions;d++)
    					{
    						bestvector[j][c][d]=particles[j][c][d];
    					}
    					
    				}
    				
    			}
    		
    				
    			//calculateCenterVectors( j );
    				//updateVelocity(j);
    				//max_diff = updateDegreeOfMembership(j);
    		
    				//System.out.println("Iteration:  "+i+"  max diff : "+max_diff);
    				
    		}
    		gbest=largest(pbest);
            
 		for(int p=0;p<swarm_size;p++){
 			if(gbest==pbest[p])
 			{
 				pos=p;
 				break;
 			}
 		}
 		for (int c=0;c<clusters;c++)
			{
				for (int d=0;d<dimensions;d++)
				{
					globalvector[c][d]=particles[pos][c][d];
				}
				
			}
    		for(int s=0;s<swarm_size;s++)
    		{
    			for(int n=0;n<clusters;n++)
    			{
    				for(int a=0;a<dimensions;a++)
    				{
    				
    					velocity[s][n][a]=(constants.W*velocity[s][n][a])+(constants.C1*r1.nextDouble()*(bestvector[s][n][a]-particles[s][n][a]))+(constants.C2*r1.nextDouble()*(globalvector[n][a]-particles[s][n][a]));
    					
    				
    				
    					particles[s][n][a]=particles[s][n][a]+velocity[s][n][a];
    					
    				}
    				
    				
    			}
    		}
    		Updatemembershipmatrix();
    	
    		
    	}
		
		
		System.out.println("PSO Finished");
    }
	public void clustering()
	{
		
		int j;
		
		
		double highest;
		for(int l=0;l<clusters;l++)
		{
			for(int g=0;g<document_number;g++)
			{
				cluster_membership[g][l]=degreeOfMembership[pos][l][g];
				
			}
		}
		for (int i = 0; i <document_number ; i++) {
		
		highest = 0.0;
		
		for (j = 0; j < clusters ; j++) {
		
			//System.out.print(cluster_membership[i][j]+"  ");
		if (cluster_membership[i][j] > highest ) {
			second_highest=choice;
		highest = cluster_membership[i][j];
		choice=j;
		
		}
	
		}
		highest=0.0;
		if(choice==0)
		{
			for (j = 0; j < clusters ; j++) {
				
				//System.out.print(cluster_membership[i][j]+"  ");
			if (cluster_membership[i][j] > highest ) {
				
			highest = cluster_membership[i][j];
			second_highest=j;
			
			}
		
			}
		}

	     check_membership[i][0]=choice;
	     check_membership[i][1]=second_highest;
	 	cluster[choice]=++cluster[choice];
	 	cluster[second_highest]=++cluster[second_highest];	//	System.out.println("\n");
		
		}
		for(int k=0;k<clusters;k++)
		{
			System.out.println("Cluster "+k+" = "+cluster[k]);
		}
	}
    public double largest(double []pbest)
    {
    	 double smallest = pbest[0];
         double largetst = pbest[0];
        
         for(int i=1; i<swarm_size; i++)
         {
                 if(pbest[i] > largetst)
                         largetst = pbest[i];
                 else if (pbest[i] < smallest)
                	     
                         smallest = pbest[i];
                
         }
        
         
    	return smallest;
    }
    	void updateVelocity(int j1){
    		
    		
    		
    		
    		
    	}
    	
   public void calculateCenterVectors(int j1)
     {

	  
	  
	   preCompute1(j1);//precomputing the powers of membership matrix
	   calcCenter(j1);
     }
	public void preCompute(int j1)
	{
		
		for (int i = 0; i < clusters ; i++) {
			for (int j = 0; j <document_number ; j++) {
			pow[i][j] = Math.pow (degreeOfMembership[j1][i][j], fuziness );
			}
			}	
		for(int i=0;i<clusters;i++)
		{
			for(int j=0;j<document_number;j++)
			{
				pow_transpose[j][i]=pow[i][j];
				
			}
		}
	}
	public void preCompute1(int j1)
	{
		
		for (int i = 0; i < clusters ; i++) {
			for (int j = 0; j <document_number ; j++) {
			pow1[i][j] = Math.pow (degreeOfMembership[j1][i][j], fuziness );
			}
			}	
		for(int i=0;i<clusters;i++)
		{
			for(int j=0;j<document_number;j++)
			{
				pow_transpose1[j][i]=pow1[i][j];
				
			}
		}
	}
	public void calcCenter(int j1)
	{
		System.out.println("in the loop :"+j1);
    	for (int j = 0; j < clusters ; j++) {
    	for (int k = 0; k < dimensions ; k++) {
    	numerator1 = 0.0;
    	denominator1 = 0.0;
    	for (int i = 0; i < document_number ; i++) {
    	numerator1 += pow_transpose1[i][j] * m.tfidf_array[i][k];
    	denominator1 += pow_transpose1[i][j];
    	}
    	
    	if(Double.isNaN(numerator1/denominator1))
    	{
    		particles[j1][j][k] = 0.0 ;
    	}
    	else
    	{
    		particles[j1][j][k] = numerator1/denominator1 ;
    	}
    	}
    	}
	}
	public double findfitnessfunction(int j1)
	{
		preCompute(j1);
		double temp=0.0;
		double sum=0.0;
		double sum2=0.0;
		for (int k=0;k<document_number;k++)
		{
			sum=0.0;
			for(int l=0;l<clusters;l++)
			{
		
		          for (int i=0;i<dimensions;i++)
		           {
			
		        	   temp+=(Math.pow ( m.tfidf_array[k][i] -particles[j1][l][i], 2));
		        	  	
			
			
		            }
		          temp=Math.sqrt(temp);
		          
		          temp= pow_transpose[k][l]*temp;
		          
		          sum=sum+temp;
		      }
			sum2+=sum;
		}
		return sum2;
	}
public void transpose()
{
	for(int i=0;i<document_number;i++)
	{
		for(int j=0;j<clusters;j++)
		{
			membership_transpose[j][i]=cm.degreeOfMembership[i][j];
			
		}
	}
	
	
	
	
	
	
	
	
}
	
	

public void Updatemembershipmatrix()
{
	for (int i1=0;i1<swarm_size;i1++)
	{
		for (int j1=0;j1<clusters;j1++)
		{
			for (int k1=0;k1<document_number;k1++)
			{
				
					degreeOfMembership[i1][j1][k1]=calculateMembership(i1,j1,k1);
				
			}
		}
	}
}
	
	public void initializemembershipmatrix()
	{
		for(int i1=0;i1<constants.SWARM_SIZE;i1++)
		{
			for (int j1=0;j1<clusters;j1++)
			{
				for (int k1=0;k1<document_number;k1++)
				{
				
					if(i1==0)
						{
						degreeOfMembership[0][j1][k1]=membership_transpose[j1][k1];
						}
					else
						degreeOfMembership[i1][j1][k1]=calculateMembership(i1,j1,k1);
				
			}
		}
		}
			/*Random r1 = new Random();
			int r;
			double s;
			int  rval;
		for (int i1=1;i1<swarm_size;i1++)
		{
			for (int j1=0;j1<document_number;j1++)
			{
				s = 0.0; // probability sum 
				r = 50; // remaining probability 
				for (int k1=1;k1<clusters/2;k1++)
				{
					int val = r1.nextInt(r);//making sure that probability sum for each data point is equal to 1 over all the clusters
					//System.out.println(val);
					rval = val % (r + 1);
					r =r-rval ;
					temp[i1][j1][k1] = rval/100.0;
					s += temp[i1][j1][k1];
				}
				temp[i1][j1][0] = 0.5-s;
				s=0.0;
				r = 50;
				for (int k1 = clusters/2+1; k1 < clusters ; k1++) {
					int val = r1.nextInt(r);//making sure that probability sum for each data point is equal to 1 over all the clusters
					//System.out.println(val);
					rval = val % (r + 1);
					r =r-rval ;
					temp[i1][j1][k1] = rval/100.0;
					s += temp[i1][j1][k1];
					
					}
				//System.out.println(s);
				temp[i1][j1][clusters/2] = 0.5-s;
			}
		}
		for (int k=1;k<swarm_size;k++)
		{
			
	    	for(int i=0;i<document_number;i++)
		{
			  for(int j=0;j<clusters;j++)
			 {
				degreeOfMembership[k][j][i]=temp[k][i][j];
				
			 }
		}
		}*/
	}
	public double calculateMembership(int particle,int c,int document)
	{
		double sum =0.0;
		double sum_numerator=0.0;
		double sum_denominator=0.0;
		double power=2/(fuziness-1);
		double temp=0.0;
		double result=0.0;
		double sqr_num=0.0;
		double sqr_den=0.0;
		for (int x=0;x<clusters;x++)
			
		{
			
			temp=0.0;
			sum_numerator=0.0;
			sum_denominator=0.0;
			sqr_num=0.0;
			sqr_den=0.0;
			for (int i=0;i<dimensions;i++)
			{
				
				numerator= Math.pow ( m.tfidf_array[document][i] -particles[particle][c][i], 2);
				sum_numerator+=numerator;
				denominator=Math.pow ( m.tfidf_array[document][i] -particles[particle][x][i], 2);
				sum_denominator+=denominator;
				
			}
			//System.out.println(sum_numerator);
			//System.out.println(sum_denominator);
			sqr_num=(Math.sqrt(sum_numerator));
			sqr_den=Math.sqrt(sum_denominator);
			if(Double.isNaN(sqr_num/sqr_den)){
				temp=0.0;
				System.out.println(sqr_num);
				System.out.println(sqr_den+"\n");
			}
			else
			{
			temp=sqr_num/sqr_den;
			}
			sum+=Math.pow(temp,power);
		   //see here
			
			
		}
		
		//System.out.println(sum);
		result=1.0/sum;
	//	System.out.println(result);
		//System.out.println(result);
		return result;
	}
	
	public void initializeParticles()
	{
		for (int i=0;i<swarm_size;i++)
		{
			for (int j=0;j<clusters;j++)
			{
				for (int k=0;k<dimensions;k++)
				{
					if(i==0)
					{
						particles[i][j][k]=cm.cluster_centers[j][k];
					}
					else{
						particles[i][j][k]=r1.nextDouble();
					}
				}
			}
		}
	}
	
}
