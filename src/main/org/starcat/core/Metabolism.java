package org.starcat.core;

import org.starcat.codelets.CodeletEvent;
import org.starcat.codelets.CodeletEventListener;

/**
 * Metabolism implementations are responsible for a few 
 * basic things.  They "do stuff" by running, they must have 
 * facilities for starting and stopping, they handle events from other 
 * components, they are tied to a specific component, and they have to 
 * have a strategy as to how to go about "doing stuff."  This 
 * interface defines that contract.  Essentially, we are trying 
 * to decouple the activity of a Component from its 
 * data structure representation.
 * 
 */
public interface Metabolism 
            extends CodeletEventListener
{
	public void start();
	public void stop();
	public void handleCodeletEvent(CodeletEvent event);
	public Component getComponent();

	//TODO why is this here?
	public Object getKey();
}
