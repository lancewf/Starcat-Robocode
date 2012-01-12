package org.robocode.genenticalgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;

public abstract class SizableChromosome implements Chromosome
{
   // --------------------------------------------------------------------------
   // #region Private Static Data
   // --------------------------------------------------------------------------
   
   private static final double PERCENTAGE_MUTATED = 0.01;
   private static final int MUTATION_DIFF_VALUE = 25;
   private static final int EIGHT_MUTATION_DIFF_VALUE = MUTATION_DIFF_VALUE/8;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   private Properties properties;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------

   public SizableChromosome()
   {
      properties = new Properties();
   }
   
   public SizableChromosome(Properties properties)
   {
      this.properties = properties;
   }
   
   public SizableChromosome(File file)
   {      
      properties = new Properties();

      FileInputStream fileInputStream = null;
      try
      {
         fileInputStream = new FileInputStream(file);
         properties.load(fileInputStream);
         
         fileInputStream.close();
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      finally
      {
         if(fileInputStream != null)
         {
            try
            {
               fileInputStream.close();
            }
            catch (IOException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
   }
   
   protected SizableChromosome(SizableChromosome sizableChromosome)
   {
      this.properties = (Properties)sizableChromosome.properties.clone();
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
   
   public SizableChromosome mutate(Random random)
   {
      SizableChromosome mutatedChromosome = (SizableChromosome)clone();
      
      int mutatingIndex = random.nextInt(getSize());
      
      String key = getKey(mutatingIndex);
      
      int newValue = getMutateBase(random, 
         mutatedChromosome.getValue(key));
      
      mutatedChromosome.setValue(key, newValue);

      return mutatedChromosome;
   }
   
   public SizableChromosome mutateAll(Random random)
   {
      SizableChromosome mutatedChromosome = (SizableChromosome)clone();
      
      int amountToMutate = (int)(getSize() * PERCENTAGE_MUTATED);
      for (int index = 0; index < getSize(); index++)
      {
         // one percent of the bases get mutated
         if (random.nextInt(amountToMutate) == 0)
         {
            String key = getKey(index);
            
            int newValue = getMutateBase(random,
               mutatedChromosome.getValue(key),
               EIGHT_MUTATION_DIFF_VALUE);

            mutatedChromosome.setValue(key, newValue);
         }
      }

      return mutatedChromosome;
   }
   
   public SizableChromosome[] crossover(Chromosome mate)
   {
      Random random = new Random();
      
      return crossover(mate, random);
   }
 
   public SizableChromosome[] crossover(Chromosome mate, Random random)
   {
      SizableChromosome sizableMate = (SizableChromosome)mate;
      SizableChromosome child1 = sizableMate.clone();
      SizableChromosome child2 = sizableMate.clone();
      
      // strandBreakPoint every 10 percent of bases 
      double percentageOfBasesToBreakOn = random.nextDouble();
      
      int numberOfBasesPerBreak = (int) 
         (getSize() * percentageOfBasesToBreakOn);
      
      //So that it is not zero
      if(numberOfBasesPerBreak == 0)
      {
         numberOfBasesPerBreak++;
      }
      
      boolean isMotherFirstChild = random.nextBoolean();
      int index = 0;
      
      for(Enumeration<Object> keyEnumberation = properties.keys(); 
      keyEnumberation.hasMoreElements(); )
      {
         String key = (String)keyEnumberation.nextElement();
         
         if(index % numberOfBasesPerBreak == 0)
         {
            isMotherFirstChild = !isMotherFirstChild;
         }
         
         if(isMotherFirstChild)
         {
            child1.setValue(key, getValue(key));
            child2.setValue(key, sizableMate.getValue(key));
         }
         else
         {
            child1.setValue(key, getValue(key));
            child2.setValue(key, sizableMate.getValue(key));
         }
         index++;
      }
      
      return new SizableChromosome[]{child1, child2};
   }
   
   /**
    * Save the Chromosome value to the output stream passed in
    * @param outputStream
    */
   public void save(OutputStream outputStream)
   {
      try
      {
         properties.store(outputStream, null);
      }
      catch (FileNotFoundException e1)
      {
         e1.printStackTrace();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Protected Members
   // --------------------------------------------------------------------------
   
   /**
    * Adjusts the value to the minimum and maximum value
    * 
    * formula use:
    * x = (max - min)/100 = 0.029
    * y = 0.1
    * 
    * value * x + y = fire amount
    * 
    * Maximum value:
    * 100 * 0.029 + 0.1 = 3
    * 
    * Minimum value:
    * 0 * 0.029 + 0.1 = 0.1
    * 
    * @param max - the maximum value to adjust the value
    * @param min - the minimum value to adjust the value
    * @param value - the value to adjust must be from 0 to 100
    * 
    * @return the adjusted value
    */
   protected double adjustValue(double max, double min, double value)
   {
      double x = (max - min)/100;
      double y = min;
      
      double adjustAmount = value * x + y;
      
      return adjustAmount;
   }
   
   protected int getValue(String tag)
   {
      String value = properties.getProperty(tag);
      if(value == null)
      {
         setValue(tag, 100);
         return 100;
      }
      return Integer.parseInt(value);
   }
   
   public void setValue(String tag, int value)
   {  
      properties.setProperty(tag, value + "");
   }
   
   protected boolean doesBaseExist(String tag)
   {
      String value = properties.getProperty(tag);
      
      if(value == null)
      {
         return false;
      }
      else
      {
         return true;
      }
   }
   
   public abstract SizableChromosome clone();
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Private Member
   // --------------------------------------------------------------------------
   
   /**
    * Get a new value for the Chromosome base with a random value plus or minus 
    * the current value with the muatationDiff being the maximum different in 
    * either direction
    */
   private int getMutateBase(Random random, int currentValue, int mutationDiff)
   {
      int newValue = 0;
      
      if(random.nextBoolean())
      {
         newValue = currentValue + random.nextInt(mutationDiff);
      }
      else
      {
         newValue = currentValue - random.nextInt(mutationDiff);
      }
      
      if(newValue > 100)
      {
         newValue = 100;
      }
      if(newValue < 0)
      {
         newValue = 0;
      }
          
      return newValue;
   }
   
   private int getMutateBase(Random random, int currentValue)
   {
      return getMutateBase(random, currentValue, MUTATION_DIFF_VALUE);
   }
   
   private String getKey(int index)
   {
      String key = (String) properties.keySet().toArray()[index];
      
      return key;
   }
   
   private int getSize()
   {
      return properties.size();
   }
   
   // #endregion
}
