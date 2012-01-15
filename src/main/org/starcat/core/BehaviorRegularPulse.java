package org.starcat.core;

import org.starcat.coderack.Coderack;
import org.starcat.configuration.ParameterData;
import org.starcat.slipnet.Slipnet;
import org.starcat.util.CircularQueue;
import org.starcat.workspace.Workspace;

public class BehaviorRegularPulse extends RegularPulse
{

   public BehaviorRegularPulse(CircularQueue queue, Component component)
   {
      super(queue, component);
   }

   public boolean isAdaptiveExecute()
   {
      return ParameterData.getBehaviorAdaptiveExecute(getComponent());
   }

   public int getExecuteFactor()
   {
      return ParameterData.getBehaviorExecuteFactor(getComponent());
   }

   public double getReductionFactor()
   {
      return ParameterData.getBehaviorReductionFactor(getComponent());
   }

   public boolean isSleeper()
   {
      return ParameterData.getBehaviorSleeper(getComponent());
   }

   public long getSleepTime()
   {
      return ParameterData.getBehaviorSleepTime(getComponent());
   }

   public void setExecuteFactor(int execFactor)
   {
      ParameterData.setBehaviorExecuteFactor(getComponent(), execFactor);
   }

   public void processAlgorithm()
   {
      // This will take codelets from the buffer (the number is determined
      // by slipnet exec factor) and executes those codelets in the
      // slipnet
      if (Slipnet.class.isAssignableFrom(getComponent().getClass()))
      {
         while (!checkIfDoneProcessing())
         {
            if (this.hasMoreCodeletsToProcess())
            {
               process(getCodeletToProcess());
            }
         }
      }
      else if (Workspace.class.isAssignableFrom(getComponent().getClass()))
      {
         while (!checkIfDoneProcessing())
         {
            if (this.hasMoreCodeletsToProcess())
            {
               process(getCodeletToProcess());
            }
         }
      }
      else if (Coderack.class.isAssignableFrom(getComponent().getClass()))
      {
         // this is different for coderack and we want to flush the
         // coderack buffer and pop a number of codelets based on the
         // coderack execuation factor
         Coderack tempComponent = (Coderack) getComponent();

         while (this.hasMoreCodeletsToProcess())
         {
            process(getCodeletToProcess());
         }
         int execFactor = getExecuteFactor();

         for (int i = 0; i < execFactor; i++)
         {
            tempComponent.pop();
         }
      }
      else
      {
         System.out.println("Did not determine a control"
               + " codelet in ControlRegularPulse");
      }

      if (isSleeper() && isAlive())
      {
         try
         {
            long time = getSleepTime();
            long count = 0;
            while(time > count && isAlive())
            {
               Thread.sleep(1);
               count++;
            }
            //Thread.sleep(time);
         }
         catch(IllegalThreadStateException ex)
         {
            return;
         }
         catch (InterruptedException ie)
         {
            return;
         }
      }
      else
      {
         // make thread stop to give other threads a chance to execute
         try
         {
            Thread.sleep(1);
         }
         catch (InterruptedException ie)
         {
            ie.printStackTrace();
         }
      }
   }

}
