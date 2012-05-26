package org.robocode.genenticalgorithm.fitnesstest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FitnessTestProperties
{
   // --------------------------------------------------------------------------
   // #region Private Static Data
   // --------------------------------------------------------------------------
   
   private static final String PROPERTIES_FILE_NAME = "fitnessTest.properties";
   private static final String NUMBER_OF_ROUNDS = "numberOfRounds";
   private static final String OPPONENTS = "opponents";
   private static final String BATTLEFIELD_WIDTH = "battlefieldWidth";
   private static final String BATTLEFIELD_HEIGHT = "battlefieldHeight";
   private static final String INACTIVITY_TIME = "inactivityTime";
   private static final String BOTCAT_PROPERTIES_PATH = "botcatPropertiesPath";
   private static final String BOTCAT_2_PROPERTIES_PATH = "botcat2PropertiesPath";
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   private Properties properties;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
      
   public FitnessTestProperties()
   {
      properties = new Properties();

      FileInputStream fileInputStream = null;
      try
      {
         File file = new File(PROPERTIES_FILE_NAME);
         
         fileInputStream = 
            new FileInputStream(file);
         
         properties.load(fileInputStream);
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
               e.printStackTrace();
            }
         }
      }
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
     
   public int getNumberOfRound()
   {
      String value = properties.getProperty(NUMBER_OF_ROUNDS);
      
      return Integer.parseInt(value);
   }
   
   public List<String> getOpponentsNames()
   {
      List<String> opponents = new ArrayList<String>();
      
      String value = properties.getProperty(OPPONENTS);
      
      for(String opponent : value.split(","))
      {
         opponents.add(opponent);
      }

      return opponents;
   }
   
   public int getBattlefieldWidth()
   {
      String value = properties.getProperty(BATTLEFIELD_WIDTH);
      
      return Integer.parseInt(value);
   }
   
   public int getBattlefieldHeight()
   {
      String value = properties.getProperty(BATTLEFIELD_HEIGHT);
      
      return Integer.parseInt(value);
   }
   
   public int getInactivityTime()
   {
      String value = properties.getProperty(INACTIVITY_TIME);
      
      return Integer.parseInt(value);
   }
   
   public String getBotCatPropertiesPath()
   {
	   return properties.getProperty(BOTCAT_PROPERTIES_PATH);
   }
   
   public String getBotCat2PropertiesPath()
   {
	   return properties.getProperty(BOTCAT_2_PROPERTIES_PATH);
   }
   
   public void dispose()
   {
      properties = null;
   }
}
