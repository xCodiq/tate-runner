package com.xcodiq.taterunner.manager.implementation;

import com.google.gson.Gson;
import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.profile.Profile;

import java.io.*;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public final class ProfileManager extends Manager {
	private static final String STORAGE_PATH = "/TateRunner/profiles/";
	private static final File STORAGE_FOLDER;

	static {
		try {
			final URI uri = ProfileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			final String path = uri.resolve(".").getPath();

			STORAGE_FOLDER = new File(path, STORAGE_PATH);
		} catch (URISyntaxException e) {
			System.exit(0);
			throw new RuntimeException(e);
		}
	}

	private final Gson gson;

	private File profileFile;
	private Profile profile;

	public ProfileManager(TateRunnerGame tateRunnerGame) {
		super(tateRunnerGame);

		// Initialize a new gson instance
		this.gson = new Gson();

		// Check if the storage folder exists
		final boolean exists = STORAGE_FOLDER.exists();
		if (!exists) STORAGE_FOLDER.mkdirs();
	}

	@Override
	public void enable() {
		if (this.profile != null) return;

		try {
			final String name = InetAddress.getLocalHost().getHostName();

			this.profileFile = new File(STORAGE_FOLDER, name + ".json");
			this.profile = upsertProfileFromDisk(name);

			Logger.debug("[ProfileManager] Loaded profile: " + this.profile.getName());
		} catch (UnknownHostException e) {
			Logger.error("[ProfileManager] Failed to load profile: " + e.getMessage());
		}
	}

	@Override
	public void disable() {
		if (profile == null) return;

		this.saveProfile();
		Logger.debug("[ProfileManager] Saved profile changes for: " + this.profile.getName());
	}

	private Profile upsertProfileFromDisk(String name) {
		try (Reader reader = new FileReader(this.profileFile)) {
			return gson.fromJson(reader, Profile.class);
		} catch (IOException e) {
			final Profile newProfile = new Profile(name);
			this.saveProfile(newProfile);
			return newProfile;
		}
	}

	public void saveProfile(Profile profile) {
		if (profile == null) return;

		try (Writer writer = new FileWriter(this.profileFile)) {
			gson.toJson(profile, writer);
		} catch (IOException e1) {
			Logger.error("[ProfileManager] Failed to create new profile: " + e1.getMessage());
			throw new IllegalStateException(e1);
		}
	}

	public void saveProfile() {
		this.saveProfile(this.profile);
	}

	public void clearProfile() {
		this.profile = new Profile(this.profile.getName());
		this.saveProfile();
	}

	public Profile getProfile() {
		return profile;
	}
}
