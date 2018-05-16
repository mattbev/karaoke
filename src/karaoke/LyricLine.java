/**
 * 
 */
package karaoke;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chessa, mattbev, sophias
 *
 * Lyric is an immutable ADT representing a line of lyrics, where one syllable is bolded.
 */
public class LyricLine {
    
    private final List<String> lyrics;
    private final int boldedIndex;
    
    // AF(lyrics, boldedIndex): A line of lyrics in a song with lyric at index boldedIndex bolded 
    //
    // Rep Invariant:
    //      boldedIndex >= 0
    //      boldedIndex < lyrics.size()
    //
    // Safety from rep exposure:
    //      fields are private and final and immutable
    //      all return types are immutable
    //      defensive copying, so no returning rep values
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - lyrics and boldedIndex are final and only mutated in the class constructor which is a threadsafe (synchronized) method by default
    //    - the client cannot mutate lyrics or boldedIndex
    
    /**
     * Make a LyricLine object that represents a line of lyrics with a single bolded section to be sung
     * 
     * @param lyrics the lyrics to add to the line
     * @param boldedIndex the index of the lyric in the line that should be bolded
     */
    public LyricLine(List<String> lyrics, int boldedIndex) {
        this.lyrics = new ArrayList<>(lyrics);
        this.boldedIndex = boldedIndex;
        
        //find current lyric string that should be bolded, then add bolding syntax
        String boldedLyric = this.lyrics.get(boldedIndex);
        this.lyrics.set(boldedIndex, "<b>"+ boldedLyric + "</b>");
        checkRep();
    }


    /**
     * check the stated and implied rep invaraint
     */
    private void checkRep() {
        assert this.boldedIndex >= 0 && this.boldedIndex < this.lyrics.size();
    }
    
    
    /**
     * gets all of the individual lyrics of a line in order
     * @return the list of line lyrics
     */
    public List<String> getLyricLine() {
        return new ArrayList<>(this.lyrics);
    }
    
    /**
     * gets the index that is bolded
     * @return the index from 0 to number of lyrics in line -1 that is bolded
     */
    public int getBoldedIndex() {
        return this.boldedIndex;
    }
    
    /**
     * Create a copy of this lyric line with the same text and bolded index
     * 
     * @return a new replicated LyricLine object
     */
    public LyricLine copy() {
        checkRep();
        return new LyricLine(this.getLyricLine(), this.getBoldedIndex());
    }
    
    /**
     * creates an empty instance of a lyric line
     * @return a new LyricLine instance representing the absence of a lyric
     */
    public static LyricLine emptyLyricLine() {
        return new LyricLine(Collections.emptyList(), 0);
    }
    

    @Override
    public boolean equals(Object that) {
        checkRep();
        if (that instanceof LyricLine) {
            if (this.toString().equals(((LyricLine) that).toString())
                    && this.boldedIndex == ((LyricLine) that).boldedIndex) {
                return true;
            }
        }
        return false;
    }

    
    @Override 
    public int hashCode() {
        checkRep();
        return this.toString().hashCode() + this.boldedIndex;
    }
    
    @Override
    public String toString() {
        checkRep();
        String lyricString = "";
        for (String lyric : this.lyrics) {
            lyricString += lyric;
        }
        return lyricString;
    }
    
}
