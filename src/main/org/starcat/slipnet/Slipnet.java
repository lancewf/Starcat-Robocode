package org.starcat.slipnet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.starcat.core.Component;
import org.starcat.codelets.CodeletEvent;
import org.starcat.codelets.Codelet;
import org.starcat.codelets.BehaviorCodelet;
import org.starcat.codelets.SlipnetNodeActivationRecipient;

/**
 * Slipnet is a standard implementation of concept nodes and the relationships
 * among them.
 * 
 * Details of the implementation are provided in the code below. It is
 * essentially a hashmap whose keys are strings naming the concept node and
 * whose values are the associated concept node data structure.
 * 
 * The slipnet also supports instruments for monitoring various information.
 */
public class Slipnet extends Component {
	// --------------------------------------------------------------------------
	// Private Static Data
	// --------------------------------------------------------------------------

	private static String DEF_OBJ_NAME = "Item";

	private static String DEF_STRUCT_NAME = "Relation";

	private static String DEF_IDENT_NAME = "Identity";

	private static int DEF_CON = 0;

	private static int DEF_ACT = 0;

	private static int DEF_THRESH = 0;

	private static int DEF_NUM_POST = 0;

	// --------------------------------------------------------------------------
	// Protected Data
	// --------------------------------------------------------------------------

	// An array of key-value pairs; keys are conceptnames
	// and values are associated nodes
	protected HashMap<String, SlipnetNode> slipnodeStore 
		= new HashMap<String, SlipnetNode>();

	// --------------------------------------------------------------------------
	//  Constructor
	// --------------------------------------------------------------------------

	public Slipnet() {
		// createIdentityNode
		SlipnetNode node = new SlipnetNode(DEF_IDENT_NAME, DEF_CON, DEF_ACT,
				DEF_THRESH, DEF_NUM_POST);
		addSlipnetNode(node);

		// createRootObjectNode
		node = new SlipnetNode(DEF_OBJ_NAME, DEF_CON, DEF_ACT, DEF_THRESH,
				DEF_NUM_POST);
		addSlipnetNode(node);

		// createRootStructureNode
		node = new SlipnetNode(DEF_STRUCT_NAME, DEF_CON, DEF_ACT, DEF_THRESH,
				DEF_NUM_POST);
		addSlipnetNode(node);
	}

	// --------------------------------------------------------------------------
	// Component Members
	// --------------------------------------------------------------------------

	public void executeCodelet(Codelet codelet) {
		preExecuteCodelet(codelet);
		codelet.execute(this);
		postExecuteCodelet(codelet);
	}

	protected final void postExecuteCodelet(Codelet codelet) {
		codelet.postExecute(this);
	}

	protected final void preExecuteCodelet(Codelet codelet) {
		codelet.preExecute(this);
	}

	/**
	 * Updates are periodically triggered by the SlipnetTide. During each
	 * update, all event data from the workspace is reviewed, and that data
	 * determines which nodes receive increased activation. Next, all nodes get
	 * to spread activation to their neighbors, and (if over the activation
	 * threshold) possibly become fully active and possibly produce codelets.
	 * Lastly, node activations decay.
	 */
	public synchronized void update() {
		for (SlipnetNode node : getSlipnetNodes()) {
			node.spreadActivation();
		}

		for (SlipnetNode node : getSlipnetNodes()) {
			node.update();
		}
	}
	
	// --------------------------------------------------------------------------
	// Local Public Members
	// --------------------------------------------------------------------------

	/**
	 * Bi-directional association from Slipnet to node and vice versa. Relieves
	 * user of having to remember to set it both ways. Identity lateral link is
	 * also built here.
	 */
	public void addSlipnetNode(SlipnetNode node) {
		slipnodeStore.put(node.getName(), node);
		node.setSlipnet(this);
		// This is important. It adds an identity link to itself. It
		// must be called after setSlipnet() as it gets the identity
		// label node from the slipnet. It cannot be called in the
		// constructor because of preliminary nodes created that need to
		// get around some issues. Basically remember this happens here
		// when a node is added to the Slipnet.
		node.linkToSelf();
	}

	/**
	 * This method returns a HashMap containing SlipnetNode objects, which are
	 * shallow clone SlipnetNode objects.
	 * 
	 */
	public static HashMap<String, SlipnetNode> shallowClone(
			HashMap<String, SlipnetNode> slipnodes)
			throws CloneNotSupportedException {
		HashMap<String, SlipnetNode> newSlipnodeStore = new HashMap<String, SlipnetNode>();

		for (SlipnetNode node : slipnodes.values()) {
			newSlipnodeStore.put(node.getName(), node.clone());
		}
		return newSlipnodeStore;
	}

	public SlipnetNode getSlipnetNode(Object key) {
		return (SlipnetNode) slipnodeStore.get(key);
	}

	public SlipnetNode getIdentityNode() {
		return getSlipnetNode(DEF_IDENT_NAME);
	}

	public SlipnetNode getRootObjectNode() {
		return getSlipnetNode(DEF_OBJ_NAME);
	}

	public SlipnetNode getRootRelationNode() {
		return getSlipnetNode(DEF_STRUCT_NAME);
	}

	/**
	 * It is recommended that you do NOT call this method right now. It destroys
	 * the Slipnet's construction of certain inherent nodes to the Slipnet when
	 * you pass in new nodes. Unless slipnetNodes has been created by another
	 * Slipnet, this method isn't safe at all.
	 * 
	 */
	public void setSlipnetNodeStore(HashMap<String, SlipnetNode> slipnetNodes) {
		slipnodeStore.clear();

		for (SlipnetNode node : slipnetNodes.values()) {
			addSlipnetNode(node);
		}
	}

	public void addActivation(SlipnetNode node, int amount) {
		node.addActivationToBuffer(amount);
	}

	public Collection<SlipnetNode> getSlipnetNodes() {
		return slipnodeStore.values();
	}

	public Iterator<SlipnetNode> nodeIterator() {
		return slipnodeStore.values().iterator();
	}

	public void postCodelet(Codelet codelet) {
		fireCodeletEvent(codelet);
	}

	public void handleBehaviorCodeletSuccessEvent(CodeletEvent event) {
		BehaviorCodelet codelet = (BehaviorCodelet) event.getCodelet();

		for (SlipnetNodeActivationRecipient recipient : codelet
				.getSuccessActivationRecipients()) {
			addActivation(recipient.getActivationRecipient(),
					recipient.getAmountToAdd());
		}
	}

	public void handleBehaviorCodeletFailureEvent(CodeletEvent event) {
		BehaviorCodelet codelet = (BehaviorCodelet) event.getCodelet();
		for (SlipnetNodeActivationRecipient recipient : codelet
				.getFailureActivationRecipients()) {
			addActivation(recipient.getActivationRecipient(),
					recipient.getAmountToAdd());
		}
	}
}
