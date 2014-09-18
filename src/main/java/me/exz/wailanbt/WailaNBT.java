package me.exz.wailanbt;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import me.exz.wailanbt.commands.CommandName;
import me.exz.wailanbt.commands.CommandReload;
import me.exz.wailanbt.configuration.config;
import me.exz.wailanbt.reference.Reference;
import me.exz.wailanbt.util.LogHelper;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class WailaNBT {
    @Mod.Instance(Reference.MOD_ID)
    public static WailaNBT instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config.loadConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.wailanbt.wailaNBTHandler.callbackRegister");
        ClientCommandHandler.instance.registerCommand(new CommandReload());
        ClientCommandHandler.instance.registerCommand(new CommandName());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }


}
