package org.starcat.core;

/**
 * Delegation class for objects wanting to implement the StarcatObject
 * interface, which is probably pretty much every class in this whole
 * architecture.
 * 
 */
public class PublicStarcatObject implements StarcatObject {
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private Object referent;
	private IdGenerator idGen = new IdGenerator();

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public PublicStarcatObject(Object obj) {
		referent = obj;
	}

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	public Object getId() {
		return idGen.getId(referent);
	}
}
