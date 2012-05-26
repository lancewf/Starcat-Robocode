package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.RobotUtilities;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

/**
 * 
 * M  |      
 * e 1|------------_-_--------------- 
 * M  |          _-   -_   
 * B  |        _-       -_    
 * E 0|_______-____________-_________
 * R  |0     90    180     270    360  : value
 *     F     R      B      L       F
 *    
 * F - forward
 * R - right
 * B - backward
 * L - left
 */              
public class BackwardTurretOrientationObserverCodelet 
extends FuzzyBehaviorCodelet 
{

   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public BackwardTurretOrientationObserverCodelet() 
   {
      setSuccessMinimumZeroValueX(90);
      setSuccessOneValueX(180);
      setSuccessMaximumZeroValueX(270);

      setFailureMinimumZeroValueX(Double.MIN_VALUE);
      setFailureOneValueX(135);
      setFailureMaximumZeroValueX(Double.MAX_VALUE);
   }

   // --------------------------------------------------------------------------
   //  Overridden Codelet Members
   // --------------------------------------------------------------------------

   @Override
   public void execute(Workspace workspace) 
   {
      if (workspace instanceof RobocodeWorkspace) 
      {
         RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;
         BotCatable robot = robocodeWorkspace.getRobot();

         double bearing = RobotUtilities.getTurretHeadingFromFront(robot);

         setCrispValue(bearing);
      }
   }
}