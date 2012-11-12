
An existing SAT solver is run over the words obtained from applexicon/OpinonFinder/General Inquirer and a set of inconsistent word polarity 
is detected based on the word frequency obtained from the wordnet library.


1.	From the returned set of inconsistent words this tool checks if there are any missing senses in Oxford and Cambridge.

2.	Then again it represents the Graph G (V, E)
 V= {huge, immense, vast, usually great in size, extremely large, enormous, very great extent}

3.	From the User Interface, User should has an option to link the words with similar meaning; For example in the above given figure 2 “Usually great in size” and “extremely large” have the same meaning so one can be discarded.

4.	From the user interface there is an option to mark the polarity of the senses obtained from Cambridge/Oxford dictionary.

5.	Again it recalculates the polarity of the inconsistent words based on the newer values derived using the fractional reduction technique.

6.	Then it updates the inconsistent words in the existing input set with the newer polarities and runs the SAT solver again.
