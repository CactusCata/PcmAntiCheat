package fr.cactuscata.pcmanticheat.cheats.utils;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.cactuscata.pcmanticheat.cheats.Cheats;
import fr.cactuscata.pcmanticheat.cheats.list.Aimbot;
import fr.cactuscata.pcmanticheat.cheats.list.ForceField;
import fr.cactuscata.pcmanticheat.cheats.list.SpeedHack;
import fr.cactuscata.pcmevent.utils.PlayerPcm;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;

public class PacketListeners extends ChannelDuplexHandler {
	private final Player p;

	public PacketListeners(Player p) {
		this.p = p;
	}

	public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
		super.channelRead(channelHandlerContext, o);
		if (o.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
			PacketPlayInUseEntity packet = (PacketPlayInUseEntity) o;

			int idk = ((Integer) getValue(packet, "a")).intValue();

			PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get(this.p);
			Cheats cheats = playerPcm.getCheats();

			EntityPlayer npc = playerPcm.getCheats().getBot();
			if ((cheats.isCheck()) && (idk == npc.getBukkitEntity().getEntityId())
					&& (getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))) {
				ForceField ff = cheats.getForceField();
				cheats.botlevel++;
				cheats.updateBot();
				ff.incremente();
				if (ff.getForcefieldLevel() >= 10) {
					ff.addWarn(ff.getForcefieldLevel() + "", playerPcm);
					cheats.stopCheck();
				}

			}
		}
		if (o.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInEntityAction")) {
			PacketPlayInEntityAction packet = (PacketPlayInEntityAction) o;
			if ((getValue(packet, "animation").toString().equalsIgnoreCase("START_SNEAKING"))
					&& (this.p.isSprinting())) {
				PlayerPcm.getPlayersPcm().get(this.p).getCheats().getSneack().addWarn(null, PlayerPcm.getPlayersPcm().get(this.p));
			}
		}
		if (o.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInPosition")) {
			PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get(this.p);
			if (!this.p.isFlying()) {
				SpeedHack speedhack = playerPcm.getCheats().getSpeedhack();
				speedhack.incrementeMovementPacket();
				if (speedhack.getMovementPacket() >= 23) {
					speedhack.addWarn(speedhack.getMovementPacket() + "", playerPcm);
				}
			}
		}
		if (o.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInPositionLook")) {
			PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get(this.p);
			Cheats cheats = playerPcm.getCheats();
			if ((cheats.isCheck()) && (cheats.isInHitBox(cheats.getBot().getBukkitEntity()))) {
				cheats.botlevel += 1;
				cheats.updateBot();
				Aimbot aimbot = cheats.getAimbot();
				aimbot.incrementeAimbotLevel();
				if (aimbot.getAimbotLevel() >= 8) {
					aimbot.addWarn(aimbot.getAimbotLevel() + "", playerPcm);
					cheats.stopCheck();
				}
			}
		}
		if (o.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInLook")) {
			PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get(this.p);
			Cheats cheats = playerPcm.getCheats();
			if ((cheats.isCheck()) && (cheats.isInHitBox(cheats.getBot().getBukkitEntity()))) {
				cheats.botlevel += 1;
				cheats.updateBot();
				Aimbot aimbot = cheats.getAimbot();
				aimbot.incrementeAimbotLevel();
				if (aimbot.getAimbotLevel() >= 8) {
					aimbot.addWarn(aimbot.getAimbotLevel() + "", playerPcm);
					cheats.stopCheck();
				}
			}
		}
	}

	public void write(ChannelHandlerContext arg0, Object arg1, ChannelPromise arg2) throws Exception {
		super.write(arg0, arg1, arg2);
	}

	public static void registerPlayer(Player player) {
		try {
			Channel c = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
			if (c.pipeline().get("packet_listener") == null) {
				c.pipeline().addBefore("packet_handler", "packet_listener", new PacketListeners(player));
				System.out.println("[HackWarnerPacket] Now read packet of " + player.getName());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void unRegisterPlayer(Player player) {
		try {
			Channel c = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
			if ((player.isOnline()) && (c.pipeline().get("packet_listener") != null)) {
				c.pipeline().remove("packet_listener");
				System.out.println("[HackWarnerPacket] Stop read packet of " + player.getName());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public Object getValue(Object obj, String name) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception localException) {
		}
		return null;
	}
}
