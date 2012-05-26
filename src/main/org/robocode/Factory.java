package org.robocode;

import java.io.File;

import org.robocode.genenticalgorithm.BotcatChromosome;
import org.robocode.genenticalgorithm.Chromosome;
import org.robocode.genenticalgorithm.GenenticAlgorithm;
import org.robocode.genenticalgorithm.GenenticUI;
import org.robocode.genenticalgorithm.GenerationRunner;
import org.robocode.genenticalgorithm.Individual;
import org.robocode.genenticalgorithm.fitnesstest.FitnessTest;
import org.robocode.genenticalgorithm.fitnesstest.IFitnessTest;
import org.robocode.genenticalgorithm.fitnesstest.DummyFitnessTest;
import org.robocode.genenticalgorithm.fitnesstest.InfightingFitnessTest;

public class Factory
{
   private static int INITIAL_POPULATION_SIZE = 50;
   
   public static void main(String[] args)
   {  
	  if (args.length != 1)
      {
    	 System.err.println("args.length: " + args.length);
         return;
      }
       
      File initalAgent = new File(args[0]);

      if (!initalAgent.exists())
      {
    	 System.err.println("inital agent not found: " + args[0]);
         return;
      }
      
      Chromosome initialChromosome = new BotcatChromosome(initalAgent);
      
      Individual initialIndividual = new Individual(initialChromosome);

      BotCatListener listener = new BotCatListener();

      IFitnessTest test = new InfightingFitnessTest(listener);
//      IFitnessTest test = new DummyFitnessTest();

      GenenticAlgorithm genenticAlgorithm = new GenenticAlgorithm(
         INITIAL_POPULATION_SIZE, test, initialIndividual);

      GenerationRunner generationRunner = new GenerationRunner(
         genenticAlgorithm);

      GenenticUI ui = new GenenticUI(generationRunner, test);

      genenticAlgorithm.setProgress(ui.getProgressBar());

      ui.setVisible(true);
   }
}
