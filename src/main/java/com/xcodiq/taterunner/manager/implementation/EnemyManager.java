package com.xcodiq.taterunner.manager.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.Entity;
import com.xcodiq.taterunner.entity.implementation.Bat;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.screen.TateGameScreen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public final class EnemyManager extends Manager {

	private final Set<Class<? extends Entity>> enemyClassesList = new HashSet<>();
	private final List<Entity> enemies = new ArrayList<>();

	private boolean isHurting = false;

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

	public void add(Entity enemy) {
		this.enemies.add(enemy);
		this.enemyClassesList.add(enemy.getClass());
	}

	public void renderAll(TateGameScreen tateGameScreen) {
		for (Entity enemy : this.enemies) {
			enemy.render(tateGameScreen);
		}
	}

	public void updateAll(double floorYCoordinate, double newX) {
		for (Entity enemy : new ArrayList<>(this.enemies)) {
			enemy.update(newX);

			if (enemy.getX() < -enemy.getWidth()) {
				this.enemies.remove(enemy);
				final int randomSize = ThreadLocalRandom.current().nextInt(75, 125);
				final int randomDistance = ThreadLocalRandom.current().nextInt(200, 450);

				final Class<? extends Entity> entityClass = this.selectRandomEntity();
				final Entity newEnemy = this.createNewEnemy(entityClass,
						TateRunnerGame.GAME_WIDTH + randomDistance, floorYCoordinate, randomSize, randomSize);
				if (newEnemy == null) continue;

				this.add(newEnemy);
			}
		}
	}

	public <T extends Entity> Class<T> selectRandomEntity() {
		final int randomIndex = ThreadLocalRandom.current().nextInt(0, this.enemyClassesList.size());
		return this.enemyClassesList.toArray(Class[]::new)[randomIndex];
	}

	public <T extends Entity> T createNewEnemy(Class<T> entityClass, double x, double y, int width, int height) {
		try {
			int yMod = entityClass.equals(Bat.class) ? (ThreadLocalRandom.current().nextInt(1,4)) : 0;
			int yDiff = 0;
			if (yMod == 1) yDiff = ThreadLocalRandom.current().nextInt(0, 51);
			else if (yMod > 1) yDiff = ThreadLocalRandom.current().nextInt(100, 151);

			return entityClass.getConstructor(double.class, double.class, int.class, int.class)
					.newInstance(x, y - yDiff, width, height);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean hitEnemy(Entity entity) {
		return !this.isHurting && this.isColliding(entity);
	}

	public boolean isColliding(Entity entity) {
		for (Entity enemy : this.enemies) {
			if (entity.collidesWith(enemy)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHurting() {
		return isHurting;
	}

	public void setHurting(boolean hurting) {
		isHurting = hurting;
	}

	public List<Entity> getEnemies() {
		return enemies;
	}
}
