package org.starcat.codelets;

import java.util.EventObject;
import org.starcat.core.PublicStarcatObject;
import org.starcat.core.StarcatObject;
import org.starcat.codelets.Codelet;

/**
 * This is a class for the events that are exchanged among the components of the
 * Starcat architecture. For example, such events arising from the coderack
 * include when a codelet is popped. The essentials of the event are the codelet
 * that is to be interpreted by listeners.
 */
public class CodeletEvent extends EventObject implements StarcatObject {
	// --------------------------------------------------------------------------
	// Private Data
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = 4738449642050896175L;

	private PublicStarcatObject sObjDelegate = new PublicStarcatObject(this);

	private Codelet codelet;

	// --------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------

	public CodeletEvent(Object source, Codelet codelet) {
		super(source);
		this.codelet = codelet;
	}

	// --------------------------------------------------------------------------
	// Public Members
	// --------------------------------------------------------------------------

	public Codelet getCodelet() {
		return codelet;
	}

	// --------------------------------------------------------------------------
	// StarcatObject Members
	// --------------------------------------------------------------------------

	public Object getId() {
		return sObjDelegate.getId();
	}
}
