package util;

public enum Polarity {
	Positive(1), Negative(2), Neutral(3);

	int value;

	Polarity(int value) {
		this.value = value;
	}

	public static String fromValue(int a) {
		for(Polarity polarity : Polarity.values()){
			if(polarity.value == a){
				return polarity.toString();
			}
		}
		return null;
	}

}
