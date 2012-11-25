
An existing SAT solver is applied over the words obtained from applexicon/OpinonFinder/General Inquirer and a set of inconsistent word polarity 
is detected based on the word frequency obtained from the wordnet library.

1.	For the returned set of inconsistent words we manually check if there are any missing senses in Oxford, Cambridge and Webster.

2.	Then again we represent the Graph G (V, E)
V= {huge, immense, vast, usually great in size, extremely large, enormous, very great extent}

3.	From the User Interface, User should have an option to link the words with similar meaning; For example in the above given figure 2 “Usually great in size” and “extremely large” have the same meaning so one can be discarded.

4.	From the user interface there should be an option to mark the polarity of the senses obtained from Cambridge/Oxford dictionary.

5.	Again recalculate the polarity of the inconsistent words based on the newer values derived using the technique explained in the introduction.

6.	Update the inconsistent words in the existing input set with the new polarities and run the SAT solver once again.
