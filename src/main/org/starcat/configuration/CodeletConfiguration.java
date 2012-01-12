package org.starcat.configuration;

import org.starcat.codelets.BehaviorCodelet;
import org.starcat.slipnet.Slipnet;
import org.starcat.slipnet.SlipnetNode;

/*
 * Configuration class for configuring codelets. NOTE: when 
 * configuring codelets, the <type> tag must be the first
 * tag inside the <codelet> tag.  This is so the proper 
 * codelet class can be created then populated with data.
 * 
 */
public class CodeletConfiguration {
	// --------------------------------------------------------------------------
	// #region Private Data
	// --------------------------------------------------------------------------

	private Slipnet slipnet;
	private BehaviorCodelet codeletBeingConstructed;

	// #endregion

	// --------------------------------------------------------------------------
	// #region Public Members
	// --------------------------------------------------------------------------

	public CodeletConfiguration(Slipnet slipnet) {
		this.slipnet = slipnet;
	}

	public void setClass(BehaviorCodelet codeletBeingConstructed) {
		this.codeletBeingConstructed = codeletBeingConstructed;
	}

	/**
	 * sets the source node for the codelet being constructed as well as adding
	 * the in-construction codelet to the node
	 */
	public void setSourceNode(String node) {
		SlipnetNode slipnetNode = slipnet.getSlipnetNode(node);
		slipnetNode.addCodelet(codeletBeingConstructed);
	}

	public void setName(String name) {
		codeletBeingConstructed.setName(name);
	}

	/**
	 * Set the urgency group value to assign to the codelet in the coderack
	 */
	public void setUrgency(double urgency) {
		codeletBeingConstructed.setUrgency(urgency);
	}

	public void setNumberToEmit(int amt) {
		codeletBeingConstructed.setNumberToEmit(amt);
	}

	/**
	 * During a sucessfull activation of the codelet the slipnet node passed
	 * in's activation is add to by the codelets failure amount
	 * 
	 * @param sucessRecipientNodeName
	 *            the name of the failure node to add as a failure recipient
	 */
	public void addSuccessActivator(String sucessRecipientNodeName,
			int amountToAdd) {
		SlipnetNode slipnetNode = slipnet
				.getSlipnetNode(sucessRecipientNodeName);
		codeletBeingConstructed.addSuccessActivationRecipient(slipnetNode,
				amountToAdd);
	}

	/**
	 * During a failed activation the slipnet node passed in activation is
	 * subtract by the codelets failure amount
	 * 
	 * @param failureNodeName
	 *            the name of the failure node to add as a failure recipient
	 */
	public void addFailureActivator(String failureNodeName, int amountToAdd) {
		SlipnetNode slipnetNode = slipnet.getSlipnetNode(failureNodeName);
		codeletBeingConstructed.addFailureActivationRecipient(slipnetNode,
				amountToAdd);
	}

	// #endregion
}
