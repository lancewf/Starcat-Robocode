package org.starcat.coderack;

import java.util.ArrayList;
import java.util.Random;

import org.starcat.codelets.Codelet;

/**
 * An urgency group is a set of codelets that all have the same 
 * urgency value.
 */
public class UrgencyGroup 
{
	// -------------------------------------------------------------------------
    // Private Data
	// -------------------------------------------------------------------------
    
    private double urgency;
    private ArrayList<Codelet> members = new ArrayList<Codelet>();
    
    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    
    public UrgencyGroup(double urgency) 
    {
        this.urgency = urgency;
    }
    
    // -------------------------------------------------------------------------
    // Public Members
    // -------------------------------------------------------------------------
    
    public void add(Codelet codelet) 
    {
        members.add(codelet);
    }
    
    public Codelet remove(Random rng) 
    {
        return (Codelet)members.remove(rng.nextInt(members.size()));
    }
    
    /**
     * This is done only when you need to get rid of a dead codelet. So
     * it shouldn't happen very often. However, the operation is O(n) 
     * where n is the list length. If it does happen a lot, we may 
     * need to do something more like a TreeMap instead of an 
     * ArrayList. With a TreeMap, insertion could be more expensive.
     * 
     * @param codelet - codelet to be removed
     */
    public void remove(Codelet codelet) 
    {
        members.remove(codelet);
    }
    
    public int size() 
    {
        return members.size();
    }
    
    public double getUrgency() 
    {
        return urgency;
    }
}

