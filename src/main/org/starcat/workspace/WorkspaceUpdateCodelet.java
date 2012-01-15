package org.starcat.workspace;

import org.starcat.codelets.ControlCodelet;
import org.starcat.coderack.Coderack;
import org.starcat.slipnet.Slipnet;

public class WorkspaceUpdateCodelet extends ControlCodelet {

	public void execute(Workspace workspace) {
		workspace.update();
	}

	public void execute(Slipnet slipent) {
		//
		// Do nothing
		//
	}

	public void execute(Coderack coderack) {
		//
		// Do nothing
		//
	}

}
