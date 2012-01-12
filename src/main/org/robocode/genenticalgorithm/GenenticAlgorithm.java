package org.robocode.genenticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.robocode.genenticalgorithm.fitnesstest.IFitnessTest;

public class GenenticAlgorithm
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------

   private List<Individual> population;

   private Random random;

   private Individual bestIndividual = null;

   private IFitnessTest fitnessTest;

   private int totalPopulationScore = 0;

   private double topPercentPopulation = 0.06;

   private IProgress progress;

   private boolean isPaused = true;

   // #endregion

   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------

   public GenenticAlgorithm(int populationSize, IFitnessTest fitnessTest,
         Individual initialIndividual)
   {
      this.fitnessTest = fitnessTest;
      random = new Random();
      generateInitialPopulation(populationSize, initialIndividual);
   }

   // #endregion

   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------

   public void setProgress(IProgress progress)
   {
      this.progress = progress;
   }

   /**
    * Get the best individual in the population
    */
   public Individual getBestIndividual()
   {
      return bestIndividual;
   }

   /**
    * Get the entire population
    */
   public List<Individual> getPopulation()
   {
      return population;
   }
   
   /**
    * Get the entire population
    */
   public void setPopulation(List<Individual> population)
   {
      this.population = population;
   }
   
   public void pause()
   {
      isPaused = true;
   }
   
   public void unPause()
   {
      isPaused = false;
   }

   /**
    * Perform the next generation
    */
   public void nextGeneration()
   {
      isPaused = false;
      List<Individual> newPopulation = new ArrayList<Individual>();

      scoreFitnessOfPopulation(fitnessTest);
      findRelativeFitness();

      Individual[] topPercentOfPopulation = getTopPercentOfPopulation();
      
      while (newPopulation.size() < 
            (population.size() - getNumberOfEliteMembers()))
      {
         Individual mate1 = selectIndividualForMating();
         Individual mate2 = selectIndividualForMating(mate1);

         Individual[] children = mate1.crossover(mate2, random);

         for (Individual child : children)
         {
            newPopulation.add(child);
         }
      }

      newPopulation = mutate(newPopulation);

      for (Individual socialite : topPercentOfPopulation)
      {
         System.out.println("socialite added fitness = "
               + socialite.getFitnessScore());

         newPopulation.add(socialite);
      }

      population = newPopulation;
   }

   // #endregion

   // --------------------------------------------------------------------------
   // #region Protected Members
   // --------------------------------------------------------------------------

   /**
    * Find the fitness of each individual in the population
    */
   protected void scoreFitnessOfPopulation(IFitnessTest fitnessTest)
   {
      int count = 0;
      progress.setValue(0);
      for (Individual individual : population)
      {
         checkForPause();
         
         fitnessTest.run(individual);
         count++;
         progress.setValue((int) (((double) count)
               / ((double) population.size()) * 100.0));
      }

      progress.setValue(100);
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Private Members
   // --------------------------------------------------------------------------
   
   private void checkForPause()
   {
      while (isPaused)
      {
         try
         {
            Thread.sleep(500);
         }
         catch (InterruptedException e)
         {
            isPaused = false;
         }
      }
   }

   /**
    * Find the relative fitness for each individual in the population
    * The relative fitness is the fitness of the individual in relation to the
    * other individuals in the population.
    */
   private void findRelativeFitness()
   {
      // sort so that the individual with the best fitness value is in the 
      // position highest or index; 
      Collections.sort(population);

      // the sum of all the ranks
      totalPopulationScore = (population.size() * (population.size() + 1)) / 2;

      for (int rank = 0; rank < population.size(); rank++)
      {
         System.out.println("Rank = " + rank + " Fitness = "
               + population.get(rank).getFitnessScore());

         population.get(rank).setRelativeFitness(rank + 1);
      }
   }

   /**
    * Select an individual from the population for mating
    * 
    * An individual is selected from the population in roulette wheel selection.
    * The selection is based of the rank of the individual or relative fitness.
    * 
    * @return the individual mate randomly selected by roulette wheel selection
    */
   private Individual selectIndividualForMating()
   {
      int selectionPercent = random.nextInt(totalPopulationScore);
      double currentPercent = 0;
      Individual mate = null;

      for (Individual individual : population)
      {
         currentPercent += individual.getRelativeFitness();

         if (currentPercent > selectionPercent)
         {
            mate = individual;
            break;
         }
      }

      return mate;
   }

   /**
    * Select an individual from the population excluding the individual 
    * passed in
    * 
    * @param mate1 - the individual mate to exclude from the roulette wheel
    * selection. 
    */
   private Individual selectIndividualForMating(Individual mate1)
   {
      int totalPopulationScoreMinsMate = totalPopulationScore
         - (int) mate1.getRelativeFitness();
      
      int selectionPercent = random.nextInt(totalPopulationScoreMinsMate);
      double currentPercent = 0;
      Individual mate2 = null;

      population.remove(mate1);

      for (Individual individual : population)
      {
         currentPercent += individual.getRelativeFitness();

         if (currentPercent > selectionPercent)
         {
            mate2 = individual;
            break;
         }
      }

      population.add(mate1);

      if (mate2 == null)
      {
         mate2 = population.get(population.size() - 1);
      }

      return mate2;
   }

   /**
    * Get the top percent of the population
    */
   private Individual[] getTopPercentOfPopulation()
   {
      List<Individual> topPercentOfPopulation = new ArrayList<Individual>();

      Collections.sort(population);
      
      for(int index = population.size() - 1; 
         index >= population.size() - getNumberOfEliteMembers() ; 
         index--)
      {
         topPercentOfPopulation.add(population.get(index));
      }

      bestIndividual = population.get(population.size() - 1);

      return (Individual[]) topPercentOfPopulation.toArray(new Individual[0]);
   }

   /**
    * Generate an initial population from an initial chromosome object. 
    * 
    * The method takes the initial chromosome and mutates it to create an 
    * individual for the population. 
    * 
    */
   private void generateInitialPopulation(int populationSize, 
                                          Individual initialIndividual)
   {
      population = new ArrayList<Individual>();

      for (int count = 0; count < populationSize - 1; count++)
      {
         Individual individual = initialIndividual.mutateAll(random);
         population.add(individual);
      }
      population.add(initialIndividual);

      bestIndividual = initialIndividual;
   }

   /**
    * mutate the population passed in 
    */
   private List<Individual> mutate(List<Individual> childrenPopulation)
   {
      List<Individual> newPopulation = new ArrayList<Individual>();

      for (Individual individual : childrenPopulation)
      {
         Individual mutateIndividual = individual.mutate(random);
         mutateIndividual = mutateIndividual.mutate(random);
         newPopulation.add(mutateIndividual);
      }

      return newPopulation;
   }

   /**
    * Get the number of elite members
    * @return
    */
   private int getNumberOfEliteMembers()
   {
      return (int) (population.size() * topPercentPopulation);
   }

   // #endregion
}
