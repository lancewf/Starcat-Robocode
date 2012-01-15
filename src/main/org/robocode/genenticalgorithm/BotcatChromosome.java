package org.robocode.genenticalgorithm;

import java.io.File;

import robocode.Rules;

public class BotcatChromosome extends SizableChromosome 
{  
   private static final int MINIMUM_TURN_AMOUNT = 1;

   private static final int MAXIMUM_TURN_AMOUNT = 45;

   private static final int MINIMUM_MOVEMENT_AMOUNT = 1;

   private static final int MAXIMUM_MOVEMENT_AMOUNT = 50;

   private static final int MINIMUM_COLOR_VALUE = 0;

   private static final int MAXIMUM_COLOR_VALUE = 255;
   
   // --------------------------------------------------------------------------
   // Static Private Data
   // --------------------------------------------------------------------------
   
   private static final String MEMORY_TAG = "memory";
   private static final String LINK_TAG = "link";
   private static final String ACTIVATION_THRESHOLD_TAG = 
      "activationThreashold";
   private static final int MAXIMUM_BASE_VALUE = 100;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public BotcatChromosome()
   {
      //
      // Do nothing
      //
   }
   
   public BotcatChromosome(File file)
   {  
      super(file);
   }
   
   private BotcatChromosome(BotcatChromosome botcatChromosome)
   {  
      super(botcatChromosome);
   }
   
   // --------------------------------------------------------------------------
   // Public Member
   // --------------------------------------------------------------------------
   
   /**
    * The Memory Level of the slipnet node. 
    * 
    * Note:
    * The higher the memory level the longer memory is stored in the slipnet node.
    * 
    * @param slipnetNodeName - the slipnet node name that the memory level is 
    * requested. 
    */
   public int getMemoryLevel(String slipnetNodeName)
   {
      String tag = MEMORY_TAG + "_" + slipnetNodeName;
      
      if(!doesBaseExist(tag))
      {
         setValue(tag, 100);
      }
      
      return getValue(tag);
   }
   
   /**
    * Set the memory level of the slipnet node name passed in with the value passed
    * in. 
    * 
    * Note:
    * The higher the memory level the longer memory is stored in the slipnet node.
    * 
    * @param slipnetNodeName - the name of the slipnet node to set the memory 
    * level of. 
    * @param memoryLevel - the memory level to set on the slipnet node. 
    */
   public void setMemoryLevel(String slipnetNodeName, int memoryLevel)
   {
      String tag = MEMORY_TAG + "_" + slipnetNodeName;
      
      setValue(tag, memoryLevel);
   }
   
   /**
    * Get the link length of the link between slipnetNodeSourceName to 
    * slipnetNodeReceiverName
    * 
    * @param slipnetNodeSourceName - the slipnet node source of the link
    * length returned
    * 
    * @param slipnetNodeReceiverName - the slipnet node receiver of the link 
    * length returned
    * 
    * @return The length of the link between the source slipnet node and the
    * receiver slipnet node. The higher the value the longer the link, meaning 
    * the higher the value the less activation that is transmitted from the 
    * source slipnet node to the receiver slipnet node.  
    */
   public int getLinkLength(String slipnetNodeSourceName, 
                            String slipnetNodeReceiverName)
   {
      String tag = LINK_TAG + "_" + slipnetNodeSourceName + 
         "_" + slipnetNodeReceiverName;
         
      if(!doesBaseExist(tag))
      {
         setValue(tag, 5);
      }
      
      return getValue(tag);
   }
   
   public void setLinkLength(String slipnetNodeSourceName, 
                            String slipnetNodeReceiverName, int value)
   {
      String tag = LINK_TAG + "_" + slipnetNodeSourceName + 
      "_" + slipnetNodeReceiverName;
     
      setValue(tag, value);
   }
   
