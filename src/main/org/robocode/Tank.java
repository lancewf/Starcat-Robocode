package org.robocode;

import robocode.ScannedRobotEvent;

/**
* This class stores data about one other robot on the Robocode battlefield 
* 
* @author lancewf
*/
public class Tank 
{
   //---------------------------------------------------------------------------
   // #region Private Data
   //---------------------------------------------------------------------------
   
   private String name;
   private double energy;
   private double bearing;
   private double distance;
   private double heading;
   private double velocity;
   private long time;
   private double x;
   private double y;
   
   //#endregion
   
   //---------------------------------------------------------------------------
   // #region Constructor
   //---------------------------------------------------------------------------
   
   public Tank(ScannedRobotEvent scannedRobotEvent, double xWhenSighted, 
               double yWhenSighted, double headingWhenSighted) 
   {
      setScannedRobotData(scannedRobotEvent);
      calculateTankPosition(headingWhenSighted, xWhenSighted, yWhenSighted);
   }
  
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
   
   public void update(Tank currentTank)
   {
      if(currentTank.getName().equals(getName()) && 
            currentTank.getTime() > getTime())
      {
         copyTank(currentTank);
      }
   }
   
   public double getX()
   {
      return x;
   }
   
   public double getY()
   {
      return y;
   }
   
   public double getVelocity()
   {
      return velocity;
   }
   
   public double getHeading()
   {
      return heading;
   }
   
   public double getDistance()
   {
      return distance;
   }
   
   public double getBearing()
   {
      return bearing;
   }
   
   public long getTime()
   {
      return time;
   }
   
   public String getName()
   {
       return name;
   }
   
   public double getEnergy()
   {
       return energy;
   }
   
   public boolean isAlive()
   {
      return energy > 0;
   }
   
   public double bearingToTank(double x1, double y1)  
   {
      double radians = bearingToTankRadian( x1, y1);
      return Math.toDegrees(radians);
   }
   
   public double distanceToTank(double x1, double y1)
   {
      double deltaX = x - x1;
      double deltaY = y - y1;
      return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Private Members
   // --------------------------------------------------------------------------

   private void setScannedRobotData(ScannedRobotEvent scannedRobotEvent)
   {
      this.name = scannedRobotEvent.getName();
      this.energy = scannedRobotEvent.getEnergy();
      this.bearing = scannedRobotEvent.getBearing();
      this.distance = scannedRobotEvent.getDistance();
      this.heading = scannedRobotEvent.getHeading();
      this.velocity = scannedRobotEvent.getVelocity();
      this.time = scannedRobotEvent.getTime();      
   }
   
   private void calculateTankPosition(double headingWhenSighted, 
                                      double xWhenSighted, 
                                      double yWhenSighted) 
   {
      double absoluteBearing = ( headingWhenSighted + getBearing()) % 360.0;
      
      if(absoluteBearing < 0)
      {
         absoluteBearing = 360.0 + absoluteBearing;
      }
      
      double distanceWhenSighted = getDistance();
      absoluteBearing = Math.toRadians(absoluteBearing);   // convert to radians
      
      x = xWhenSighted + Math.sin(absoluteBearing) * distanceWhenSighted;
      y = yWhenSighted + Math.cos(absoluteBearing) * distanceWhenSighted;
  }
   
   private double bearingToTankRadian(double x1, double y1)
   {
      double deltaX = x - x1;
      double deltaY = y - y1;
      double distanceAway = distanceToTank(x1, y1);
      if (deltaX == 0)
         return 0;

      if (deltaX > 0)
      {
         if (deltaY > 0)
            return Math.asin(deltaX / distanceAway);
         else
            return (Math.PI - Math.asin(deltaX / distanceAway));
      }
      else
      { // deltaX < 0
         if (deltaY > 0)
            return ((2 * Math.PI) - Math.asin(-deltaX / distanceAway));
         else
            return Math.PI + Math.asin(-deltaX / distanceAway);
      }
   }
   
   private void copyTank(Tank tank)
   {
      this.name = tank.getName();
      this.energy = tank.getEnergy();
      this.bearing = tank.getBearing();
      this.distance = tank.getDistance();
      this.heading = tank.getHeading();
      this.velocity = tank.getVelocity();
      this.time = tank.getTime();   
      this.x = tank.x;
      this.y = tank.y;
   }
   
   // #endregion
}

