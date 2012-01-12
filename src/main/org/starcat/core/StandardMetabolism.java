package org.starcat.core;


import org.starcat.codelets.CodeletEvent;
import org.starcat.codelets.CodeletEventListener;
import org.starcat.util.CircularQueue;
import org.starcat.codelets.Codelet;
import org.starcat.codelets.BehaviorCodelet;
import org.starcat.codelets.ControlCodelet;
import org.starcat.core.BehaviorRegularPulse;
import org.starcat.core.ControlRegularPulse;

/** TODO this comment does not make sense...
 * This class probably needs an interface to conform to.  Right now it is 
 * just an abstract class.  Essentially, the 
 * <code>StandardMetabolism</code> class provides a strategy for 
 * <em>how</em> a <code>Metabolism</code> runs.  In this implementation, 
 * there are two queues, which separately hold <code>BehaviorCodelet</code> and 
 * <code>ControlCodelet</code> objects.  Each queue has a separate 
 * <code>MetabolismPulse</code> associated with it, and this pulse is 
 * selected to run based on which thread is currently acting when the 
 * <code>pulse()</code> method is called.  I'm sure there are plenty of 
 * ways to set up a strategy to run, that is why it is decoupled from the 
 * <code>MetabolismPulse</code>.  
 */
public class StandardMetabolism 
            implements CodeletEventListener, Metabolism, StarcatObject
{
	// -------------------------------------------------------------------------
    // Protected Data
	// -------------------------------------------------------------------------
    
	protected BehaviorRegularPulse behaviorPulse;
	protected ControlRegularPulse controlPulse;
	protected CircularQueue behaviorQueue;
	protected CircularQueue controlQueue;
	protected Component component;
	protected Object key;
	protected PublicStarcatObject sObjDelegate = new PublicStarcatObject(this);
	
	// -------------------------------------------------------------------------
    // Constructor
	// -------------------------------------------------------------------------
    
	public StandardMetabolism(Component c)
	{
		setKey(getId());
		component = c;
		behaviorQueue = new CircularQueue();
		controlQueue = new CircularQueue();
		behaviorPulse = new BehaviorRegularPulse(behaviorQueue, component);
		controlPulse = new ControlRegularPulse(controlQueue, component);
	}
	
	// -------------------------------------------------------------------------
    // Public Members
	// -------------------------------------------------------------------------
    
	public void setKey(Object key)
	{
		this.key = key;
	}
	
	public Object getKey()
	{
		return key;
	}
	
	public void handleCodeletEvent(CodeletEvent event)
	{
		Codelet codelet = event.getCodelet();
		if (BehaviorCodelet.class.isAssignableFrom(codelet.getClass()))
      {
				behaviorQueue.push(codelet);
      }
		else if(ControlCodelet.class.isAssignableFrom(codelet.getClass()))
      {
				controlQueue.push(codelet);
      }
	}
	
	public void start()
	{
		behaviorPulse.start();
		controlPulse.start();
	}
	
	public void stop()
	{
		behaviorPulse.stop();
		controlPulse.stop();
	}
	
	public RegularPulse getBehaviorPulse()
	{
		return behaviorPulse;
	}
	
	public RegularPulse getControlPulse()
	{
		return controlPulse;
	}	

	public Component getComponent()
	{
		return component;
	}

	public Object getId() 
    {
		return sObjDelegate.getId();
	}
}