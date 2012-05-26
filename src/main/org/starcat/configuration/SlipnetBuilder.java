package org.starcat.configuration;

import java.util.List;
import java.util.UUID;

import org.robocode.genenticalgorithm.Chromosome;
import org.starcat.codelets.BehaviorCodelet;
import org.starcat.slipnet.Link;
import org.starcat.slipnet.Slipnet;
import org.starcat.slipnet.SlipnetNode;

public abstract class SlipnetBuilder {
	// -------------------------------------------------------------------------
	//  Public Members
	// -------------------------------------------------------------------------

	private Slipnet slipnet = new Slipnet();
	private SlipnetLinkBuilder slipnetLinkBuilder = new SlipnetLinkBuilder(slipnet);
	private CodeletBuilder codeletBuilder = new CodeletBuilder(slipnet);
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	public Slipnet buildSlipnet(Chromosome chromosome){
		initializeSystemConfigurations(chromosome);
		createSlipnetNodes(chromosome);
		createLinks(chromosome);
		createCodelets(chromosome);
		
		return slipnet;
	}

	// -------------------------------------------------------------------------
	// Protected Abstract Members
	// -------------------------------------------------------------------------

	protected abstract void initializeSystemConfigurations(Chromosome chromosome);

	protected abstract void createSlipnetNodes(Chromosome chromosome);

	protected abstract void createLinks(Chromosome chromosome);

	protected abstract void createCodelets(Chromosome chromosome);

	// -------------------------------------------------------------------------
	// Protected Members
	// -------------------------------------------------------------------------

	/**
	 * Create a codelet
	 * 
	 * @param codeletBeingConstructed
	 *            the codelet being constructed
	 * @param sourceNode
	 *            the slipnet node associated to the codelet
	 * @param urgency
	 *            The urgency group to assign to the codelet in the coderack.
	 *            the higher the urgency the more likely that it gets chosen in
	 *            the coderack
	 */
	protected void createCodelet(BehaviorCodelet codeletBeingConstructed,
			String sourceNode, double urgency, int numberToEmit) {
		codeletBuilder.setColeletBeingConstructed(codeletBeingConstructed);
		codeletBuilder.setName(UUID.randomUUID().toString());
		codeletBuilder.setSourceNode(sourceNode);
		codeletBuilder.setUrgency(urgency);
		codeletBuilder.setNumberToEmit(numberToEmit);
	}

	/**
	 * Create a codelet
	 * 
	 * @param codeletBeingConstructed
	 *            the codelet being constructed
	 * @param name
	 *            the name of the codelet
	 * @param sourceNode
	 *            the Slipnet node associated to the codelet
	 * @param urgency
	 *            The urgency group to assign to the codelet in the Coderack.
	 *            the higher the urgency the more likely that it gets chosen in
	 *            the Coderack
	 * @param succAmount
	 *            the amount to add to the Slipnet node if the codelet succeeds
	 * @param failureAmount
	 *            the amount to add to the Slipnet node if the codelet fails
	 * @param numberToEmit
	 *            The number of codelets clones to emit to the Coderack when
	 *            activated
	 * @param successActivatorName
	 *            during a successful activation the slipnet node to add the
	 *            success amount to
	 * @param failureActivatorName
	 *            during a failure activation the slipnet node to subtract the
	 *            failure amount to
	 */
	protected void createCodelet(BehaviorCodelet codeletBeingConstructed,
			String sourceNode, double urgency, int succAmount,
			int failureAmount, int numberToEmit, String successActivatorName,
			String failureActivatorName) {
		codeletBuilder.setColeletBeingConstructed(codeletBeingConstructed);
		codeletBuilder.setName(UUID.randomUUID().toString());
		codeletBuilder.setSourceNode(sourceNode);
		codeletBuilder.setUrgency(urgency);
		codeletBuilder.setNumberToEmit(numberToEmit);
		codeletBuilder.addSuccessActivator(successActivatorName,
				succAmount);
		codeletBuilder.addFailureActivator(failureActivatorName,
				failureAmount);
	}

