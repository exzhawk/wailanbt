package me.exz.wailanbt.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import me.exz.wailanbt.commands.CommandName;
import me.exz.wailanbt.commands.CommandReload;
import me.exz.wailanbt.configuration.config;
import me.exz.wailanbt.util.LogHelper;
import net.minecraftforge.client.ClientCommandHandler;

import java.io.File;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy extends CommonProxy {
    private static File modConfigurationDirectory;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        modConfigurationDirectory = event.getModConfigurationDirectory();
    }
    @Override
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.wailanbt.wailaNBTHandler.callbackRegister");
        ClientCommandHandler.instance.registerCommand(new CommandReload());
        ClientCommandHandler.instance.registerCommand(new CommandName());
        FMLCommonHandler.instance().bus().register(this);
    }
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        LogHelper.info("Initializing config");
        config.init(modConfigurationDirectory, event.player);
    }

}
