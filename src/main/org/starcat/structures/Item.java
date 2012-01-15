package org.starcat.structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.starcat.structures.Bond;

/**
 * This class represents items other than WorkspaceStructure objects in the
 * Workspace. Because we are trying to create an architecture that is
 * domain-neutral, we should probably strive to have this be as general as
 * possible, and allow Description objects to define it. If we follow the
 * Copycat model, it seems that anything that would like to be an object, should
 * be instance-linked to the "object-category" node in the Slipnet
 */
public abstract class Item extends Entity {
	// --------------------------------------------------------------------------
	// Protected Data
	// --------------------------------------------------------------------------

	private Set<Bond> bonds = new HashSet<Bond>();

	// --------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------

	public Item() {}

	// --------------------------------------------------------------------------
	// Public Members
	// --------------------------------------------------------------------------

	public Iterator<Bond> bondIterator() {
		return bonds.iterator();
	}

	public List<Bond> getBonds() {
		return new ArrayList<Bond>(bonds);
	}

	public void addBond(Bond bond) {
		bonds.add(bond);
	}

	public boolean removeBond(Bond bond) {
		return bonds.remove(bond);
	}

	public boolean containsBond(Bond bond) {
		return bonds.contains(bond);
	}
}
