package org.robocode;

import org.robocode.genenticalgorithm.BotcatChromosome;

public class SlipnetView {

	private BotcatChromosome chromosome;
	public SlipnetView(BotcatChromosome chromosome){
		this.chromosome = chromosome;
	}
	
	public void println() {
		System.out.println("Memory Level, Activation Threshold");
		for (String slipnetNodeName : chromosome.getSlipnetNodeList()) {
			System.out.println(slipnetNodeName + ": "
					+ chromosome.getMemoryLevel(slipnetNodeName) + ", "
					+ chromosome.getActivationThreshold(slipnetNodeName));
		}

		System.out.println("\nStrong Links");
		for (String fromSlipnetNode : chromosome.getSlipnetNodeList()) {
			for (String toSlipnetNode : chromosome.getSlipnetNodeList()) {
				int value = chromosome.getLinkLength(fromSlipnetNode,
						toSlipnetNode);

				if (value <= 10) {
					System.out.println(fromSlipnetNode + "->" + toSlipnetNode
							+ " = " + value);
				}
			}
		}
		
		System.out.println("\nMedium Links");
		for (String fromSlipnetNode : chromosome.getSlipnetNodeList()) {
			for (String toSlipnetNode : chromosome.getSlipnetNodeList()) {
				int value = chromosome.getLinkLength(fromSlipnetNode,
						toSlipnetNode);

				if (value == 95) {
					System.out.println(fromSlipnetNode + "->" + toSlipnetNode
							+ " = " + value);
				}
			}
		}
	}
}
