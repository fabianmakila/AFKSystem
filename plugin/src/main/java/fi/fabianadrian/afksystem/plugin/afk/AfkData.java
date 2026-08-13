package fi.fabianadrian.afksystem.plugin.afk;

public final class AfkData {
	private long idleStartNanos;
	private boolean warned = false;
	private boolean afk = false;

	public AfkData() {
		this.idleStartNanos = System.nanoTime();
	}

	public void markAsActive() {
		this.idleStartNanos = System.nanoTime();
		this.warned = false;
		this.afk = false;
	}

	public void markAsAfk() {
		this.afk = true;
	}

	public void markAsWarned() {
		this.warned = true;
	}

	public long idleStartNanos() {
		return this.idleStartNanos;
	}

	public long idleDurationNanos() {
		return System.nanoTime() - this.idleStartNanos;
	}

	public boolean warned() {
		return this.warned;
	}

	public boolean afk() {
		return this.afk;
	}
}
