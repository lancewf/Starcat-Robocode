package org.robocode.genenticalgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GenerationRunner {
	// --------------------------------------------------------------------------
	// #region Private Static Data
	// --------------------------------------------------------------------------

	private static final String POPULATION_INFO_FILE_NAME = "populationInfo.properties";

	private static final String GENERATION_COUNT = "generations";

	// #endregion

	// --------------------------------------------------------------------------
	// #region Private Data
	// --------------------------------------------------------------------------

	private int numberOfGenerationsRan = 0;
	private GenenticAlgorithm genenticAlgorithm;

	// #endregion

	// --------------------------------------------------------------------------
	// #region Constructor
	// --------------------------------------------------------------------------

	public GenerationRunner(GenenticAlgorithm genenticAlgorithm) {
		this.genenticAlgorithm = genenticAlgorithm;
	}

	// #endregion

	// --------------------------------------------------------------------------
	// #region Public Members
	// --------------------------------------------------------------------------

	public void run() {
		genenticAlgorithm.nextGeneration();

		numberOfGenerationsRan++;

		Individual bestIndividual = genenticAlgorithm.getBestIndividual();

		bestIndividual.save(new File("bestAgent.properties"));
	}

	public void pause() {
		genenticAlgorithm.pause();
	}

	public void unPause() {
		genenticAlgorithm.unPause();
	}

	public int getNumberOfGenerationsRan() {
		return numberOfGenerationsRan;
	}

	public void saveAll(File directory) {
		if (directory.isDirectory()) {
			File newDirectory = new File(directory.getPath() + "/"
					+ numberOfGenerationsRan);

			newDirectory.mkdir();

			int index = 0;
			for (Individual individual : genenticAlgorithm.getPopulation()) {
				individual.save(new File(newDirectory.getPath() + "/Agent"
						+ index + ".properties"));

				index++;
			}

			createPopulationInformationFile(newDirectory);
		}
	}

	public void load(File file) {
		if (file.isDirectory()) {
			List<Individual> population = new ArrayList<Individual>();

			for (File agentFile : file.listFiles()) {
				if (agentFile.isFile()
						&& agentFile.getName().endsWith(".properties")) {
					if (agentFile.getName().equals(POPULATION_INFO_FILE_NAME)) {
						Properties populationInformation = readProperty(agentFile);

						String generationString = populationInformation
								.getProperty(GENERATION_COUNT);

						numberOfGenerationsRan = Integer
								.parseInt(generationString);
					} else {
						Chromosome loadedChromosome = new BotcatChromosome(
								agentFile);
						Individual individual = new Individual(loadedChromosome);
						population.add(individual);
					}
				}
			}

			genenticAlgorithm.setPopulation(population);
		}
	}

	// #endregion

	// --------------------------------------------------------------------------
	// #region Private Members
	// --------------------------------------------------------------------------

	private void createPopulationInformationFile(File directory) {
		File populationInformationFile = new File(directory.getPath() + "/"
				+ POPULATION_INFO_FILE_NAME);

		Properties populationInformation = new Properties();

		populationInformation.setProperty(GENERATION_COUNT,
				numberOfGenerationsRan + "");

		try {
			OutputStream outputStream = new FileOutputStream(
					populationInformationFile, false);

			populationInformation.store(outputStream, null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Properties readProperty(File agentFile) {
		Properties properties = new Properties();

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(agentFile);
			properties.load(fileInputStream);

			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return properties;
	}

	// #endregion
}