   /**
    * Get the color values of the robot parts body, turret, and radar. 
    * 
    * @param colorType - Use the static variables of this class below:
    * <code>BODY_RED<code>
    * <code>BODY_GREEN<code>
    * <code>BODY_BLUE<code>
    * 
    * <code>TURRET_RED<code>
    * <code>TURRET_GREEN<code>
    * <code>TURRET_BLUE<code>
    * 
    * <code>RADAR_RED<code>
    * <code>RADAR_GREEN<code>
    * <code>RADAR_BLUE<code>
    * 
    * @return
    */
   public int getColorValue(String colorType)
   {
      if(!doesBaseExist(colorType))
      {
         setValue(colorType, MAXIMUM_BASE_VALUE);
      }
      
      int value = getValue(colorType);
      
      int adjustedValue = (int)adjustValue(
         MAXIMUM_COLOR_VALUE, MINIMUM_COLOR_VALUE, value);

      return adjustedValue;
   }
   
   /**
    * Set the color values of the robot parts body, turret, and radar.
    * 
    * @param colorType - Use the static variables of this class below:
    * <code>BODY_RED<code>
    * <code>BODY_GREEN<code>
    * <code>BODY_BLUE<code>
    * 
    * <code>TURRET_RED<code>
    * <code>TURRET_GREEN<code>
    * <code>TURRET_BLUE<code>
    * 
    * <code>RADAR_RED<code>
    * <code>RADAR_GREEN<code>
    * <code>RADAR_BLUE<code>
    * 
    * @param value - the color value of the types. The value must be between 
    * 0 - 255. 
    */
   public void setColorValue(String colorType, int value)
   {
      int adjustedValue = (int)(((double)value) / 2.55);
      
      setValue(colorType, adjustedValue);
   }
   
   /**
    * Get the activation threshold for the chromosome. 
    * 
    * Note:
    * When the slipnet node's activation reaches the activation threshold it 
    * becomes possible for codelets to be produced.
    * 
    * @param slipnetNodeName - the name of the slipnet node to set the activation
    * threshold for. 
    */
   public int getActivationThreshold(String slipnetNodeName)
   {
      String tag = ACTIVATION_THRESHOLD_TAG + "_" + slipnetNodeName;
      
      if(!doesBaseExist(tag))
      {
         setValue(tag, 3);
      }
      
      return getValue(tag);
   }
   
   /**
    * Set the activation threshold for the chromosome. 
    * 
    * <p>
    * Note:
    * When the slipnet node's activation reaches the activation threshold it 
    * becomes possible for codelets to be produced.
    * </p>
    * @param slipnetNodeName
    * @param value
    */
   public void setActivationThreshold(String slipnetNodeName, int value)
   {
      String tag = ACTIVATION_THRESHOLD_TAG + "_" + slipnetNodeName;
      
      setValue(tag, value);
   }
   
   /**
    * Get the movement amount.
    * 
    * <p>
    * Note:
    * The movement amount is the amount the botcat moves in its environment when
    * the command to move is made.  
    * </p>
    */
   public double getMovementAmount()
   {
      if(!doesBaseExist(MOVEMENT_AMOUNT_TAG))
      {
         setValue(MOVEMENT_AMOUNT_TAG, 5);
      }
      
      int value = getValue(MOVEMENT_AMOUNT_TAG);
      
      double adjustedValue = adjustValue(
         MAXIMUM_MOVEMENT_AMOUNT, MINIMUM_MOVEMENT_AMOUNT, value);
      
      return adjustedValue;
   }

   /**
    * Get the turn amount.
    * 
    * <p>
    * Note:
    * The turn amount is the amount the botcat turns in its environment when
    * the command to turn is made.  
    * </p>
    */
   public double getTurnAmount()
   {
      if(!doesBaseExist(TURN_AMOUNT_TAG))
      {
         setValue(TURN_AMOUNT_TAG, 3);
      }
      
      int value = getValue(TURN_AMOUNT_TAG);
      
      double adjustedValue = adjustValue(
         MAXIMUM_TURN_AMOUNT, MINIMUM_TURN_AMOUNT, value);
      
      return adjustedValue;
   }
   
   /**
    * The fire power must be in the range from 0.1 to 3
    * 
    * @return
    */
   public double getFireAmount()
   {
      if(!doesBaseExist(FIRE_AMOUNT_TAG))
      {
         setValue(FIRE_AMOUNT_TAG, 1);
      }
      
      int value = getValue(FIRE_AMOUNT_TAG);
      
      double adjustedValue = adjustValue(Rules.MAX_BULLET_POWER, 
         Rules.MIN_BULLET_POWER, value);
      
      return adjustedValue;
   }
   
