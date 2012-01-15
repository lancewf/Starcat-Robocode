package org.starcat.core;

import org.starcat.util.CircularQueue;
import org.starcat.codelets.Codelet;

public abstract class RegularPulse implements MetabolismPulse, StarcatObject {

	// -------------------------------------------------------------------------
	// Protected Data
	// -------------------------------------------------------------------------

	private boolean isAlive = false;
	private int beforePulse;
	private Component component;
	private boolean concurrent;
	private boolean done = false;
	private Thread executionThread;
	private Object key;
	private PublicStarcatObject sObjDelegate = new PublicStarcatObject(this);

	/*
	 * how many codelets have been executed so far in this pulse(). This should
	 * be incremented every time a Codelet is processed.
	 */
	private int currentCodeletsExecuted;

	/*
	 * queue storing codelets to be executed
	 */
	private CircularQueue queue;

	// -------------------------------------------------------------------------
	// Abstract Members
	// -------------------------------------------------------------------------

	/**
	 * It is rare that an item from the ParameterData class needs both get and
	 * set methods. Because executeFactor changes during adaptation (if on) this
	 * value needs to be written back to that class. Also, once the run-time GUI
	 * for changing those data items is in place, it will be possible to
	 * interfere with the adaptation process. So do this at peril.
	 * 
	 */
	public abstract boolean isAdaptiveExecute();

	public abstract int getExecuteFactor();

	public abstract void setExecuteFactor(int execFactor);

	public abstract double getReductionFactor();

	public abstract boolean isSleeper();

	public abstract long getSleepTime();

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public RegularPulse() {

	}

	public RegularPulse(CircularQueue queue, Component component) {
		this.queue = queue;
		this.component = component;
		executionThread = new Thread(this);
	}

	// -------------------------------------------------------------------------
	// MetabolismPulse Members
	// -------------------------------------------------------------------------

	public void run() {
		try {
			do {
				algorithmShell();
				Thread.sleep(1);
			} while (isAlive);
		} catch (InterruptedException ie) {
			isAlive = false;
			return;
		} catch (IllegalThreadStateException ex) {
			isAlive = false;
			return;
		} catch (Exception ex) {
			isAlive = false;
			return;
		}
	}

	public void start() {
		isAlive = true;
		executionThread.start();
	}

	public void stop() {
		isAlive = false;
	}
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	protected void push(Object o){
		queue.push(o);
	}
	
	/**
	 * The algorithm shell for pulsing wraps the main algorithm with concurrency
	 * related code. Unless there are bugs or other reasons to make this method
	 * overridable, it should stay final.
	 */
	public final void algorithmShell() throws InterruptedException {
		preProcess();
		processAlgorithm();
		postProcess();
	}

	/**
	 * return true if the number of codelets it has executed in this processing
	 * cycle is equal to the limit of how many it is allowed to process defined
	 * by executeFactor or if there no more codelets available to be processed.
	 * Note that if the limit is reached, the codelet count to be processed is
	 * reset, but if the limit has not been reached, this number is not reset
	 * regardless of whether there are any codelets available to be processed.
	 */
	public boolean checkIfDoneProcessing() {
		int execFactor = getExecuteFactor();
		boolean done;
		if (execFactor > 0) {
			done = (currentCodeletsExecuted == execFactor)
					|| !hasMoreCodeletsToProcess();
			if (currentCodeletsExecuted == execFactor) {
				currentCodeletsExecuted = 0;
			}
		} else {
			done = !hasMoreCodeletsToProcess();
		}
		return done;
	}

	public Codelet getCodeletToProcess() {
		Codelet cod = (Codelet) queue.pop();
		return cod;
	}

	public Component getComponent() {
		return component;
	}

	public int getCurrentCodeletsExecuted() {
		return currentCodeletsExecuted;
	}

	/**
	 * return <code>true</code> if the queue is not empty, false otherwise
	 */
	public boolean hasMoreCodeletsToProcess() {
		if (!queue.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * TODO should we keep this stuff in here? Is that some variant to the arch
	 * we can build in?
	 * 
	 * return true if this Pulse object uses <code>StarcatController</code>,
	 * false otherwise
	 */
	public boolean isConcurrent() {
		return concurrent;
	}

	/**
	 * adjusts the execution factor for the pulse by increasing it or decreasing
	 * it based on the algorithm defined in this method
	 */
	public void postProcess() {
		int execFactor = getExecuteFactor();
		if (isAdaptiveExecute()) {
			int afterPulse = queue.size();
			int change = Math.abs(beforePulse - afterPulse);
			if (afterPulse == 0 && execFactor > 1) {
				execFactor -= Math.round(execFactor * getReductionFactor());
			} else {
				execFactor += change;
			}
			setExecuteFactor(execFactor);
		}
	}

	/**
	 * for adaptation purposes, the pulse needs to know how big the queue is
	 * before processing, which is what this implementation of the method does
	 */
	public void preProcess() {
		beforePulse = queue.size();
	}

	/**
	 * increments the "codelets processed" count and executes the codelet.
	 */
	public void process(Codelet codelet) {
		if (codelet != null) {
			currentCodeletsExecuted++;
			getComponent().executeCodelet(codelet);
		}
	}

	/**
	 * the algorithm here is essentially:
	 * 
	 * if (not done processing) if (have more codelets to do something with) do
	 * something with them if (this pulse sleeps after finishing codelet
	 * processing) sleep
	 * 
	 */
	public void processAlgorithm() {
		while (!checkIfDoneProcessing()) {
			// removed if statement to check for more codelets to process
			process(getCodeletToProcess());
		}
		if (isSleeper() && isAlive) {
			try {
				long time = getSleepTime();
				long count = 0;
				while (time > count && isAlive) {
					Thread.sleep(1);
					count++;
				}
				// Thread.sleep(getSleepTime());
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			} catch (Exception ex) {
				return;
			}
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setConcurrent(boolean concurrent) {
		this.concurrent = concurrent;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isDone() {
		return done;
	}

	public Object getId() {
		return sObjDelegate.getId();
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	// #endregion
}
