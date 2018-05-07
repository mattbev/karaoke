package karaoke.music;

import java.util.List;

public class Tuplet implements Playable {
    
    private final List<Note> notes;
    
    public Tuplet(List<Note> notes) {
        this.notes = notes;
    }

    public double getDuration() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Lyric getLyric() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getLyricText() {
        // TODO Auto-generated method stub
        return null;
    }

}
