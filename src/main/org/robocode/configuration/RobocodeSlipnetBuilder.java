package org.robocode.configuration;

import org.robocode.RobotActionType;
import org.robocode.codelets.BackwardTurretOrientationObserverCodelet;
import org.robocode.codelets.BulletHitMissBehaviorCodlet;
import org.robocode.codelets.EnergyLevelBehaviorCodlet;
import org.robocode.codelets.ForwardTurretOrientationObserverCodelet;
import org.robocode.codelets.FuzzyObstacleBehaviorCodelet;
import org.robocode.codelets.LeftTurretOrientationObserverCodelet;
import org.robocode.codelets.NorthBodyOrientationObserverCodelet;
import org.robocode.codelets.PerformerBehaviorCodelet;
import org.robocode.codelets.RightTurretOrientationObserverCodelet;
import org.robocode.codelets.SouthBodyOrientationObserverCodelet;
import org.robocode.codelets.TargetObserverBehaviorCodelet;
import org.robocode.codelets.EastBodyOrientationObserverCodelet;
import org.robocode.codelets.WestBodyOrientationObserverCodelet;
import org.robocode.genenticalgorithm.BotcatChromosome;
import org.robocode.genenticalgorithm.Chromosome;
import org.starcat.configuration.ParameterData;
import org.starcat.configuration.SlipnetBuilder;

public class RobocodeSlipnetBuilder extends SlipnetBuilder
{
   // --------------------------------------------------------------------------
   // Private Static Data
   // --------------------------------------------------------------------------
   
   private static final String OBSERVER = "obstacleObserver";

   // --------------------------------------------------------------------------
   // Protected Overridden Methods
   // --------------------------------------------------------------------------
   
   /**
    * Create all the slipnet nodes for the system
    */
   protected void createSlipnetNodes(Chromosome chromosome)
   {
      createSlipnetNode(OBSERVER, 100, 99, 1);
      
      for(String slipnetNode : chromosome.getSlipnetNodeList())
      {
         int memoryLevel = chromosome.getMemoryLevel(slipnetNode);
         
          //only the CodeletPreformers use this. 
         int activationThreashold = 
            chromosome.getActivationThreshold(slipnetNode);
         
         int initalActivation = chromosome.getInitalActivation(slipnetNode);
         
         createSlipnetNode(slipnetNode, 
            memoryLevel, initalActivation, activationThreashold);
      }
   }

   /**
    * Create all the links for the system
    */
   protected void createLinks(Chromosome chromosome)
   {
      //link length must be from 0 - 100 integer
      for(String fromSlipnetNode : chromosome.getSlipnetNodeList())
      {
         for(String toSlipnetNode : chromosome.getSlipnetNodeList())
         {
            int value = chromosome.getLinkLength(
               fromSlipnetNode, toSlipnetNode);
            
            // A link with length of 100 is the same as no link at all
            // to save process time do not create a link
            if(value < 100)
            {
               createLink(fromSlipnetNode, toSlipnetNode, value);
            }
         }
      }
   }

   /**
    * Create all the codelets for the system
    */
   protected void createCodelets(Chromosome chromosome)
   {
      createCodeletObservers(chromosome);
      
      createCodeletPreformers(chromosome);
   }

