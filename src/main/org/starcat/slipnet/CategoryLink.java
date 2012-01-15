package org.starcat.slipnet;

/**
 * Representation of a Category Link as found in Copycat
 */
public class CategoryLink extends Link {
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public CategoryLink() {
		super();
	}

	public CategoryLink(String name, int intrinsicLength, SlipnetNode from,
			SlipnetNode to) {
		super(name, intrinsicLength, from, to);
	}

	public CategoryLink(int intrinsicLength, SlipnetNode from, SlipnetNode to) {
		this("", intrinsicLength, from, to);
	}

	// -------------------------------------------------------------------------
	// Public static Members
	// -------------------------------------------------------------------------

	public static CategoryLink createCategoryLink(int intrinsicLength,
			SlipnetNode from, SlipnetNode to) {
		return new CategoryLink(intrinsicLength, from, to);
	}
}
