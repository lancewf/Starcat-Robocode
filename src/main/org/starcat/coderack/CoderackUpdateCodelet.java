package org.starcat.coderack;

import org.starcat.codelets.ControlCodelet;
import org.starcat.slipnet.Slipnet;
import org.starcat.workspace.Workspace;


public class CoderackUpdateCodelet 
                extends ControlCodelet 
{
	// -------------------------------------------------------------------------
    // Public Members
	// -------------------------------------------------------------------------
    
	public void execute(Workspace wkspc) 
    {	
        //
        // Do nothing
        //
	}
    
	public void execute(Slipnet slpnt) 
    {
        //
        // Do nothing
        //
	}
    
	public void execute(Coderack cdrck) 
    {
		cdrck.update();
	}
}
