import java.io.File;


public class CrawlFile {

	public static void main(String[] args) {
		//Usage: node run.js path/to/input/dir path/to/output/dir path/of/preprocess.js
		// TODO Auto-generated method stub
		File file = new File("/Users/siyang/Documents/itp/data/");
		String [] test;
		test = file.list();
		for(int i = 0; i <test.length; i++){
			System.out.println(test[i]);
		}
	}

}