   /**
    * Initialize the system configurations
    */
	protected void initializeSystemConfigurations(Chromosome chromosome) {
		// Adaptive Execute methods
		ParameterData.initializeWorkspaceBehaviorAdaptiveExecute(false);
		ParameterData.initializeWorkspaceControlAdaptiveExecute(false);
		ParameterData.initializeCoderackBehaviorAdaptiveExecute(false);
		ParameterData.initializeCoderackControlAdaptiveExecute(false);
		ParameterData.initializeSlipnetBehaviorAdaptiveExecute(false);
		ParameterData.initializeSlipnetControlAdaptiveExecute(false);

		// Execute Factor methods
		ParameterData.initializeWorkspaceBehaviorExecuteFactor(chromosome
				.getWorkspaceBehaviorExecuteFactor());
		ParameterData.initializeWorkspaceControlExecuteFactor(chromosome
				.getWorkspaceControlExecuteFactor());

		ParameterData.initializeCoderackBehaviorExecuteFactor(chromosome
				.getCoderackBehaviorExecuteFactor());

		ParameterData.initializeCoderackControlExecuteFactor(chromosome
				.getCoderackControlExecuteFactor());

		ParameterData.initializeSlipnetBehaviorExecuteFactor(chromosome
				.getSlipnetBehaviorExecuteFactor());

		ParameterData.initializeSlipnetControlExecuteFactor(chromosome
				.getSlipnetControlExecuteFactor());

		// Reduction factor methods
		ParameterData.initializeWorkspaceBehaviorReductionFactor(chromosome
				.getWorkspaceBehaviorReductionFactor());

		ParameterData.initializeWorkspaceControlReductionFactor(chromosome
				.getWorkspaceControlReductionFactor());

		ParameterData.initializeCoderackBehaviorReductionFactor(chromosome
				.getCoderackBehaviorReductionFactor());

		ParameterData.initializeCoderackControlReductionFactor(chromosome
				.getCoderackControlReductionFactor());

		ParameterData.initializeSlipnetBehaviorReductionFactor(chromosome
				.getSlipnetBehaviorReductionFactor());

		ParameterData.initializeSlipnetControlReductionFactor(chromosome
				.getSlipnetControlReductionFactor());

		// Sleeper methods
		ParameterData.initializeWorkspaceBehaviorSleeper(true);
		ParameterData.initializeWorkspaceControlSleeper(true);
		ParameterData.initializeCoderackBehaviorSleeper(true);
		ParameterData.initializeCoderackControlSleeper(true);
		ParameterData.initializeSlipnetBehaviorSleeper(true);
		ParameterData.initializeSlipnetControlSleeper(true);

		// Sleep time methods
		ParameterData.initializeWorkspaceBehaviorSleepTime(chromosome
				.getWorkspaceBehaviorSleepTime());

		ParameterData.initializeWorkspaceControlSleepTime(chromosome
				.getWorkspaceControlSleepTime());

		ParameterData.initializeCoderackBehaviorSleepTime(chromosome
				.getCoderackBehaviorSleepTime());

		ParameterData.initializeCoderackControlSleepTime(chromosome
				.getCoderackControlSleepTime());

		ParameterData.initializeSlipnetBehaviorSleepTime(chromosome
				.getSlipnetBehaviorSleepTime());

		ParameterData.initializeSlipnetControlSleepTime(chromosome
				.getSlipnetControlSleepTime());
	}
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private void createCodeletPreformers(Chromosome chromosome)
   {
      createCodelet(new PerformerBehaviorCodelet(RobotActionType.FORWARD),
    	 BotcatChromosome.MOVE_FORWARD,
         100,
         1);
      
      createCodelet(new PerformerBehaviorCodelet(RobotActionType.FIRE),
    	 BotcatChromosome.FIRE,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotActionType.DONT_MOVE),
         BotcatChromosome.DO_NOT_MOVE,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotActionType.BACKWARD),
    	 BotcatChromosome.MOVE_BACKWARD,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotActionType.TURN_RIGHT),
    	 BotcatChromosome.TURN_RIGHT,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotActionType.TURN_LEFT),
         BotcatChromosome.TURN_LEFT,
         100,
         1);
      
      createCodelet(new PerformerBehaviorCodelet(RobotActionType.TURN_TURRET_RIGHT),
         BotcatChromosome.TURN_TURRET_RIGHT,
         100,
         1);

      createCodelet(new PerformerBehaviorCodelet(RobotActionType.TURN_TURRET_LEFT),
         BotcatChromosome.TURN_TURRET_LEFT,
         100,
         1);
   }
   
   private void createCodeletObservers(Chromosome chromosome)
   {
      int bufferDistance = chromosome.getBufferDistance();
      
      // configuration/codelet
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.LEFT, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         BotcatChromosome.OBSTACLE_LEFT,
         BotcatChromosome.CLEAR_LEFT);
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.RIGHT, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         BotcatChromosome.OBSTACLE_RIGHT,
         BotcatChromosome.CLEAR_RIGHT);
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.FORWARD, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         BotcatChromosome.OBSTACLE_FRONT,
         BotcatChromosome.CLEAR_FOREWARD);
      createCodelet(new FuzzyObstacleBehaviorCodelet(
         FuzzyObstacleBehaviorCodelet.BACKWARD, bufferDistance),
         OBSERVER,
         1,
         100,
         100,
         1,
         BotcatChromosome.OBSTACLE_BACKWARD,
         BotcatChromosome.CLEAR_BACKWARD);
    	      
      int targetDistance = chromosome.getTargetDistance();
      
      // Target Observers
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.FORWARD, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_FORWARD);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.FORWARD_RIGHT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_FORWARD_RIGHT);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.RIGHT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_RIGHT);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.BACKWARD_RIGHT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_BACKWARD_RIGHT);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.BACKWARD, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_BACKWARD);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.BACKWARD_LEFT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_BACKWARD_LEFT);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.LEFT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_LEFT);
      createCodelet(new TargetObserverBehaviorCodelet(
         TargetObserverBehaviorCodelet.FORWARD_LEFT, targetDistance),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TARGET_FORWARD_LEFT);

      int value0 = chromosome.getEnergyLevelPeg(0);
      int value1 = chromosome.getEnergyLevelPeg(1);
      int value2 = chromosome.getEnergyLevelPeg(2);
      int value3 = chromosome.getEnergyLevelPeg(3);
      
      createCodelet(new EnergyLevelBehaviorCodlet(
         value0, value1, value2, value3),
         OBSERVER,
         1,
         100,
         100,
         1,
         BotcatChromosome.ENERGY_LOW_NODE_NAME, 
         BotcatChromosome.ENERGY_HIGH_NODE_NAME);
      
      //Orientation
      createCodelet(new EastBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.ORIENTATION_EAST);
      createCodelet(new WestBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.ORIENTATION_WEST);
      createCodelet(new NorthBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.ORIENTATION_NORTH);
      createCodelet(new SouthBodyOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.ORIENTATION_SOUTH);
      
      createCodelet(new RightTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TURRET_RIGHT);
      createCodelet(new LeftTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TURRET_LEFT);
      createCodelet(new BackwardTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TURRET_BACKWARD);
      createCodelet(new ForwardTurretOrientationObserverCodelet(),
         OBSERVER,
         1,
         100,
         1,
         BotcatChromosome.TURRET_FORWARD);
      
      int bulletAccuracySuccessOne = chromosome.getBulletAccuracySuccessOne();
      int bulletAccuracyFailureOne = chromosome.getBulletAccuracyFailureOne();
      
      createCodelet(new BulletHitMissBehaviorCodlet(bulletAccuracySuccessOne,
    		  bulletAccuracyFailureOne),
    	OBSERVER,
    	1,
    	100,
    	100,
    	1,
    	BotcatChromosome.BULLET_HIT,
    	BotcatChromosome.BULLET_MISS);
   }
}
