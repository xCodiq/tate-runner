package com.xcodiq.taterunner.manager.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.Entity;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.screen.TateGameScreen;

import java.util.ArrayList;
import java.util.List;

public final class EnemyManager extends Manager {

	private boolean isHurting = false;
	private final List<Entity> enemies = new ArrayList<>();

	public EnemyManager(TateRunnerGame tateRunnerGame) {
		super(tateRunnerGame);
	}

	@Override
	public void enable() {
		// Call the reset method
		this.reset();
	}

	@Override
	public void disable() {
		// Call the reset method
		this.reset();
	}

	public void reset() {
		// Clear all the remaining enemies from the game
		this.enemies.clear();

		// Set the hurting boolean back to false
		this.isHurting = false;
	}

	public void renderAll(TateGameScreen tateGameScreen) {
		for (Entity enemy : this.enemies) {
			enemy.render(tateGameScreen);
		}
	}

	public void updateAll(TateGameScreen tateGameScreen, Entity entity, double newX) {
		//todo

		// Make sure they don't get hurt again if they are still hitting an enemy
		this.isHurting = this.isColliding(entity);
	}

	public boolean isColliding(Entity entity) {
		return this.enemies.stream().anyMatch(entity::collidesWith);
	}
}
