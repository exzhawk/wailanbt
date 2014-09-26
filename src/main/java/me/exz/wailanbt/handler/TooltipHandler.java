package me.exz.wailanbt.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipHandler {
    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        NBTTagCompound n =event.itemStack.getTagCompound();
        event.toolTip.add(n.toString());
    }

}
