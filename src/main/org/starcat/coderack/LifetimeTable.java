package org.starcat.coderack;

import java.util.Date;
import java.util.TreeMap;

/**
 * This class is used for maintaining a list of codelets on the coderack 
 * that are ordered by their time-to-live. 
 */
public class LifetimeTable 
{
	// -------------------------------------------------------------------------
    // Private Data
	// -------------------------------------------------------------------------
    
    private TreeMap<CodeletGroupPair,Date> timetable 
            = new TreeMap<CodeletGroupPair,Date>(new LifetimeComparator());
    
    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    
    public LifetimeTable() 
    {      
        //
        //Do nothing
        //
    }
    
    // -------------------------------------------------------------------------
    // Public Members
    // -------------------------------------------------------------------------
    
    public void addCodelet(CodeletGroupPair codeletGroupPair) 
    {
        timetable.put(codeletGroupPair, 
                codeletGroupPair.codelet.getTimeToDie());
    }
    
    public CodeletGroupPair[] getDeadCodelets(Date deathDate) 
    {
        // TODO fix this stuff (getDeadCodelets)
        //INVESTIGATE (see notes) and FIX THIS
        //timetable.headMap(deathDate).values().toArray
        return (CodeletGroupPair[])(new CodeletGroupPair[0]);
    }
    
    public void removeCodelet(CodeletGroupPair codeletGroupPair) 
    {
        // TODO fix this stuff (removeCodelet)
        //This is slightly wrong. We really want to remove _the_ 
        //codelet and not just _some_ codelet with the same time-to-die. 
        timetable.remove(codeletGroupPair.codelet.getTimeToDie());
    }
}
