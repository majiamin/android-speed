package com.speed.faster.utils;

import android.text.TextUtils;

/**
 * Created by mjm on 2017/9/7.
 * Describe: 检查String字符串
 */
public class StringUtils {
    //手机号11位验证
    public static boolean checkUserName(String username) {
        if (username.length() == 11) {
            return true;
        }
        return false;
    }

    //检查密码
    public static boolean checkPassWord(String password) {
        if (password != null && password.length() >= 6
                && password.length() <= 20) {
            return true;
        }
        return false;
    }

    //验证手机格式
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    public static String SubStringMy(String pString, String cString) {
        String t = "";
        String tString = "";
        //绑定客户经理
        String[] SumString = pString.split("\\?");
        String contentString = SumString[1].toString();
        String[] contentArray = contentString.split("&");
        for (int i = 0; i < contentArray.length; i++) {
            if (contentArray[i].contains(cString + "=")) {
                tString = contentArray[i];
                String[] Array = tString.split("=");
                if (cString.equals(Array[0])) {
                    //解决t没有值的情况--数组越界
                    if (Array.length > 1) {
                        t = Array[1].toString();
                    } else {
                        t = "";
                    }
                }
                return t;
            }
        }
        return t;
    }

    //密码强度判断
    public static int passStrength(String str) {
        // 8--16
        //8位以下全部弱    同一种 弱
        //8位-10位 同二种  中
        //10以上,字母,数字,特殊字符 强
        if (str.matches("^[0-9]+$")) {
            //输入的纯数字为弱
            return 1;
        } else if (str.matches("^[a-z]+$")) {
            //输入的纯小写字母为弱
            return 1;
        } else if (str.matches("^[A-Z]+$")) {
            //输入的纯大写字母为弱
            return 1;
        }
        else if (str.matches("^[^\\w\\s]+$")) {
            //输入的大写字母和小写字母和数字，输入的字符小于8个个密码为弱
            return 1;
        }
        else if (str.matches("^[a-z0-9]{1,7}")) {
            //输入的小写字母和数字，输入的字符小于8个密码为弱
            return 1;
        } else if (str.matches("^[A-Za-z]{1,7}")) {
            //输入的大写字母和小写字母，输入的字符小于8个密码为弱
            return 1;
        }
        else if (str.matches("^[A-Za-z0-9]{1,7}")) {
            //3中  小于7
            return 1;
        }
        else if (str.matches("^(?=.*?[0-9])(?=.*?[^\\w\\s]).{1,7}")) {
            //2中  小于7
            return 1;
        }
        else if (str.matches("^(?=.*?[a-z])(?=.*?[^\\w\\s]).{1,7}")) {
            //2中  小于7
            return 1;
        }
        else if (str.matches("^(?=.*?[A-Z])(?=.*?[^\\w\\s]).{1,7}")) {
            //2中  小于7
            return 1;
        }
        else if (str.matches("^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[^\\w\\s]).{1,7}")) {
            //3中  小于7
            return 1;
        }
        else if (str.matches("^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[^\\w\\s]).{1,7}")) {
            //3中  小于7
            return 1;
        }
        else if (str.matches("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\\w\\s]).{1,7}")) {
            //3中  小于7
            return 1;
        }
        else if (str.matches("^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z]).{10,16}$")) {
            //输入数字和特殊字符，输入的字符大于8个个密码为中
            return 3;
        }
        else if(str.matches("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\\w\\s]).{10,16}$")){
            //输入的大写字母和小写字母和数字,特殊字符，输入的字符大于8个密码为强
            return 3;
        }
        else if(str.matches("^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[^\\w\\s]).{10,16}$")){
            //输入的大写字母和小写字母和数字,特殊字符，输入的字符大于8个密码为强
            return 3;
        }
        else if(str.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[^\\w\\s]).{10,16}$")){
            //输入的大写字母和小写字母和数字,特殊字符，输入的字符大于8个密码为强
            return 3;
        }
        else if(str.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\\w\\s]).{10,16}$")){
            //输入的大写字母和小写字母和数字,特殊字符，输入的字符大于8个密码为强
            return 3;
        }
        else if(str.equals("")){

            return 4;
        }
        return 2;
    }

}
