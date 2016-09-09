import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class StringToDouble {
	public static PSOConstants constants= new PSOConstants();
 // static int dimensions=constants.dimensions;
  static int rows;
 static String [] documents;
	static int length;
  static	int length2;
  static int dimensions=constants.dimensions;
  static String [] words;
	double[][] twoDArrayofStrings(String path) throws FileNotFoundException
	{
		int count=0;
		double perc;
		Scanner in = new Scanner(new File(path));
		List<String[]> lines = new ArrayList<>();
		while(in.hasNextLine()&& count<2000) {
		    String line = in.nextLine().trim();
		 
		   perc= (count++ /4000.0)*100;
		   
		   System.out.println(perc);
		    String[] splitted = line.split(";");
		    for(int i = 0; i<splitted.length; i++) {
		        //get rid of additional " at start and end
		        splitted[i] = splitted[i].substring(0, splitted[i].length());
		    }
		    lines.add(splitted);
		}
		in.close();

		//pretty much done, now convert List<String[]> to String[][]
		String[][] result = new String[lines.size()][];
		for(int i = 0; i<result.length; i++) {
		    result[i] = lines.get(i);
		}
        rows=result.length;
	//rows=1000;
		//double count2=0,perc2;
		System.out.println("Number of rows = "+rows);
		System.out.println(result[1][1]);
	
		 documents=new String[rows];
		for (int i=1;i<rows;i++)
		{
			documents[i-1]=result[i][0];
		}
	/*	for (int i=0;i<constants.Document_number;i++)
		{
			if(documents[i]==null)
			{
				System.out.println("this is wrong");
				
			}
			System.out.println(documents[i]+"\n");
		}*/
		length=result[0].length;
		// length2=result[1].length;
		// length2+=length;
		 System.out.println(length);
		// System.out.println(length2);
		int i1;
		int i2;
		//words=new String[length+length2];
		words=new String[length];
		for (i1=1;i1<length;i1++)
		{
			
			words[i1-1]=result[0][i1];
			
		}
		System.out.println(words[209]);
		System.out.println(words[823]);
			/*	for (i2=length;i2<length2;i2++)
		{
			
			words[i2]=result[1][i2-length];
			
			
		}*/
	/*	for ( i1=0;i1<length;i1++)
		{
			System.out.println(words[i1]);
			
		}
		for ( int i2=length;i2<length2;i2++)
		{
			System.out.println(words[i2]);
			
			
		}
		*/
		 //dimensions=length+length2;
		 //System.out.println(dimensions);
		double[][] matrix=new double[rows][dimensions];
		for(int i=1;i<rows;i++)
		{
			//perc2=(i/rows)*100;
			//int col=result[i].length;
			//int count1=0;
			for(int j=1;j<dimensions;j++)
			{
				//System.out.println("Progressing "+(count1++));
				matrix[i-1][j-1]=Double.parseDouble(result[i][j]);
			}
		}
		
		System.out.println("Its done for "+path);
		//System.out.println(Arrays.deepToString(matrix));
		return matrix;
		

	}

	
}
