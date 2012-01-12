package org.starcat.slipnet;

/*
 * This class represents a slipable link, i.e. one whose current length can
 * shrink based on its label node's activation level.
 */
public class SlipLink extends Link
{
// -----------------------------------------------------------------------------
// #region Private Data
// -----------------------------------------------------------------------------
   
   private SlipnetNode labelNode;
   private int minShrunkLength = 0;

// #endregion

// -----------------------------------------------------------------------------
// #region Constructor
// -----------------------------------------------------------------------------
   
   public SlipLink()
   {
      super();
   }

   public SlipLink(String name, int intrinsicLength, int minShrunkLength,
         SlipnetNode from, SlipnetNode to, SlipnetNode label)
   {
      super(name, intrinsicLength, from, to);
      this.minShrunkLength = minShrunkLength;
      this.labelNode = label;
   }

   public SlipLink(int intrinsicLength, int minShrunkLength, SlipnetNode from,
         SlipnetNode to, SlipnetNode label)
   {
      super("", intrinsicLength, from, to);
      this.minShrunkLength = minShrunkLength;
      this.labelNode = label;
   }

   public SlipLink(String name, int intrinsicLength, SlipnetNode from,
         SlipnetNode to, SlipnetNode label)
   {
      super(name, intrinsicLength, from, to);
      this.labelNode = label;
   }

   public SlipLink(int intrinsicLength, SlipnetNode from, SlipnetNode to,
         SlipnetNode label)
   {
      super("", intrinsicLength, from, to);
      this.labelNode = label;
   }
   
// #endregion
   
// -----------------------------------------------------------------------------
// #region Public Members
// -----------------------------------------------------------------------------
   
   public SlipnetNode getLabelNode()
   {
      return labelNode;
   }

   public void setLabelNode(SlipnetNode labelNode)
   {
      this.labelNode = labelNode;
   }

   public double getDegreeOfAssociation()
   {
      return 100 - getCurrentLength();
   }

   public static SlipLink createSlipLink(String name,
                                         int intrinLen,
                                         int maxShrunkLen,
                                         SlipnetNode from,
                                         SlipnetNode to,
                                         SlipnetNode label)
   {
      return new SlipLink(name, intrinLen, maxShrunkLen, from, to, label);
   }
   
   public int getMinShrunkLength()
   {
      return minShrunkLength;
   }

   public void setMinShrunkLength(int minShrunkLength)
   {
      this.minShrunkLength = minShrunkLength;
   }
   
   public String toString()
   {
      return super.toString() + " [Label= " + getLabelNode() + "]"
            + "/" + minShrunkLength + "-->" + getToNode();
   }
   
// #endregion
   
// -----------------------------------------------------------------------------
// #region Private Members
// -----------------------------------------------------------------------------
   
   /**
    * This method first asks this link's label node for its current activation
    * then computes this links shrunk length based on that.
    * 
    * Note: this is not the method called during the spread of activation by a
    * node.
    * 
    */
   private int getCurrentLength()
   {
      int currentLength = getIntrinsicLength();
      int activation = labelNode.getActivation();
      int activationThreshold = labelNode.getActivationThreshold();

      if (activation > activationThreshold)
      {
         double activationD = activation;
         double activationThresholdD = activationThreshold;
         double maxActivationD = 100.0;
         double intrinsicLengthD = getIntrinsicLength();
         double temp = 1.0 - ((activationD - activationThresholdD) / 
               (maxActivationD - activationThresholdD));

         double currentLengthD = intrinsicLengthD * temp;
         currentLength = (int) Math.round(currentLengthD);
      }
      if (currentLength < minShrunkLength)
         currentLength = minShrunkLength;

      return currentLength;
   }

// #endregion
}
