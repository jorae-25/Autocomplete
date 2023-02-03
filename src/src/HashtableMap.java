package src;

import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * A Map from keys to values that is implemented with a hash table.  Chaining is used to resolve collisions.
 * The user may set the starting size of the hash table, which never changes.
 */
public class HashtableMap<K, V> {

    // The ArrayList that stores the table itself.  Because we are using chaining, each entry in the ArrayList
    // is a linked list, containing key-value pairs.
    private final ArrayList<LinkedList<KVPair<K, V>>> table;

    /**
     * Create a new hash table of size initialSize.
     */
    HashtableMap(int initialSize) {
        // Initialize table to have a pre-defined size.
        table = new ArrayList<LinkedList<KVPair<K, V>>>(initialSize);

        // Go through table and add an empty linked list to each slot.
        for (int x = 0; x < initialSize; x++)
        {
            table.add(new LinkedList<KVPair<K, V>>());
        }
    }

    /**
     * Add a new key-value pair into the hash table.  If there is already an entry in
     * the table for this key, then overwrite it with the new value.
     *
     * Since we are using chaining, add items to the front of the linked
     * list (using addFirst), once you have found the correct index in the
     * table in which to insert.
     */
    public void put(K newKey, V newValue) {
        // This is the index you should use to insert the key-value pair.
        int hashIndex = Math.abs(newKey.hashCode() % table.size());
        LinkedList<KVPair<K,V>> currentList = table.get(hashIndex);
        boolean sameKeyFound = false;
        for(KVPair<K,V> pair: currentList){
            if(pair.key.equals(newKey)) { //determines if the newkey is already in the LinkedList
                pair.value = newValue;
                sameKeyFound = true;
                break;
            }
        }
        if(!sameKeyFound){
            KVPair<K,V> newPair = new KVPair<>();
            newPair.key = newKey;
            newPair.value = newValue;
            currentList.addFirst(newPair);
        }
    }


    /**
     * Get a value from this hash table, based on its key.  If the key doesn't already exist in the table,
     * this method returns null.
     */
    public V get(K searchKey) {
        int hashIndex = Math.abs(searchKey.hashCode() % table.size());
        LinkedList<KVPair<K,V>> currentList = table.get(hashIndex);
        for(KVPair<K,V> pair: currentList){
            if(pair.key.equals(searchKey)) {
                return pair.value;
            }
        }
        return null;
    }

    /**
     * Test if this key exists in the hash table, and return true if it does, and false if it doesn't.
     */
    public boolean containsKey(K searchKey) {
        return get(searchKey) != null;
    }

    /**
     * Print the hash table.  If the table has more than 100 slots, only print the top 100 (indices 0-99).
     * Print the contents of each index in the table on a single line.  Include the index number, the
     * number of entries at that index (hint--use .size() on the linked list), and each individual entry
     * in the format "key: value" or something similar.
     *
     * Hint: Try overriding toString() in the KVPair class below.
     *
     * Example:
     * Index 0 (2): key1: value1, key2: value2
     * Index 1 (3): key3: value3, etc, etc
     */
    public void printTable() {
        if(table.size()>100){
            for(int i = 0; i<100;i++){
                LinkedList<KVPair<K,V>> currentList = table.get(i);
                System.out.print("Index "+ i + " (" + currentList.size() + "): [");
                for(KVPair<K,V>pair : currentList){
                    if(pair.equals(currentList.getLast())){
                        System.out.print(pair);
                    }
                    else{
                        System.out.print(pair +", ");
                    }

                }
                System.out.print("]");
                System.out.println();
            }
        }
        else{
            for(int i = 0; i<table.size();i++){
                LinkedList<KVPair<K,V>> currentList = table.get(i);
                System.out.print("Index "+ i + " (" + currentList.size() + "): [");
                for(KVPair<K,V>pair : currentList){
                    if(pair.equals(currentList.getLast())){
                        System.out.print(pair);
                    }
                    else{
                        System.out.print(pair +", ");
                    }
                }
                System.out.print("]");
                System.out.println();
            }
        }

    }
    /**
     * Return the total number of key-value pairs stored in the hash table.
     * Note, this is not the same thing as the number of slots in the table.
     */
    public int size() {
        int tableSize = 0;
        for (LinkedList<KVPair<K, V>> kvPairs : table) {
            tableSize += kvPairs.size();
        }
        return tableSize;
    }

    /**
     * Turn the hash table into a Set of all the keys in the table (values are ignored).
     * Do this by creating an empty HashSet<K> (built-in Java class),
     * looping through the hash table, looping over each linked list in each slot of the table,
     * and add()ing each key into the hash set.
     */
    Set<K> keySet() {
        HashSet<K> hashSet = new HashSet<>();
        for (LinkedList<KVPair<K, V>> currentList : table) {
            for (KVPair<K, V> pair : currentList) {
                hashSet.add(pair.key);
            }
        }
        return hashSet;
    }

    /**
     * It is very common to have private classes nested inside other classes.  This is most commonly used when
     * the nested class has no meaning apart from being a helper class or utility class for the outside class.
     * In this case, this KVPair class has no meaning outside of this Hashtable class, so we nest it inside here
     * so as to not prevent another class from declaring a KVPair class as well.
     */
    private static class KVPair<K, V> {
        public K key = null;
        public V value = null;
        public String toString() {
            String dataString ="";
            dataString = dataString + "{key=" + key + ", value=" + value + "}";
            return dataString;
        }
    }
}
