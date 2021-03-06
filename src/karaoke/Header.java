package karaoke;

import java.util.Map;

/**
 * immutable data type representing a karaoke music header
 * @author mattj
 *
 */
public class Header {
    
    private final String composer;
    private final String key;
    private final String defaultLength;
    private final String meter;
    private final String tempo;
    private final String title;
    private final String voices;
    private final String index;
    
    // Abstraction Function:
    //   AF(composer, key, defaultLength, meter, tempo, title, voices, index) = 
    //      a Header instance with composer of name composer
    //                             key of key
    //                             default note length of defaultLength
    //                             meter of meter
    //                             tempo of tempo
    //                             title of title
    //                             number of voices voices
    //                             piece index of index
    //
    // Rep Invariant:
    //   -every header must have a:
    //      index number
    //      title
    //      key of the piece
    //
    // Rep Safety Argument:
    //   all fields are private final
    //   all getter methods return Strings which are immutable 
    //
    // Thread Safety Argument:
    //   no rep fields are mutated outside of the constructor, which is a threadsafe method by default
    
    
    /**
     * creates an instance of a header object
     * @param headerMap a map mapping header fields to their values
     */
    public Header(Map<Character, String> headerMap) {
        if (headerMap.containsKey('C')) { 
            this.composer = headerMap.get('C');
        } else {
            this.composer = "Unknown";
        }
        
        if (headerMap.containsKey('X')) {
            this.index = headerMap.get('X');
        } else {
            throw new AssertionError("missing index value");
        }
        
        if (headerMap.containsKey('T')) {
            this.title = headerMap.get('T');
        } else {
            throw new AssertionError("missing title");
        }
        
        if (headerMap.containsKey('K')) { 
            this.key = headerMap.get('K');
        } else {
            throw new AssertionError("missing key");
        }
        
        if (headerMap.containsKey('M')) {
            this.meter = headerMap.get('M');
        } else {
            this.meter = "4/4";
        }
        
        if (headerMap.containsKey('L')) {
            this.defaultLength = headerMap.get('L');
        } else {
            final double numerator = (double) Integer.parseInt(this.meter.split("/")[0]);
            final double denominator = (double) Integer.parseInt(this.meter.split("/")[1]);
            final double ratio = numerator/denominator;
            final double cutoff = .75;
            if (ratio < cutoff) {
                this.defaultLength = "1/16";
            } else {
                this.defaultLength = "1/8";
            }
        }
        
        if (headerMap.containsKey('Q')) {
            this.tempo = headerMap.get('Q');
        } else {
            this.tempo = this.defaultLength + "=100";
        }
        
        if (headerMap.containsKey('V')) {
            this.voices = headerMap.get('V');
        } else {
            this.voices = "1";
        }
    }
    
    
    /**
     * check the stated and implied rep invariant
     */
    private void checkRep() {
        assert this.key != null;
        assert this.title != null;
        assert this.index != null;
    }
    
    /**
     * gets the name of the composer
     * @return the composer
     */
    public String getComposer() {
        checkRep();
        return this.composer;
    }
    
    /**
     * gets the key
     * @return the key
     */
    public String getKey() {
        checkRep();
        return this.key;
    }
    
    /**
     * gets the default note length
     * @return the default note length
     */
    public String getDefaultLength() {
        checkRep();
        return this.defaultLength;
    }
    
    /**
     * gets the meter
     * @return the meter
     */
    public String getMeter() {
        checkRep();
        return this.meter;
    }
    
    /**
     * gets the tempo
     * @return the tempo
     */
    public String getTempo() {
        checkRep();
        return this.tempo;
    }
    
    /**
     * gets the title
     * @return the title
     */
    public String getTitle() {
        checkRep();
        return this.title;
    }
    
    /**
     * gets the number of voices
     * @return the voices
     */
    public String getVoices() {
        checkRep();
        return this.voices;
    }
    
    /**
     * gets the index
     * @return the index
     */
    public String getIndex() {
        checkRep();
        return this.index;
    }
    
    /**
     * get the length of the default note as a double
     * @return the length of the default note
     */
    public double getDefaultLengthDouble() {
        checkRep();
        String numerator;
        String denominator;
        if (this.defaultLength.length() == 1) {
            numerator = this.defaultLength;
            denominator = "1";
        } else {
            final String[] lengthParams = this.defaultLength.split("/");
            numerator = lengthParams[0];
            denominator = lengthParams[1];
        }
        int numeratorInt = 1;
        int denominatorInt = 1;
        if (numerator.length() > 0) {
            numeratorInt = Integer.parseInt(numerator);
        }
        if (denominator.length() > 0) {
            denominatorInt = Integer.parseInt(denominator);
        }
        return ((double) numeratorInt) / ((double) denominatorInt);
    }
    
    @Override
    public boolean equals(Object that) {
        checkRep();
        if (that instanceof Header) {
            return ((Header) that).composer.equals(this.composer)
                    && ((Header) that).key.equals(this.key)
                    && ((Header) that).defaultLength.equals(this.defaultLength)
                    && ((Header) that).meter.equals(this.meter)
                    && ((Header) that).tempo.equals(this.tempo)
                    && ((Header) that).title.equals(this.title)
                    && ((Header) that).voices.equals(this.voices)
                    && ((Header) that).index.equals(this.index);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        checkRep();
        return this.composer.hashCode() +
                this.defaultLength.hashCode() +
                this.index.hashCode() +
                this.key.hashCode() +
                this.meter.hashCode() +
                this.tempo.hashCode() +
                this.title.hashCode() +
                this.voices.hashCode();
    }
    
    @Override
    public String toString() {
        checkRep();
        return this.composer + "\n" +
                this.defaultLength + "\n" +
                this.index + "\n" +
                this.key + "\n" +
                this.meter + "\n" +
                this.tempo + "\n" +
                this.title + "\n" +
                this.voices;
    }
}
