package com.xcodiq.taterunner.util.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AnimatedText {

	private final int x, y;
	private final List<String> frames = new ArrayList<>();

	public AnimatedText(int x, int y, String... frames) {
		this.x = x;
		this.y = y;

		this.frames.addAll(List.of(frames));
	}

	public List<String> getFrames() {
		return frames;
	}

	public String getFrame(int index) {
		try {
			return this.frames.get(index);
		} catch (IndexOutOfBoundsException exception) {
			throw new RuntimeException(exception.getCause());
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnimatedText that = (AnimatedText) o;
		return x == that.x && y == that.y && Objects.equals(frames, that.frames);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, frames);
	}
}
