package org.starcat.configuration;

import java.util.List;
import java.util.UUID;

import org.robocode.genenticalgorithm.Chromosome;
import org.starcat.codelets.BehaviorCodelet;
import org.starcat.slipnet.Link;
import org.starcat.slipnet.Slipnet;
import org.starcat.slipnet.SlipnetNode;

public abstract class SlipnetBuilder {
	// -----------------------------------------------------------------------------
	// #region Public Members
	// -----------------------------------------------------------------------------

	private Slipnet slipnet = new Slipnet();
	private SlipnetLinkConfiguration slipnetLinkConfiguration = null;
	private SystemConfiguration systemConfiguration = new SystemConfiguration();
	private CodeletConfiguration codeletConfiguration = null;

	// -----------------------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------------------

	public SlipnetBuilder() {
		this.slipnetLinkConfiguration = new SlipnetLinkConfiguration(slipnet);
		this.codeletConfiguration = new CodeletConfiguration(slipnet);
	}
	
	// -----------------------------------------------------------------------------
	// #region Public Members
	// -----------------------------------------------------------------------------

	public Slipnet buildSlipnet(Chromosome chromosome){
		List<String> slipnetNodeNames = getSlipnetNodeList(chromosome);
		initializeSystemConfigurations(systemConfiguration, chromosome);
		createSlipnetNodes(chromosome, slipnetNodeNames);
		createLinks(chromosome, slipnetNodeNames);
		createCodelets(chromosome);
		
		return slipnet;
	}

	// -----------------------------------------------------------------------------
	// #region Protected Abstract Members
	// -----------------------------------------------------------------------------

	protected abstract List<String> getSlipnetNodeList(Chromosome chromosome);

	protected abstract void initializeSystemConfigurations(
			SystemConfiguration systemConfiguration, Chromosome chromosome);

	protected abstract void createSlipnetNodes(Chromosome chromosome,
			List<String> slipnetNodeNames);

	protected abstract void createLinks(Chromosome chromosome,
			List<String> slipnetNodeNames);

	protected abstract void createCodelets(Chromosome chromosome);

	// #endregion

	// -----------------------------------------------------------------------------
	// #region Protected Members
	// -----------------------------------------------------------------------------

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
	 */
	protected void createCodelet(BehaviorCodelet codeletBeingConstructed,
			String sourceNode, double urgency, int numberToEmit) {
		codeletConfiguration.setClass(codeletBeingConstructed);
		codeletConfiguration.setName(UUID.randomUUID().toString());
		codeletConfiguration.setSourceNode(sourceNode);
		codeletConfiguration.setUrgency(urgency);
		codeletConfiguration.setNumberToEmit(numberToEmit);
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
		codeletConfiguration.setClass(codeletBeingConstructed);
		codeletConfiguration.setName(UUID.randomUUID().toString());
		codeletConfiguration.setSourceNode(sourceNode);
		codeletConfiguration.setUrgency(urgency);
		codeletConfiguration.setNumberToEmit(numberToEmit);
		codeletConfiguration.addSuccessActivator(successActivatorName,
				succAmount);
		codeletConfiguration.addFailureActivator(failureActivatorName,
				failureAmount);
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
	 */
	protected void createCodelet(BehaviorCodelet codeletBeingConstructed,
			String sourceNode, double urgency, int succAmount,
			int numberToEmit, String successActivatorName) {
		codeletConfiguration.setClass(codeletBeingConstructed);
		codeletConfiguration.setName(UUID.randomUUID().toString());
		codeletConfiguration.setSourceNode(sourceNode);
		codeletConfiguration.setUrgency(urgency);
		codeletConfiguration.setNumberToEmit(numberToEmit);
		codeletConfiguration.addSuccessActivator(successActivatorName,
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
		codeletConfiguration.setClass(codeletBeingConstructed);
		codeletConfiguration.setName(name);
		codeletConfiguration.setSourceNode(sourceNode);
		codeletConfiguration.setUrgency(urgency);
		codeletConfiguration.setNumberToEmit(numberToEmit);

		for (String failureActivatorName : failureActivatorNames) {
			codeletConfiguration.addFailureActivator(failureActivatorName,
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
	protected void createLink(Link linkBegingBuilt, String fromSlipnetNode,
			String toSlipnetNode, int intrinsicLength) {
		String name = linkBegingBuilt.getClass().getName() + " - "
				+ fromSlipnetNode + " -> " + toSlipnetNode;

		slipnetLinkConfiguration.buildLink(linkBegingBuilt);
		slipnetLinkConfiguration.setName(name);
		slipnetLinkConfiguration.setIntrinsicLength(intrinsicLength);
		slipnetLinkConfiguration.setNodes(fromSlipnetNode, toSlipnetNode);
	}

	/**
	 * Create a slipnet node
	 * 
	 * @param name
	 *            the name of the slipnet node
	 * @param conceptualDepth
	 *            the depth of abstraction. the higher the value the longer
	 *            memory is stored in the node.
	 * @param initalActivation
	 *            the initial activation value
	 * @param activationThreashold
	 *            the activation threshold to release a codelet
	 */
	protected void createSlipnetNode(String name, int conceptualDepth,
			int initalActivation, int activationThreashold) {
		SlipnetNode newNode = new SlipnetNode();
		newNode.setName(name);
		newNode.setConceptualDepth(conceptualDepth);
		newNode.setActivation(initalActivation);
		newNode.setActivationThreshold(activationThreashold);

		slipnet.addSlipnetNode(newNode);
	}

	// #endregion
}
