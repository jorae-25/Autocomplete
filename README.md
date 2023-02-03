# Autocomplete

Program that autocompletes text input. Once the user types in text, the predicted word will be printed in addition to an array of the next best suggestions.
A HashMap stores word frequencies where the key is the word and the value is the number of times the word has appeared within the example text file. The most
frequent word (i.e the one that has apppeared the most times) in the map that has the user input string as a prefix is then printed. 

To run the program, run AutoCorrectDemo.java.

Example output:

Enter a word, or 'stop' to end: craz

Autocomplete: crazy

Autocorrect (order might be different):

  [crazy]
  
Best suggestions (order should match):

  [crazy]
  
