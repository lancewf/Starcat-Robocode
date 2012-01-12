package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

/**
 *            M
 *            e 1|      
 *            m  |-------_         _-------
 *            b  |        ---___---    
 *            e  |        __-   -__       
 *            r 0|______--_________--_______
 * Energy Level  |0    30    50     70   100
 * 
 */
public class EnergyLevelBehaviorCodlet extends FuzzyBehaviorCodelet {
   // -----------------------------------------------------------------------------
   // #region Private Data
   // -----------------------------------------------------------------------------

   // #endregion

   // -----------------------------------------------------------------------------
   // #region Constructor
   // -----------------------------------------------------------------------------

   /**
    * 
    * @param value0 initial = 10 ; range 0 - 100
    * @param value1 initial = 60 ; range 0 - 100
    * @param value2 initial = 10 ; range 0 - 100
    * @param value3 initial = 60 ; range 0 - 100
    * 
    * setSuccessMinimumZeroValueX(Double.MIN_VALUE);
    * setSuccessOneValueX(10);
    * setSuccessMaximumZeroValueX(70);
    * 
    * setFailureMinimumZeroValueX(30);
    * setFailureOneValueX(90);
    * setFailureMaximumZeroValueX(Double.MAX_VALUE);
    */
   public EnergyLevelBehaviorCodlet(int value0, int value1, 
                                    int value2, int value3) 
   {  
      int failureOne = 100 - value2;
      int failureMinZero = failureOne - value3;
      int successMaxZero = value0 + value1;
      
      setSuccessMinimumZeroValueX(Double.MIN_VALUE);
      setSuccessOneValueX(value0);
      setSuccessMaximumZeroValueX(successMaxZero);

      setFailureMinimumZeroValueX(failureMinZero);
      setFailureOneValueX(failureOne);
      setFailureMaximumZeroValueX(Double.MAX_VALUE);
   }

   // #endregion

   // -----------------------------------------------------------------------------
   // #region Overridden Codelet Members
   // -----------------------------------------------------------------------------

   @Override
   public void execute(Workspace workspace) 
   {
      if (workspace instanceof RobocodeWorkspace)
      {
         RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;
         BotCatable robot = robocodeWorkspace.getRobot();

         double energy = robot.getEnergy();

         setCrispValue(energy);
      }
   }

   // #endregion
}