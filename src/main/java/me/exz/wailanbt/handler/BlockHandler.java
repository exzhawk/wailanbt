package me.exz.wailanbt.handler;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class BlockHandler implements IWailaDataProvider {
    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        BlockHandler instance = new BlockHandler();
        registrar.registerSyncedNBTKey("*", Block.class);
        registrar.registerBodyProvider(instance, Block.class);

    }
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
        NBTTagCompound n = accessor.getNBTData();
        if (n == null) {
            return currenttip;
        }
        EntityPlayer player = accessor.getPlayer();
        ItemStack holdItemReal = player.getHeldItem();
        String holdItemNameReal = "";
        if (holdItemReal != null) {
            holdItemNameReal = Item.itemRegistry.getNameForObject(holdItemReal.getItem());
        }
        List<String> tips = NBTHandler.getTipsFromNBT(n, holdItemNameReal);
        currenttip.addAll(tips);
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
