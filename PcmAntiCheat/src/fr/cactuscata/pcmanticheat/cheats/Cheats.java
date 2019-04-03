package fr.cactuscata.pcmanticheat.cheats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.mojang.authlib.GameProfile;

import fr.cactuscata.pcmanticheat.cheats.list.Aimbot;
import fr.cactuscata.pcmanticheat.cheats.list.AntiKnockBack;
import fr.cactuscata.pcmanticheat.cheats.list.AutoLeftClick;
import fr.cactuscata.pcmanticheat.cheats.list.AutoRightClick;
import fr.cactuscata.pcmanticheat.cheats.list.FastBow;
import fr.cactuscata.pcmanticheat.cheats.list.Fly;
import fr.cactuscata.pcmanticheat.cheats.list.ForceField;
import fr.cactuscata.pcmanticheat.cheats.list.HitBox;
import fr.cactuscata.pcmanticheat.cheats.list.Reach;
import fr.cactuscata.pcmanticheat.cheats.list.Sneack;
import fr.cactuscata.pcmanticheat.cheats.list.SpeedHack;
import fr.cactuscata.pcmanticheat.cheats.list.Sprint;
import fr.cactuscata.pcmevent.utils.PlayerPcm;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class Cheats {

	private final PlayerPcm playerPcm;
	private static final Map<Player, Cheats> players = new HashMap<>();
	private final List<ICheat> cheats = new ArrayList<>();
	private static final List<String> cheatersList = new ArrayList<>();
	private final AutoRightClick autoRightClick;
	private final AutoLeftClick autoLeftClick;
	private final AntiKnockBack antiKockBack;
	private final FastBow fastBow;
	private final Fly fly;
	private final ForceField forceField;
	private final HitBox hitBox;
	private final Reach reach;
	private final Sneack sneack;
	private final Sprint sprint;
	private final SpeedHack speedhack;
	private final Aimbot aimbot;

	private final List<String> loreTotalAlert = new ArrayList<>();
	private int numberTotalAlert;
	private EntityPlayer bot;
	private Location botloc;
	private boolean check;
	public int botlevel;
	private int checktask;
	private int checktimer = 1;

	public Cheats(final PlayerPcm playerPcm) {
		this.playerPcm = playerPcm;
		players.put(this.playerPcm.getPlayer(), this);
		this.autoRightClick = new AutoRightClick();
		this.autoLeftClick = new AutoLeftClick();
		this.antiKockBack = new AntiKnockBack();
		this.fastBow = new FastBow();
		this.fly = new Fly();
		this.forceField = new ForceField();
		this.hitBox = new HitBox();
		this.reach = new Reach();
		this.sneack = new Sneack();
		this.sprint = new Sprint();
		this.speedhack = new SpeedHack();
		this.aimbot = new Aimbot();
		for (final Object object : this.getClass().getFields())
			if (object instanceof ICheat)
				this.cheats.add((ICheat) object);

	}

	public AutoRightClick getAutoRightClick() {
		return autoRightClick;
	}

	public AutoLeftClick getAutoLeftClick() {
		return autoLeftClick;
	}

	public AntiKnockBack getAntiKockBack() {
		return antiKockBack;
	}

	public FastBow getFastBow() {
		return fastBow;
	}

	public Fly getFly() {
		return fly;
	}

	public ForceField getForceField() {
		return forceField;
	}

	public HitBox getHitBox() {
		return hitBox;
	}

	public Reach getReach() {
		return reach;
	}

	public Sneack getSneack() {
		return sneack;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public List<ICheat> getCheats() {
		return cheats;
	}

	public final int getNumberTotalAlert() {
		updateNumberTotalAlert();
		return this.numberTotalAlert;
	}

	public final void updateNumberTotalAlert() {
		this.numberTotalAlert = 0;
		for (ICheat cheats : this.getCheats())
			this.numberTotalAlert += cheats.getAlertNumber();

	}

	public final List<String> getTotalLore() {
		updateTotalLore();
		return this.loreTotalAlert;
	}

	private void updateTotalLore() {
		List<String> allLore = new ArrayList<>();
		for (ICheat cheats : this.getCheats())
			for (String warn : cheats.getWarnList())
				allLore.add(warn);

	}

	public final static boolean isAlreadyRegisterCheater(Player player) {
		return Cheats.cheatersList.contains(player);
	}

	public final static void registerNewCheater(String playerName) {
		Cheats.cheatersList.add(playerName);
	}

	public final static void unregisterCheater(String playerName) {
		Cheats.cheatersList.remove(playerName);
	}

	public final static List<String> getCheaters() {
		return Cheats.cheatersList;
	}

	public EntityPlayer getBot() {
		return this.bot;
	}

	public void startCheck(Plugin plugin) {
		this.check = true;

		spawnBot();

		this.checktask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				Cheats.this.checktimer -= 1;
				if (Cheats.this.checktimer == 0) {
					Bukkit.getScheduler().cancelTask(Cheats.this.checktask);

					Cheats.this.removeBot();

					Cheats.this.check = false;

					Cheats.this.botlevel = 0;

					Cheats.this.checktimer = 1;

					Cheats.this.getAimbot().resetAimbotLevel();

					Cheats.this.getForceField().resetForceFieldLevel();
				}
			}
		}, 20L, 21L);
	}

	public void spawnBot() {
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) this.playerPcm.getPlayer().getWorld()).getHandle();

		this.bot = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UUID.randomUUID(), generate(10)),
				new PlayerInteractManager(nmsWorld));

		this.botloc = this.playerPcm.getPlayer().getLocation()
				.add(this.playerPcm.getPlayer().getLocation().getDirection().multiply(-2));

		this.bot.setLocation(this.botloc.getX(), this.botloc.getY(), this.botloc.getZ(), this.botloc.getYaw(),
				this.botloc.getPitch());

		this.bot.setInvisible(false);

		PlayerConnection connection = ((CraftPlayer) this.playerPcm.getPlayer()).getHandle().playerConnection;

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
				new EntityPlayer[] { this.bot }));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.bot));
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
				new EntityPlayer[] { this.bot }));
	}

	public void removeBot() {
		PlayerConnection connection = ((CraftPlayer) this.playerPcm.getPlayer()).getHandle().playerConnection;

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
				new EntityPlayer[] { this.bot }));
		connection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { this.bot.getId() }));
	}

	public void updateBot() {
		PlayerConnection connection = ((CraftPlayer) this.playerPcm.getPlayer()).getHandle().playerConnection;

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
				new EntityPlayer[] { this.bot }));
		connection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { this.bot.getId() }));
		if (this.botlevel == 1) {
			this.botloc = this.playerPcm.getPlayer().getLocation().add(0.0D, -1.5D, -1.0D);
		}
		if (this.botlevel == 2) {
			this.botloc = this.playerPcm.getPlayer().getLocation().add(0.0D, 2.0D, 1.0D);
		}
		if (this.botlevel == 3) {
			this.botloc = this.playerPcm.getPlayer().getLocation().add(-1.0D, 2.0D, 1.0D);
		}
		if (this.botlevel == 4) {
			this.botloc = this.playerPcm.getPlayer().getLocation().add(1.0D, -1.5D, 0.0D);
		}
		if (this.botlevel == 5) {
			this.botloc = this.playerPcm.getPlayer().getLocation().add(0.0D, 2.0D, 0.0D);
		}
		if (this.botlevel == 6) {
			this.botloc = this.playerPcm.getPlayer().getLocation().add(-1.0D, -1.5D, -1.0D);
		}
		if (this.botlevel == 7) {
			this.botloc = this.playerPcm.getPlayer().getLocation().add(1.0D, -1.5D, 1.0D);
		}
		this.bot.setLocation(this.botloc.getX(), this.botloc.getY(), this.botloc.getZ(), this.botloc.getYaw(),
				this.botloc.getPitch());

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
				new EntityPlayer[] { this.bot }));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.bot));
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
				new EntityPlayer[] { this.bot }));
	}

	public void stopCheck() {
		if (Bukkit.getScheduler().isCurrentlyRunning(this.checktask)) {
			Bukkit.getScheduler().cancelTask(this.checktask);
		}
		removeBot();

		this.check = false;

		this.botlevel = 0;

		this.checktimer = 1;

		this.getAimbot().resetAimbotLevel();

		this.getForceField().resetForceFieldLevel();
	}

	public boolean isCheck() {
		return this.check;
	}

	public boolean isInHitBox(Player damaged) {
		double offset = 0.0D;

		Location entityLoc = damaged.getLocation().add(0.0D, damaged.getEyeHeight(), 0.0D);
		Location playerLoc = this.playerPcm.getPlayer().getLocation().add(0.0D,
				this.playerPcm.getPlayer().getEyeHeight(), 0.0D);

		Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0F);
		Vector expectedRotation = getRotation(playerLoc, entityLoc);

		double deltaYaw = clamp180(playerRotation.getX() - expectedRotation.getX());
		double deltaPitch = clamp180(playerRotation.getY() - expectedRotation.getY());

		double horizontalDistance = getHorizontalDistance(playerLoc, entityLoc);
		double distance = getDistance3D(playerLoc, entityLoc);

		double offsetX = deltaYaw * horizontalDistance * distance;
		double offsetY = deltaPitch * Math.abs(entityLoc.getY() - playerLoc.getY()) * distance;

		offset += Math.abs(offsetX);
		offset += Math.abs(offsetY);
		if (offset > 125.0D) {
			return false;
		}
		return true;
	}

	private Vector getRotation(Location one, Location two) {
		double dx = two.getX() - one.getX();
		double dy = two.getY() - one.getY();
		double dz = two.getZ() - one.getZ();
		double distanceXZ = Math.sqrt(dx * dx + dz * dz);
		float yaw = (float) (Math.atan2(dz, dx) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(dy, distanceXZ) * 180.0D / 3.141592653589793D);
		return new Vector(yaw, pitch, 0.0F);
	}

	private double clamp180(double theta) {
		theta %= 360.0D;
		if (theta >= 180.0D) {
			theta -= 360.0D;
		}
		if (theta < -180.0D) {
			theta += 360.0D;
		}
		return theta;
	}

	private double getHorizontalDistance(Location one, Location two) {
		double toReturn = 0.0D;
		double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
		double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
		double sqrt = Math.sqrt(xSqr + zSqr);
		toReturn = Math.abs(sqrt);
		return toReturn;
	}

	private double getDistance3D(Location one, Location two) {
		double toReturn = 0.0D;
		double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
		double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
		double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
		double sqrt = Math.sqrt(xSqr + ySqr + zSqr);
		toReturn = Math.abs(sqrt);
		return toReturn;
	}

	public final String generate(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuffer pass = new StringBuffer();
		for (int x = 0; x < length; x++) {
			int i = (int) Math.floor(Math.random() * (chars.length() - 1));
			pass.append(chars.charAt(i));
		}
		return pass.toString();
	}

	public Aimbot getAimbot() {
		return aimbot;
	}

	public SpeedHack getSpeedhack() {
		return speedhack;
	}

}
