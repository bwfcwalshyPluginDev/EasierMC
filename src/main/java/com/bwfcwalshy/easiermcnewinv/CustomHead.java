package com.bwfcwalshy.easiermcnewinv;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class CustomHead {

    public static ItemStack getSkull(String url){
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap map = profile.getProperties();
        if(map == null)
            throw new IllegalStateException("Profile does not have a property map!");
        String data = Base64.encodeBase64String(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        map.put("textures", new Property("textures", data));
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta im = is.getItemMeta();
        Class<?> headMeta = im.getClass();
        getField(headMeta, "profile", GameProfile.class).set(im, profile);
        is.setItemMeta(im);
        return is;
    }

    public static FieldAccessor getField(Class<?> targetClass, String name, Class<?> fieldType){
        for(final Field field : targetClass.getDeclaredFields()){
            if(name == null || field.getName().equals(name)){
                field.setAccessible(true);

                return new FieldAccessor() {
                    @Override
                    public Object get(Object o) throws IllegalArgumentException {
                        try{
                            return field.get(o);
                        }catch(IllegalAccessException e){
                            throw new RuntimeException("Cannot access field!");
                        }
                    }

                    @Override
                    public void set(Object o, Object o1) throws IllegalArgumentException {
                        try{
                            field.set(o, o1);
                        }catch(IllegalAccessException e){
                            throw new RuntimeException("Cannot access field!");
                        }
                    }
                };
            }
        }

        if(targetClass.getSuperclass() != null)
            return getField(targetClass.getSuperclass(), name, fieldType);
        throw new IllegalArgumentException("Cannot find field '" + name + "' with type " + fieldType.getSimpleName());
    }

    public interface FieldAccessor<T> {

        T get(Object o);

        void set(Object o, Object o1);
    }
}
