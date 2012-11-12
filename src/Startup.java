import java.util.List;

import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.cambridge.CambridgeDictReader;


public class Startup {
	public static void main(String args[]){
		IDictionaryReader idictReader = new CambridgeDictReader();
		List<String> meanings = idictReader.getMeaning("large");
		for(String str: meanings){
			System.out.println(str);
		}
	}
}
