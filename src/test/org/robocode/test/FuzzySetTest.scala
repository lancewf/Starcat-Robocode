package org.robocode.test

import org.junit._
import Assert._
import org.robocode.codelets._
import org.robocode.genenticalgorithm.BotcatChromosome
import java.io.FileInputStream
import java.io.File
import scala.collection.JavaConversions._
import java.util.Properties
import java.io.FileOutputStream
import org.robocode.SlipnetView
import org.robocode.genenticalgorithm.Chromosome

@Test
class FuzzySetTest {
  
  @Test
  def testjunk(){
//      val initalAgent = new File("/home/lancewf/robocode/bestAgent.properties")
      val initalAgent = new File("/home/lancewf/Desktop/Handmade.properties")
      
      val initialChromosome:BotcatChromosome = new BotcatChromosome(initalAgent);
    val view = new SlipnetView(initialChromosome)
    
    view.println()
    
//    val outputStream = new FileOutputStream(initalAgent, false);
//    
//    initialChromosome.save(outputStream)
  }
  
  @Test
  def testNorthBodyOrientationObserverCodelet(){
    val codelet = new NorthBodyOrientationObserverCodelet()
    
    codelet.setCrispValue(0)
    assertTrue(codelet.getSuccessMemberValue() == 1)
    
    codelet.setCrispValue(-90)
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    
    codelet.setCrispValue(90)
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(45)
    assertTrue(codelet.getSuccessMemberValue() == 0.5)
    
    codelet.setCrispValue(-45)
    assertTrue(codelet.getSuccessMemberValue() == 0.5)
    
    codelet.setCrispValue(-75)
    
    println("SuccessMemberValue: " + codelet.getSuccessMemberValue())
    
    assertTrue(codelet.getSuccessMemberValue() < 0.5  && codelet.getSuccessMemberValue() > 0)
    
    codelet.setCrispValue(30)
    
    println("SuccessMemberValue: " + codelet.getSuccessMemberValue())
    
    assertTrue(codelet.getSuccessMemberValue() > 0.5  && codelet.getSuccessMemberValue() < 1)
  }
  
  @Test
  def testFuzzyObstacleBehaviorCodelet(){
    val codelet = new TargetObserverBehaviorCodelet(FuzzyObstacleBehaviorCodelet.FORWARD, 100)
    
    codelet.setCrispValue(50)
    
    assertTrue(codelet.getSuccessMemberValue() == 1)
    assertTrue(codelet.getFailureMemberValue() == 0)
    
    codelet.setCrispValue(150)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    assertTrue(codelet.getFailureMemberValue() == 1)
    
    codelet.setCrispValue(100)
    
    assertTrue(codelet.getSuccessMemberValue() == .5)
    assertTrue(codelet.getFailureMemberValue() == .5)
    
    codelet.setCrispValue(5)
    
    assertTrue(codelet.getSuccessMemberValue() == 1)
    assertTrue(codelet.getFailureMemberValue() == 0)
  }
  
  @Test
  def testBulletHitMissBehaviorCodlet(){
    val codelet = new BulletHitMissBehaviorCodlet(2, -2)
    
    codelet.setCrispValue(0)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    assertTrue(codelet.getFailureMemberValue() == 0)
    
    codelet.setCrispValue(2)
    
    assertTrue(codelet.getSuccessMemberValue() == 1)
    assertTrue(codelet.getFailureMemberValue() == 0)
    
    codelet.setCrispValue(-2)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    assertTrue(codelet.getFailureMemberValue() == 1)
    
    codelet.setCrispValue(-3)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    assertTrue(Math.round(codelet.getFailureMemberValue()) == 1)
    
    codelet.setCrispValue(-1)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    assertTrue(codelet.getFailureMemberValue() > 0 && codelet.getFailureMemberValue() < 1)
    
    codelet.setCrispValue(1)
    
    assertTrue(codelet.getSuccessMemberValue() > 0 && codelet.getSuccessMemberValue() < 1)
    assertTrue(codelet.getFailureMemberValue() == 0)
  }
  
  @Test
  def testSouthBodyOrientationObserver(){
    val codelet = new SouthBodyOrientationObserverCodelet()
    
    codelet.setCrispValue(70)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(275)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(180)
    
    assertTrue(codelet.getSuccessMemberValue() == 1)
    
    codelet.setCrispValue(135)
    
    assertTrue(codelet.getSuccessMemberValue() > 0 && codelet.getSuccessMemberValue() < 1)
    
    codelet.setCrispValue(225)
    
    assertTrue(codelet.getSuccessMemberValue() > 0 && codelet.getSuccessMemberValue() < 1)
  }
  
  @Test
  def testRightTurretOrientationObserver(){
    val codelet = new RightTurretOrientationObserverCodelet()
    
    codelet.setCrispValue(0)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(180)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(200)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(300)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(359)
    
    assertTrue(codelet.getSuccessMemberValue() == 0)
    
    codelet.setCrispValue(90)
    
    assertTrue(codelet.getSuccessMemberValue() == 1)
    
    codelet.setCrispValue(45)
    
    assertTrue(codelet.getSuccessMemberValue() > 0 && codelet.getSuccessMemberValue() < 1)
    
    codelet.setCrispValue(135)
    
    assertTrue(codelet.getSuccessMemberValue() > 0 && codelet.getSuccessMemberValue() < 1)
  }
}