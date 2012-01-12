package org.starcat.slipnet;

/**
 * Implementation of a lateral link as described in Copycat
 */
public class LateralLink extends Link
{
// -----------------------------------------------------------------------------
// #region Constructor
// -----------------------------------------------------------------------------
   
   public LateralLink()
   {
      super();
   }

   public LateralLink(String name, int intrinsicLength, SlipnetNode from,
         SlipnetNode to)
   {
      super(name, intrinsicLength, from, to);
   }

   public LateralLink(int intrinsicLength, SlipnetNode from, SlipnetNode to)
   {
      this("", intrinsicLength, from, to);
   }
   
// #endregion
}
