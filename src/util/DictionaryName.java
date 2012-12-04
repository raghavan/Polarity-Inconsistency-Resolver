package util;

public enum DictionaryName {

	CAMBRIDGE_DICTIONARY("Cambrige Dictionary"),
	OXFORD_DICTIONARY("Oxford Dictionary"),
	WORDNET_DICTIONARY("WordNet");
	
	public String value;

	private DictionaryName(String name) {
		this.value = name;
	}
	
	public static DictionaryName fromString(String name) {
		if (name != null) {
			for (DictionaryName b : DictionaryName.values()) {
				if (name.equalsIgnoreCase(b.value)) {
					return b;
				}
			}
		}
		return null;
	}


}
