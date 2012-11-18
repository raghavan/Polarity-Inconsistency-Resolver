import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.oxford.OxfordDictReader;

import java.util.List;


public class Startup {
	public static void main(String args[]){
		IDictionaryReader idictReader = new OxfordDictReader();
		List<String> meanings = idictReader.getMeaning("large");
		for(String str: meanings){
			System.out.println(str);
		}
	}
}
