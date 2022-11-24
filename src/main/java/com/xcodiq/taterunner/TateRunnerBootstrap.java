package com.xcodiq.taterunner;

public final class TateRunnerBootstrap {

	public static void main(String[] args) {
		try {
			// Initialize the game and call the start game function
			final TateRunnerGame tateRunner = new TateRunnerGame();
			tateRunner.startGame(args);

			// Add a shutdown hook to the runtime
			Runtime.getRuntime().addShutdownHook(new Thread(tateRunner::stopGame));
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
}
