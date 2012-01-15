package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

/**
 * 
 * M  |      
 * e 1|-----_-_---------_-_----- 
 * M  |   _-   -_     _-   -_
 * B  | _-       -_ _-       -_
 * E 0|-___________-___________-_
 * R  |0    90    180   270    360  : value
 *    N     E      S     W      N
 */  
public class EastBodyOrientationObserverCodelet 
extends FuzzyBehaviorCodelet 
{

   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public EastBodyOrientationObserverCodelet() {
      setSuccessMinimumZeroValueX(0);
      setSuccessOneValueX(90);
      setSuccessMaximumZeroValueX(180);

      setFailureMinimumZeroValueX(Double.MIN_VALUE);
      setFailureOneValueX(135);
      setFailureMaximumZeroValueX(Double.MAX_VALUE);
   }

   // --------------------------------------------------------------------------
   // Overridden Codelet Members
   // --------------------------------------------------------------------------

   @Override
   public void execute(Workspace workspace) {
      if (workspace instanceof RobocodeWorkspace) {
         RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;
         BotCatable robot = robocodeWorkspace.getRobot();

         double bearingToTarget = robot.getHeading();

         setCrispValue(bearingToTarget);
      }
   }
}