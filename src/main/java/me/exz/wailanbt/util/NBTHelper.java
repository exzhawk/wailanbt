package me.exz.wailanbt.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringUtils;


public class NBTHelper {
    public static String NBTTypeToString(NBTTagCompound n, String id) {
        if (n.hasKey(id)) {
            NBTBase tag = n.getTag(id);
            switch (tag.getId()) {
                case 0:
                case 3:
                case 7:
                case 9:
                case 10:
                case 11:
                    return tag.toString();
                case 1:
                case 2:
                case 4:
                case 5:
                case 6:
                    return StringUtils.substring(tag.toString(), 0, -1);
                case 8:
                    return StringUtils.substring(tag.toString(), 1, -1);
                default:
                    return "__ERROR__";

            }
        } else {
            return "__ERROR__";
        }


    }

    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    @Deprecated
    public static String NBTTypeToString_old(NBTTagCompound n, String id, String type) {
        try {
            switch (NBTTypeName.TypeNameToID("TAG_" + type)) {
                case TAG_End:
                    return "TAG_End";
                case TAG_Byte:
                    return String.valueOf(n.getByte(id));
                case TAG_Short:
                    return String.valueOf(n.getShort(id));
                case TAG_Integer:
                    return String.valueOf(n.getInteger(id));
                case TAG_Long:
                    return String.valueOf(n.getLong(id));
                case TAG_Float:
                    return String.valueOf(n.getFloat(id));
                case TAG_Double:
                    return String.valueOf(n.getDouble(id));
                case TAG_ByteArray:
                    return "TAG_ByteArray";
                case TAG_String:
                    return n.getString(id);
                case TAG_List:
                    return "TAG_List";
                case TAG_Compound:
                    return "TAG_Compound";
                case TAG_IntArray:
                    return "TAG_IntArray";
                default:
                    return "Bad Type";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Deprecated
    public static enum NBTTypeName {
        TAG_End, TAG_Byte, TAG_Short, TAG_Integer, TAG_Long, TAG_Float, TAG_Double, TAG_ByteArray, TAG_String, TAG_List, TAG_Compound, TAG_IntArray, ERROR_VALUE;

        @SuppressWarnings("deprecation")
        public static NBTTypeName TypeNameToID(String name) {
            try {
                return valueOf(name);
            } catch (Exception e) {
                e.printStackTrace();
                return ERROR_VALUE;
            }
        }
    }


}
