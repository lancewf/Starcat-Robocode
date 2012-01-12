package org.starcat.codelets;

import java.util.ArrayList;
import java.util.List;
import org.starcat.codelets.Codelet;
import org.starcat.slipnet.Slipnet;
import org.starcat.slipnet.SlipnetNode;
import org.starcat.coderack.Coderack;
import org.starcat.workspace.Workspace;

/**
 * BehaviorCodelet objects contain pieces of executable code that perform
 * operations within some.
 * 
 * BehaviorCodelets are to be thought of like RNA--the behavior that results
 * from a codelet depends on the environment that engages it. In the coderack a
 * codelet's behavior is to enqueue itself with its own urgency value. In the
 * slipnet a codelet's behavior is to add activation to a set of nodes, etc.
 * 
 * BehaviorCodelets are the workhorse of this architecture. A particular
 * subclass of codelet will carry knowledge of all the slipnet nodes to which
 * the codelet will add activation upon success. It will also carry knowledge of
 * all the slipnet nodes to which the codelet will add activation upon failure.
 * Subclasses may further extend this machinery so that other behavior outcomes
 * can add activation to certain nodes. This knowledge is stored in array lists
 * as references to the relevant nodes. These array lists are static and must be
 * set up at the same time as the slipnet static relationships, during
 * instantiation of the framework. The slipnetExec method uses these array lists
 * to direct the slipnet to add activation to those nodes when the codelet
 * arrives at the slipnet from the workspace.
 * 
 * All instances carry knowledge of their urgency, so that the execute(Coderack)
 * method can push this codelet into the coderack with the appropriate urgency.
 * 
 * There is no particular data associated with how the execute(Workspace) method
 * runs the codelet in the workspace. On the other hand, the semantics of that
 * behavior are the richest out of the three components, supported by the fact
 * that the workspace has the most methods in its interface. There is need for a
 * local boolean flag indicating success.
 * 
 * Subclasses may add local instance variables that support the specific work
 * that their various exec methods engage.
 * 
 * The current implementation assumes a one-to-one correspondence between a node
 * in the slipnet and a particular type of codelet. This is captured by the
 * static reference in the codelet.
 * 
 */
public abstract class BehaviorCodelet extends Codelet {
	// -----------------------------------------------------------------------------
	// #region Private Data
	// ------------------------------------------------------------------------------

	private static long executeCount = 0;

	private List<SlipnetNodeActivationRecipient> successActivationRecipients = new ArrayList<SlipnetNodeActivationRecipient>();

	private List<SlipnetNodeActivationRecipient> failureActivationRecipients = new ArrayList<SlipnetNodeActivationRecipient>();

	private String name;

	private boolean workspaceSuccess;

	private int numberToEmit;

	// -----------------------------------------------------------------------------
	// #region Constructor
	// -----------------------------------------------------------------------------

	public BehaviorCodelet() {
		// Do nothing
	}

	public BehaviorCodelet(Double urgency) {
		this(null, urgency);
	}

	public BehaviorCodelet(String name, Double urgency) {
		super(urgency);
		this.name = name;
		this.workspaceSuccess = false;
	}
	
	// -----------------------------------------------------------------------------
	// Public Members
	// -----------------------------------------------------------------------------

	public void setSuccessActivationRecipients(
			List<SlipnetNodeActivationRecipient> succRec) {
		successActivationRecipients = succRec;
	}

	public List<SlipnetNodeActivationRecipient> getSuccessActivationRecipients() {
		return successActivationRecipients;
	}

	public void addSuccessActivationRecipient(SlipnetNode node, int amountToAdd) {
		successActivationRecipients.add(new SlipnetNodeActivationRecipient(
				node, amountToAdd));
	}

	public void addSuccessActivationRecipient(
			SlipnetNodeActivationRecipient node) {
		successActivationRecipients.add(node);
	}

	public void setFailureActivationRecipients(
			List<SlipnetNodeActivationRecipient> failRec) {
		failureActivationRecipients = failRec;
	}

	public List<SlipnetNodeActivationRecipient> getFailureActivationRecipients() {
		return failureActivationRecipients;
	}

	public void addFailureActivationRecipient(SlipnetNode node, int amountToAdd) {
		failureActivationRecipients.add(new SlipnetNodeActivationRecipient(
				node, amountToAdd));
	}

	public void addFailureActivationRecipient(
			SlipnetNodeActivationRecipient node) {
		failureActivationRecipients.add(node);
	}

	/**
	 * Set the number of codelets to emit to the coderack when the the
	 * associated slipnet node is activated
	 */
	public void setNumberToEmit(int numToEmit) {
		this.numberToEmit = numToEmit;
	}

	/**
	 * Get the number of codelets to emit to the coderack when the codelet is
	 * activated
	 */
	public int getNumberToEmit() {
		return numberToEmit;
	}

	/**
	 * Set the name of the codelet
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isWorkspaceSuccess() {
		return workspaceSuccess;
	}

	public void setWorkspaceSuccess(boolean workspaceSuccess) {
		this.workspaceSuccess = workspaceSuccess;
	}

	// -----------------------------------------------------------------------------
	// Overidden Codelet Members
	// -----------------------------------------------------------------------------

	/*
	 * Codelets are duplicated many times during execution. The clone method
	 * facilitates that duplication process.
	 */
	public BehaviorCodelet clone() {
		BehaviorCodelet cod = (BehaviorCodelet) super.clone();
		cod.name = name;
		cod.workspaceSuccess = workspaceSuccess;
		cod.setSuccessActivationRecipients(this
				.getSuccessActivationRecipients());
		cod.setFailureActivationRecipients(this
				.getFailureActivationRecipients());

		return cod;
	}

	public void execute(Coderack coderack) {
		coderack.push(this, getUrgency());
	}

	public void execute(Slipnet slipnet) {
		if (workspaceSuccess) {
			for (SlipnetNodeActivationRecipient successfullRecipient : successActivationRecipients) {
				slipnet.addActivation(
						successfullRecipient.getActivationRecipient(),
						successfullRecipient.getAmountToAdd());
			}
		} else {
			for (SlipnetNodeActivationRecipient failureRecipient : failureActivationRecipients) {
				slipnet.addActivation(
						failureRecipient.getActivationRecipient(),
						failureRecipient.getAmountToAdd());
			}
		}
	}

	public void preExecute(Coderack coderack) {
		coderack.registerCodelet(this);
	}

	public void preExecute(Slipnet slipnet) {
		slipnet.registerCodelet(this);
	}

	public void preExecute(Workspace workspace) {
		workspace.registerCodelet(this);
	}

	public void postExecute(Coderack coderack) {
		coderack.unregisterCodelet(this);
	}

	public void postExecute(Slipnet slipnet) {
		slipnet.unregisterCodelet(this);
	}

	public void postExecute(Workspace workspace) {
		executeCount++;
		workspace.fireCodeletEvent(this);
		workspace.unregisterCodelet(this);
	}

	// -----------------------------------------------------------------------------
	// #region Public static Members
	// -----------------------------------------------------------------------------

	/**
	 * Get the total amount of time all codelets were executed
	 * 
	 * Used by system to determine how many codelets have been executed in the
	 * workspace. This is used to determine the age of workspace entities.
	 * Incremented in postExecute(workspace)
	 */
	public static long getTotalAmountOfTimesCodeletWereExecuted() {
		return executeCount;
	}

	// #endregion
}