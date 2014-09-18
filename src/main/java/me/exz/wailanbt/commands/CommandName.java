package me.exz.wailanbt.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;

public class CommandName extends CommandBase{
    @Override
    public String getCommandName() {
        return "wnn";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/wnn";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] array) {
        EntityPlayer player = (EntityPlayer) sender;
        player.addChatComponentMessage(new ChatComponentText(Item.itemRegistry.getNameForObject(player.getHeldItem().getItem())));
    }
}
