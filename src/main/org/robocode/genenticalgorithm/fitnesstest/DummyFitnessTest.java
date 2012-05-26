package org.robocode.genenticalgorithm.fitnesstest;

import java.util.Random;

import org.robocode.genenticalgorithm.Individual;

public class DummyFitnessTest implements IFitnessTest {

	private Random random = new Random();
	
	@Override
	public void run(Individual individual) {
		// TODO Auto-generated method stub
		
		individual.setFitnessScore(random.nextInt(100));
	}

}
