package propertyHandler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import util.Constants;

public class PropertyHandlerImpl {
	


	public static void writePropertyFile(String fileName,
			Map<String, String> wordPolarity) {

		PrintWriter out = null;
		try {
			out = new PrintWriter(new File(fileName));
			for (Map.Entry<String, String> entry : wordPolarity.entrySet()) {
				out.println(entry.getKey() + "=" + entry.getValue());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			System.out.println("File written Successfully");
			out.close();
		}
	}

	public static Map<String, String> readpropFile(String fileName) {
		ResourceBundle bundle = ResourceBundle.getBundle(fileName);
		Map<String, String> propContents = new LinkedHashMap<String, String>();

		Enumeration keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			propContents.put(key, bundle.getString(key));
			//System.out.println(key + "=" + bundle.getString(key));
		}
		return propContents;
	}

	public static Map<String, String> getInconsistentWords() {
		Map<String,String> inconsistentWordPolarity = new HashMap<String, String>();
		try {
			FileInputStream fstream = new FileInputStream(Constants.INCONSISTENT_POLAIRY_OUTPUT_FILE);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			ArrayList<String> wordList = new ArrayList<String>();
			while ((strLine = br.readLine()) != null) {
				String[] a = strLine.split("\\s+");
				for (int i = 0; i < a.length; i++) {
					if (a[i].equals(">>>>>>>>*") || a[i].equals(">>>>>>>>")) {
						for (int j = i + 1; j < a.length; j++) {
							if (a[j].equals("--"))
								break;
							// System.out.println("word: "+a[j]);
							wordList.add(a[j]);
						}
					}
				}
			}
			Iterator<String> it = wordList.iterator();
			while (it.hasNext()) {
				String[] x = it.next().split("[\\[\\]]");
				for (int i = 0; i < x.length; i = i + 2) {
					//System.out.println(x[i] + " : " + x[i + 1]);
					inconsistentWordPolarity.put(x[i].toLowerCase(), x[i+1].toLowerCase());
				}
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return inconsistentWordPolarity;
	}
}
