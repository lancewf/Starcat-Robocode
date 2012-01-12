package org.robocode

import scala.actors.Actor

class BotCatInternal extends Actor {
  private var currentMovement = RobotAction.DONT_MOVE
  private var currentCommandCount = 1
  private var tanks = new scala.collection.mutable.ListBuffer[Tank]()
  private var starCatRunner:StartCatControler = null
 
  def act() {
    loop {
      react {
        case (botCat:BotCatable) => {
          starCatRunner = new StartCatControler(botCat)
          starCatRunner.start()
        }
        case "getTanks" => {
          reply(tanks.toList)
        }
        case newTank: Tank => {
          val sameTank = findTank(newTank);

          if (sameTank != null) {
            sameTank.update(newTank);
          } else {
            tanks += newTank
          }
        }
        case "currentMovent" => {
          reply((currentMovement, currentCommandCount))
        }
        case movement: RobotAction => {
          if (currentMovement != movement) {
            currentMovement = movement
            currentCommandCount = 1
          } else {
            currentCommandCount += 1
          }
        }
        case "exit" =>
          starCatRunner.stop()
          exit()
        case _ => //do nothing
      }
    }
  }
  
  private def findTank(lookingTank: Tank): Tank = {
    return findTank(lookingTank.getName());
  }

  private def findTank(tankName: String): Tank = {
    for (tank <- tanks) {
      if (tank.getName().equals(tankName)) {
        return tank;
      }
    }
    return null;
  }
}