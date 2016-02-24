package com.open.imooc.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class McApplication extends Application {
    private static McApplication instance;
    private List<BaseActivity> activities = new ArrayList<BaseActivity>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initInamgeCache();
    }


    public static McApplication getInstance() {
        if(instance==null){
           instance=new McApplication();
        }
        return instance;
    }

    public String getUserAgent() {
        String formatVerison = "02";
        String os = String.format("%-7s", "Android");
        String osVersion = String.format("%-6s", android.os.Build.VERSION.RELEASE);
        String deviceToken = String.format("%-64s",
                ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        String ua = (formatVerison + os + osVersion + deviceToken ).replace(" ", "=");
        return ua;
    }

    public void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    public void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    public BaseActivity getCurrentActivity() {
        if (activities.size() == 0) return null;
        return activities.get(activities.size() - 1);
    }


    private void initInamgeCache() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imooc/Cache");//获取到缓存的目录地址
        //创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                        //.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation你可以通过自己的内存缓存实现
                        //.memoryCacheSize(2 * 1024 * 1024)
                        ///.discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                        //.discCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        //.discCacheFileCount(100) //缓存的File数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }

    public void quitApp() {
        if (activities.size() == 0) return;
        for (int i = activities.size() - 1; i >= 0; i--) {
            BaseActivity activity = activities.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
