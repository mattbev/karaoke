/**
 * 
 */
package karaoke;

import java.util.List;

/**
 * @author chessa, mattbev, sophias
 *
 * Lyric is an immutable ADT representing a line of lyrics, where one syllable is bolded.
 */
public class LyricLine {
    
    private final List<String> lyrics;
    private final String boldedLyric;
    
    // AF(lyrics, boldedLyric): A line of lyrics in a song  
    //
    // Rep Invariant:
    //      lyric.size() > 0
    //
    // Safety from rep exposure:
    //      field is private and final and immutable
    //      all return types are immutable
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - lyric is final and an immutable datatype
    //    - lyric is never exposed to a client.
    
    /**
     * Make a lyric object that represents a single syllable's lyric
     * 
     * @param lyric the lyric text
     */
    public Lyric(String lyric) {
        this.lyric = lyric;
        checkRep();
    }


    /**
     * check the stated and implied rep invaraint
     */
    private void checkRep() {
        assert lyric.length() > 0 : "cannot have empty lyric text (empty lyric has one space)";
    }
    
    /**
     * Return the lyric text of this component
     * 
     * @return the lyric text
     */
    public String getText() {
        checkRep();
        return this.lyric;
    }
    
    /**
     * Create a copy of this lyric with the same text
     * 
     * @return a new replicated lyric object
     */
    public Lyric createLyricCopy() {
        checkRep();
        return new Lyric(this.getText());
    }
    
    /**
     * creates an empty instance of a lyric
     * @return a new Lyric instance representing the absence of a lyric
     */
    public static Lyric emptyLyric() {
        return new Lyric(" ");
    }
    

    @Override
    public boolean equals(Object that) {
        checkRep();
        if (that instanceof Lyric) {
            if (this.getText().equals(((Lyric) that).getText())) {
                return true;
            }
        }
        return false;
    }

    
    @Override 
    public int hashCode() {
        checkRep();
        return this.getText().hashCode();
    }
    
    @Override
    public String toString() {
        return this.lyric;
    }
}
