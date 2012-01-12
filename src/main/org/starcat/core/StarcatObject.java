package org.starcat.core;

/*
 * Universal interface for objects in the Starcat architecture.  
 * Implementors will be guaranteed a unique id and an appropriate 
 * Logger object.  
 * 
 */
public interface StarcatObject
{	
	public Object getId();	
}
