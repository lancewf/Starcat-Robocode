package org.robocode
import org.robocode.genenticalgorithm.Chromosome

trait BotCatable {
	def setMovement(action:RobotAction);
	def getTanks():java.util.List[Tank]
	def getChromosome():Chromosome
	def getGunHeading():Double
	def getHeading():Double
	def getEnergy():Double
	def getX():Double
	def getY():Double
	def getBattleFieldWidth():Double
	def getBattleFieldHeight():Double
}