package com.speed.faster.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author: 马佳敏
 * date: 2018/7/17
 * desc:ping工具类
 */
public class PingNet {
    private static final String TAG = "MA";

    /**
     * @param pingNetEntity 检测网络实体类
     * @return 检测后的数据
     * -w   ==> 执行的最后期限,单位为秒，也就是执行的时间为5秒，超过5秒则失败.
     */
    public static PingNetEntity ping(PingNetEntity pingNetEntity) {
        String line = null;
        Process process = null;
        BufferedReader successReader = null;
        String command = "ping -c " + pingNetEntity.getPingCount() + " -w " + pingNetEntity.getPingWtime() + " " + pingNetEntity.getIp();
//        String command = "ping -c " + pingCount + " " + host;
        int time2 = 0;
        try {
            process = Runtime.getRuntime().exec(command);
            if (process == null) {
                L.e("ping fail:process is null.");
                append(pingNetEntity.getResultBuffer(), "ping fail:process is null.");
                pingNetEntity.setPingTime(null);
                pingNetEntity.setPingTimeTotal(null);//设置总时间
                pingNetEntity.setResult(false);
                return pingNetEntity;
            }
            successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            double timeTotal = 0;
            double successCount = 0;
            while ((line = successReader.readLine()) != null) {
                L.i("line--" + line);
                append(pingNetEntity.getResultBuffer(), line);
                String time;
                if ((time = getTime(line)) != null) {
                    timeTotal = timeTotal + Double.parseDouble(time.trim());//每次成功增加时间
                    successCount = ++successCount;//每次成功增加1
                    pingNetEntity.setPingTimeTotal(timeTotal + "");//设置总时间
                    pingNetEntity.setPingSuccessCount(successCount + "");//设置成功次数
                    pingNetEntity.setPingTime(time);//设置单个时间
                }
            }
            int status = process.waitFor();
            if (status == 0) {
                L.i("exec cmd success:" + command);
                append(pingNetEntity.getResultBuffer(), "exec cmd success:" + command);
                pingNetEntity.setResult(true);
            } else {
                L.e("exec cmd fail.");
                append(pingNetEntity.getResultBuffer(), "exec cmd fail.");
                pingNetEntity.setPingTime(null);
                pingNetEntity.setResult(false);
            }
            L.i(TAG, "exec finished.");
            append(pingNetEntity.getResultBuffer(), "exec finished.");
        } catch (IOException e) {
            L.e(TAG, String.valueOf(e));
        } catch (InterruptedException e) {
            L.e(TAG, String.valueOf(e));
        } finally {
            L.i(TAG, "ping exit.");
            if (process != null) {
                process.destroy();
            }
            if (successReader != null) {
                try {
                    successReader.close();
                } catch (IOException e) {
                    L.e(TAG, String.valueOf(e));
                }
            }
        }
        L.i(TAG, pingNetEntity.getResultBuffer().toString());
        return pingNetEntity;
    }

    private static void append(StringBuffer stringBuffer, String text) {
        if (stringBuffer != null) {
            stringBuffer.append(text + "\n");
        }
    }

    private static String getTime(String line) {
        String[] lines = line.split("\n");
        String time = null;
        String time2 = null;
        for (String l : lines) {
            if (!l.contains("time="))
                continue;
            int index = l.indexOf("time=");
            time = l.substring(index + "time=".length());
            time2 = time.substring(0, time.length() - 2);
            L.e("getTimetime2--" + time2);
        }
        return time2;
    }
}