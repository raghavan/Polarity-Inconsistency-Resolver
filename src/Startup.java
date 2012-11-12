import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.cambridge.CambridgeDictReader;


public class Startup {
	public static void main(String args[]){
		IDictionaryReader idictReader = new CambridgeDictReader();
		idictReader.printString("Raghavan");
	}
}
