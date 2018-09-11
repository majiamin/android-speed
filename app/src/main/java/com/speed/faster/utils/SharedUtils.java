package com.speed.faster.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;


import com.speed.faster.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author 马佳敏
 * @time 2018-7-9
 * @描述 保存工具类
 */
public class SharedUtils {
  public final static SharedPreferences sp = MyApplication.getSystemConfigSharedFile();

  /**
   * 保存登录bean对象的key值
   */
  public static final String LOGIN_BEAN = "login_bean";
  public static final String COUNTRY_LIST_BEAN = "countryListBean";
  public static final String APP_BEAN = "app_bean";
  /**
   * @return
   * @throws IOException
   * @描述 序列化对象
   */
  public static String serialize(Object object) throws IOException {
    long startTime = System.currentTimeMillis();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
      byteArrayOutputStream);
    objectOutputStream.writeObject(object);
    String serStr = byteArrayOutputStream.toString("ISO-8859-1");
    serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
    objectOutputStream.close();
    byteArrayOutputStream.close();
    // LogUtils.e("serial", "serialize str =" + serStr);
    long endTime = System.currentTimeMillis();
    // LogUtils.e("serial", "序列化耗时为:" + (endTime - startTime));
    return serStr;
  }

  /**
   * @param str
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   * @描述 反序列化对象
   * @author maJian
   */
  public static <T> T deSerialization(String str) throws IOException,
          ClassNotFoundException {
    // long startTime = System.currentTimeMillis();
    String redStr = java.net.URLDecoder.decode(str, "UTF-8");
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
      redStr.getBytes("ISO-8859-1"));
    ObjectInputStream objectInputStream = new ObjectInputStream(
      byteArrayInputStream);
    @SuppressWarnings("unchecked")
    T t = (T) objectInputStream.readObject();
    objectInputStream.close();
    byteArrayInputStream.close();
    long endTime = System.currentTimeMillis();
    // LogUtils.e("serial", "反序列化耗时为:" + (endTime - startTime));
    return t;
  }

  /**
   * @param key    ---对象名--LOGIN_BEAN
   * @param object
   * @return
   * @描述 保存对象--序列化对象并保存到SharedPreferences
   * @author 马佳敏
   */
  public static boolean saveObject(String key, Object object) {
    Editor edit = sp.edit();
    String strObject = null;
    try {
      strObject = serialize(object);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (TextUtils.isEmpty(strObject)) {
      // 序列化对象失败
      return false;
    }
    edit.putString(key, strObject);
    edit.commit();
    return true;
  }

  /**
   * @param key --对象名--LOGIN_BEAN
   * @return
   * @描述 获取对象
   * @author 马佳敏
   */
  public static <T> T getObject(String key) {
    try {
      return deSerialization(sp.getString(key, ""));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * put string preferences
   *
   * @param key     The name of the preference to modify
   * @param value   The new value for the preference
   * @return True if the new values were successfully written to persistent
   * storage.
   */
  public static boolean putString(String key, String value) {
    Editor editor = sp.edit();
    editor.putString(key, value);
    return editor.commit();
  }

  /**
   * get string preferences
   *
   * @param key     The name of the preference to retrieve
   * @return The preference value if it exists, or null. Throws
   * ClassCastException if there is a preference with this name that
   * is not a string
   */
  public static String getString(String key) {
    return getString(key, null);
  }

  /**
   * get string preferences
   *
   * @param key          The name of the preference to retrieve
   * @param defaultValue Value to back_return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws
   * ClassCastException if there is a preference with this name that
   * is not a string
   */
  public static String getString(String key, String defaultValue) {
    return sp.getString(key, defaultValue);
  }

  /**
   * put int preferences
   *
   * @param key     The name of the preference to modify
   * @param value   The new value for the preference
   * @return True if the new values were successfully written to persistent
   * storage.
   */
  public static boolean putInt(String key, int value) {
    Editor editor = sp.edit();
    editor.putInt(key, value);
    return editor.commit();
  }

  /**
   * get int preferences
   *
   * @param key     The name of the preference to retrieve
   * @return The preference value if it exists, or -1. Throws
   * ClassCastException if there is a preference with this name that
   * is not a int
   */
  public static int getInt(String key) {
    return getInt(key, -1);
  }

  /**
   * get int preferences
   *
   * @param key          The name of the preference to retrieve
   * @param defaultValue Value to back_return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws
   * ClassCastException if there is a preference with this name that
   * is not a int
   */
  public static int getInt(String key, int defaultValue) {
    return sp.getInt(key, defaultValue);
  }

  /**
   * put long preferences
   *
   * @param key     The name of the preference to modify
   * @param value   The new value for the preference
   * @return True if the new values were successfully written to persistent
   * storage.
   */
  public static boolean putLong(String key, long value) {
    Editor editor = sp.edit();
    editor.putLong(key, value);
    return editor.commit();
  }

  /**
   * get long preferences
   *
   * @param key     The name of the preference to retrieve
   * @return The preference value if it exists, or -1. Throws
   * ClassCastException if there is a preference with this name that
   * is not a long
   */
  public static long getLong(String key) {
    return getLong(key, -1);
  }

  /**
   * get long preferences
   *
   * @param key          The name of the preference to retrieve
   * @param defaultValue Value to back_return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws
   * ClassCastException if there is a preference with this name that
   * is not a long
   */
  public static long getLong(String key, long defaultValue) {
    return sp.getLong(key, defaultValue);
  }

  /**
   * put float preferences
   *
   * @param key     The name of the preference to modify
   * @param value   The new value for the preference
   * @return True if the new values were successfully written to persistent
   * storage.
   */
  public static boolean putFloat(String key, float value) {
    Editor editor = sp.edit();
    editor.putFloat(key, value);
    return editor.commit();
  }

  /**
   * get float preferences
   *
   * @param key     The name of the preference to retrieve
   * @return The preference value if it exists, or -1. Throws
   * ClassCastException if there is a preference with this name that
   * is not a float
   */
  public static float getFloat(String key) {
    return getFloat(key, -1);
  }

  /**
   * get float preferences
   *
   * @param key          The name of the preference to retrieve
   * @param defaultValue Value to back_return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws
   * ClassCastException if there is a preference with this name that
   * is not a float
   */
  public static float getFloat(String key, float defaultValue) {
    return sp.getFloat(key, defaultValue);
  }

  /**
   * put boolean preferences
   *
   * @param key     The name of the preference to modify
   * @param value   The new value for the preference
   * @return True if the new values were successfully written to persistent
   * storage.
   */
  public static boolean putBoolean(String key, boolean value) {

    Editor editor = sp.edit();
    editor.putBoolean(key, value);
    return editor.commit();
  }

  /**
   * get boolean preferences, default is false
   *
   * @param key     The name of the preference to retrieve
   * @return The preference value if it exists, or false. Throws
   * ClassCastException if there is a preference with this name that
   * is not a boolean
   */
  public static boolean getBoolean(String key) {
    return getBoolean(key, false);
  }

  /**
   * get boolean preferences
   *
   * @param key          The name of the preference to retrieve
   * @param defaultValue Value to back_return if this preference does not exist
   * @return The preference value if it exists, or defValue. Throws
   * ClassCastException if there is a preference with this name that
   * is not a boolean
   */
  public static boolean getBoolean(String key, boolean defaultValue) {
    return sp.getBoolean(key, defaultValue);
  }
}
