package me.exz.wailanbt.handler;

import com.google.gson.JsonElement;
import me.exz.wailanbt.configuration.config;
import me.exz.wailanbt.util.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mcp.mobius.waila.api.SpecialChars.ALIGNRIGHT;
import static mcp.mobius.waila.api.SpecialChars.TAB;
import static mcp.mobius.waila.api.SpecialChars.WHITE;

public class NBTHandler {
    protected static byte flag;//0 for block; 1 for entity
    protected static String id = "";
    protected static List<String> getTipsFromNBT(NBTTagCompound n, String heldItemName) {
        List<String> tips = new ArrayList<String>();
        Set<Map.Entry<String, JsonElement>> holdItemA = config.configJson.entrySet();
        for (Map.Entry<String, JsonElement> holdItem : holdItemA) {
            Pattern pattern = Pattern.compile(holdItem.getKey());
            Matcher matcher = pattern.matcher(heldItemName);
            if (matcher.matches()||flag==2) {
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
        String teIDReal = id;
        Pattern pattern = Pattern.compile(teID);
        Matcher matcher = pattern.matcher(teIDReal);
        if (matcher.matches()) {
            Set<Map.Entry<String, JsonElement>> pathA = teEntry.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> path : pathA) {
                String tip = getTipFromNBTWithPath(n, path.getKey(), path.getValue().getAsString());
                if (!tip.equals("__ERROR__")) {
                    tips.add(tip);
                }
            }
        }
        return tips;
    }

    private static String getTipFromNBTWithPath(NBTTagCompound n, String path, String displayName) {
        List<String> pathDeep = new ArrayList<String>(Arrays.asList(path.split(">>>")));
        String tip = getTipFromPathDeep(n, pathDeep, displayName);
        if (!tip.equals("__ERROR__")){
            return tip;
        }else{
            return "__ERROR__";
        }

    }

    private static String getTipFromPathDeep(NBTTagCompound n, List<String> pathDeep, String displayName) {
        if (pathDeep.size() == 1) {
            String tagName = pathDeep.get(0);
            String value = NBTHelper.NBTTypeToString(n, tagName);
            if (value.equals("__ERROR__")) {
                return "__ERROR__";
            }
            if (flag==2) {
                return String.format("%s : %s", displayName.isEmpty() ? tagName : displayName, value);
            }else{
                return String.format("%s" + TAB + ALIGNRIGHT + WHITE + "%s", displayName.isEmpty() ? tagName : displayName, value);
            }
        } else {
            String compoundID = pathDeep.get(0);
            pathDeep.remove(0);
            return getTipFromPathDeep(n.getCompoundTag(compoundID), pathDeep, displayName);
        }
    }
}
