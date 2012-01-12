package org.robocode.codelets;

import java.util.Random;

import org.robocode.BotCatable;
import org.robocode.RobotUtilities;
import org.robocode.workspace.RobocodeWorkspace;
import org.starcat.codelets.FuzzyBehaviorCodelet;
import org.starcat.workspace.Workspace;

public class TargetObserverBehaviorCodelet extends FuzzyBehaviorCodelet 
{
   // --------------------------------------------------------------------------
   // #region Public static Data
   // --------------------------------------------------------------------------
   
   public static final int LEFT = -90;
   public static final int BACKWARD = 180;
   public static final int FORWARD = 0;
   public static final int RIGHT = 90;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------

   private double headingToLook = 0.0;
   
   private int targetDistance = 400;
   
   private Random random = new Random();

   // #endregion

   // --------------------------------------------------------------------------
   // #region Constructor
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

   // #endregion

   // --------------------------------------------------------------------------
   // #region Overridden Codelet Members
   // --------------------------------------------------------------------------

   @Override
   public void execute(Workspace workspace) 
   {
      if (workspace instanceof RobocodeWorkspace) 
      {
         RobocodeWorkspace robocodeWorkspace = (RobocodeWorkspace) workspace;
         BotCatable robot = robocodeWorkspace.getRobot();

         if (robot != null) 
         {
            double distance = findDistanceToObstacle(robot);

            setCrispValue(distance);
         }
      }
   }

   // #endregion

   // --------------------------------------------------------------------------
   // #region Private Members
   // --------------------------------------------------------------------------

   private int getOneHalfTargetDistance()
   {
      return targetDistance + getHalfTargetDistance();
   }

   private int getHalfTargetDistance()
   {
      return targetDistance / 2;
   }
   
   private double findDistanceToObstacle(BotCatable robot)
   {
      double modifiedHeadingToLook = headingToLook;

      int amountToAdd = random.nextInt(30);

      if (random.nextBoolean())
      {
         modifiedHeadingToLook += amountToAdd;
      }
      else
      {
         modifiedHeadingToLook -= amountToAdd;
      }

      double distance = RobotUtilities.findDistanceOpponets(robot,
         modifiedHeadingToLook);

      return distance;
   }

   // #endregion
}