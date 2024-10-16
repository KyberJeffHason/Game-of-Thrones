package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class GOTPacketHiredUnitInteract implements IMessage {
	public int entityID;
	public int entityAction;

	public GOTPacketHiredUnitInteract() {
	}

	public GOTPacketHiredUnitInteract(int id, int a) {
		entityID = id;
		entityAction = a;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		entityAction = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeByte(entityAction);
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredUnitInteract, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredUnitInteract packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npc = world.getEntityByID(packet.entityID);
			if (npc instanceof GOTEntityNPC) {
				GOTEntityNPC hiredNPC = (GOTEntityNPC) npc;
				if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					int action = packet.entityAction;
					boolean closeScreen = false;
					if (action == 0) {
						hiredNPC.npcTalkTick = hiredNPC.getNPCTalkInterval();
						closeScreen = hiredNPC.speakTo(entityplayer);
					} else if (action == 1) {
						hiredNPC.hiredNPCInfo.sendClientPacket(true);
					}
					if (closeScreen) {
						entityplayer.closeScreen();
					}
				}
			}
			return null;
		}
	}

}
