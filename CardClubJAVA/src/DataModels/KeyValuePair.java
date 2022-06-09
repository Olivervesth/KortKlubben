package DataModels;

import java.io.Serializable;

/**
 * Class for pairing a key and a value
 */
public class KeyValuePair implements Serializable {
    /**
     * Key
     */
    private Object key;
    /**
     * Value of key
     */
    private Object value;

    /**
     * Constructor for a KeyValuePair
     *
     * @param key   Object
     * @param value Object
     */
    public KeyValuePair(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Method to get the key
     *
     * @return Object key
     */
    public Object getKey() {
        return this.key;
    }

    /**
     * Method to get the value
     *
     * @return Object value
     */
    public Object getValue() {
        return this.value;
    }
}
