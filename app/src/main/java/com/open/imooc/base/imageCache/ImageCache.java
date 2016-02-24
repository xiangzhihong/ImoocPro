package com.open.imooc.base.imageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.open.imooc.base.McApplication;
import com.open.imooc.utils.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**@author xiangzhihong
 * 图片缓存公共类
 */
public class ImageCache {
	private static String baseDir;
	private static ExecutorService clearExecutor = new ThreadPoolExecutor(0, 1,
			1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	private static class ImageCacheHolder {
		public static ImageCache instance = new ImageCache();
	}

	private ImageCache() {
		baseDir = getPicCachePath();
	}

	public static ImageCache getInstance() {
		return ImageCacheHolder.instance;
	}

	public static FileNameGenerator getFileNameGenerator() {
		return new Md5FileNameGenerator();
	}

	public static String getPicCachePath() {
		if (!TextUtils.isEmpty(baseDir)) {
			return baseDir;
		}
		return getPicCacheRootPath() + "default" + File.separator;

	}

	private static String getPicCacheRootPath() {
		String dir = null;
		String sdcard = Utils.getSDPath();
		if (sdcard != null) {
			dir = sdcard;
		} else {
			File cacheDir = McApplication.getInstance().getCacheDir();
			dir = cacheDir.getAbsolutePath();
		}
		if (dir != null) {
			if (dir.endsWith(File.separator)) {
				dir = dir + "ymt/imagecachedir" + File.separator;
			} else {
				dir = dir + File.separator + "ymt/imagecachedir"
						+ File.separator;
			}
		}
		return dir;
	}

	public static void display(Context ct,String url, ImageView imgView) {
		display(ct,url, imgView, null);
	}
	public static void display(Context ct,String url, ImageView imgView,
			Integer defaultImgRes) {
		display(ct,url, imgView, null, defaultImgRes);
	}

	public static void display(Context ct,String url, ImageView imgView,
			BitmapDisplayer displayer, Integer defaultImgRes) {
		display(ct,url, imgView, displayer, defaultImgRes, 2);
	}

	public static void display(Context ct,String url, ImageView imgView,
			BitmapDisplayer displayer, Integer defaultImgRes, int cacheStatus) {
		DisplayImageOptions options = null;
		DisplayImageOptions.Builder builder = null;
		if (displayer == null) {
			displayer = DefaultConfigurationFactory.createBitmapDisplayer();
		}
		builder = new DisplayImageOptions.Builder().displayer(displayer);
		if (defaultImgRes != null) {
			builder.showStubImage(defaultImgRes);
			builder.showImageOnFail(defaultImgRes);
			builder.showImageForEmptyUri(defaultImgRes);
		}
		if (cacheStatus == 0) {
			builder.bitmapConfig(Bitmap.Config.RGB_565);
			builder.cacheOnDisc();
		} else if (cacheStatus == 1) {
			builder.cacheInMemory();
		} else if (cacheStatus == 2) {
			builder.cacheOnDisc().cacheInMemory();
		}
		options = builder.build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ct));
		imageLoader.displayImage(url, imgView, options,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {

					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {

					}
				});

	}

	public static void display(String url, ImageView imgView,
			BitmapDisplayer displayer, BitmapProcessor preProcessor,
			Bitmap defaultBm) {
		if (displayer == null) {
			displayer = DefaultConfigurationFactory.createBitmapDisplayer();
		}
		if (defaultBm != null && imgView != null) {
			imgView.setImageBitmap(defaultBm);
		}
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(url, imgView,
				new DisplayImageOptions.Builder().cacheOnDisc().cacheInMemory()
						.displayer(displayer).preProcessor(preProcessor)
						.build());
	}

	public boolean putCache(String path, String fileName, InputStream is)
			throws IOException {
		if (is == null) {
			return false;
		}

		BufferedInputStream bis = new BufferedInputStream(is);
		FileOutputStream fos = null;
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			} else {
				if (!dir.isDirectory()) {
					dir.delete();
					dir.mkdirs();
				}
			}
			if (!path.endsWith(File.separator)) {
				path = path + File.separator;
			}
			File file = new File(path + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[32 * 1024];
			int count = 0;
			while ((count = bis.read(buffer, 0, 32 * 1024)) != -1) {
				fos.write(buffer, 0, count);
			}
			fos.flush();

			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (fos != null) {
				fos.close();
			}
			bis.close();
			is.close();
		}
	}

	private void putCache(String path, String fileName, Bitmap bmp)
			throws IOException {
		if (bmp == null) {
			return;
		}
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		} else {
			if (!dir.isDirectory()) {
				dir.delete();
				dir.mkdirs();
			}
		}
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File file = new File(path + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
	}

	public Bitmap getCache(String path, String fileName) {
		File dir = new File(path);
		if ((!dir.exists()) || (!dir.isDirectory())) {
			return null;
		}
		File file = new File(path + File.separator + fileName);
		if (!file.exists()) {
			return null;
		}
		Bitmap bmp = BitmapFactory.decodeFile(path + File.separator + fileName);
		return bmp;
	}

	public void clear(String catalog, List<String> checkList) {
		clear(catalog, checkList, null);
	}

	public void clear(String catalog, List<String> checkList,
					  CallBackHandler callBackHandler) {
		if (TextUtils.isEmpty(catalog)) {
			return;
		}
		if (!catalog.contains("/")) {
			catalog = getPicCacheRootPath() + catalog;
		}
		ClearTask task = new ClearTask(catalog, checkList, callBackHandler);
		clearExecutor.submit(task);
	}

	private static class ClearTask implements Runnable {
		private String path;
		private List<String> checkList;
		private CallBackHandler callBackHandler;

		public ClearTask(String path, List<String> checkList,
				CallBackHandler callBackHandler) {
			this.path = path;
			this.checkList = checkList;
			this.callBackHandler = callBackHandler;
		}

		@Override
		public void run() {
			File dir = new File(path);
			if ((!dir.exists()) || (!dir.isDirectory())) {
				if (callBackHandler != null) {
					callBackHandler.onCallBack();
				}
				return;
			}
			File[] files = dir.listFiles();
			if (checkList == null || checkList.size() <= 0) {
				if (files != null && files.length > 0) {
					for (File file : files) {
						if (file.isFile()) {
							file.delete();
						}
					}
				}

			} else {
				List<String> encodedlist = new ArrayList<String>();
				for (String url : checkList) {
					encodedlist.add(ImageCache.getFileNameGenerator().generate(
							url));
				}

				if (files != null && files.length > 0) {
					for (File file : files) {
						if (file.isFile()) {
							String fileName = file.getName();
							if (!encodedlist.contains(fileName)) {
								file.delete();
							}
						}
					}
				}
			}

			if (callBackHandler != null) {
				callBackHandler.onCallBack();
			}
		}
	}

}
