package me.exz.wailanbt.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class TooltipHandler {
    public TooltipHandler(){
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        NBTTagCompound n = event.itemStack.getTagCompound();
        NBTHandler.flag=2;
        NBTHandler.id= Item.itemRegistry.getNameForObject(event.itemStack.getItem());
        List<String> tips = NBTHandler.getTipsFromNBT(n, "tooltip");
        event.toolTip.addAll(tips);
    }

}
