package me.exz.wailanbt.handler;

import com.google.gson.JsonElement;
import me.exz.wailanbt.configuration.config;
import me.exz.wailanbt.util.NBTHelper;
import net.minecraft.nbt.*;
import org.apache.commons.lang3.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mcp.mobius.waila.api.SpecialChars.*;

public class NBTHandler {
    public static ScriptEngineManager manager;
    public static ScriptEngine engine;
    public static HashSet<String> scriptSet;
    static byte flag;//0 for block; 1 for entity
    static String id = "";

    static List<String> getTipsFromNBT(NBTTagCompound n, String heldItemName) {
        List<String> tips = new ArrayList<String>();
        Set<Map.Entry<String, JsonElement>> holdItemA = config.configJson.entrySet();
        for (Map.Entry<String, JsonElement> holdItem : holdItemA) {
            Pattern pattern = Pattern.compile(holdItem.getKey());
            Matcher matcher = pattern.matcher(heldItemName);
            if (matcher.matches() || flag == 2) {
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
        String tip = getTipFromPathDeep(n, pathDeep, displayName.isEmpty() ? path : displayName);
        if (!tip.equals("__ERROR__")) {
            return tip;
        } else {
            return "__ERROR__";
        }

    }


    private static String getTipFromPathDeep(NBTBase n, List<String> pathDeep, String displayName) {
        if (pathDeep.size() == 0) {
            String tipValue = NBTHelper.NBTToString(n);
            if (tipValue.equals("__ERROR__")) {
                return "__ERROR__";
            }
            return getTipFormatted(displayName, tipValue);
        } else {
            String subID = pathDeep.get(0);
            pathDeep.remove(0);
            NBTBase subTag = null;
            try {
                switch (n.getId()) {
                    case 10:
                        subTag = ((NBTTagCompound) n).getTag(subID);
                        break;
                    case 9:
                        NBTTagList tag = (NBTTagList) n;
                        Integer subTagType = tag.func_150303_d();
                        Integer subTagID = Integer.valueOf(subID);
                        switch (subTagType) {
                            case 10:
                                subTag = tag.getCompoundTagAt(subTagID);
                                break;
                            case 11:
                                subTag = new NBTTagIntArray(tag.func_150306_c(subTagID));
                                break;
                            case 6:
                                subTag = new NBTTagDouble(tag.func_150309_d(subTagID));
                                break;
                            case 5:
                                subTag = new NBTTagFloat(tag.func_150308_e(subTagID));
                                break;
                            case 8:
                                String tipValue = tag.getStringTagAt(subTagID);
                                return getTipFormatted(displayName, StringUtils.substring(tipValue, 1, -1));
                            default:
                                return "__ERROR__";
                        }
                }
                return getTipFromPathDeep(subTag, pathDeep, displayName);
            } catch (Exception e) {
                return "__ERROR__";
            }
        }

    }

    private static String getTipFormatted(String displayName, String tipValue) {
        if (displayName.startsWith("function p(v){")) {
            try {
                String hash = "S" + MD5(displayName);
                if (!scriptSet.contains(hash)) {
                    scriptSet.add(hash);
                    String script = "function " + hash + displayName.substring(10);
                    //LogHelper.info(script);
                    engine.eval(script);
                }
                Invocable invoke = (Invocable) engine;
                return String.valueOf(invoke.invokeFunction(hash, tipValue));
            } catch (Exception e) {
                e.printStackTrace();
                return "__ERROR__";
            }
        } else {
            if (displayName.contains("%")) {
                try {
                    return String.format(displayName, Double.valueOf(tipValue));
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                try {
                    return String.format(displayName, String.valueOf(tipValue));
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
            if (flag == 2) {
                return String.format("%s : %s", displayName, tipValue);
            } else {
                return String.format("%s" + TAB + ALIGNRIGHT + WHITE + "%s", displayName, tipValue);
            }

        }
    }

    private static String MD5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
