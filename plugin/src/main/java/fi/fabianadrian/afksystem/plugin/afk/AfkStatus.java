package fi.fabianadrian.afksystem.plugin.afk;

public final class AfkStatus {
	private long afkSince;
	private boolean warned = false;
	private boolean afk = false;

	public AfkStatus() {
		this.afkSince = System.nanoTime();
	}

	public void markAsActive() {
		this.afkSince = System.nanoTime();
		this.warned = false;
		this.afk = false;
	}

	public void markAsAfk() {
		this.afk = true;
	}

	public void markAsWarned() {
		this.warned = true;
	}

	public long afkNanos() {
		return System.nanoTime() - this.afkSince;
	}

	public boolean warned() {
		return this.warned;
	}

	public boolean afk() {
		return this.afk;
	}
}
