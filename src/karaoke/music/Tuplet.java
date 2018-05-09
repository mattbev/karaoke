package karaoke.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import karaoke.sound.SequencePlayer;

public class Tuplet implements Playable {
    
    public static final int DUPLET_NOTES = 2;
    public static final int TRIPLET_NOTES = 3;
    public static final int QUADRUPLET_NOTES = 4;
    public static final int TIME_OF_NOTES = 3;
    
    enum Type{
        DUPLET, TRIPLET, QUADRUPLET
        
    }
    private final List<Chord> newChords = new ArrayList<Chord>();
    private final Type type;
    private double duration;
    private final  Map<Chord,List<Lyric>> lyricMap = new HashMap<Chord,List<Lyric>>();
    
    // AF(chords, lyrics): A tuplet of type type where chords are the chords in the tuplet, and lyrics is the ordered list of Lyric syllables 
    //                     associated with the chords
    //
    //
    //
    //
    //
    
    /**
     * creates an instance of a Tuplet object
     * @param chords the notes in the tuplet
     */
    public Tuplet(List<Chord> chords) {
        
        //determine type of tuplet {duplet, triplet, quadruplet}
        if(chords.size() == DUPLET_NOTES) {
            this.type = Type.DUPLET;
        }
        else if(chords.size() == TRIPLET_NOTES) {
            this.type = Type.TRIPLET;
        }
        else {
            type = Type.QUADRUPLET;
        }
        
        //determine initial duration of notes in tuplets
        double noteDuration = chords.get(0).getDuration();
        
        //determine correct duration of tuplet based on type
        if(type == Type.DUPLET) {
            this.duration = noteDuration * TIME_OF_NOTES;
        }
        if(type == Type.TRIPLET) {
            this.duration = noteDuration * 2;
        }
        if(type == Type.QUADRUPLET) {
            this.duration = noteDuration * TIME_OF_NOTES;
        }
        
        
        //add notes of correct length into chord list
        for(Chord chord : chords) {
            Chord newChord = chord.copyChordNewDuration(this.duration, this.type);
            newChords.add(newChord);
        }
        
        //create HashMap mapping chords to the list of lyrics associated with them
        for (Chord chord : this.newChords) {
            List<Lyric> chordLyricList = new ArrayList<Lyric>();
            for(Lyric lyric: chord.getLyrics()) {
                chordLyricList.add(lyric);    
            }
            this.lyricMap.put(chord, chordLyricList);
        }
    }
    
    @Override
    public double getDuration() {
        return this.duration;
    }
    
    @Override
    public Lyric getLyric() {
        return Lyric.emptyLyric(); //not correct, need to revisit
    }
    
    @Override
    public void play(SequencePlayer player, double startBeat) {
        
        double beginBeat = startBeat;
        for(Chord chord: newChords) {
            chord.play(player, beginBeat);
            beginBeat += chord.getDuration();
        }
    }
}