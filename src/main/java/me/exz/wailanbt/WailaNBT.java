package me.exz.wailanbt;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.exz.wailanbt.commands.CommandName;
import me.exz.wailanbt.commands.CommandReload;
import me.exz.wailanbt.configuration.config;
import me.exz.wailanbt.reference.Reference;
import me.exz.wailanbt.util.LogHelper;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:Waila")
public class WailaNBT {
    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Initializing config");
        config.init(event.getModConfigurationDirectory());
        LogHelper.info("Config initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void init(@SuppressWarnings("UnusedParameters") FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.wailanbt.wailaNBTHandler.callbackRegister");
        ClientCommandHandler.instance.registerCommand(new CommandReload());
        ClientCommandHandler.instance.registerCommand(new CommandName());
    }
}
