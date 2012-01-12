package org.starcat.codelets;

import org.starcat.slipnet.SlipnetNode;

public class SlipnetNodeActivationRecipient {
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private SlipnetNode activationRecipient;
	private int amountToAdd;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public SlipnetNodeActivationRecipient(SlipnetNode activationRecipient,
			int amountToAdd) {
		this.amountToAdd = amountToAdd;
		this.activationRecipient = activationRecipient;
	}

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	public int getAmountToAdd() {
		return amountToAdd;
	}

	public SlipnetNode getActivationRecipient() {
		return activationRecipient;
	}
}
