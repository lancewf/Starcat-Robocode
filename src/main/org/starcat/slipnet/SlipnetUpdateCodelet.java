package org.starcat.slipnet;


import org.starcat.codelets.ControlCodelet;
import org.starcat.coderack.Coderack;
import org.starcat.workspace.Workspace;

public class SlipnetUpdateCodelet extends ControlCodelet
{
// -----------------------------------------------------------------------------
// #region Public Members
// -----------------------------------------------------------------------------
   
   public void execute(Workspace workspace)
   {
      //
      //Do nothing
      //
   }

   public void execute(Slipnet slpnt)
   {
      slpnt.update();
   }

   public void execute(Coderack cdrck)
   {
      //
      //Do nothing
      //
   }
   
//#endregion
}
