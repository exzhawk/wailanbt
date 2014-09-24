package me.exz.wailanbt.commands;

import me.exz.wailanbt.configuration.config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandReload extends CommandBase {

    @Override
    public String getCommandName() {
        return "wnr";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/wnr";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] array) {
        config.loadConfig((EntityPlayer) sender);
    }
}
