package karaoke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Immutable data type representing a karaoke music body
 *
 */
public class Body {
    private HashMap<String,Music> voiceToMusic = new HashMap<String,Music>();
    private HashMap<String,List<LyricLine>> voiceToLyrics = new HashMap<>();
    
    // Abstraction Function:
    //   AF(voiceToMusic, voiceToLyrics) = one or more musics played at the same time by different voices
    //                                     with zero or more lyrics where the music for voice <voice> is
    //                                     at voiceToMusic.get(<voice>) and the lyrics are at voiceToLyrics.get(<voice>)
    //      
    //
    // Rep Invariant:
    //      voiceToMusic.size() > 0
    //      voiceToMusic.size() > 0
    //   
    //
    // Rep Safety Argument:
    //   all fields are private 
    //   all getter methods return Strings which are immutable 
    //
    // Thread Safety Argument:
    //   no rep fields are mutated outside of the constructor, which is a threadsafe method by default
    
    /**
     * Creates an isntance of a body object
     * @param musicMap map from voices Music
     * @param lyricMap map from voices to LyricLines
     */
    public Body(HashMap<String,Music> musicMap, HashMap<String,List<LyricLine>> lyricMap) {
        this.voiceToMusic = musicMap;
//        this.voiceToLyrics = lyricMap;
    }
    
   
    
//    /**
//     * Provides a copy of a certain voice's lyric line list
//     * @param voice voice whose lyric list is returned
//     * @return lyric line list 
//     */
//    public List<String> getVoiceLyricLines(String voice){
//        List<String> lyricLineListCopy= new ArrayList<String>();
//        for(String line: voiceToLyrics.get(voice).getLyricLine()) {
//            lyricLineListCopy.add(line);
//        }
//        return lyricLineListCopy;
//    }
    
    /**
     * Provides a copy of a certain voice's music's duration list
     * @param voice voice whose music's duration list is returned
     * @return list of durations for the music of voice <voice>
     */
    public List<Double> getVoiceDurationList(String voice){
        List<Double> durationList = new ArrayList<Double>();
        Music voiceMusic = this.voiceToMusic.get(voice);
        List<Double> voiceMusicDurationList = voiceMusic.getDurationList();
        for(Double duration: voiceMusicDurationList) {
            durationList.add(duration);
        }
        return durationList;
    }
    
    
    //don't really need this but necessary from interface
    
    public List<Double> getDurationList(){
        List<Double> durationList = new ArrayList<Double>();
        for(String voice: this.voiceToMusic.keySet()) {
            Music voiceMusic = this.voiceToMusic.get(voice);
            List<Double> voiceMusicDurationList = voiceMusic.getDurationList();
            for(Double duration: voiceMusicDurationList) {
                durationList.add(duration);
            }
        }
        return durationList;

    }
    
    
    public double duration() {
        // TODO Auto-generated method stub
        return 0;
    }

    
    public void play(SequencePlayer player, double startBeat) {
        for(String voice: this.voiceToMusic.keySet()) {
            Music voiceMusic = this.voiceToMusic.get(voice);
            voiceMusic.play(player, startBeat);
            
        }
    }

    public List<Measure> getMusic() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Get all the voices in this song
     * 
     * @return a list of the voices in this piece
     */
    public List<String> getVoices() {
        List<String> voices = new ArrayList<>();
        for (String s : this.voiceToLyrics.keySet()) {
            voices.add(s);
        } return voices;
    }

}
