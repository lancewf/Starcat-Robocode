package org.starcat.core;

import org.starcat.slipnet.Slipnet;
import org.starcat.slipnet.SlipnetUpdateCodelet;
import org.starcat.workspace.Workspace;
import org.starcat.workspace.WorkspaceUpdateCodelet;
import org.starcat.coderack.Coderack;
import org.starcat.coderack.CoderackUpdateCodelet;
import org.starcat.configuration.ParameterData;
import org.starcat.util.CircularQueue;

public class ControlRegularPulse extends RegularPulse {
	
	public ControlRegularPulse(CircularQueue queue, Component component)
	{
		super(queue,component);
	}

	public boolean isAdaptiveExecute() {
		return ParameterData.getControlAdaptiveExecute(getComponent());
	}
	public int getExecuteFactor() {
		return ParameterData.getControlExecuteFactor(getComponent());
	}
	public double getReductionFactor() {
		return ParameterData.getControlReductionFactor(getComponent());
	}
	public boolean isSleeper() {
		return ParameterData.getControlSleeper(getComponent());
	}
	public long getSleepTime() {
		return ParameterData.getControlSleepTime(getComponent());
	}
	
	public void setExecuteFactor(int execFactor) {
		ParameterData.setControlExecuteFactor(getComponent(), execFactor);
	}

	public void preProcess()
	{
		int executes = getExecuteFactor();
	
		for (int i = 0; i < executes; i++)
		{
			if (Slipnet.class.isAssignableFrom(getComponent().getClass())){
				SlipnetUpdateCodelet codelet = new SlipnetUpdateCodelet();
				push(codelet);
			}
			else if (Workspace.class.isAssignableFrom(getComponent().getClass())){
				WorkspaceUpdateCodelet codelet = new WorkspaceUpdateCodelet();
				push(codelet);
			}
			else if (Coderack.class.isAssignableFrom(getComponent().getClass())){
				CoderackUpdateCodelet codelet = new CoderackUpdateCodelet();
				push(codelet);
			}
			else {
			    System.out.println("Did not determine a control codelet in ControlRegularPulse");
			}
		}
		super.preProcess();
	}	
}
