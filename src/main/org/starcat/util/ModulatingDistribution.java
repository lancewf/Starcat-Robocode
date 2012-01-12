package org.starcat.util;

/* Classes that implement this interface will provide a method
 * probabilityOf(float) that accepts a float between 0 and 8 
 * inclusive. This range is assumed by the ModulatingDistribution
 * class that uses this interface. It returns a float that
 * represents the likelihood that the 
 * underlying distribution yields the input float value. This
 * returned float will be between 0 and 1 inclusive, if
 * the parameters for the distribution are appropriate. For
 * example, if the implementing class is GaussianDistribution
 * the sigma cannot be less than 0.4 in order to get values
 * out between 0 and 1. Each implementing class must specify
 * the appropriate ranges for parameters.
 */

public interface ModulatingDistribution {

	float probabilityOf(float value);
}
