package org.robocode.genenticalgorithm.fitnesstest;

import java.io.File;
import java.util.List;

import org.robocode.BotCatListener2;
import org.robocode.genenticalgorithm.Individual;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class FitnessTest implements IFitnessTest
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   public static String BOTCAT_NAME = "org.robocode.BotCat";
   private BotCatListener2 listener;
   private FitnessTestProperties fitnessTestProperties;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public FitnessTest(BotCatListener2 listener)
   {
      this.listener = listener;
      this.fitnessTestProperties = new FitnessTestProperties();
   }
   
   // --------------------------------------------------------------------------
   //  Public Members
   // --------------------------------------------------------------------------

   public void run(Individual individual)
   {
	  File file = new File(fitnessTestProperties.getBotCatPropertiesPath());
      individual.save(file);

      runBattle();

      int score = getScore();
      
      individual.setFitnessScore(score);
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private int getScore()
   {
	   BattleResults robotResults = listener.endResult();
      
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
      
      listener.engine_$eq(engine);

      RobotSpecification[] robots = getRobots(engine, fitnessTestProperties);

      BattleSpecification battle = new BattleSpecification(
         fitnessTestProperties.getNumberOfRound(), 
         fitnessTestProperties.getInactivityTime(),
         0.1, 
         battleField, 
         robots);

      listener.runBattle(battle);
      
      battleField = null;
      battle = null;
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
}
