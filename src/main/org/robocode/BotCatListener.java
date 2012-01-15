package org.robocode;

import org.robocode.genenticalgorithm.fitnesstest.FitnessTest;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattlePausedEvent;
import robocode.control.events.BattleResumedEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.IBattleListener;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.TurnStartedEvent;

public class BotCatListener implements IBattleListener {
	// --------------------------------------------------------------------------
	// Private Data
	// --------------------------------------------------------------------------

	private boolean isBattleDone = true;
	private RobocodeEngine engine;
	private BattleResults endResult;

	// --------------------------------------------------------------------------
	// Public Members
	// --------------------------------------------------------------------------

	public BattleResults getRobotResults() {
		return endResult;
	}

	public void runBattle(BattleSpecification battle) {
		if (isBattleDone) {
			endResult = null;
			isBattleDone = false;
			engine.setVisible(true);
			engine.runBattle(battle);
			waitForTest();
		}
	}

	public void setEngine(RobocodeEngine engine) {
		this.engine = engine;
	}

	public void dispose() {
		engine = null;
		endResult = null;
	}

	// --------------------------------------------------------------------------
	// Private Members
	// --------------------------------------------------------------------------

	private void waitForTest() {
		while (!isBattleDone) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// --------------------------------------------------------------------------
	// RobocodeListener Members
	// --------------------------------------------------------------------------

	@Override
	public void onBattleStarted(BattleStartedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleFinished(BattleFinishedEvent event) {
		isBattleDone = true;
	}

	@Override
	public void onBattleCompleted(BattleCompletedEvent event) {

		endResult = null;
		for (BattleResults result : event.getSortedResults()) {
			if (result.getTeamLeaderName().equals(FitnessTest.BOTCAT_NAME + "*")) {
				endResult = result;
			}
		}
		isBattleDone = true;
	}

	@Override
	public void onBattlePaused(BattlePausedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleResumed(BattleResumedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoundStarted(RoundStartedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoundEnded(RoundEndedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTurnStarted(TurnStartedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTurnEnded(TurnEndedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleMessage(BattleMessageEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleError(BattleErrorEvent event) {
		// TODO Auto-generated method stub

	}
}
