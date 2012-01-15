package org.robocode.codelets;

import org.robocode.BotCatable;
import org.robocode.RobotUtilities;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

/**
 * Codelet that senses how close to an Target in one given direction
 * 
 * @author lancewf
 *
 */
public class TargetObserverBehaviorCodelet extends FuzzyBehaviorCodelet 
{
   // --------------------------------------------------------------------------
   // Public static Data
   // --------------------------------------------------------------------------
   
   public static final int FORWARD = 0;
   public static final int FORWARD_RIGHT = 45;
   public static final int RIGHT = 90;
   public static final int BACKWARD_RIGHT = 135;
   public static final int BACKWARD = 180;
   public static final int BACKWARD_LEFT = -135;
   public static final int LEFT = -90;
   public static final int FORWARD_LEFT = -45;
   
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private double headingToLook = 0.0;
   
   private int targetDistance = 400;

   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   /**
    * Creates a obstacle observer in a specific direction and buffer distance. 
    * 
    * @param headingToLook - the direction that the codelet is observing
    * @param bufferDistance - the buffer distance of the obstacle from the robot
    */
   public TargetObserverBehaviorCodelet(double headingToLook, int targetDistance) 
   {
      this.targetDistance = targetDistance;
      
      setSuccessMinimumZeroValueX(Double.MIN_VALUE);
      setSuccessOneValueX(getHalfTargetDistance());
      setSuccessMaximumZeroValueX(getOneHalfTargetDistance());

      setFailureMinimumZeroValueX(getHalfTargetDistance());
      setFailureOneValueX(getOneHalfTargetDistance());
      setFailureMaximumZeroValueX(Double.MAX_VALUE);
      
      this.headingToLook = headingToLook;
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

         double distance = RobotUtilities.findDistanceOpponets(robot,
        		 headingToLook);
         
         setCrispValue(distance);
      }
   }

   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   private int getOneHalfTargetDistance()
   {
      return targetDistance + getHalfTargetDistance();
   }

   private int getHalfTargetDistance()
   {
      return targetDistance / 2;
   }
}