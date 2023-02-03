package src;

import java.util.*;

public class Autocorrecter {

    // Holds frequencies of every possible word.
    private final HashtableMap<String, Integer> wordFreqs;

    /**
     * Create a new Autocorrecter, based on the supplied map of word frequencies.
     */
    public Autocorrecter(HashtableMap<String, Integer> wordFrequencies)
    {
        wordFreqs = wordFrequencies;
    }

    /**
     * Returns the most frequent word in the wordFreqs map that has inputString as a prefix.
     * If no string in the wordFreq map starts with this string, return null.
     */
    public String getBestAutocomplete(String inputString)
    {
        Set<String>wordFreqSet = wordFreqs.keySet();
        int largestFreq = Integer.MIN_VALUE;
        String largestFreqWord = null;
        for(String s:wordFreqSet){
            if(s.startsWith(inputString)){
                if(wordFreqs.get(s)>largestFreq){
                    largestFreqWord = s;
                    largestFreq = wordFreqs.get(s);
                }
            }
        }
        return largestFreqWord;
    }

    /**
     * Return the set of possible words that are *both* an edit distance of 1 away from the inputString,
     * *and* are contained in our dictionary (wordFreq).
     */
    public Set<String> getBestAutocorrect(String inputString)
    {
        Set<String> edits = editDistance1(inputString);
        edits.retainAll(wordFreqs.keySet());
        return edits;
    }

    /**
     * Return the "best suggestions" for an inputString, based on both the most likely autocompletion,
     * and the set of possible autocorrections.  The suggestions are in decreasing sorted order of frequency.
     */
    public List<String> getBestSuggestions(String inputString)
    {
        String bestAutoComplete = getBestAutocomplete(inputString);
        Set<String> bestAutoCorrect = getBestAutocorrect(inputString);

        ArrayList<String> finalResult = new ArrayList<>(bestAutoCorrect);
        if(bestAutoCorrect.isEmpty()||(bestAutoComplete!=null &&!finalResult.contains(bestAutoComplete))){
            finalResult.add(0,bestAutoComplete);
        }
        WordByFrequencyComparator comp = new WordByFrequencyComparator();
        Mergesort.mergesort(finalResult, comp);

        return finalResult;
    }

    /**
     * Returns the set of possible strings that have an edit distance of 1 from string s.
     * (No need to modify this function.)
     */
    private static Set<String> editDistance1(String s)
    {
        HashSet<String[]> splits = new HashSet<>();
        for (int x = 0; x < s.length() + 1; x++)
        {
            splits.add(new String[] { s.substring(0, x), s.substring(x) });
        }

        HashSet<String> edits = new HashSet<>();

        // deletions
        for (String[] splitString : splits)
        {
            String L = splitString[0], R = splitString[1];

            // deletion
            if (!R.equals(""))
                edits.add(L + R.substring(1));

            // transposition
            if (R.length() > 1)
                edits.add(L + R.charAt(1) + R.charAt(0)+ R.substring(2));

            String alphabet = "abcdefghijklmnopqrstuvwxyz";

            // replace
            if (!R.equals(""))
            {
                for (char ch : alphabet.toCharArray())
                    edits.add(L + ch + R.substring(1));
            }

            // insert
            for (char ch : alphabet.toCharArray())
                edits.add(L + ch + R);
        }
        return edits;
    }

    /**
     * This comparator compares two strings according to their frequency in the wordFreq map.
     * The string that appears more frequently should be "less than" the other string.
     * This will sort the more common strings earlier in the list.
     * If two words have the same frequency, compare them alphabetically.
     */
    class WordByFrequencyComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int s1Freq = wordFreqs.get(s1);
            int s2Freq = wordFreqs.get(s2);
            if(s1Freq<s2Freq){
                return 1;
            }
            else if(s1Freq>s2Freq){
                return -1;
            }
            else{
                return s1.compareTo(s2);
            }
          }
        }
    }

