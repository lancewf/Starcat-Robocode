package org.starcat.slipnet;

import org.starcat.core.PublicStarcatObject;
import org.starcat.core.StarcatObject;

/**
 * This class represents a Link between two nodes in the slipnet. Links go in
 * one direction. To represent a bi-directional Link, you must make two Links.
 * 
 */
public class Link implements Cloneable, StarcatObject {
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private final static double MAXIMUM_LENGTH = 100.0;

	private String name = "";
	private int intrinsicLength = 50;
	private SlipnetNode fromNode;
	private SlipnetNode toNode;
	private PublicStarcatObject sObjDelegate = new PublicStarcatObject(this);

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public Link() {
		//
		// Do nothing
		//
	}

	protected Link(String name, int intrinsicLength, SlipnetNode from,
			SlipnetNode to) {
		setName(name);
		setIntrinsicLength(intrinsicLength);
		setFromNode(from);
		setToNode(to);
	}

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/*
	 * shallow clone right now
	 */
	public Link clone() {
		Link link;
		try {
			link = (Link) super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new InternalError(cnse.getMessage());
		}
		return link;
	}

	/**
	 * Get the intrinsic length is the natural length of the link The lower the
	 * value of the length the more the activation that is transfered across the
	 * link.
	 * 
	 * All links have an intrinsic length, which can change for SlipLink
	 * objects.
	 * 
	 * @return the length value
	 */
	public int getIntrinsicLength() {
		return intrinsicLength;
	}

	public SlipnetNode getFromNode() {
		return fromNode;
	}

	public SlipnetNode getToNode() {
		return toNode;
	}

	public String getName() {
		return name;
	}

	public static Link createIdentityLink(SlipnetNode node) {
		String name = "IndentityLink - " + node.getName();

		return new Link(name, 99, node, node);
	}

	public double getDegreeOfAssociation() {
		return MAXIMUM_LENGTH - intrinsicLength;
	}

	public boolean isToNode(SlipnetNode node) {
		return toNode == node;
	}

	public boolean isFromNode(SlipnetNode node) {
		return fromNode == node;
	}

	public void setFromNode(SlipnetNode fromNode) {
		this.fromNode = fromNode;
	}

	public void setToNode(SlipnetNode toNode) {
		this.toNode = toNode;
	}

	/**
	 * Set the intrinsic length is the natural length of the link The lower the
	 * value of the length the more the activation that is transfered across the
	 * link.
	 * 
	 * @param the
	 *            length value to set
	 */
	public void setIntrinsicLength(int intrinsicLength) {
		this.intrinsicLength = intrinsicLength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return getClass().getSimpleName() + ":" + getFromNode() + "--"
				+ intrinsicLength + "/";
	}

	public Object getId() {
		return sObjDelegate.getId();
	}
}