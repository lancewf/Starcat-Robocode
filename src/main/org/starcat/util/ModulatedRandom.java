package org.starcat.util;

import java.util.List;
import java.util.Random;
import org.starcat.structures.Entity;
import org.starcat.util.ModulatingDistribution;

public class ModulatedRandom {
	

	private static int NUMBINS = 2000;
	//private static int BINCAP  = 1000; NEVER READ
	private static int inputRange = 8 - 0;
	private static Random flatRNG = new Random();

	private int[] discretizedDistribution = new int[NUMBINS];
	//private float scaleFactor; NEVER READ
	private int[] sampleset;
	private int size;
	private ModulatingDistribution modDist;

	public String getName()
	{
		return modDist.getClass().getName();
	}

	public ModulatedRandom( List<Entity> list, ModulatingDistribution md )
	{
		// Build a histogram of salience values
		// ---------------------------------------------------------
		int[] hist = new int[ 100 ];
		for ( int i=0; i<hist.length; i++ ) hist[ i ] = 0;
		for ( int i=0; i<list.size(); i++ ) hist[ ( int )list.get( i ).getSalience()]++;

		modDist = md;
		int distSum = 0;
		int[] distCounts = new int[ 100 ];
		for ( int i=0; i<hist.length; i++ )
		{
			distCounts[ i ] = ( int )( ( float )hist[ i ] * modDist.probabilityOf( ((float) i ) * inputRange / NUMBINS ) );
			distSum += distCounts[ i ];
		}

		int[] indexes = new int[ 100*distSum ];
		for ( int i=0; i<distCounts.length; i++ )
		{
			for ( int j=0; j<distCounts[ i ]; j++ )
				indexes[ i + j ] = i;	// This won't work!!
		}

	}
	
	public float nextFloat() {
		return
		  ( (float) sampleset[flatRNG.nextInt(size)] ) / NUMBINS;
	}
	
	
	public void showSourceProbabilities() {
		System.out.println();
		System.out.println("SOURCE PROBABILITIES =====================");
		float newProb = 0.0f;
		for (int i=0; i<NUMBINS; i++) {
			System.out.print( ((float) i ) * inputRange / NUMBINS );
			newProb = modDist.probabilityOf( ((float) i ) * inputRange / NUMBINS );
			System.out.println(" prob: " + newProb);
		}
	}

	public void showDiscretizedDistribution() {
		System.out.println();
		System.out.println("DISCRETIZED DISTRIBUTION =====================");
		for (int i=0; i<NUMBINS; i++) {
			System.out.println(discretizedDistribution[i]);
		}
	}
	
	public void showSampleSet() {
		System.out.println();
		System.out.println("SAMPLESET =====================");
		for (int i=0; i<size; i++)
			System.out.println("sampleset["+i+"]: "+sampleset[i]);
		
	}
	
}
