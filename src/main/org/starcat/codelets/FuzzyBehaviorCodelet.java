package org.starcat.codelets;

import org.starcat.slipnet.Slipnet;
import org.starcat.util.FuzzySet;

public abstract class FuzzyBehaviorCodelet extends BehaviorCodelet
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
      
   private FuzzySet successFuzzySet = new FuzzySet();
   private FuzzySet failureFuzzySet = new FuzzySet();
   private double crispValue;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
   
   public void setCrispValue(double crispValue)
   {
      this.crispValue = crispValue;
   }
   
   /**
    * Set the value one on the success fuzzy set.
    * 
    * M 1|
    * e  |      _-|-_
    * M  |    _-  |  -_
    * B  |  _-    |    -_
    * E 0|________________
    * R  |min    one    max  : value
    *  
    */
   public void setSuccessOneValueX(double oneValueX)
   {
      successFuzzySet.setOneValueX(oneValueX);
   }
   
   /**
    * Set the minimum zero value on the success fuzzy set. A value less than the minimum zero value 
    * always has a membership value of one 
    * 
    * @param minimumZeroValueX if the minimum value is 
    * Negative infinity pass in Double.MIN_VALUE, which would look like below
    * M 1|
    * e  |--------|-_
    * M  |        |  -_
    * B  |        |    -_
    * E 0|________________
    * R  |min    one   max  : value
    * 
    */
   public void setSuccessMinimumZeroValueX(double minimumZeroValueX)
   {
      successFuzzySet.setMinimumZeroValueX(minimumZeroValueX);
   }
   
   /**
    * Set the maximum zero value on the success fuzzy set. This value is 
    * the point where any crisp value greater than 
    * always has a membership value of one
    *  
    * @param maximumZeroValueX if the maximum value is positive infinity pass in
    * Double.MAX_VALUE, which would look link below.
    * 
    * M 1|      
    * e  |      _-|---------------
    * M  |    _-  |  
    * B  |  _-    |    
    * E 0|________________
    * R  |min    one   max  : value
    * 
    */
   public void setSuccessMaximumZeroValueX(double maximumZeroValueX)
   {
      successFuzzySet.setMaximumZeroValueX(maximumZeroValueX);
   }
   
   /**
    * Set the value one on the failure fuzzy set on the graph below
    * 
    * M 1|
    * e  |      _-|-_
    * M  |    _-  |  -_
    * B  |  _-    |    -_
    * E 0|________________
    * R  |min    one    max  : value
    *  
    */
   public void setFailureOneValueX(double oneValueX)
   {
      failureFuzzySet.setOneValueX(oneValueX);
   }
   
   /**
    * Set the minimum zero value on the failure fuzzy set. A value less than the
    * minimum zero value always has a membership value of one 
    * 
    * @param minimumZeroValueX if the minimum value is 
    * Negative infinity pass in Double.MIN_VALUE, which would look like below
    * M 1|
    * e  |--------|-_
    * M  |        |  -_
    * B  |        |    -_
    * E 0|________________
    * R  |min    one   max  : value
    * 
    */
   public void setFailureMinimumZeroValueX(double minimumZeroValueX)
   {
      failureFuzzySet.setMinimumZeroValueX(minimumZeroValueX);
   }
   
   /**
    * Set the maximum zero value on the failure fuzzy set. 
    * This value is the point where any crisp
    * value greater than always has a membership value of one
    *  
    * @param maximumZeroValueX if the maximum value is positive infinity pass in
    * Double.MAX_VALUE, which would look link below.
    * 
    * M 1|      
    * e  |      _-|---------------
    * M  |    _-  |  
    * B  |  _-    |    
    * E 0|________________
    * R  |min    one   max  : value
    * 
    */
   public void setFailureMaximumZeroValueX(double maximumZeroValueX)
   {
      this.failureFuzzySet.setMaximumZeroValueX(maximumZeroValueX);
   }

   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Overridden Codelet Members
   // --------------------------------------------------------------------------
      
   public void execute(Slipnet slipnet)
   {
      double successMemberValue = successFuzzySet.getMemberValue(crispValue);
      double failureMemberValue = failureFuzzySet.getMemberValue(crispValue);
      for (SlipnetNodeActivationRecipient successfullRecipient : 
         getSuccessActivationRecipients())
      {
         int amountToAdd = successfullRecipient.getAmountToAdd();
         amountToAdd = (int) ( successMemberValue * (double) amountToAdd);

         slipnet.addActivation(successfullRecipient.getActivationRecipient(),
            amountToAdd);
      }
      
      for (SlipnetNodeActivationRecipient failureRecipient : 
         getFailureActivationRecipients())
      {
         int amountToAdd = failureRecipient.getAmountToAdd();
         amountToAdd = (int) ( failureMemberValue * (double) amountToAdd);

         slipnet.addActivation(failureRecipient.getActivationRecipient(),
            amountToAdd);
      }
   }
   
   // #endregion
   
}
