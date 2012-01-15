package org.starcat.coderack;

import java.util.Comparator;
import java.util.Date;

/**
 * This is a simple comparator to make keeping the dead codelets out of the
 * coderack easier to manage. It uses the CodeletGroupPair so that versing the
 * normal sorting order.
 * 
 */
public class LifetimeComparator implements Comparator<Object> {
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/**
	 * Compares two values. Assumes both values are either Date's or
	 * CodeletGroupPair's. In the latter case, it peels the death date from the
	 * CodeletGroupPair and uses that for comparison. Other than that, uses
	 * standard comparison semantics for longs.
	 * 
	 * @param o1
	 *            Had better be a Double or UrgenncyGroup.
	 * @param o2
	 *            Had also better be a Double or UrgencyGroup.
	 * 
	 * @return 1 if o1<o2, -1 if o1>o2, 0 otherwise.
	 */
	public int compare(Object o1, Object o2) {
		long death1 = 0;
		long death2 = 0;
		int seq1 = Integer.MAX_VALUE;
		int seq2 = Integer.MAX_VALUE;

		// We use class equivalence rather than instanceof for performance.
		if (Date.class == o1.getClass()) {
			death1 = ((Date) o1).getTime();
		} else {
			CodeletGroupPair cgp = (CodeletGroupPair) o1;
			death1 = cgp.getCodelet().getTimeToDie().getTime();
			seq1 = cgp.getSequenceNumber();
		}
		if (Date.class == o2.getClass()) {
			death2 = ((Date) o2).getTime();
		} else {
			CodeletGroupPair cgp = (CodeletGroupPair) o2;
			death2 = cgp.getCodelet().getTimeToDie().getTime();
			seq2 = cgp.getSequenceNumber();
		}

		// Now do the real comparison. Death times trump sequence numbers.
		// The sequence numbers are only here to get uniqueness (no two
		// CodeletGroupPair's with the same death time will have the same
		// sequence number. So if the death times are different, that's
		// enough to make the call. If the death times are the same, decide
		// based on sequence number.
		if (death1 < death2) {
			return -1;
		} else if (death1 > death2) {
			return 1;
		}
		// At this point, we know that the death times are the same. So
		// compare sequence numbers.
		if (seq1 < seq2) {
			return -1;
		} else if (seq1 > seq2) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Need this method for the Comparator interface.
	 * 
	 * @param o
	 *            The object to compare to.
	 * 
	 * @return Whether o is exactly this (same address).
	 */
	public boolean equals(Object o) {
		return (o == this);
	}
}
