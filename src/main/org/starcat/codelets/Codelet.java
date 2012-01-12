package org.starcat.codelets;

import java.util.Date;
import org.starcat.core.Keyable;
import org.starcat.core.PublicStarcatObject;
import org.starcat.core.StarcatObject;
import org.starcat.coderack.Coderack;
import org.starcat.slipnet.Slipnet;
import org.starcat.workspace.Workspace;

/**
 * This is the root class for codelets, which are little pieces of executable
 * code that can control Component objects or execute within them.
 */
public abstract class Codelet implements Keyable, Cloneable, StarcatObject {
	// -----------------------------------------------------------------------------
	// Protected Data
	// -----------------------------------------------------------------------------

	private Object key = getClass().getName();

	private PublicStarcatObject sObjDelegate = new PublicStarcatObject(this);

	private double urgency;

	// -----------------------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------------------

	public Codelet() {
		//
		// Do nothing
		//
	}

	public Codelet(double urgency) {
		this.urgency = urgency;
	}
	
	// -----------------------------------------------------------------------------
	// Abstract Members
	// -----------------------------------------------------------------------------

	public abstract void execute(Coderack coderack);

	public abstract void execute(Slipnet slipnet);

	public abstract void execute(Workspace workspace);

	/**
	 * These granularize the execution pattern by providing stub methods for
	 * what needs to happen before and after execution
	 */
	public abstract void preExecute(Coderack coderack);

	public abstract void preExecute(Slipnet slipnet);

	public abstract void preExecute(Workspace workspace);

	public abstract void postExecute(Coderack coderack);

	public abstract void postExecute(Slipnet slipnet);

	public abstract void postExecute(Workspace workspace);

	// -----------------------------------------------------------------------------
	// Public Members
	// -----------------------------------------------------------------------------

	/**
	 * Get the urgency group value to assign to the codelet in the coderack
	 */
	public double getUrgency() {
		return urgency;
	}

	/**
	 * Set the urgency group value to assign to the codelet in the coderack
	 */
	public void setUrgency(double urgency) {
		this.urgency = urgency;
	}

	/**
	 * Default implementation of long-living codelets Instantiating applications
	 * may wish to overload and obtain finite lifetimes for codelets
	 * Coderack.update is responsible for removal
	 */
	public Date getTimeToDie() {
		return new Date(System.currentTimeMillis()
				+ (long) (365.25 * 8640000000L));
	}

	// -----------------------------------------------------------------------------
	// Implemented Keyable
	// -----------------------------------------------------------------------------

	/**
	 * Codelet objects are keyed by their class name. If left untouched, all
	 * codelets can then be keyed by their class name, which will be different
	 * for all differently behaving codelets.
	 */
	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	// -----------------------------------------------------------------------------
	// Implemented Starcat object
	// -----------------------------------------------------------------------------

	public Object getId() {
		return sObjDelegate.getId();
	}

	// -----------------------------------------------------------------------------
	// Overidden methods objects
	// -----------------------------------------------------------------------------

	/**
	 * The construction logic for a codelet (things like pointers to nodes to
	 * give activation to and so on) is in the XML digesting code. In order to
	 * avoid repeating that logic in the constructor for the codelet, we simply
	 * use cloning.
	 */
	public Codelet clone() {
		try {
			Codelet cod = (Codelet) super.clone();
			cod.urgency = urgency;
			return cod;
		} catch (CloneNotSupportedException cnse) {
			throw new InternalError();
		}
	}
}
