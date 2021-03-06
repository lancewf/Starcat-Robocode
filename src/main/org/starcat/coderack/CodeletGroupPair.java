package org.starcat.coderack;

import org.starcat.codelets.Codelet;

/**
 * This is a helper class. When you add a codelet to the 
 * LifetimeTable, you need to add it with the group that it is in so 
 * that you can later find the group easily. This little class 
 * bundles the codelet with the group.
 */
public class CodeletGroupPair 
{
	// -------------------------------------------------------------------------
    // Public Data
	// -------------------------------------------------------------------------
    
    public static int runningSequenceNumber = 1;
    private Codelet codelet;
    private UrgencyGroup group;
    private int sequenceNumber;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    
    public CodeletGroupPair(Codelet codelet, UrgencyGroup group) {
        this.codelet = codelet;
        this.group = group;
        if (runningSequenceNumber < Integer.MAX_VALUE) {
            this.sequenceNumber = ++runningSequenceNumber;
        } else {
            this.sequenceNumber = 1;
        }
    }
    
    // -------------------------------------------------------------------------
    // Public Members
    // -------------------------------------------------------------------------

	public Codelet getCodelet() {
		return codelet;
	}

	public UrgencyGroup getGroup() {
		return group;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
}
