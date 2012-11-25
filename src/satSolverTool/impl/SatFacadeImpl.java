package satSolverTool.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import satSolverTool.ISatFacade;

public class SatFacadeImpl implements ISatFacade {

	public List<String> getInconsistentWords() {
		List<String> wordsWithIncnstPolarity = new ArrayList<String>();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("textfile.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				System.out.println(strLine);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return wordsWithIncnstPolarity;
	}

	public void updateInconsistentWords(String filename, Map<String,String> wordPolarity) {
		try {
			File file = new File(filename);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for(String word : wordPolarity.keySet()){
				String polarity = wordPolarity.get(word);
				String data = word+"="+polarity;
				bufferWritter.write(data);
			}
			bufferWritter.close();
			
			System.out.println("File written successfully");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