   /**
    * 
    * initial started with 16 nodes
    * 
    * @return
    */
   public int getNumberOfExtraNodes()
   {
      if(!doesBaseExist(EXTRA_NODES_1))
      {
         setValue(EXTRA_NODES_1, 0);
         setValue(EXTRA_NODES_2, 0);
         setValue(EXTRA_NODES_3, 0);
         setValue(EXTRA_NODES_4, 0);
      }
      
      int value1 = getValue(EXTRA_NODES_1);
      int value2 = getValue(EXTRA_NODES_2);
      int value3 = getValue(EXTRA_NODES_3);
      int value4 = getValue(EXTRA_NODES_4);
      
      int adjustedValue1 = (int)adjustValue(25, 0, value1);
      int adjustedValue2 = (int)adjustValue(25, 0, value2);
      int adjustedValue3 = (int)adjustValue(25, 0, value3);
      int adjustedValue4 = (int)adjustValue(25, 0, value4);
      
      return adjustedValue1 + adjustedValue2 + adjustedValue3 + adjustedValue4;
   }
   
   public int getEnergyLevelPeg(int peg)
   {
      String tag = ENERGY_LEVEL_PEG + "_" + peg;
      
      if(!doesBaseExist(tag))
      {
         setValue(tag, 40);
      }
      
      return getValue(tag);
   }
   
   public int getBufferDistance()
   {
      if(!doesBaseExist(BUFFER_DISTANCE))
      {
         setValue(BUFFER_DISTANCE, 10);
      }
      
      int value = getValue(BUFFER_DISTANCE);
      
      int adjustedValue = (int)adjustValue(300, 25, value);
      
      return adjustedValue;
   }
  
   
   public int getTargetDistance()
   {
      if(!doesBaseExist(TARGET_DISTANCE))
      {
         setValue(TARGET_DISTANCE, 10);
      }
      
      int value = getValue(TARGET_DISTANCE);
      
      int adjustedValue = (int)adjustValue(800, 100, value);
      
      return adjustedValue;
   }
   
   public SizableChromosome clone()
   {
      return new BotcatChromosome(this);
   }

   @Override
   public int getCoderackBehaviorExecuteFactor()
   {
      return 10;
   }

   @Override
   public double getCoderackBehaviorReductionFactor()
   {
      return 0.01;
   }

   @Override
   public long getCoderackBehaviorSleepTime()
   {
      return 10;
   }

   @Override
   public int getCoderackControlExecuteFactor()
   {
      return 1;
   }

   @Override
   public double getCoderackControlReductionFactor()
   {
      return 0.01;
   }

   @Override
   public long getCoderackControlSleepTime()
   {
      return 10;
   }

   @Override
   public int getSlipnetBehaviorExecuteFactor()
   {
      return 10;
   }

   @Override
   public double getSlipnetBehaviorReductionFactor()
   {
      return 0.01;
   }

   @Override
   public long getSlipnetBehaviorSleepTime()
   {
      return 10;
   }

   @Override
   public int getSlipnetControlExecuteFactor()
   {
      return 1;
   }

   @Override
   public double getSlipnetControlReductionFactor()
   {
      return 0.01;
   }

   @Override
   public long getSlipnetControlSleepTime()
   {
      return 10;
   }

   @Override
   public int getWorkspaceBehaviorExecuteFactor()
   {
      return 20;
   }

   @Override
   public double getWorkspaceBehaviorReductionFactor()
   {
      return 0.01;
   }

   @Override
   public long getWorkspaceBehaviorSleepTime()
   {
      return 10;
   }

   @Override
   public int getWorkspaceControlExecuteFactor()
   {
      return 1;
   }

   @Override
   public double getWorkspaceControlReductionFactor()
   {
      return 0.01;
   }

   @Override
   public long getWorkspaceControlSleepTime()
   {
      return 10;
   }
   
   // #endregion
}
