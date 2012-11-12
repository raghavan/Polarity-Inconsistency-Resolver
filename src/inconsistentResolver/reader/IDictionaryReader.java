package inconsistentResolver.reader;

import java.util.List;

public interface IDictionaryReader{
	void printString(String value);
	List<String> getMeaning(String word);
}