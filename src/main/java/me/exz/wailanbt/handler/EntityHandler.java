package me.exz.wailanbt.handler;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.exz.wailanbt.util.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class EntityHandler implements IWailaEntityProvider {

    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        EntityHandler instance = new EntityHandler();
        registrar.registerSyncedNBTKey("*", Entity.class);
        registrar.registerBodyProvider(instance, Entity.class);
        LogHelper.info("entity handler");
    }

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        Entity currentEntity = accessor.getEntity();
        Class currentEntityClass = currentEntity.getClass();
        if (EntityList.classToStringMapping.containsKey(currentEntityClass)) {
            NBTTagCompound n = accessor.getNBTData();
            //LogHelper.info(n.toString());
            EntityPlayer player = accessor.getPlayer();
            ItemStack holdItemReal = player.getHeldItem();
            String holdItemNameReal = "";
            if (holdItemReal != null) {
                holdItemNameReal = Item.itemRegistry.getNameForObject(holdItemReal.getItem());
            }
            NBTHandler.flag=1;
            NBTHandler.id= EntityList.getEntityString(currentEntity);
            //currenttip.add(NBTHandler.id);
            List<String> tips = NBTHandler.getTipsFromNBT(n, holdItemNameReal);
            currenttip.addAll(tips);
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
