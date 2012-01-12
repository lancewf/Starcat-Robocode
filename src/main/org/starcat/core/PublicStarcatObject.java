package org.starcat.core;

/**
 * Delegation class for objects wanting to implement the 
 * StarcatObject interface, which is probably pretty much 
 * every class in this whole architecture.
 * 
 */
public class PublicStarcatObject 
            implements StarcatObject
{
// -----------------------------------------------------------------------------
// #region Data
// -----------------------------------------------------------------------------
   
	private Object referent;
	protected IdGenerator idGen;
    
//#endregion

// -----------------------------------------------------------------------------
// #region Constructor
// -----------------------------------------------------------------------------
    
	public PublicStarcatObject(Object obj)
	{
		referent = obj;
		idGen = new IdGenerator();
	}
    
//#endregion

// -----------------------------------------------------------------------------
// #region Public Members
// -----------------------------------------------------------------------------
	
   public Object getId()
	{
		return idGen.getId(referent);
	}	
    
//#endregion
}
