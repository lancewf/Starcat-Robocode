package org.robocode;

public class Position
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   private double x;
   private double y;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
   
   /** Creates a new instance of Position */
   public Position(double x, double y) 
   {
       this.x = x;
       this.y = y;
   }
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------

   public double getX()
   {
      return x;
   }

   public void setX(double x1)
   {
      x = x1;
   }

   public double getY()
   {
      return y;
   }

   public void setY(double y1)
   {
      y = y1;
   }

   // #endregion
}
