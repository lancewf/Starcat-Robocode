package org.starcat.configuration;

import org.starcat.slipnet.CategoryLink;
import org.starcat.slipnet.InstanceLink;
import org.starcat.slipnet.Link;
import org.starcat.slipnet.PropertyLink;
import org.starcat.slipnet.SlipLink;
import org.starcat.slipnet.Slipnet;
import org.starcat.slipnet.SlipnetNode;

/*
 * Configuration class for Slipnet links.  NOTE: the 
 * <class> tag must be the first tag 
 * nested inside the <link> tag.  This is so the
 * correct link type gets created to then be configured.
 */
public class SlipnetLinkBuilder {

	// --------------------------------------------------------------------------
	// Private Data
	// --------------------------------------------------------------------------

	private Slipnet slipnet;
	private Link linkBeingBuilt;

	// --------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------

	public SlipnetLinkBuilder(Slipnet slipnet) {
		this.slipnet = slipnet;
	}

	// --------------------------------------------------------------------------
	// Public Members
	// --------------------------------------------------------------------------

	public void setName(String name) {
		linkBeingBuilt.setName(name);
	}

	public void setIntrinsicLength(int intrinsLen) {
		linkBeingBuilt.setIntrinsicLength(intrinsLen);
	}

	public void setNodes(String fromNode, String toNode) {
		setToNode(toNode);
		setFromNode(fromNode);
	}

	public void setToNode(String toNode) {
		linkBeingBuilt.setToNode(slipnet.getSlipnetNode(toNode));
	}

	public void setFromNode(String fromNode) {
		SlipnetNode node = slipnet.getSlipnetNode(fromNode);
		linkBeingBuilt.setFromNode(node);

		if (linkBeingBuilt instanceof PropertyLink) {
			node.addPropertyLink((PropertyLink) linkBeingBuilt);
		}
		else if (linkBeingBuilt instanceof CategoryLink) {
			node.addCategoryLink((CategoryLink) linkBeingBuilt);
		}
		else if (linkBeingBuilt instanceof InstanceLink) {
			node.addInstanceLink((InstanceLink) linkBeingBuilt);
		}
		else if (linkBeingBuilt instanceof SlipLink) {
			SlipLink slipLink = (SlipLink) linkBeingBuilt;
			node.addSlipLink(slipLink);
		}
		else {
			node.addLateralLink(linkBeingBuilt);
		}
	}

	public void setMinShrunkLength(int len) {
		((SlipLink) linkBeingBuilt).setMinShrunkLength(len);
	}

	public void setLabelNode(String labelNode) {
		if (SlipLink.class.isInstance(linkBeingBuilt)) {
			SlipLink slipLink = (SlipLink) linkBeingBuilt;
			slipLink.setLabelNode(slipnet.getSlipnetNode(labelNode));
		}
	}

	public void buildLink(Link linkBeingBuilt) {
		this.linkBeingBuilt = linkBeingBuilt;
	}
}
