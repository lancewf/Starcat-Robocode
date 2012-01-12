package org.starcat.configuration;

import org.starcat.slipnet.CategoryLink;
import org.starcat.slipnet.InstanceLink;
import org.starcat.slipnet.LateralLink;
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
public class SlipnetLinkConfiguration {
	private Slipnet slipnet;
	private Link linkBeingBuilt;

	public SlipnetLinkConfiguration(Slipnet slipnet){
		this.slipnet = slipnet;
	}

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

		if (linkBeingBuilt instanceof LateralLink) {
			node.addLateralLink((LateralLink) linkBeingBuilt);
		}
		if (linkBeingBuilt instanceof PropertyLink) {
			node.addPropertyLink((PropertyLink) linkBeingBuilt);
		}
		if (linkBeingBuilt instanceof CategoryLink) {
			node.addCategoryLink((CategoryLink) linkBeingBuilt);
		}
		if (linkBeingBuilt instanceof InstanceLink) {
			node.addInstanceLink((InstanceLink) linkBeingBuilt);
		}
		if (linkBeingBuilt instanceof SlipLink) {
			SlipLink slipLink = (SlipLink) linkBeingBuilt;
			node.addSlipLink(slipLink);
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