	/**
	 * Create a codelet
	 * 
	 * @param codeletBeingConstructed
	 *            the codelet being constructed
	 * @param sourceNode
	 *            the Slipnet node associated to the codelet
	 * @param urgency
	 *            The urgency group to assign to the codelet in the Coderack.
	 *            the higher the urgency the more likely that it gets chosen in
	 *            the Coderack
	 * @param succAmount
	 *            the amount to add to the Slipnet node if the codelet succeeds
	 * @param failureAmount
	 *            the amount to add to the Slipnet node if the codelet fails
	 * @param numberToEmit
	 *            The number of codelets clones to emit to the Coderack when
	 *            activated
	 * @param successActivatorName
	 *            during a successful activation the slipnet node to add the
	 *            success amount to
	 */
	protected void createCodelet(BehaviorCodelet codeletBeingConstructed,
			String sourceNode, double urgency, int succAmount,
			int numberToEmit, String successActivatorName) {
		codeletBuilder.setColeletBeingConstructed(codeletBeingConstructed);
		codeletBuilder.setName(UUID.randomUUID().toString());
		codeletBuilder.setSourceNode(sourceNode);
		codeletBuilder.setUrgency(urgency);
		codeletBuilder.setNumberToEmit(numberToEmit);
		codeletBuilder.addSuccessActivator(successActivatorName,
				succAmount);
	}

	/**
	 * Create a codelet
	 * 
	 * @param codeletBeingConstructed
	 *            the codelet being constructed
	 * @param name
	 *            the name of the codelet
	 * @param sourceNode
	 *            the slipnet node associated to the codelet
	 * @param urgency
	 *            The urgency group to assign to the codelet in the coderack.
	 *            the higher the urgency the more likely that it gets chosen in
	 *            the coderack
	 * @param succAmount
	 *            the amount to add to the slipnet node if the codelet succeeds
	 * @param failureAmount
	 *            the amount to add to the slipnet node if the codelet fails
	 * @param numberToEmit
	 *            The number of codelets clones to emit to the coderack when
	 *            activated
	 * @param successActivatorName
	 *            during a successful activation the slipnet node to add the
	 *            success amount to
	 * @param failureActivatorName
	 *            during a failure activation the slipnet node to subtract the
	 *            failure amount to
	 */
	protected void createCodelet(BehaviorCodelet codeletBeingConstructed,
			String name, String sourceNode, double urgency, int succAmount,
			int failureAmount, int numberToEmit,
			List<String> failureActivatorNames) {
		codeletBuilder.setColeletBeingConstructed(codeletBeingConstructed);
		codeletBuilder.setName(name);
		codeletBuilder.setSourceNode(sourceNode);
		codeletBuilder.setUrgency(urgency);
		codeletBuilder.setNumberToEmit(numberToEmit);

		for (String failureActivatorName : failureActivatorNames) {
			codeletBuilder.addFailureActivator(failureActivatorName,
					failureAmount);
		}
	}

	/**
	 * Create a one way link
	 * 
	 * @param linkBegingBuilt
	 *            The link being created
	 * @param fromSlipnetNode
	 *            the source of the spread of activation
	 * @param toSlipnetNode
	 *            the receiver of the spread of activation
	 * @param intrinsicLength
	 *            The percentage of activation from the source (from) to
	 *            receiver (to)
	 * @param labelNode
	 */
	protected void createLink(String fromSlipnetNode,
			String toSlipnetNode, int intrinsicLength) {
		Link linkBegingBuilt = new Link();
		
		String name = linkBegingBuilt.getClass().getName() + " - "
				+ fromSlipnetNode + " -> " + toSlipnetNode;
		
		slipnetLinkBuilder.buildLink(linkBegingBuilt);
		slipnetLinkBuilder.setName(name);
		slipnetLinkBuilder.setIntrinsicLength(intrinsicLength);
		slipnetLinkBuilder.setNodes(fromSlipnetNode, toSlipnetNode);
	}

	/**
	 * Create a slipnet node
	 * 
	 * @param name
	 *            the name of the slipnet node
	 * @param memoryLevel
	 *            The higher the value the longer
	 *            memory is stored in the node.
	 * @param initalActivation
	 *            the initial activation value
	 * @param activationThreashold
	 *            This is the level that the slipnet has to be before it places 
	 *            an assigned codelet into the coderack
	 */
	protected void createSlipnetNode(String name, int memoryLevel,
			int initalActivation, int activationThreashold) {
		
		SlipnetNode newNode = new SlipnetNode(name, memoryLevel, 
				initalActivation, activationThreashold, 0);

		slipnet.addSlipnetNode(newNode);
	}
}
