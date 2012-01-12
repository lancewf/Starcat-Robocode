/*
 * Created on Mar 16, 2006
 *
 */
package org.starcat.configuration;

public class SystemConfiguration {
	
	// Adaptive Execute methods
	public void setWorkspaceBehaviorAdaptiveExecute(String flag)
	{
		Boolean workingFlag = false;
		
		if (flag.contentEquals("true"))
		{
			workingFlag = true;
		}
		
		ParameterData.initializeWorkspaceBehaviorAdaptiveExecute(workingFlag);
	}
	public void setWorkspaceControlAdaptiveExecute(String flag)
	{
		Boolean workingFlag = false;
		
		if (flag.contentEquals("true"))
		{
			workingFlag = true;
		}
		
		ParameterData.initializeWorkspaceControlAdaptiveExecute(workingFlag);
	}
	public void setCoderackBehaviorAdaptiveExecute(String flag)
	{
		Boolean workingFlag = false;
		
		if (flag.contentEquals("true"))
		{
			workingFlag = true;
		}
		
		ParameterData.initializeCoderackBehaviorAdaptiveExecute(workingFlag);
	}
	public void setCoderackControlAdaptiveExecute(String flag)
	{
		Boolean workingFlag = false;
		
		if (flag.contentEquals("true"))
		{
			workingFlag = true;
		}
		
		ParameterData.initializeCoderackControlAdaptiveExecute(workingFlag);
	}
	public void setSlipnetBehaviorAdaptiveExecute(String flag)
	{
		Boolean workingFlag = false;
		
		if (flag.contentEquals("true"))
		{
			workingFlag = true;
		}
		
		ParameterData.initializeSlipnetBehaviorAdaptiveExecute(workingFlag);
	}
	public void setSlipnetControlAdaptiveExecute(String flag)
	{
		Boolean workingFlag = false;
		
		if (flag.contentEquals("true"))
		{
			workingFlag = true;
		}
		
		ParameterData.initializeSlipnetControlAdaptiveExecute(workingFlag);
	}
	
	// Execute Factor methods
	public void setWorkspaceBehaviorExecuteFactor(int factor)
	{
		ParameterData.initializeWorkspaceBehaviorExecuteFactor(factor);
	}
	public void setWorkspaceControlExecuteFactor(int factor)
	{
		ParameterData.initializeWorkspaceControlExecuteFactor(factor);
	}
	public void setCoderackBehaviorExecuteFactor(int factor)
	{
		ParameterData.initializeCoderackBehaviorExecuteFactor(factor);
	}
	public void setCoderackControlExecuteFactor(int factor)
	{
		ParameterData.initializeCoderackControlExecuteFactor(factor);
	}
	public void setSlipnetBehaviorExecuteFactor(int factor)
	{
		ParameterData.initializeSlipnetBehaviorExecuteFactor(factor);
	}
	public void setSlipnetControlExecuteFactor(int factor)
	{
		ParameterData.initializeSlipnetControlExecuteFactor(factor);
	}
	
	// Reduction factor methods
	public void setWorkspaceBehaviorReductionFactor(double factor)
	{
		ParameterData.initializeWorkspaceBehaviorReductionFactor(factor);
	}
	public void setWorkspaceControlReductionFactor(double factor)
	{
		ParameterData.initializeWorkspaceControlReductionFactor(factor);
	}
	public void setCoderackBehaviorReductionFactor(double factor)
	{
		ParameterData.initializeCoderackBehaviorReductionFactor(factor);
	}
	public void setCoderackControlReductionFactor(double factor)
	{
		ParameterData.initializeCoderackControlReductionFactor(factor);
	}
	public void setSlipnetBehaviorReductionFactor(double factor)
	{
		ParameterData.initializeSlipnetBehaviorReductionFactor(factor);
	}
	public void setSlipnetControlReductionFactor(double factor)
	{
		ParameterData.initializeSlipnetControlReductionFactor(factor);
	}	
	
	// Sleeper methods
	public void setWorkspaceBehaviorSleeper(String flag)
	{
		Boolean workingFlag = false;
		if (flag.contentEquals("true"))
			workingFlag = true;
		ParameterData.initializeWorkspaceBehaviorSleeper(workingFlag);
	}
	public void setWorkspaceControlSleeper(String flag)
	{
		Boolean workingFlag = false;
		if (flag.contentEquals("true"))
			workingFlag = true;
		ParameterData.initializeWorkspaceControlSleeper(workingFlag);
	}
	public void setCoderackBehaviorSleeper(String flag)
	{
		Boolean workingFlag = false;
		if (flag.contentEquals("true"))
			workingFlag = true;
		ParameterData.initializeCoderackBehaviorSleeper(workingFlag);
	}
	public void setCoderackControlSleeper(String flag)
	{
		Boolean workingFlag = false;
		if (flag.contentEquals("true"))
			workingFlag = true;
		ParameterData.initializeCoderackControlSleeper(workingFlag);
	}
	public void setSlipnetBehaviorSleeper(String flag)
	{
		Boolean workingFlag = false;
		if (flag.contentEquals("true"))
			workingFlag = true;
		ParameterData.initializeSlipnetBehaviorSleeper(workingFlag);
	}
	public void setSlipnetControlSleeper(String flag)
	{
		Boolean workingFlag = false;
		if (flag.contentEquals("true"))
			workingFlag = true;
		ParameterData.initializeSlipnetControlSleeper(workingFlag);
	}
	
	// Sleep time methods
	public void setWorkspaceBehaviorSleepTime(long factor)
	{
		ParameterData.initializeWorkspaceBehaviorSleepTime(factor);
	}
	public void setWorkspaceControlSleepTime(long factor)
	{
		ParameterData.initializeWorkspaceControlSleepTime(factor);
	}
	public void setCoderackBehaviorSleepTime(long factor)
	{
		ParameterData.initializeCoderackBehaviorSleepTime(factor);
	}
	public void setCoderackControlSleepTime(long factor)
	{
		ParameterData.initializeCoderackControlSleepTime(factor);
	}
	public void setSlipnetBehaviorSleepTime(long factor)
	{
		ParameterData.initializeSlipnetBehaviorSleepTime(factor);
	}
	public void setSlipnetControlSleepTime(long factor)
	{
		ParameterData.initializeSlipnetControlSleepTime(factor);
	}	
}
