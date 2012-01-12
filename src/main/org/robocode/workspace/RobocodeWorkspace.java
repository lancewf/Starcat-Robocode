package org.robocode.workspace;

import org.robocode.BotCatable;
import org.starcat.workspace.Workspace;

public class RobocodeWorkspace extends Workspace
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   private BotCatable robot;
   
   //#endregion

   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
   
   public RobocodeWorkspace(BotCatable robot)
   {
      this.robot = robot;
   }
   
   //#endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
   
   public BotCatable getRobot()
   {
      return robot;
   }

   //#endregion
}
