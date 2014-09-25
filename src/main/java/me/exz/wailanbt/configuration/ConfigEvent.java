package me.exz.wailanbt.configuration;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.exz.wailanbt.util.LogHelper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ConfigEvent {
    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if(event.entity instanceof EntityClientPlayerMP){
            LogHelper.info("Initializing config");
            config.init(config.modConfigurationDirectory, (EntityPlayer)event.entity);
        }
    }

}
