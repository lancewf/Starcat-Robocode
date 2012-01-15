package org.starcat.coderack;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Date;
import org.starcat.core.Component;
import org.starcat.codelets.Codelet;

/**
 * Coderack is an implementation of stochastic priority queues. The algorithm
 * for selecting the codelet to remove from the coderack works as follows:
 * 
 * A codelet is enqueued with an urgency level. The urgency must be positive. A
 * codelet with an urgency of 5.0 is 5 times as likely to be selected as a
 * codelet with an urgency of 1.0, and 10 times as likely to be selectes as a
 * codelet with an urgency of 0.5.
 * 
 * The coderack also supports instruments for monitoring the number of codelets
 * and urgency groups that are currently in use. If there is a codelet on the
 * coderack with an urgency value of 2.0, then there is an urgency group for
 * 2.0. If there is no codelet on the coderack with an urgency of 2.0, then
 * there is no urgency group for 2.0. So the number of urgency groups represents
 * the number of different levels of urgency that are currently represented on
 * the coderack.
 * 
 */
public class Coderack extends Component {
	
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private double totalUrgency = 0.0;

	private TreeMap<Double, UrgencyGroup> groups = new TreeMap<Double, UrgencyGroup>(
			new UrgencyComparator());

	private LifetimeTable lifetimeTable = new LifetimeTable();

	private Random rng = new Random();

	// --------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------

	public Coderack() {}

	// --------------------------------------------------------------------------
	// Public Members
	// --------------------------------------------------------------------------

	/**
	 * Adds a codelet to the coderack at the given urgency level.
	 * 
	 * @param codelet
	 *            The Codelet to enqueue.
	 * @param urgency
	 *            The urgency level to assign to the codelet.
	 * 
	 * @throws IllegalArgumentException
	 *             if the urgency level is non-positive.
	 */
	public synchronized void push(Codelet codelet, double urgency)
			throws IllegalArgumentException {
		// Normal housekeeping.
		if (codelet == null) {
			return;
		}
		if (urgency < 0) {
			throw new IllegalArgumentException("Urgency value must be positive");
		}

		// Figure out which group to add to, and if no group for the given
		// urgency value exists, create one.
		UrgencyGroup group = (UrgencyGroup) groups.get(new Double(urgency));
		if (group == null) {
			group = makeGroup(urgency);
		}

		// Finally, add the codelet to the appropriate group and
		// update state, making sure to update the lifetime table while
		// you are at it.
		addCodelet(codelet, group);
		lifetimeTable.addCodelet(new CodeletGroupPair(codelet, group));
	}

	public void executeCodelet(Codelet codelet) {
		preExecuteCodelet(codelet);
		codelet.execute(this);
		postExecuteCodelet(codelet);
	}

	public void update() {
		// There may be more to do here...
		killDeadCodelets();
	}

	/**
	 * Makes a stochastic choice as to which codelet to dequeue from the
	 * coderack, and returns it.
	 * 
	 * @return a Codelet object chosen stochastically, by urgency
	 */
	public synchronized void pop() {
		// Make sure we can actually do this.
		if (groups.size() == 0) {
			return;
		}

		// Draw a random number and see just how deep we need
		// to go into the queue.
		double targetValue = rng.nextDouble() * totalUrgency;

		// Find the urgency group that we need to draw the codelet from.
		Iterator<Double> it = groups.keySet().iterator();
		UrgencyGroup group = null;
		double urgencySum = 0.0;
		do {
			group = (UrgencyGroup) groups.get((Double) it.next());
		} while ((urgencySum += (group.size() * group.getUrgency())) < targetValue);

		// Now we've found the group. So remove a codelet from the group,
		// update the state variables, and return the codelet.
		Codelet codelet = removeCodelet(group);

		fireCodeletEvent(codelet);
	}

	// --------------------------------------------------------------------------
	// Protected Members
	// --------------------------------------------------------------------------

	protected final void postExecuteCodelet(Codelet codelet) {
		codelet.postExecute(this);
	}

	protected final void preExecuteCodelet(Codelet codelet) {
		codelet.preExecute(this);
	}

	// --------------------------------------------------------------------------
	// Private Members
	// --------------------------------------------------------------------------

	/**
	 * Used to create a new group and add it to the set of urgency groups.
	 * 
	 * @param urgency
	 *            The urgency level associated with the new group.
	 * 
	 * @return The new group.
	 */
	private UrgencyGroup makeGroup(double urgency) {
		UrgencyGroup group = new UrgencyGroup(urgency);
		groups.put(new Double(urgency), group);
		return group;
	}

	/**
	 * Removes an urgency group from the set of groups.
	 * 
	 * @param group
	 *            The group to remove.
	 */
	private void removeGroup(UrgencyGroup group) {
		groups.remove(group);
	}

	/**
	 * Adds a codelet to the coderack.
	 * 
	 * @param codelet
	 *            The codelet to add.
	 * @param group
	 *            The urgency group to add it to.
	 */
	private void addCodelet(Codelet codelet, UrgencyGroup group) {
		group.add(codelet);
		updateUrgency(group.getUrgency());
	}

	/**
	 * Remove a codelet from the specified urgency group.
	 * 
	 * @param group
	 *            The urgency group to remove the codelet from.
	 * 
	 * @return The codelet that was removed.
	 */
	private Codelet removeCodelet(UrgencyGroup group) {
		Codelet codelet = group.remove(rng);
		updateUrgency(-group.getUrgency());

		if (group.size() == 0) {
			removeGroup(group);
		}
		return codelet;
	}

	/**
	 * Augments the total urgency by the given amount (positive or negative).
	 * Currently not instrumented but might become instrumented.
	 * 
	 * @param urgency
	 *            The urgency value to augment.
	 */
	private void updateUrgency(double urgency) {
		totalUrgency += urgency;

		// Just in case we need get roundoff error.
		if (totalUrgency < 0.0) {
			totalUrgency = 0.0;
		}
	}

	private void killDeadCodelets() {
		CodeletGroupPair[] deadGuys = lifetimeTable.getDeadCodelets(new Date());

		for (int i = 0; i < deadGuys.length; i++) {
			// First remove the codelet group pair from the lifetime
			// table.
			lifetimeTable.removeCodelet(deadGuys[i]);
			// Now remove the codelet from the urgency group.
			deadGuys[i].getGroup().remove(deadGuys[i].getCodelet());
		}
	}
}
