package org.starcat.coderack;

import java.util.Comparator;

/**
 * This is a simple comparator to make the searching faster when you pop. 
 * Basically what it does is looks for the highest urgency codelets first 
 * by reversing the normal sorting order.
 *
 */
public class UrgencyComparator implements Comparator<Object> {
    /**
     * Compares two values. Assumes both values are either Double's or
     * UrgencyGroup's. In the latter case, it substitutes the group's 
     * urgency value. Puts bigger values ahead of smaller values.
     *
     * @param o1    Had better be a Double or UrgenncyGroup.
     * @param o2    Had also better be a Double or UrgencyGroup.
     *
     * @return 1 if o1<o2, -1 if o1>o2, 0 otherwise.
     */
    public int compare(Object o1, Object o2) 
    {
        double urgency1;
        double urgency2;
        //We use class equivalence rather than instanceof for performance
        if (Double.class == o1.getClass()) 
        {
            urgency1 = ((Double)o1).doubleValue();
        } 
        else 
        {
            urgency1 = ((UrgencyGroup)o1).getUrgency();
        }
        if (Double.class == o2.getClass()) 
        {
            urgency2 = ((Double)o2).doubleValue();
        } 
        else 
        {
            urgency2 = ((UrgencyGroup)o2).getUrgency();
        }
        
        //  Now do the real comparison.
        if (urgency1 < urgency2) 
        {
            return 1;
        } 
        else if (urgency1 > urgency2) 
        {
            return -1;
        } 
        else 
        {
            return 0;
        }
    }
    
    /*
     * Need this method for the Comparator interface.
     *
     * @param o The object to compare to.
     *
     * @return Whether o is exactly this (same address).
     */
    public boolean equals(Object o) {
        return o==this;
    }
}
