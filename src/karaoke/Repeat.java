package karaoke;

import java.util.ArrayList;
import java.util.List;

import karaoke.server.WebServer;
import karaoke.sound.SequencePlayer;

public class Repeat implements Music{
    private final Music toBeRepeated;
    private final List<Music> endings = new ArrayList<Music>();
    
    // AF(toBeRepeated, endings): A Music consisting of repeated music sections with toBeRepeated playing twice;
    //                            and if they exist, two alternate endings <endings> play one each after
    //                            the first and second playings of <toBeRepeated>, respectively.,
    //                       
    //
    // RI: 
    //      -True
    //
    // Safety from rep exposure:
    //      all fields private final, and never mutated after contructor
    //      all return types are immutable, rep is never exposed
    //
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - firstMusic, and secondMusic are final
    //    - neither Music objects are every exposed to the client
    
    
    /**
     * Creates a repeat object with first ending
     * endingOne and second ending endingTwo
     * 
     * @param rMusic the music to be repeated
     * @param endingOne the first ending of the music
     * @param endingTwo the optional second ending of
     *        the music
     */
    public Repeat(Music rMusic, Music endingOne,Music endingTwo) {
        this.endings.add(endingOne);
        this.endings.add(endingTwo);
        this.toBeRepeated = rMusic;
    }
    
    /**
    * Creates a repeat object
    * 
    * @param rMusic the music to be repeated
    *        the music
    */
   public Repeat(Music rMusic) {
       this.toBeRepeated = rMusic;
   }
   @Override
    public double duration() {
        Music finalCombined;
        if(this.endings.size() != 2) {
            finalCombined = new Concat(this.toBeRepeated, this.toBeRepeated);
       }
       
       else {
           Music combined = new Concat(this.toBeRepeated, this.endings.get(0));
           Music newCombined = new Concat(combined,this.toBeRepeated);
            finalCombined = new Concat(newCombined, this.endings.get(1));
       }
        return finalCombined.duration();
    }


    @Override
    
    public void play(SequencePlayer player, double startBeat, WebServer server) {
        Music finalCombined;
        if(this.endings.size() != 2) {
             finalCombined = new Concat(this.toBeRepeated, this.toBeRepeated);
        }
        
        else {
            Music combined = new Concat(this.toBeRepeated, this.endings.get(0));
            Music newCombined = new Concat(combined,this.toBeRepeated);
             finalCombined = new Concat(newCombined, this.endings.get(1));
        }
        finalCombined.play(player,startBeat, server);
           
    }
    @Override
    public List<Playable> getComponents() {
        Music finalCombined;
        if(this.endings.size() != 2) {
             finalCombined = new Concat(this.toBeRepeated, this.toBeRepeated);
        }
        
        else {
            Music combined = new Concat(this.toBeRepeated, this.endings.get(0));
            Music newCombined = new Concat(combined,this.toBeRepeated);
             finalCombined = new Concat(newCombined, this.endings.get(1));
        }
        return finalCombined.getComponents();
    }
    
    



    

}
