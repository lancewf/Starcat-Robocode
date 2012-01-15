package org.starcat.slipnet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.starcat.core.Keyable;
import org.starcat.core.PublicStarcatObject;
import org.starcat.core.StarcatObject;
import org.starcat.codelets.Codelet;
import org.starcat.codelets.BehaviorCodelet;

public class SlipnetNode implements Cloneable, Keyable, StarcatObject {
	
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private static final int MAXACTIVATION = 100;
	private static final int MINACTIVATION = 0;
	private static Random rng = new Random();

	private Object key;
	private String name;
	private int activation;
	private int activationBuffer = 0;
	private int activationThreshold;
	private int numTimesCalcFullAct = 0;
	private int numTimesGoFullAct = 0;

	// linkage management
	private List<Link> incomingLinks = new ArrayList<Link>();
	private List<Link> outgoingLinks = new ArrayList<Link>();

	private List<CategoryLink> categoryLinks = new ArrayList<CategoryLink>();
	private List<InstanceLink> instanceLinks = new ArrayList<InstanceLink>();
	private List<LateralLink> lateralLinks = new ArrayList<LateralLink>();
	private List<PropertyLink> propertyLinks = new ArrayList<PropertyLink>();
	private List<SlipLink> slipLinks = new ArrayList<SlipLink>();

	private List<Codelet> codeletList = new ArrayList<Codelet>();
	private int conceptualDepth;

	// instrumentation
	private int numActivationIncreaseAttempts = 0;

	/*
	 * When the SlipnetNode goes to full activation, this is the number of
	 * Codelet objects posted.
	 */
	private int numCodeletsToPost;
	private int numTimesFullyActivated = 0;

	/*
	 * Sets the number of slipnet updates that occur before activation can
	 * change. Default is 0.
	 */
	private int numUpdatesToClampAct = 0;

	private Slipnet slipnet;

	private PublicStarcatObject sObjDelegate = new PublicStarcatObject(this);

	// -----------------------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------------------

	public SlipnetNode(String name, int conceptualDepth, int activation,
			int activationThreshold, int numCodeletsToPost) {
		if (name == null) {
			throw new IllegalArgumentException(
					"Slipnet node names cannot be null");
		}
		this.name = name;
		this.activation = activation;
		this.activationThreshold = activationThreshold;
		this.conceptualDepth = conceptualDepth;
		this.numCodeletsToPost = numCodeletsToPost;
	}

	// -----------------------------------------------------------------------------
	// Public members
	// -----------------------------------------------------------------------------

	public void linkToSelf() {
		LateralLink lLink = Link.createIdentityLink(this);
		addLateralLink(lLink);
	}

	public void activate() {
		activation = MAXACTIVATION;
	}

	public void addActivationToBuffer(int amount) {
		numActivationIncreaseAttempts++;
		activationBuffer = activationBuffer + amount;
	}

	public void addCodelet(Codelet cod) {
		codeletList.add(cod);
	}

	public void addCategoryLink(CategoryLink link) {
		categoryLinks.add(link);
		outgoingLinks.add(link);
		link.getToNode().addIncomingLink(link);
	}

	public void addInstanceLink(InstanceLink link) {
		instanceLinks.add(link);
		outgoingLinks.add(link);
		link.getToNode().addIncomingLink(link);
	}

	public void addLateralLink(LateralLink link) {
		lateralLinks.add(link);
		outgoingLinks.add(link);
		link.getToNode().addIncomingLink(link);
	}

	public void addPropertyLink(PropertyLink link) {
		propertyLinks.add(link);
		outgoingLinks.add(link);
		link.getToNode().addIncomingLink(link);
	}

	public void addSlipLink(SlipLink link) {
		slipLinks.add(link);
		outgoingLinks.add(link);
		link.getToNode().addIncomingLink(link);
	}

	public void addToActivationBuffer(int activationBufferIncrement) {
		this.activationBuffer += activationBufferIncrement;
	}

	public void clampActivation(int numUpdates) {
		numUpdatesToClampAct = numUpdates;
	}

	/*
	 * shallow clone implementation
	 */
	public SlipnetNode clone() throws CloneNotSupportedException {
		SlipnetNode node;
		try {
			node = (SlipnetNode) super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new InternalError(cnse.getMessage());
		}
		return node;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		return false;
	}

	public int getActivation() {
		return activation;
	}

	public void setActivation(int act) {
		activation = act;
	}

	public int getActivationThreshold() {
		return activationThreshold;
	}

	public Set<SlipnetNode> getCategoryNodes() {
		Set<SlipnetNode> cat = new HashSet<SlipnetNode>();

		for (CategoryLink categortyLink : categoryLinks) {
			cat.add(categortyLink.getToNode());
		}

		return cat;
	}

	public Set<SlipnetNode> getInstanceNodes() {
		Set<SlipnetNode> instances = new HashSet<SlipnetNode>();
		for (InstanceLink instanceLink : instanceLinks) {
			instances.add(instanceLink.getToNode());
		}

		return instances;
	}

