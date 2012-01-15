package org.starcat.core;


/**
 * TODO what is this interface doing?
 * The MetabolismPulse class provides timing for behavior within each 
 * component.  This can be whatever timing mechanism the user desires.
 * Implementations must "pulse" with some sort of activity which generally 
 * does something to or with a Component.
 *   
 */
public interface MetabolismPulse 
            extends Keyable, Runnable
{
	public void run();
	public void start();
	public void stop();		
}
