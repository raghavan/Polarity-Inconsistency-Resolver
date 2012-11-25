package propertyHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
		Map<String,String> propContents = new LinkedHashMap<String,String>();
		
		Enumeration keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			propContents.put(key, bundle.getString(key));
			System.out.println(key+"="+bundle.getString(key));
		}
		return propContents;
	}
}
