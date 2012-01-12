package org.robocode;

import org.starcat.coderack.Coderack;
import org.starcat.core.StandardMetabolism;
import org.starcat.slipnet.Slipnet;
import org.robocode.configuration.RobocodeSlipnetBuilder;
import org.robocode.workspace.RobocodeWorkspace;

public class StartCatControler
{
// -----------------------------------------------------------------------------
// #region Private Data
// -----------------------------------------------------------------------------
   
   private Coderack coderack;
   private Slipnet slipnet;
   private RobocodeWorkspace workspace;

// -----------------------------------------------------------------------------
// #region Constructor
// -----------------------------------------------------------------------------
   
   public StartCatControler(BotCatable robot)
   {
      coderack = new Coderack();
      workspace = new RobocodeWorkspace(robot);

      RobocodeSlipnetBuilder slipnetBuilder = new RobocodeSlipnetBuilder();
      slipnet = slipnetBuilder.buildSlipnet(robot.getChromosome());
      
      StandardMetabolism coderackMetabolism = new StandardMetabolism(coderack);
      StandardMetabolism slipnetMetabolism = new StandardMetabolism(slipnet);
      StandardMetabolism workspaceMetabolism = 
         new StandardMetabolism(workspace);

      coderack.setMetabolism(coderackMetabolism);
      slipnet.setMetabolism(slipnetMetabolism);
      workspace.setMetabolism(workspaceMetabolism);

      // note: you add to a component the listener that will be listening for
      // codelets from that component (i.e. read it as "add me as a source of
      // codelets to the
      // listener named in the argument)
      slipnet.addCodeletEventListener(coderack);
      coderack.addCodeletEventListener(workspace);
      workspace.addCodeletEventListener(slipnet);
   }

// -----------------------------------------------------------------------------
// #region Public Members
// -----------------------------------------------------------------------------
   
   /**
    * Starts all the starcat components' threads
    */
   public void start()
   {
      coderack.start();
      workspace.start();
      slipnet.start();
   }
   
   /**
    * Stops all the starcat componets' threads
    */
   public void stop()
   {
      coderack.stop();
      workspace.stop();
      slipnet.stop();
   }
}
