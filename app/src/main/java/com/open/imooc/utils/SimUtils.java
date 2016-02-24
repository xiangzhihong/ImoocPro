package com.open.imooc.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by xiangzhihong on 2016/1/11 on 11:56.
 */
public class SimUtils {
    public static boolean hasSimCard(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        int state = mTelephonyManager.getSimState();
        if (TelephonyManager.SIM_STATE_READY == state) {
            return true;
        }
        return false;
    }

    public static String getNetworkSuport(Context context) {
        String suport=null;
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                suport="中国移动";
            } else if (operator.equals("46001")) {
                suport="中国联通";
            } else if (operator.equals("46003")) {
                suport="中国电信";
            }
        }
        return suport;
    }

}
