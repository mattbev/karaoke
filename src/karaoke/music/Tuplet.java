package karaoke.music;

import java.util.List;

public class Tuplet implements Playable {
    
    private final List<Chord> chords;
    private double duration;
    private String lyricText;
    
    /**
     * creates an instance of a Tuplet object
     * @param chords the notes in the tuplet
     */
    public Tuplet(List<Chord> chords) {
        this.chords = chords;
        for (Chord chord : this.chords) {
            this.duration += chord.getDuration();
        }
        
        for (Chord chord : this.chords) {
            this.lyricText += chord.getLyricText();
        }
    }
    
    @Override
    public double getDuration() {
        return this.duration;
    }
    
    @Override
    public Lyric getLyric() {
        return new Lyric(this.lyricText);
    }
    
    @Override
    public String getLyricText() {
        return this.lyricText;
    }

}
