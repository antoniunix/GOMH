package com.go_sharp.gomh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.go_sharp.gomh.contextApp.ContextApp;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by gnu on 28/02/18.
 */

public class SharePreferenceCustom {

    private static final String desKey = "c$0c36o%";
    private final static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
    private static final IvParameterSpec ivspec = new IvParameterSpec(iv);

    public static void write(String nameFile, String nameKey, String nameValue) {
        SharedPreferences preferences = ContextApp.context.getSharedPreferences(nameFile, Context.MODE_PRIVATE);
        String key = encryptKey(nameKey);
        String value = encrypt(nameValue);
        preferences.edit().putString(key, value).apply();
    }

    public static void write(int idResourceNameFile, int idResourceNameKey, int idResourceNameValue) {
        SharedPreferences preferences = ContextApp.context.getSharedPreferences(ContextApp.context.getString(idResourceNameFile), Context.MODE_PRIVATE);
        String key = encryptKey(ContextApp.context.getString(idResourceNameKey));
        String value = encrypt(ContextApp.context.getString(idResourceNameValue));
        preferences.edit().putString(key, value).apply();
    }

    public static void write(int idResourceNameFile, int idResourceNameKey, String Value) {
        SharedPreferences preferences = ContextApp.context.getSharedPreferences(ContextApp.context.getString(idResourceNameFile), Context.MODE_PRIVATE);
        String key = encryptKey(ContextApp.context.getString(idResourceNameKey));
        String value = encrypt(Value);
        preferences.edit().putString(key, value).apply();
    }

    public static String read(String nameFile, String nameKey, String nameDefault) {
        SharedPreferences preferences = ContextApp.context.getSharedPreferences(nameFile, Context.MODE_PRIVATE);
        String key = encryptKey(nameKey);
        String passEncrypted = preferences.getString(key, nameDefault);
        return passEncrypted != null ? decrypt(passEncrypted) : null;
    }

    public static String read(int idResourceNameFile, int idResourceNameKey, String nameDefault) {
        SharedPreferences preferences = ContextApp.context.getSharedPreferences(ContextApp.context.getString(idResourceNameFile), Context.MODE_PRIVATE);
        String key = encryptKey(ContextApp.context.getString(idResourceNameKey));
        String passEncrypted = preferences.getString(key, null);
        return passEncrypted != null ? decrypt(passEncrypted) : nameDefault;
    }

    public static boolean contains(int idResourceNameFile, int idResourceNameKey) {
        SharedPreferences preferences = ContextApp.context.getSharedPreferences(ContextApp.context.getString(idResourceNameFile), Context.MODE_PRIVATE);
        String key = encryptKey(ContextApp.context.getString(idResourceNameKey));
        return preferences.contains(key);
    }

    public static void remove(int idResourceNameFile, int idResourceNameKey) {
        SharedPreferences preferences = ContextApp.context.getSharedPreferences(ContextApp.context.getString(idResourceNameFile), Context.MODE_PRIVATE);
        String key = encryptKey(ContextApp.context.getString(idResourceNameKey));
        preferences.edit().remove(key).apply();
    }

    private static String encrypt(String input) {
        //return Base64.encodeToString(input.getBytes(), Base64.NO_PADDING);
        try {
            byte[] encodedKey = desKey.getBytes();
            SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES/CBC/PKCS5Padding");

            Cipher ecipher;
            ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            ecipher.init(Cipher.ENCRYPT_MODE, originalKey, ivspec);

            byte[] utf8 = input.getBytes("UTF8");

            byte[] enc = ecipher.doFinal(utf8);

            return Base64.encodeToString(enc, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e("SHCustom", e.toString());
            return "";
        }
    }

    private static String decrypt(String input) {
        //return new String(Base64.decode(input, Base64.NO_PADDING));
        try {
            byte[] encodedKey = desKey.getBytes();
            SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES/CBC/PKCS5Padding");

            Cipher dcipher;
            dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            dcipher.init(Cipher.DECRYPT_MODE, originalKey, ivspec);

            byte[] dec = Base64.decode(input, Base64.DEFAULT);

            byte[] utf8 = dcipher.doFinal(dec);

            return new String(utf8, "UTF8");
        } catch (Exception e) {
            Log.e("SHCustom", e.toString());
            return "";
        }
    }

    private static String encryptKey(String key){
        return MD5.md5(key).substring(0, 5).trim();
    }
}