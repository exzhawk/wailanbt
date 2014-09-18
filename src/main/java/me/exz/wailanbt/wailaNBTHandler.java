package me.exz.wailanbt;


import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class wailaNBTHandler implements IWailaDataProvider {
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te == null) {
            return currenttip;
        }
        NBTTagCompound n = new NBTTagCompound();
        te.writeToNBT(n);
        //String id = n.getString("id");
        Integer mana = n.getInteger("mana");
        Boolean hasMana = n.hasKey("mana");
        EntityPlayer player = accessor.getPlayer();
        //LogHelper.info(Item.itemRegistry.getNameForObject(player.getHeldItem().getItem()));
        currenttip.add("Mana: "+mana.toString());
        return currenttip;

    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        wailaNBTHandler instance = new wailaNBTHandler();
        registrar.registerBodyProvider(instance, Block.class);
    }
}
