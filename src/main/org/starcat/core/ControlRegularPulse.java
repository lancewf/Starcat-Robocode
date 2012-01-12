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
		return ParameterData.getControlAdaptiveExecute(component);
	}
	public int getExecuteFactor() {
		return ParameterData.getControlExecuteFactor(component);
	}
	public double getReductionFactor() {
		return ParameterData.getControlReductionFactor(component);
	}
	public boolean isSleeper() {
		return ParameterData.getControlSleeper(component);
	}
	public long getSleepTime() {
		return ParameterData.getControlSleepTime(component);
	}
	
	public void setExecuteFactor(int execFactor) {
		ParameterData.setControlExecuteFactor(component, execFactor);
	}

	public void preProcess()
	{
		int executes = getExecuteFactor();
	
		for (int i = 0; i < executes; i++)
		{
			if (Slipnet.class.isAssignableFrom(component.getClass())){
				SlipnetUpdateCodelet codelet = new SlipnetUpdateCodelet();
				queue.push(codelet);
			}
			else if (Workspace.class.isAssignableFrom(component.getClass())){
				WorkspaceUpdateCodelet codelet = new WorkspaceUpdateCodelet();
				queue.push(codelet);
			}
			else if (Coderack.class.isAssignableFrom(component.getClass())){
				CoderackUpdateCodelet codelet = new CoderackUpdateCodelet();
				queue.push(codelet);
			}
			else {
			    System.out.println("Did not determine a control codelet in ControlRegularPulse");
			}
			beforePulse = queue.size();
		}
	}	
}
