package got.common.block.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public abstract class GOTBlockOreStorageBase extends Block {
	@SideOnly(value = Side.CLIENT)
	public IIcon[] oreStorageIcons;
	public String[] oreStorageNames;

	public GOTBlockOreStorageBase() {
		super(Material.iron);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(5.0f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeMetal);
	}

	@Override
	public boolean canSilkHarvest() {
		return true;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= oreStorageNames.length) {
			j = 0;
		}
		return oreStorageIcons[j];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < oreStorageNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, int i, int j, int k, int beaconX, int beaconY, int beaconZ) {
		return true;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		oreStorageIcons = new IIcon[oreStorageNames.length];
		for (int i = 0; i < oreStorageNames.length; ++i) {
			oreStorageIcons[i] = iconregister.registerIcon(getTextureName() + "_" + oreStorageNames[i]);
		}
	}

	public void setOreStorageNames(String... names) {
		oreStorageNames = names;
	}
}
