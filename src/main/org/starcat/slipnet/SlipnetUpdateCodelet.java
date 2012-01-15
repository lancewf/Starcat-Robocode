package org.starcat.slipnet;


import org.starcat.codelets.ControlCodelet;
import org.starcat.coderack.Coderack;
import org.starcat.workspace.Workspace;

public class SlipnetUpdateCodelet extends ControlCodelet
{
   public void execute(Workspace workspace)
   {
      //
      //Do nothing
      //
   }

   public void execute(Slipnet slipent)
   {
	   slipent.update();
   }

   public void execute(Coderack coderack)
   {
      //
      //Do nothing
      //
   }
}
