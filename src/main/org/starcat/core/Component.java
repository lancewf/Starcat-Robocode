package org.starcat.core;

import javax.swing.event.EventListenerList;
import org.starcat.codelets.CodeletEvent;
import org.starcat.codelets.CodeletEventListener;
import org.starcat.codelets.Codelet;

/*
 * Components are communicating objects within Starcat.  There are 
 * essentially three of them:  The Coderack, 
 * Slipnet, 
 * and Workspace. 
 *  
 * They are the logic behind the Starcat framework.
 * 
 */
public abstract class Component implements StarcatObject, CodeletEventListener,
		Keyable {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	/*
	 * The current codelet being processed, NOT any codelets going out.
	 */
	private Codelet currentCodelet;
	private EventListenerList listenerList;
	private Metabolism metabolism;
	private Object key;
	private StarcatObject sObjDelegate;
	private CodeletEvent codeletEvent = null;

	// --------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------

	public Component() {
		sObjDelegate = new PublicStarcatObject(this);
		listenerList = new EventListenerList();
	}

	// --------------------------------------------------------------------------
	// StarcatObject Members
	// --------------------------------------------------------------------------

	public Object getId() {
		return sObjDelegate.getId();
	}

	// --------------------------------------------------------------------------
	// CodeletEventListener Members
	// --------------------------------------------------------------------------

	/**
	 * Metabolism objects actually do the work of handling events between
	 * components, so events are forwarded to them. If a component's metabolism
	 * is not set, then the default behavior of the component is to immediately
	 * execute the codelet.
	 */
	public void handleCodeletEvent(CodeletEvent event) {
		if (metabolism != null) {
			metabolism.handleCodeletEvent(event);
		} else {
			executeCodelet(event.getCodelet());
		}
	}

	// --------------------------------------------------------------------------
	// Keyable Members
	// --------------------------------------------------------------------------

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	// --------------------------------------------------------------------------
	// Abstract Members
	// --------------------------------------------------------------------------

	protected abstract void preExecuteCodelet(Codelet codelet);

	public abstract void executeCodelet(Codelet codelet);

	protected abstract void postExecuteCodelet(Codelet codelet);

	public abstract void update();

	// --------------------------------------------------------------------------
	// Protected Members
	// --------------------------------------------------------------------------

	protected Codelet getCurrentCodelet() {
		return currentCodelet;
	}

	// --------------------------------------------------------------------------
	// Public Members
	// --------------------------------------------------------------------------

	public void setMetabolism(Metabolism metab) {
		this.metabolism = metab;
	}

	public Metabolism getMetabolism() {
		return metabolism;
	}

	/*
	 * Metabolisms provide the activity for all components. This method simply
	 * calls metabolism.start(). If this component has no metabolism (null),
	 * then the method does nothing.
	 */
	public void start() {
		if (getMetabolism() != null) {
			getMetabolism().start();
		}
	}

	/*
	 * Metabolisms provide the activity for all components. This method simply
	 * calls metabolism.stop(). If the metabolism is null, then this method does
	 * nothing.
	 */
	public void stop() {
		if (getMetabolism() != null) {
			getMetabolism().stop();
		}
	}

	/**
	 * Codelets executing in a component are registered with the component for
	 * the duration of execution. This may or may not be necessary so default
	 * implementation is defined here, which essentially sets the currentCodelet
	 * field to the argument.
	 */
	public void registerCodelet(Codelet codelet) {
		currentCodelet = codelet;
	}

	/*
	 * This method usually just sets the <code>currentCodelet</code> field to
	 * null but we pass the codelet in case more needs to be done in some
	 * implementations
	 */
	public void unregisterCodelet(Codelet codelet) {
		currentCodelet = null;
	}

	/*
	 * Components have listeners to listen for CodeletEvent objects. This is one
	 * of the fundamental architectural commitments of Starcat. That is to say:
	 * Starcat is an asynchronous message passing architecture, which is quite
	 * different from how Copycat's components interacted.
	 */
	public synchronized void addCodeletEventListener(
			CodeletEventListener listener) {
		listenerList.add(CodeletEventListener.class, listener);
	}

	public synchronized void removeCodeletEventListener(
			CodeletEventListener listener) {
		listenerList.remove(CodeletEventListener.class, listener);
	}

	/**
	 * NOTE: right now listener lists have a "type" class stored first and the
	 * actual component stored second for those situations where we someday
	 * might want different kinds of listeners (this happens in the AgentCat
	 * code). But we think for now that we will only want CodeletEventListener
	 * objects in there This code could be much cleaner using typing less
	 * loosely (putting stuff in a list of objects is not so clean)
	 * 
	 * @param codelet
	 */
	public void fireCodeletEvent(Codelet codelet) {
		if (codelet != null) {
			Object[] listeners = listenerList.getListenerList();
			codeletEvent = new CodeletEvent(this, codelet);
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
				if (listeners[i] == CodeletEventListener.class) {
					CodeletEventListener codeletEventListener = (CodeletEventListener) listeners[i + 1];

					codeletEventListener.handleCodeletEvent(codeletEvent);
				}
			}
		}
	}
}
