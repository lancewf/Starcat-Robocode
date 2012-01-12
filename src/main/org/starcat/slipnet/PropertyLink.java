package org.starcat.slipnet;

public class PropertyLink extends Link
{
// -----------------------------------------------------------------------------
// #region Constructor
// -----------------------------------------------------------------------------
   
   public PropertyLink()
   {
      super();
   }

   public PropertyLink(String name, int intrinsicLength, SlipnetNode from,
         SlipnetNode to)
   {
      super(name, intrinsicLength, from, to);
   }

   public PropertyLink(int intrinsicLength, SlipnetNode from, SlipnetNode to)
   {
      this("", intrinsicLength, from, to);
   }
// #endregion

}
