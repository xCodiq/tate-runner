package com.xcodiq.taterunner.logger;

import com.xcodiq.taterunner.TateRunnerGame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static void log(String message) {
		System.out.printf("[%s] [tate-runner] [INFO] %s%n", DATE_FORMAT.format(new Date()), message);
	}

	public static void debug(String message) {
		if (!TateRunnerGame.DEBUG_MODE) return;
		System.out.printf("[%s] [tate-runner] [DEBUG] %s%n", DATE_FORMAT.format(new Date()), message);
	}
}
