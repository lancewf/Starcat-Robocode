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
    
    public LifetimeTable() { }
    
    // -------------------------------------------------------------------------
    // Public Members
    // -------------------------------------------------------------------------
    
    public void addCodelet(CodeletGroupPair codeletGroupPair) 
    {
        timetable.put(codeletGroupPair, 
                codeletGroupPair.getCodelet().getTimeToDie());
    }
    
    public CodeletGroupPair[] getDeadCodelets(Date deathDate) 
    {
        return (CodeletGroupPair[])(new CodeletGroupPair[0]);
    }
    
    public void removeCodelet(CodeletGroupPair codeletGroupPair) 
    {
        timetable.remove(codeletGroupPair.getCodelet().getTimeToDie());
    }
}
