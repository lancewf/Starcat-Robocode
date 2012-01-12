package org.starcat.util;

/*
 * Implements ModulatingDistribution which requires a method
 * probabilityOf(float). As that interface describes, this method
 * takes float values between 0 and 8 inclusive. For this 
 * method to return a value between 0 and 1 inclusive, as is
 * called for by the description of that interface, the sigma
 * value with which it is instantiated must be greater than
 * or equal to 0.4f. The mean value, around which the distribution
 * is centered, should be kept at 4.0f, unless it is acceptable that
 * parts of the distribution will fall outside the input value
 * range. This would only be of concern if the distribution is
 * being used in a situation where its normalization is required.
 */

public class GaussianDistribution 
implements ModulatingDistribution {

	private float mu;
	private float sigma;
	private float exponentDenom;
	private float primaryDenom;
	private static float rootTwoPi = (float) Math.sqrt( 2.0 * Math.PI );
	
	
	public GaussianDistribution(float mean, float scale) {
		mu = mean;
		sigma = scale;
		exponentDenom = 1.0f / ( 2.0f * sigma * sigma );
		primaryDenom =  1.0f / ( sigma * rootTwoPi );
	}
	
	public float probabilityOf(float value) {
		return
			(float)
				Math.exp(
					-1.0 *
					Math.pow( value - mu , 2.0 ) *
					exponentDenom
						)
				*
				primaryDenom
		     ;
	}
}
