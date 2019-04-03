package fr.cactuscata.pcmanticheat.cheats;

public enum CheatType {

	AUTO_CLICK_RIGHT("Auto click (droit)"),
	AUTO_CLICK_LEFT("Auto click (gauche)"),
	SPRINT("Sprint"),
	FLY("Fly"),
	REACH("Reach"),
	FORCEFIELD("ForceField"),
	SPEED_HACK("Speed hack"),
	NO_FALL("No Fall"),
	FAST_HEAL("Fast heal"),
	FAST_FOOD("Fast food"),
	FAST_BOW("Fast bow"),
	AIMBOT("Aimbot"),
	FAST_PLACE("Fast place"),
	FAST_BREAK("Fast break"),
	ANTI_KNOCKBACK("Anti knockback"),
	CRITICALS("Criticals"),
	JESUS("Jesus"),
	HIT_BOX("HitBox"),
	SNEAK("Sneak");

	private final String cheatName;

	private CheatType(final String cheatName) {
		this.cheatName = cheatName;
	}

	public final String getCheatName() {
		return this.cheatName;
	}

}
