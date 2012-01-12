package org.robocode.genenticalgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;


public class Individual implements Comparable<Individual>
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   private Chromosome chromosome;
   private double relativeFitness;
   private double fitnessScore;
   
   // #endregion

   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
   
   public Individual(Chromosome chromosome)
   {
      this.chromosome = chromosome;
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
   
   public Individual mutate(Random random)
   {
      return new Individual(chromosome.mutate(random));
   }
   
   public Individual mutateAll(Random random)
   {
      Chromosome matatedChromosome = chromosome.mutateAll(random);
      
      return new Individual(matatedChromosome);
   }
   
   public Individual[] crossover(Individual mate, Random random)
   {
      Chromosome[] chromosomes = chromosome.crossover(mate.chromosome, random);
      
      if(random.nextBoolean())
      {
         return new Individual[]{new Individual(chromosomes[0])};
      }
      else
      {
         return new Individual[]{new Individual(chromosomes[1])};
      }
   }
   
   public double getFitnessScore()
   {
      return fitnessScore;
   }
   
   public void setFitnessScore(double fitnessScore)
   {
      this.fitnessScore = fitnessScore;
   }
   
   public double getRelativeFitness()
   {
      return relativeFitness;
   }

   public void setRelativeFitness(double relativeFitness)
   {
      this.relativeFitness = relativeFitness;
   }
   
   public void save(File file)
   {  
      OutputStream outputStream = null;
      try
      {
         outputStream = new FileOutputStream(file, false);
         
         chromosome.save(outputStream);
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      finally
      {
         if(outputStream != null)
         {
            try
            {
               outputStream.close();
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Comparable<Individual> Members
   // --------------------------------------------------------------------------

   @Override
   public int compareTo(Individual compareToIndividual)
   {
      if(getFitnessScore() > compareToIndividual.getFitnessScore())
      {
         return 1;
      }
      else if(getFitnessScore() < compareToIndividual.getFitnessScore())
      {
         return -1;
      }
      else
      {
         return 0;
      }
   }
   
   // #endregion
}
