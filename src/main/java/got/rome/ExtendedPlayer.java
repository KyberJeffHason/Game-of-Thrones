package got.rome;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties
{
	
public final static String EXT_PROP_NAME = "ExtendedPlayer";




private final EntityPlayer player;


private int attackCooldown;



public ExtendedPlayer(EntityPlayer player)
{
this.player = player;

this.attackCooldown = 0;
}



public static final void register(EntityPlayer player)
{
player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
}



public static final ExtendedPlayer get(EntityPlayer player)
{
return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
}


@Override
public void saveNBTData(NBTTagCompound compound)
{

NBTTagCompound properties = new NBTTagCompound();

properties.setInteger("attackCooldown", this.attackCooldown);



compound.setTag(EXT_PROP_NAME, properties);
}


@Override
public void loadNBTData(NBTTagCompound compound)
{
	
NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

this.attackCooldown = properties.getInteger("attackCooldown");

}

@Override
public void init(Entity entity, World world)
{
}

public final int getAttackCooldown() { return attackCooldown; }
public final void setAttackCooldown(int i) { attackCooldown = i; }

}