	public Set<SlipnetNode> getPropertyNodes() {
		Set<SlipnetNode> props = new HashSet<SlipnetNode>();

		for (PropertyLink propertyLink : propertyLinks) {

			props.add(propertyLink.getToNode());
		}

		return props;
	}

	public Set<SlipnetNode> getLateralNodes() {
		Set<SlipnetNode> lats = new HashSet<SlipnetNode>();

		for (LateralLink lateralLink : lateralLinks) {
			lats.add(lateralLink.getToNode());
		}
		return lats;
	}

	public LateralLink getIdentityLink() {
		for (LateralLink lateralLink : lateralLinks) {
			if (lateralLink.getToNode() == lateralLink.getFromNode()
					&& lateralLink.getToNode() == this) {
				return lateralLink;
			}
		}
		return null;
	}

	public List<CategoryLink> getCategoryLinks() {
		return categoryLinks;
	}

	public List<Codelet> getCodeletList() {
		return codeletList;
	}

	public int getConceptualDepth() {
		return conceptualDepth;
	}

	public List<Link> getIncomingLinks() {
		return incomingLinks;
	}

	public List<InstanceLink> getInstanceLinks() {
		return instanceLinks;
	}

	public Object getKey() {
		return key;
	}

	public SlipnetNode getLabelNode(SlipnetNode toNode) {
		SlipLink link = getSlipLinkTo(toNode);
		if (link == null) {
			return null;
		}
		return link.getLabelNode();
	}

	public List<LateralLink> getLateralLinks() {
		return lateralLinks;
	}

