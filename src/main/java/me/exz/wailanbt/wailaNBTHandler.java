package me.exz.wailanbt;


import com.google.gson.JsonElement;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.exz.wailanbt.configuration.config;
import me.exz.wailanbt.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mcp.mobius.waila.api.SpecialChars.*;

public class wailaNBTHandler implements IWailaDataProvider {
    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        wailaNBTHandler instance = new wailaNBTHandler();
        registrar.registerSyncedNBTKey("*",Block.class);
        registrar.registerBodyProvider(instance, Block.class);
    }

    private static List<String> getTipsFromNBT(NBTTagCompound n, String heldItemName) {
        List<String> tips = new ArrayList<String>();
            Set<Map.Entry<String, JsonElement>> holdItemA = config.configJson.entrySet();
            for (Map.Entry<String, JsonElement> holdItem : holdItemA) {
                Pattern pattern = Pattern.compile(holdItem.getKey());
                Matcher matcher = pattern.matcher(heldItemName);
                if (matcher.matches()) {
                    Set<Map.Entry<String, JsonElement>> teIDA = holdItem.getValue().getAsJsonObject().entrySet();
                    for (Map.Entry<String, JsonElement> teID : teIDA) {
                        tips.addAll(getTipsFromNBTWithHeldItem(n, teID.getKey(), teID.getValue()));
                    }
                }
            }

        return tips;
    }

    private static List<String> getTipsFromNBTWithHeldItem(NBTTagCompound n, String teID, JsonElement teEntry) {
        List<String> tips = new ArrayList<String>();
        String teIDReal = n.getString("id");
        Pattern pattern = Pattern.compile(teID);
        Matcher matcher = pattern.matcher(teIDReal);
        if (matcher.matches()) {
            Set<Map.Entry<String, JsonElement>> pathA = teEntry.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> path : pathA) {
                tips.add(getTipFromNBTWithPath(n, path.getKey(), path.getValue().getAsString()));
            }
        }
        return tips;
    }

    private static String getTipFromNBTWithPath(NBTTagCompound n, String path, String type) {
        List<String> pathDeep = new ArrayList<String>(Arrays.asList(path.split(">>>")));
        return getTipFromPathDeep(n, pathDeep, type);
    }

    private static String getTipFromPathDeep(NBTTagCompound n, List<String> pathDeep, String type) {
        if (pathDeep.size() == 1) {
            String tagName = pathDeep.get(0);
            String value = NBTHelper.NBTTypeToString(n, tagName, type);
            if (value == null){
                return null;
            }
            return String.format("%s" + TAB + ALIGNRIGHT + WHITE + "%s", tagName, value);
        } else {
            String compoundID = pathDeep.get(0);
            pathDeep.remove(0);
            return getTipFromPathDeep(n.getCompoundTag(compoundID), pathDeep, type);
        }
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        NBTTagCompound n = accessor.getNBTData();
        if (n==null){
            return currenttip;
        }
        EntityPlayer player = accessor.getPlayer();
        ItemStack holdItemReal = player.getHeldItem();
        String holdItemNameReal="";
        if (holdItemReal!=null){
            holdItemNameReal = Item.itemRegistry.getNameForObject(holdItemReal.getItem());
        }
        List<String> tips = getTipsFromNBT(n, holdItemNameReal);
        currenttip.addAll(tips);
        return currenttip;

    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

}
