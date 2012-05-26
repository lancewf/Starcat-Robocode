package org.robocode.genenticalgorithm.fitnesstest;

import java.io.File;
import java.util.List;

import org.robocode.BotCatListener;
import org.robocode.genenticalgorithm.BotcatChromosome;
import org.robocode.genenticalgorithm.Chromosome;
import org.robocode.genenticalgorithm.Individual;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class InfightingFitnessTest implements IFitnessTest {
	// ----------------------------------------------------------------------
	// Private Data
	// ----------------------------------------------------------------------

	public static String BOTCAT_NAME = "org.robocode.BotCat";
	public static String BOTCAT_2_NAME = "org.robocode.BotCat2";
	private BotCatListener listener;
	private FitnessTestProperties fitnessTestProperties;

	// ----------------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------------

	public InfightingFitnessTest(BotCatListener listener) {
		this.listener = listener;
		this.fitnessTestProperties = new FitnessTestProperties();

	}

	// ----------------------------------------------------------------------
	// Public Members
	// ----------------------------------------------------------------------

	public void run(Individual individual) {
		File individualFile = new File(
				fitnessTestProperties.getBotCatPropertiesPath());
		individual.save(individualFile);
		File bestIndividualFile = new File(
				fitnessTestProperties.getBotCat2PropertiesPath());
		getBestIndividual().save(bestIndividualFile);

		runBattle();

		int score = getScore();

		individual.setFitnessScore(score);

		// to read back in any changes to the dna
		individual.load(individualFile);
	}

	// ----------------------------------------------------------------------
	// Private Members
	// ----------------------------------------------------------------------

	private Individual getBestIndividual() {
		File bestBotFile = new File(
				"/home/lancewf/robocode/bestAgent.properties");
		Chromosome bestBotChromosome = new BotcatChromosome(bestBotFile);

		Individual bestBotIndividual = new Individual(bestBotChromosome);

		return bestBotIndividual;
	}
	
	private int getScore() {
		BattleResults robotResults = listener.getRobotResults();

		if (robotResults != null) {
			int fitnessScore = robotResults.getScore();
			System.out.println("Fitness = " + fitnessScore + " Total Score = "
					+ robotResults.getScore() + " Bullet = "
					+ robotResults.getBulletDamage() + " Ram = "
					+ robotResults.getRamDamage());

			return fitnessScore;
		} else {
			System.out.println("error in set Score the results was not found");
			return 0;
		}
	}

	private void runBattle() {
		BattlefieldSpecification battleField = new BattlefieldSpecification(
				fitnessTestProperties.getBattlefieldWidth(),
				fitnessTestProperties.getBattlefieldHeight());

		RobocodeEngine engine = getEngine();

		listener.setEngine(engine);

		RobotSpecification[] robots = getRobots(engine, fitnessTestProperties);

		BattleSpecification battle = new BattleSpecification(
				fitnessTestProperties.getNumberOfRound(),
				fitnessTestProperties.getInactivityTime(), 0.1, battleField,
				robots);

		listener.runBattle(battle);

		battleField = null;
		battle = null;
		robots = null;

		System.gc();
	}

	private RobocodeEngine engine = null;

	private RobocodeEngine getEngine() {
		if (engine == null) {
			engine = new RobocodeEngine(listener);
		}

		return engine;
	}

	private RobotSpecification[] getRobots(RobocodeEngine engine,
			FitnessTestProperties fitnessTestProperties) {
		List<String> opponents = fitnessTestProperties.getOpponentsNames();

		String opponentsNames = "";

		for (String opponentName : opponents) {
			opponentsNames += opponentName + "*,";
		}

		opponentsNames += BOTCAT_NAME + "*," + BOTCAT_2_NAME + "*";

		return engine.getLocalRepository(opponentsNames);
	}
}
