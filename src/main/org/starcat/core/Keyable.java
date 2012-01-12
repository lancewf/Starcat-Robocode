package org.starcat.core;


/*
 * Classes implementing this interface would do so because they want to 
 * be marked has having some unique key from which they can be identified.
 * 
 */
public interface Keyable
{
	public Object getKey();
	public void setKey(Object key);
}
