package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.RobotAction;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.BehaviorCodelet;
import org.starcat.workspace.Workspace;

public class PerformerBehaviorCodelet extends BehaviorCodelet
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private RobotAction robotAction;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public PerformerBehaviorCodelet(RobotAction robotAction)
   {
      this.robotAction = robotAction;
   }
   
   // --------------------------------------------------------------------------
   // Codelet Overridden Members
   // --------------------------------------------------------------------------
   
   @Override
   public void execute(Workspace workspace)
   {
      if (workspace instanceof RobocodeWorkspace)
      {
         RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;

         BotCatable robot = robocodeWorkspace.getRobot();

         robot.setMovement(robotAction);
      }
   }
}