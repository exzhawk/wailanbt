package me.exz.wailanbt.handler;

import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerTooltipHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class TooltipHandler implements IContainerTooltipHandler {

    @Override
    public List<String> handleTooltip(GuiContainer guiContainer, int i, int i2, List<String> strings) {
        return strings;
    }

    @Override
    public List<String> handleItemDisplayName(GuiContainer guiContainer, ItemStack itemStack, List<String> strings) {
        return strings;
    }

    @Override
    public List<String> handleItemTooltip(GuiContainer guiContainer, ItemStack itemStack, int i, int i2, List<String> strings) {
        if (guiContainer != null && GuiContainerManager.shouldShowTooltip(guiContainer) && itemStack != null) {
            NBTTagCompound n = itemStack.getTagCompound();
            if (n != null) {
                NBTHandler.flag = 2;
                NBTHandler.id = Item.itemRegistry.getNameForObject(itemStack.getItem());
                List<String> tips = NBTHandler.getTipsFromNBT(n, "tooltip");
                for (String tip:tips){
                    strings.add(1, "\u00a77" + tip);
                }
                return strings;
            }
        }
        return strings;
    }
}
