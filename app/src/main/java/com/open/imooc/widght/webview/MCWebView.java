package com.open.imooc.widght.webview;

import android.webkit.WebView;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.open.imooc.utils.Contants;

/**
 * Created by xiangzhihong on 2015/12/23 on 14:17.
 */
public class MCWebView extends WebView {
    private String mApsid;
    private Context mContext;

    public MCWebView(Context paramContext) {
        this(paramContext, null);
    }

    public MCWebView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    private void init(Context paramContext) {
        this.mContext = paramContext;
//        this.mApsid = MCSaveData.getUserInfo(Contants.APSID, paramContext).toString();
    }

    public void setCookie(String paramString) {
        CookieSyncManager.createInstance(this.mContext);
        CookieManager localCookieManager = CookieManager.getInstance();
        localCookieManager.setAcceptCookie(true);
        localCookieManager.removeSessionCookie();
        localCookieManager.setCookie(paramString, Contants.APSID + "=" + this.mApsid);
        CookieSyncManager.getInstance().sync();
    }
}
