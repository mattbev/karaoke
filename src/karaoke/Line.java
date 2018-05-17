package karaoke;

import java.util.ArrayList;
import java.util.List;

import karaoke.server.WebServer;
import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Measure is an immutable variant of Karaoke
 *
 */
public class Line implements Music {
    
    private final List<Playable> components;

    // AF(components): A measure of music where components.get(i) is the ith component to be played in the measure, 
    //                 which is a type of playable.
    //
    // Rep invariant: 
    //      components.size() > 0
    //
    // safety from rep exposure:
    //      field is private and final
    //      components is copied defensively in constructor, never mutated afterwards
    //      all return types immutable
    //      
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - components is private and final, and never exposed to a client
    //    -The mutable rep is only mutated in the constructor
    //      which is a synchronized, threadsafe method
    
    /**
     * Create a measure of music
     * 
     * @param components the ordered playable components that make up this measure
     */
    public Line(List<Playable> components) {
        this.components = new ArrayList<>(components);
        checkRep();
    }
    
    /**
     * check the stated and implied rep invariant
     */
    private void checkRep() {
        assert components.size() > 0 : "A measure cannot be empty";
        for (Playable p : components) {
            assert p != null : "A measure cannot contain null elements";
        }
    }
    
    @Override
    public List<Playable> getComponents() {
        return new ArrayList<>(this.components);
    }
    
    @Override
    public double duration() {
        double d = 0;
        checkRep();
        for(Playable playable: components) {
            d += playable.duration();
        }
        System.out.println("lineDur = "+d);
        return d;
    }

    @Override
    public void play(SequencePlayer player, double startBeat, WebServer server) {
        
        double beginBeat = startBeat;
        for(Playable component: components) {
            component.play(player, beginBeat,  server);
            beginBeat += component.duration();
        }
        checkRep();
    }
    
  
    /**
     * Provides a list of durations for the measure's components
     * 
     * @return list of playable durations in the exact
     *         order they appear in this.components
     */
    @Override
    public List<Double> getDurationList(){
        List<Double> durationList = new ArrayList<Double>();
        
        //will likely be changed when we figure out how to deal w/ lyrics during rests
        for(int i = 0; i < components.size(); i++) {
            Playable component = components.get(i);
            double dur = component.duration();
            durationList.add(dur);
        }
        return durationList;
    }
}
