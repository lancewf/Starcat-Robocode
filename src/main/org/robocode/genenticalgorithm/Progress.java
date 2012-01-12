package org.robocode.genenticalgorithm;

import javax.swing.JProgressBar;

public class Progress implements IProgress
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------
   
   private JProgressBar progressBar;
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
   
   public Progress(JProgressBar progressBar)
   {
      this.progressBar = progressBar;
   }

   // #endregion
   
   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------
   
   public void setValue(int value)
   {
      progressBar.setValue(value);
   }
   
   // #endregion
}
