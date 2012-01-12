package org.starcat.slipnet;

/**
 * Implementation of an instance link as described in Copycat.
 * 
 */
public class InstanceLink extends Link
{
// -----------------------------------------------------------------------------
// #region Constructor
// -----------------------------------------------------------------------------
   
   public InstanceLink()
   {
      super();
   }

   public InstanceLink(String name, int intrinsicLength, SlipnetNode from,
         SlipnetNode to)
   {
      super(name, intrinsicLength, from, to);
   }

   public InstanceLink(int intrinsicLength, SlipnetNode from, SlipnetNode to)
   {
      this("", intrinsicLength, from, to);
   }
// #endregion

}
