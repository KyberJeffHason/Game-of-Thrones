package got.common.network.base;

import com.google.common.base.Throwables;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import got.GOT;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;


public abstract  class AbstractPacket<T extends AbstractPacket<T>> implements IMessage, IMessageHandler<T, IMessage> {

    protected abstract void read(PacketBuffer buffer) throws IOException;

    protected abstract void write(PacketBuffer buffer) throws IOException;

    public abstract void process(EntityPlayer player, Side side);

    protected boolean isValidOnSide(Side side) {
        return true;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        try {
            read(new PacketBuffer(buffer));
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        try {
            write(new PacketBuffer(buffer));
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public final IMessage onMessage(T msg, MessageContext ctx) {
        if (!msg.isValidOnSide(ctx.side)) {
            throw new RuntimeException("Invalid side " + ctx.side.name() + " for " + msg.getClass().getSimpleName());
        }
        msg.process(GOT.proxy.getPlayerEntity(ctx), ctx.side);
        return null;
    }

    public static abstract class AbstractClientMessage<T extends AbstractPacket<T>> extends AbstractPacket<T> {
        @Override
        protected final boolean isValidOnSide(Side side) {
            return side.isClient();
        }
    }

    public static abstract class AbstractServerMessage<T extends AbstractPacket<T>> extends AbstractPacket<T> {
        @Override
        protected final boolean isValidOnSide(Side side) {
            return side.isServer();
        }
    }
}

