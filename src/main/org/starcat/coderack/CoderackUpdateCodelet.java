package org.starcat.coderack;

import org.starcat.codelets.ControlCodelet;
import org.starcat.slipnet.Slipnet;
import org.starcat.workspace.Workspace;


public class CoderackUpdateCodelet extends ControlCodelet 
{
	public void execute(Workspace workspace) {}
    
	public void execute(Slipnet slipent) {}
    
	public void execute(Coderack coderack) {
		coderack.update();
	}
}
