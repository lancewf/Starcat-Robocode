package org.robocode

import java.awt.Graphics2D
import robocode._
import java.io.File
import org.robocode.genenticalgorithm.BotcatChromosome;
import org.robocode.genenticalgorithm.Chromosome;

import java.awt.Color
import scala.collection.JavaConversions._

class BotCat extends AdvancedRobot with BotCatable {

  private var isAlive = false
  private val CHROMOSOME_FILE_NAME = "BotCat.properties"
  private lazy val chromosome = createChromosome()
  private lazy val turnAmount= chromosome.getTurnAmount()
  private lazy val fireAmount = chromosome.getFireAmount()
  private lazy val forwardMovementAmount = chromosome.getMovementAmount()
  private lazy val botCatInternal = new BotCatInternal()

  // ---------------------------------------------------------------------------
  // BotCatable Methods
  // ---------------------------------------------------------------------------
  
  def setMovement(movement: RobotAction) {
    botCatInternal ! movement
  }
  
  def getTanks():java.util.List[Tank] = {
     botCatInternal !? "getTanks" match{
       case tanks:List[Tank] =>{
         val arrayListTanks = new java.util.ArrayList[Tank]()
         for(tank <- tanks){
           arrayListTanks.add(tank)
         }
         arrayListTanks
       }
     }
  }
  
  def getChromosome() = chromosome

  // ---------------------------------------------------------------------------
  // AdvancedRobot Methods
  // ---------------------------------------------------------------------------
  
  override def run() {
    applyGeniticFeatures()
    botCatInternal.start()
    botCatInternal ! this

    isAlive = true
    var count: Long = 0
    while (isAlive) {
      if (count == 10000000) {
        move()
        count = 0
      }
      count += 1
    }
  }

  /**
   * onScannedRobot: What to do when you see another robot
   */
  override def onScannedRobot(e: ScannedRobotEvent) {
	val newTank = new Tank(e, getX(), getY(), getHeading())
    botCatInternal ! newTank
  }

  /**
   * This method is called when another robot dies.
   */
  override def onRobotDeath(event: RobotDeathEvent) {}

  override def onPaint(g: Graphics2D) {}

  /**
   * This method is called when your robot collides with a wall.
   *
   * @see robocode.Robot#onHitWall(robocode.HitWallEvent)
   */
  override def onHitWall(event: HitWallEvent) {}

  override def onHitRobot(event: HitRobotEvent) {}

  /**
   * This method is called if your robot wins a battle.
   *
   * @see robocode.Robot#onWin(robocode.WinEvent)
   */
  override def onWin(event: WinEvent) {
    botCatInternal ! "exit"
    isAlive = false;
  }

  override def onDeath(event: DeathEvent) {
    botCatInternal ! "exit"
    isAlive = false;
  }

  // ---------------------------------------------------------------------------
  // Private Methods
  // ---------------------------------------------------------------------------
  
  private def createChromosome():Chromosome ={
    val file = getDataFile(CHROMOSOME_FILE_NAME)
    
    new BotcatChromosome(file)
  }
  
  private def applyGeniticFeatures() {
    val bodyColor = new Color(
      chromosome.getColorValue(Chromosome.BODY_RED),
      chromosome.getColorValue(Chromosome.BODY_GREEN),
      chromosome.getColorValue(Chromosome.BODY_BLUE));

    val turretColor = new Color(
      chromosome.getColorValue(Chromosome.TURRET_RED),
      chromosome.getColorValue(Chromosome.TURRET_GREEN),
      chromosome.getColorValue(Chromosome.TURRET_BLUE));

    val radarColor = new Color(
      chromosome.getColorValue(Chromosome.RADAR_RED),
      chromosome.getColorValue(Chromosome.RADAR_GREEN),
      chromosome.getColorValue(Chromosome.RADAR_BLUE));

    setColors(bodyColor, turretColor, radarColor);
  }

  /**
   * Perform a move on the robotCode battle field
   */
  private def move() {
    val value = botCatInternal !? "currentMovent"
    
     value match {
      case (RobotAction.FORWARD, currentCommandCount:Int) => {
        setAhead(forwardMovementAmount * currentCommandCount);
      }
      case (RobotAction.BACKWARD, currentCommandCount:Int) => {
        setBack(forwardMovementAmount * currentCommandCount);
      }
      case (RobotAction.TURN_LEFT, currentCommandCount:Int) => {
        setTurnLeft(turnAmount * currentCommandCount);
      }
      case (RobotAction.TURN_RIGHT, currentCommandCount:Int) => {
        setTurnRight(turnAmount * currentCommandCount);
      }
      case (RobotAction.TURN_TURRET_LEFT, currentCommandCount:Int) => {
        setTurnGunLeft(turnAmount * currentCommandCount);
      }
      case (RobotAction.TURN_TURRET_RIGHT, currentCommandCount:Int) => {
        setTurnGunRight(turnAmount * currentCommandCount);
      }
      case (RobotAction.FIRE, currentCommandCount:Int) => {
        setFire(fireAmount * currentCommandCount);
      }
      case (RobotAction.DONT_MOVE, currentCommandCount:Int) => {
        //do nothing
      }
    }
    setTurnRadarRight(RobotConstance.ONE_REV);
    execute();
  }
}