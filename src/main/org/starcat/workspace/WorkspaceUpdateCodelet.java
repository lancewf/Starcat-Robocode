package org.starcat.workspace;

import org.starcat.codelets.ControlCodelet;
import org.starcat.coderack.Coderack;
import org.starcat.slipnet.Slipnet;

public class WorkspaceUpdateCodelet extends ControlCodelet {

	public void execute(Workspace wkspc) {
		wkspc.update();

	}

	public void execute(Slipnet slpnt) {

	}

	public void execute(Coderack cdrck) {

	}

}
