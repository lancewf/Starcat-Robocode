package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.RobotAction;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.BehaviorCodelet;
import org.starcat.workspace.Workspace;

public class PerformerBehaviorCodelet extends BehaviorCodelet
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   private RobotAction robotAction;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
   
   public PerformerBehaviorCodelet(RobotAction robotAction)
   {
      this.robotAction = robotAction;
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Codelet Overridden Members
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
   
   // #endregion
}