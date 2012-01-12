package org.starcat.codelets;

import org.starcat.coderack.Coderack;
import org.starcat.codelets.Codelet;
import org.starcat.slipnet.Slipnet;
import org.starcat.workspace.Workspace;

/**
 * Control Codelets are the lowest level codelets. Their interface contract
 * merely requires a specification as to what to do with or in a particular
 * starcat component. Behavior codelets have many more attributes, but also must
 * be define their behavior within the various components. The abstract methods
 * of this class define the execution methods available to all codelets in
 * Starcat.
 */
public abstract class ControlCodelet extends Codelet implements Cloneable
{
   // --------------------------------------------------------------------------
   // Abstract Members
   // --------------------------------------------------------------------------

   public abstract void execute(Coderack coderack);

   public abstract void execute(Slipnet slipnet);

   public abstract void execute(Workspace workspace);

   // --------------------------------------------------------------------------
   // Overridden Codelet Members
   // --------------------------------------------------------------------------

   public void preExecute(Coderack coderack)
   {
      //
      // Do nothing
      //
   }

   public void preExecute(Slipnet slipnet)
   {
      //
      // Do nothing
      //        
   }

   public void preExecute(Workspace workspace)
   {
      //
      // Do nothing
      //
   }

   public void postExecute(Coderack coderack)
   {
      //
      // Do nothing
      //   
   }

   public void postExecute(Slipnet slipnet)
   {
      //
      // Do nothing
      //   
   }

   public void postExecute(Workspace workspace)
   {
      //
      // Do nothing
      //   
   }

   public ControlCodelet clone()
   {
      return (ControlCodelet) super.clone();
   }
}
