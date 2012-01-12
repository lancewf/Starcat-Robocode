package org.robocode.genenticalgorithm.fitnesstest;

import java.io.File;
import java.util.List;

import org.robocode.BotCatListener;
import org.robocode.genenticalgorithm.Individual;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class FitnessTest implements IFitnessTest
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   public static String BOTCAT_NAME = "org.robocode.BotCat";
   private BotCatListener listener;
   private FitnessTestProperties fitnessTestProperties;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
   
   public FitnessTest(BotCatListener listener)
   {
      this.listener = listener;
      this.fitnessTestProperties = new FitnessTestProperties();
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
   
   /* (non-Javadoc)
    */
   public void run(Individual individual)
   {
      individual.save(new File(fitnessTestProperties.getBotCatPropertiesPath()));

      runBattle();

      int score = getScore();
      
      individual.setFitnessScore(score);
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Private Members
   // --------------------------------------------------------------------------
   
   private int getScore()
   {
	   BattleResults robotResults = listener.getRobotResults();
      
      if(robotResults != null)
      {
         System.out.println("Score = " + robotResults.getScore() + 
            " Bullet = " + robotResults.getBulletDamage() +
            " Ram = " + robotResults.getRamDamage());
      
         return robotResults.getScore();
      }
      else
      {
         System.out.println("error in set Score the results was not found");
         return 0;
      }
   }
   
   private void runBattle()
   {      
      BattlefieldSpecification battleField = 
         new BattlefieldSpecification(
            fitnessTestProperties.getBattlefieldWidth(),
            fitnessTestProperties.getBattlefieldHeight());
      
      RobocodeEngine engine = getEngine();
      
      listener.setEngine(engine);

      RobotSpecification[] robots = getRobots(engine, fitnessTestProperties);

      BattleSpecification battle = new BattleSpecification(
         fitnessTestProperties.getNumberOfRound(), 
         fitnessTestProperties.getInactivityTime(),
         0.1, 
         battleField, 
         robots);

      listener.runBattle(battle);
      
      //engine.close();
      
      fitnessTestProperties.dispose();
      
      battleField = null;
      fitnessTestProperties = null;
      battle = null;
      //engine = null;
      robots = null;
      
      System.gc();
   }
   
   private RobocodeEngine engine = null;
   
   private RobocodeEngine getEngine()
   {
      if(engine == null)
      {
         engine = new RobocodeEngine(listener);
      }
      
      return engine;
   }
   
   private RobotSpecification[] getRobots(RobocodeEngine engine, 
                                    FitnessTestProperties fitnessTestProperties)
   {
      List<String> opponents = fitnessTestProperties.getOpponentsNames();
      
      String opponentsNames = "";
      
      for(String opponentName : opponents){
    	  opponentsNames += opponentName + "*,";
      }
      
      opponentsNames += BOTCAT_NAME + "*";
      
      return engine.getLocalRepository(opponentsNames);
   }
   
   // #endregion
}
