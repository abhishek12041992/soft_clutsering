package Tfidf;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Driver {

	
	static String filepath="/home/anshu/assign1/textfiles";
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DocumentParser docparser= new DocumentParser();
		
		docparser.parseFiles(filepath);
		docparser.tfIdfCalculator();
		

	}

}
