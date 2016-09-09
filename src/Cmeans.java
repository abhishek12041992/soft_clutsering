import java.util.Random;

public class Cmeans {

	static PSOConstants constants=new PSOConstants();
	static StringToDouble s=new StringToDouble();
	static double fuzziness=constants.fuziness;
	static double epsilon=constants.epsilon;
	static int d=constants.Document_number;
	static int c=constants.clusters;
	static int dimensions=constants.dimensions;
	//static int dimensions=2224;
	static double[][] degreeOfMembership=new double[d][c];
	static double[][] check_membership=new double[d][2];
	static double [][] cluster_centers=new double[c][dimensions];
	static int rangeMin=0;
	static int  rangeMax=1;
	static double numerator;
	static double denominator;
	int choice;
	int second_highest;
	static int[] cluster=new int[c];
    static Fpso f=new Fpso();
    
	double [][] pow=new double[d][c];
	
	static ReadingMatrix m=new ReadingMatrix();
	public void doCmeans()
	{
		
		
		System.out.println("Running Cmeans......");
		initializeMembershipMatrix();
	/*	for(int i=0;i<d;i++)
		{
			for (int j=0;j<c;j++)
			{
			System.out.print("	"+degreeOfMembership[i][j]);
			}
			System.out.println("\n");
		}*/
	   fcm();//start the Fuzzy Cmeans Clustering process;
	  /* for(int i=0;i<d;i++)
		{
			for (int j=0;j<c;j++)
			{
			System.out.print("	"+degreeOfMembership[i][j]);
			}
			System.out.println("\n");
		}*/
		clustering();//straight forward clustering
		
		f.doPSO();
	}
	public void clustering()
	{
		
		int j;
		
		
		double highest;
		
		for (int i = 0; i <d ; i++) {
		
		highest = 0.0;
		for (j = 0; j < c ; j++) {
		if (degreeOfMembership[i][j] > highest ) {
			second_highest=choice;
		highest = degreeOfMembership[i][j];
		choice=j;
		
		}
		
		}
		highest=0.0;
		if(choice==0)
		{
			for (j = 1; j < c ; j++) {
				if (degreeOfMembership[i][j] > highest ) {
					
				highest = degreeOfMembership[i][j];
				second_highest=j;
				
				}
				
				}
		}
	     check_membership[i][0]=choice;
	     
	     check_membership[i][1]=second_highest;
	 	cluster[choice]=++cluster[choice];
	 	cluster[second_highest]=++cluster[second_highest];

		}
		for(int k=0;k<c;k++)
		{
			System.out.println("Cluster "+k+" = "+cluster[k]);
		}
	}
	public void initializeMembershipMatrix()
	{
		//Initializing degree of membership matrix
	           	Random r1 = new Random();
				int r;
				double s;
				int  rval;
				for (int i = 0; i < d ; i++) {
				s = 0.0; // probability sum 
				r = 50; // remaining probability 
				for (int j = 1; j < c/2 ; j++) {
				int val = r1.nextInt(r);//making sure that probability sum for each data point is equal to 1 over all the clusters
				//System.out.println(val);
				rval = val % (r + 1);
				r =r-rval ;
				degreeOfMembership[i][j] = rval/100.0;
				s += degreeOfMembership[i][j];
				}
				degreeOfMembership[i][0] = 0.5-s;
				s=0.0;
				r = 50;
				for (int j = c/2+1; j < c ; j++) {
					int val = r1.nextInt(r);//making sure that probability sum for each data point is equal to 1 over all the clusters
					//System.out.println(val);
					rval = val % (r + 1);
					r =r-rval ;
					degreeOfMembership[i][j] = rval/100.0;
					s += degreeOfMembership[i][j];
					
					}
				//System.out.println(s);
				degreeOfMembership[i][c/2] = 0.5-s;
				}
				
	}
	public void fcm()
	{
		
		
		double max_diff;
		int i=0;
		do {
			System.out.println("in fcm");
		calculateCenterVectors( );
		max_diff = updateDegreeOfMembership( );
		System.out.println("Iteration:  "+ i++ +"  max diff : "+max_diff);
		} while (max_diff > epsilon );
		System.out.println("Fuzzy Cmeans Finished");
	}
	double updateDegreeOfMembership ( )
	{
	int i, j;
	double newUij ;
	double max_diff = 0.0, diff ;
	for (j = 0; j < c; j++) {
	for (i = 0; i < d ; i++) {
	newUij = getValue (i, j);
	diff = newUij - degreeOfMembership[i][j];
	if (diff > max_diff ) max_diff = diff ;
	degreeOfMembership[i][j] = newUij ;
	}
	}
	return max_diff ;
	}
	double getValue (int i, int j)
	{
	int k;
	double t, p, sum;
	sum = 0.0;
	p = 2/(fuzziness -1);
	for (k = 0; k < c ; k++) {
	t = distanceFromCenter(i, j)/distanceFromCenter(i,k);
	t = Math.pow (t, p);
	sum += t;
	}
	return 1.0/sum;
	}
	public double distanceFromCenter(int i ,int j)
	{
		int k;
		double sum = 0.0;
		for (k = 0; k < dimensions ; k++) {
		sum += Math.pow ( m.tfidf_array[i][k] -cluster_centers[j][k], 2);
		}
		return Math.sqrt (sum);
	}
	public void calculateCenterVectors()
	{
		int i, j, k;
		double numerator , denominator ;
		double[][] pow=new double[d][c];
		preCompute();//precomputing the powers of membership matrix
		calcCenter();
	}
	public void preCompute()
	{

		//precomputing the powers of membership
		for (int i = 0; i < d ; i++) {
		for (int j = 0; j < c ; j++) {
		pow[i][j] = Math.pow (degreeOfMembership[i][j], fuzziness );
		}
		}
	}
    public void calcCenter()
    {
    	//System.out.println("in the loop");
    	for (int j = 0; j < c ; j++) {
    	for (int k = 0; k < dimensions ; k++) {
    	numerator = 0.0;
    	denominator = 0.0;
    	for (int i = 0; i < d ; i++) {
    	numerator += pow[i][j] * m.tfidf_array[i][k];
    	denominator += pow[i][j];
    	}
    	
    	if(Double.isNaN(numerator/denominator))
    	{
    		cluster_centers [j][k] = 0.0 ;
    	}
    	else
    	{
    		
    		cluster_centers [j][k] = numerator/denominator ;
    	}
    	}
    	}
    }
}
