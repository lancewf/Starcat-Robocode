package org.starcat.configuration;

import org.starcat.core.Component;
import org.starcat.slipnet.Slipnet;
import org.starcat.workspace.Workspace;
import org.starcat.coderack.Coderack;



/**
 *  We include this class to provide centralized access to
 *  all the magic numbers in the code. There is a commitment
 *  (unavoidable) to some of the design decisions in the core
 *  here (for example the data here are specific to the 
 *  RegularPulse). This kind of commitment is ultimately
 *  going to show up somewhere, better to have it in as few
 *  places as possible. 
 *
 */
public class ParameterData {

  //Defaults provided here but possibly overridden by initialize methods
	
  // Adaptive Execute variables and methods
  private static boolean workspaceBehaviorAdaptiveExecute = false;
  private static boolean workspaceControlAdaptiveExecute = false;
  private static boolean coderackBehaviorAdaptiveExecute = false;
  private static boolean coderackControlAdaptiveExecute = false;
  private static boolean slipnetBehaviorAdaptiveExecute = false;
  private static boolean slipnetControlAdaptiveExecute = false;
  
  public static boolean getBehaviorAdaptiveExecute(Component c)
  {
	  boolean adaptiveExecute;
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  adaptiveExecute = slipnetBehaviorAdaptiveExecute;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  adaptiveExecute = workspaceBehaviorAdaptiveExecute;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  adaptiveExecute = coderackBehaviorAdaptiveExecute;
	  } 
	  else {
		  adaptiveExecute = false;
		  System.out.println("Attempt to use an adaptiveExecute for a nonexistent component");
	  }
	  return adaptiveExecute;
  }
  
  public static boolean getControlAdaptiveExecute(Component c){
	  boolean adaptiveExecute;	  
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  adaptiveExecute = slipnetControlAdaptiveExecute;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  adaptiveExecute = workspaceControlAdaptiveExecute;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  adaptiveExecute = coderackControlAdaptiveExecute;
	  }
	  else {
		  adaptiveExecute = false;
		  System.out.println("Attempt to use an adaptiveExecute for a nonexistent component");
	  }
	  return adaptiveExecute;
  }

  public static void initializeSlipnetBehaviorAdaptiveExecute(boolean adaptiveExecute) {
	  slipnetBehaviorAdaptiveExecute = adaptiveExecute;
  }

  public static void initializeCoderackBehaviorAdaptiveExecute(boolean adaptiveExecute) {
	  coderackBehaviorAdaptiveExecute = adaptiveExecute;
  }
  
  public static void initializeWorkspaceBehaviorAdaptiveExecute(boolean adaptiveExecute) {
	  workspaceBehaviorAdaptiveExecute = adaptiveExecute;
  }
  
  public static void initializeSlipnetControlAdaptiveExecute(boolean adaptiveExecute) {
	  slipnetControlAdaptiveExecute = adaptiveExecute;
  }

  public static void initializeCoderackControlAdaptiveExecute(boolean adaptiveExecute) {
	  coderackControlAdaptiveExecute = adaptiveExecute;
  }
	  
  public static void initializeWorkspaceControlAdaptiveExecute(boolean adaptiveExecute) {
	  workspaceControlAdaptiveExecute = adaptiveExecute;
  }    
  
  // Execute Factor variables and methods
  private static int workspaceBehaviorExecuteFactor = 10;
  private static int workspaceControlExecuteFactor = 1;
  private static int coderackBehaviorExecuteFactor = 10;
  private static int coderackControlExecuteFactor = 1;
  private static int slipnetBehaviorExecuteFactor = 10;
  private static int slipnetControlExecuteFactor = 1;
  
  public static int getBehaviorExecuteFactor(Component c)
  {
	  int executeFactor;
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  executeFactor = slipnetBehaviorExecuteFactor;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  executeFactor = workspaceBehaviorExecuteFactor;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  executeFactor = coderackBehaviorExecuteFactor;
	  }
	  else {
		  executeFactor = 0;
		  System.out.println("Attempt to use an BehaviorExecuteFactor for a nonexistent component");
	  }
	  return executeFactor;
  }
  
  public static int getControlExecuteFactor(Component c){
	  int executeFactor;	  
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  executeFactor = slipnetControlExecuteFactor;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  executeFactor = workspaceControlExecuteFactor;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  executeFactor = coderackControlExecuteFactor;
	  }
	  else {
		  executeFactor = 0;
		  System.out.println("Attempt to use an executeFactor for a nonexistent component");
	  }
	  return executeFactor;
  }

  //executeFactor is one of those rare data items that gets changed by the running code
  public static void setBehaviorExecuteFactor(Component c, int execFactor)
  {
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  slipnetBehaviorExecuteFactor = execFactor;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  workspaceBehaviorExecuteFactor = execFactor;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  coderackBehaviorExecuteFactor = execFactor;
	  }
	  else {
		  System.out.println("Attempt to set an executeFactor for a nonexistent component");
	  }
  }

  //executeFactor is one of those rare data items that gets changed by the running code
  public static void setControlExecuteFactor(Component c, int execFactor)
  {
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  slipnetControlExecuteFactor = execFactor;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  workspaceControlExecuteFactor = execFactor;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  coderackControlExecuteFactor = execFactor;
	  }
	  else {
		  System.out.println("Attempt to set an executeFactor for a nonexistent component");
	  }
  }  
  
  public static void initializeSlipnetBehaviorExecuteFactor(int execFactor) {
	  slipnetBehaviorExecuteFactor = execFactor;
  }

  public static void initializeCoderackBehaviorExecuteFactor(int execFactor) {
	  coderackBehaviorExecuteFactor = execFactor;
  }
  
  public static void initializeWorkspaceBehaviorExecuteFactor(int execFactor) {
	  workspaceBehaviorExecuteFactor = execFactor;
  }
  
  public static void initializeSlipnetControlExecuteFactor(int execFactor) {
	  slipnetControlExecuteFactor = execFactor;
  }

  public static void initializeCoderackControlExecuteFactor(int execFactor) {
	  coderackControlExecuteFactor = execFactor;
  }
	  
  public static void initializeWorkspaceControlExecuteFactor(int execFactor) {
	  workspaceControlExecuteFactor = execFactor;
  }  
  
  // Reduction Factor variables and methods
  private static double workspaceBehaviorReductionFactor = 0.01;
  private static double workspaceControlReductionFactor = 0.01;
  private static double coderackBehaviorReductionFactor = 0.01;
  private static double coderackControlReductionFactor = 0.01;
  private static double slipnetBehaviorReductionFactor = 0.01;
  private static double slipnetControlReductionFactor = 0.01;
  
  public static double getBehaviorReductionFactor(Component c)
  {
	  double reductionFactor;
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  reductionFactor = slipnetBehaviorReductionFactor;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  reductionFactor = workspaceBehaviorReductionFactor;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  reductionFactor = coderackBehaviorReductionFactor;
	  }
	  else {
		  reductionFactor = 0;
		  System.out.println("Attempt to use an ReductionFactor for a nonexistent component");
	  }
	  return reductionFactor;
  }
  
  public static double getControlReductionFactor(Component c){
	  double reductionFactor;	  
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  reductionFactor = slipnetControlReductionFactor;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  reductionFactor = workspaceControlReductionFactor;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  reductionFactor = coderackControlReductionFactor;
	  }
	  else {
		  reductionFactor = 0;
		  System.out.println("Attempt to use an ReductionFactor for a nonexistent component");
	  }
	  return reductionFactor;
  }

  public static void initializeSlipnetBehaviorReductionFactor(double reductionFactor) {
	  slipnetBehaviorReductionFactor = reductionFactor;
  }

  public static void initializeCoderackBehaviorReductionFactor(double reductionFactor) {
	  coderackBehaviorReductionFactor = reductionFactor;
  }
  
  public static void initializeWorkspaceBehaviorReductionFactor(double reductionFactor) {
	  workspaceBehaviorReductionFactor = reductionFactor;
  }
  
  public static void initializeSlipnetControlReductionFactor(double reductionFactor) {
	  slipnetControlReductionFactor = reductionFactor;
  }

  public static void initializeCoderackControlReductionFactor(double reductionFactor) {
	  coderackControlReductionFactor = reductionFactor;
  }
	  
  public static void initializeWorkspaceControlReductionFactor(double reductionFactor) {
	  workspaceControlReductionFactor = reductionFactor;
  }    
    
  // sleeper variables and methods
  private static boolean workspaceBehaviorSleeper = true;
  private static boolean workspaceControlSleeper = true;
  private static boolean coderackBehaviorSleeper = true;
  private static boolean coderackControlSleeper = true;
  private static boolean slipnetBehaviorSleeper = true;
  private static boolean slipnetControlSleeper = true;
  
  public static boolean getBehaviorSleeper(Component c)
  {
	  boolean sleeper;
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  sleeper = slipnetBehaviorSleeper;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  sleeper = workspaceBehaviorSleeper;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  sleeper = coderackBehaviorSleeper;
	  }
	  else {
		  sleeper = false;
		  System.out.println("Attempt to use an Sleeper for a nonexistent component");
	  }
	  return sleeper;
  }
  
  public static boolean getControlSleeper(Component c){
	  boolean sleeper;	  
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  sleeper = slipnetControlSleeper;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  sleeper = workspaceControlSleeper;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  sleeper = coderackControlSleeper;
	  }
	  else {
		  sleeper = false;
		  System.out.println("Attempt to use an Sleeper for a nonexistent component");
	  }
	  return sleeper;
  }

  public static void initializeSlipnetBehaviorSleeper(boolean sleeper) {
	  slipnetBehaviorSleeper = sleeper;
  }

  public static void initializeCoderackBehaviorSleeper(boolean sleeper) {
	  coderackBehaviorSleeper = sleeper;
  }
  
  public static void initializeWorkspaceBehaviorSleeper(boolean sleeper) {
	  workspaceBehaviorSleeper = sleeper;
  }
  
  public static void initializeSlipnetControlSleeper(boolean sleeper) {
	  slipnetControlSleeper = sleeper;
  }

  public static void initializeCoderackControlSleeper(boolean sleeper) {
	  coderackControlSleeper = sleeper;
  }
	  
  public static void initializeWorkspaceControlSleeper(boolean sleeper) {
	  workspaceControlSleeper = sleeper;
  }  
  
  // Sleep Time variables and methods
  private static long workspaceBehaviorSleepTime = 10;
  private static long workspaceControlSleepTime = 10;
  private static long coderackBehaviorSleepTime = 10;
  private static long coderackControlSleepTime = 10;
  private static long slipnetBehaviorSleepTime = 100;
  private static long slipnetControlSleepTime = 100;
  
  public static long getBehaviorSleepTime(Component c)
  {
	  long sleepTime;
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  sleepTime = slipnetBehaviorSleepTime;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  sleepTime = workspaceBehaviorSleepTime;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  sleepTime = coderackBehaviorSleepTime;
	  }
	  else {
		  sleepTime = 10;
		  System.out.println("Attempt to use an SleepTime for a nonexistent component");
	  }
	  return sleepTime;
  }
  
  public static long getControlSleepTime(Component c){
	  long sleepTime;	  
	  if (Slipnet.class.isAssignableFrom(c.getClass())){
		  sleepTime = slipnetControlSleepTime;
	  }
	  else if (Workspace.class.isAssignableFrom(c.getClass())){
		  sleepTime = workspaceControlSleepTime;
	  }
	  else if (Coderack.class.isAssignableFrom(c.getClass())){
		  sleepTime = coderackControlSleepTime;
	  }
	  else {
		  sleepTime = 10;
		  System.out.println("Attempt to use an SleepTime for a nonexistent component");
	  }
	  return sleepTime;
  }

  public static void initializeSlipnetBehaviorSleepTime(long sleepTime) {
	  slipnetBehaviorSleepTime = sleepTime;
  }

  public static void initializeCoderackBehaviorSleepTime(long sleepTime) {
	  coderackBehaviorSleepTime = sleepTime;
  }
  
  public static void initializeWorkspaceBehaviorSleepTime(long sleepTime) {
	  workspaceBehaviorSleepTime = sleepTime;
  }
  
  public static void initializeSlipnetControlSleepTime(long sleepTime) {
	  slipnetControlSleepTime = sleepTime;
  }

  public static void initializeCoderackControlSleepTime(long sleepTime) {
	  coderackControlSleepTime = sleepTime;
  }
	  
  public static void initializeWorkspaceControlSleepTime(long sleepTime) {
	  workspaceControlSleepTime = sleepTime;
  }   
  
}
 

