package util;

public interface Constants {
	final static String WORD_POLARITY_SRC_FILENAME = "3in1_adj.properties";
	public static final String INCONSISTENT_POLAIRY_OUTPUT_FILE = "output.txt";
	String WORDNET_SENSES_FREQ = "wordnet_sense_freq.properties";
	String EXISTING_INCONST_PROP = "InconsistentWordPolarity.properties";
	String OUTPUT_PROP_FILE = "UpdatedWordPolarity.properties";
	
	
	
	String WELCOME_NOTE = "Welcome to Word Polarity Support Tool\n \bInstruction to use this tool:\n\n\n\t";
	String WELCOME_INSTRUCTIONS = 	"1.Click Start to enter the application.\n" +
			"\n\t2.Clicking Next will move on to the next conflicting set of words.\n" +
			"\n\t3.Senses/Words can be moved along the screen for ease of visualization.\n" +
			"\n\t4.If Word is clicked the Polarity keeps on Changing \n (For Ex.1st Click change to Positive, next click to Negative and keep on rotating)\n" +
			"\n\t5.Save to File button helps to save the changes intermittently to avoid data loss" +
			"" +
			"\n\n\tDesigned By," +
			"\n\tRaghavan KL" +
			"\n\tPradeepa" +
			"\n\tKeerthika";
}