	public Link getLinkTo(SlipnetNode toNode) {
		if (isLinkedTo(toNode)) {
			for (Link link : getOutgoingLinks()) {
				if (link.getToNode() == toNode) {
					return link;
				}
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public int getnumActivationIncreaseAttempts() {
		return numActivationIncreaseAttempts;
	}

	public int getNumActivationIncreaseAttempts() {
		return numActivationIncreaseAttempts;
	}

	public int getNumTimesFullyActivated() {
		return numTimesFullyActivated;
	}

	public int getNumUpdatesToClampAct() {
		return numUpdatesToClampAct;
	}

	public List<Link> getOutgoingLinks() {
		return outgoingLinks;
	}

	public List<PropertyLink> getPropertyLinks() {
		return propertyLinks;
	}

	public List<SlipLink> getSlipLinks() {
		return slipLinks;
	}

	public SlipLink getSlipLinkTo(SlipnetNode toNode) {
		if (isLinkedTo(toNode)) {
			for (Link link : slipLinks) {
				if (SlipLink.class.isInstance(link)) {
					return (SlipLink) link;
				}
			}
		}
		return null;
	}

	public Slipnet getSlipnet() {
		return slipnet;
	}

	protected boolean goToFullActivation(double probability) {
		numTimesCalcFullAct++;

		double rand = rng.nextDouble();

		if (rand < probability) {
			numTimesGoFullAct++;
			return true;
		}
		return false;
	}

	public boolean isActive() {
		return (activation == MAXACTIVATION);
	}

	protected boolean isCategoryLinkedTo(SlipnetNode anotherNode) {
		return isLinkedTo(categoryLinks, anotherNode);
	}

	public boolean isInstanceOf(SlipnetNode anotherNode) {
		return isLinkedTo(categoryLinks, anotherNode);
	}

	protected boolean isInstanceLinkedTo(SlipnetNode anotherNode) {
		return isLinkedTo(instanceLinks, anotherNode);
	}

	public boolean isCategoryOf(SlipnetNode anotherNode) {
		return isLinkedTo(instanceLinks, anotherNode);
	}

	public boolean isLinkedTo(SlipnetNode toNode) {
		return isLinkedTo(outgoingLinks, toNode);
	}

	public boolean isRelatedTo(SlipnetNode toNode) {
		if (this == toNode) {
			return true;
		}
		return isLinkedTo(toNode);
	}

	public boolean isSlipLinkedTo(SlipnetNode toNode) {
		return isLinkedTo(slipLinks, toNode);
	}

	/*
	 * a little funky, this takes the other node and looks to see if it has a
	 * property link to this node, since property links are not really
	 * bidirectional
	 */
	public boolean isPropertyOf(SlipnetNode anotherNode) {
		return isLinkedTo(anotherNode.getPropertyLinks(), this);
	}

	public void setActivationThreshold(int activationThreshold) {
		this.activationThreshold = activationThreshold;
	}

	public void setCodeletList(List<Codelet> codeletList) {
		this.codeletList = codeletList;
	}

	/**
	 * the higher the value the longer memory is stored in the node.
	 * 
	 * @param conceptualDepth
	 */
	public void setConceptualDepth(int conceptualDepth) {
		this.conceptualDepth = conceptualDepth;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumTimesFullyActivated(int numTimesFullyActivated) {
		this.numTimesFullyActivated = numTimesFullyActivated;
	}

	public void setNumUpdatesToClampAct(int numUpdatesToClampAct) {
		this.numUpdatesToClampAct = numUpdatesToClampAct;
	}

	public void setSlipnet(Slipnet net) {
		slipnet = net;
	}

	public synchronized void spreadActivation() {
		if (activation == 0) {
			return;
		}

		for (SlipnetNode neighborNode : getNeighbors()) {
			if (getOutgoingLinks() != null) {
				Link link = getLinkTo(neighborNode);
				if (link != null && link.getIntrinsicLength() != 100) {
					double increaseAmmountD = ((double) activation)
							* (0.01 * link.getDegreeOfAssociation());

					int increaseAmmount = (int) Math.round(increaseAmmountD);
					neighborNode.addActivationToBuffer(increaseAmmount);
				}
			}
		}
	}

	/*
	 * Algorithm: if node is not at full activation if activation + buffer is at
	 * least max activation then go to full activation else add buffer to
	 * current activation if node is above threshold probabilistically go to
	 * full activation if at full activation then probabilistically post
	 * codelets decay()
	 */
	public void update() {
		if (numUpdatesToClampAct != 0) {
			numUpdatesToClampAct--;
			return;
		}
		if (activation != MAXACTIVATION) {
			if (activation + activationBuffer >= MAXACTIVATION) {
				activation = MAXACTIVATION;
				numTimesFullyActivated++;
			} else {
				activation += activationBuffer;
			}
		}
		activationBuffer = 0;

		// if over threshold, probability of discontinuously
		// becoming fully activated
		if (activation >= activationThreshold) {
			if (goToFullActivation(getActivationProbability())) {
				activation = MAXACTIVATION;
				numTimesFullyActivated++;
			}
			// if over threshold, probability of posting codelets
			postCodelets(getActivationProbability());
		}
		decay();
	}

	public static int getMaxActivation() {
		return MAXACTIVATION;
	}

	public static int getMinActivation() {
		return MINACTIVATION;
	}

	public int getNumTimesCalcFullAct() {
		return numTimesCalcFullAct;
	}

	public int getNumTimesGoFullAct() {
		return numTimesGoFullAct;
	}

	public String toString() {
		return "{node=" + name + "}";
	}

	public Object getId() {
		return sObjDelegate.getId();
	}

	// -----------------------------------------------------------------------------
	// Implemented members of Keyable
	// -----------------------------------------------------------------------------

	public void setKey(Object key) {
		this.key = key;
	}

	// -----------------------------------------------------------------------------
	// Private Methods
	// -----------------------------------------------------------------------------

	private void addIncomingLink(Link link) {
		incomingLinks.add(link);
	}

	/**
	 * Post codelets to the coderack with the given probability <b> Up to the
	 * given number of codelets to post, probabilistically post the codelets to
	 * the coderack based on probability
	 * 
	 * @param probability
	 *            the probability to post each new codelet
	 */
	private void postCodelets(double probability) {
		for (Codelet codelet : codeletList) {
			BehaviorCodelet behaviorCodelet = (BehaviorCodelet) codelet;
			numCodeletsToPost = behaviorCodelet.getNumberToEmit();
			for (int i = 0; i < numCodeletsToPost; i++) {
				double rand = rng.nextDouble();
				if (rand < probability) {
					behaviorCodelet = (BehaviorCodelet) behaviorCodelet.clone();
					slipnet.postCodelet(behaviorCodelet);
				}
			}
		}
	}

	/*
	 * Algorithm: Activation buffer goes to 0 if (node has activation above min)
	 * then reduce activation by amount equal to 1% of difference between max
	 * activation and conceptual depth times the current activation if (the
	 * difference between the current activation and the amount to be reduced
	 * puts the node at minimum activation or below) then set the node's
	 * activation to minimum else reduce activation by calculated amount
	 */
	private void decay() {
		if (activation != MINACTIVATION) {
			int ammount = (int) Math.ceil(0.01
					* (MAXACTIVATION - conceptualDepth) * activation);

			if (activation - ammount <= MINACTIVATION) {
				activation = MINACTIVATION;
			} else {
				activation -= ammount;
			}
		}
	}

	private boolean isLinkedTo(List<? extends Link> linkList,
			SlipnetNode anotherNode) {
		if (linkList == null) {
			return false;
		}

		for (Link link : linkList) {
			if (link.isToNode(anotherNode)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The algorithm for the probability of posting codelets is basically the
	 * amount over the threshold the SlipnetNode is divided by the maximum
	 * activation less the activation threshold.
	 */
	private double getActivationProbability() {
		return (double) (activation - activationThreshold)
				/ (MAXACTIVATION - activationThreshold);
	}

	private Set<SlipnetNode> getNeighbors() {
		List<Link> nodeLinks = getOutgoingLinks();
		// SlipnetNode[] neighbors = new SlipnetNode[nodeLinks.size()];
		Set<SlipnetNode> neighbors = new HashSet<SlipnetNode>(nodeLinks.size());
		Link link;
		for (int i = 0; i < nodeLinks.size(); i++) {
			if (nodeLinks.get(i) != null) {
				link = (Link) nodeLinks.get(i);
				neighbors.add(link.getToNode());
			}
		}
		return neighbors;

	}
}
