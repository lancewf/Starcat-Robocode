package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.RobotUtilities;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

/**
 * 
 * M  |      
 * e 1|------------_-_--------- 
 * M  |          _-   -_   
 * B  |        _-       -_    
 * E 0|_______-____________-_______
 * R  |-180  -90    0      90     180  : value
 *          West   North  East
 */              
public class ForwardTurretOrientationObserverCodelet 
extends FuzzyBehaviorCodelet 
{

   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public ForwardTurretOrientationObserverCodelet() 
   {
      setSuccessMinimumZeroValueX(-90);
      setSuccessOneValueX(0);
      setSuccessMaximumZeroValueX(90);

      setFailureMinimumZeroValueX(Double.MIN_VALUE);
      setFailureOneValueX(135);
      setFailureMaximumZeroValueX(Double.MAX_VALUE);
   }

   // --------------------------------------------------------------------------
   // Overridden Codelet Members
   // --------------------------------------------------------------------------

   @Override
   public void execute(Workspace workspace) 
   {
      if (workspace instanceof RobocodeWorkspace) 
      {
         RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;
         BotCatable robot = robocodeWorkspace.getRobot();

         double bearing = RobotUtilities.getTurretHeadingFromFront(robot);
         
         if(bearing > 180)
         {
            bearing = bearing - 360;
         }

         setCrispValue(bearing);
      }
   }
}