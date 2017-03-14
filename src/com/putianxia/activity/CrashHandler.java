package com.putianxia.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	// 系统默认的UncaughtException处理�??
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context mContext;

	// 用来存储设备信息和异常信�??
	// private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日�??,作为日志文件名的�??部分
	// private DateFormat formatter = new
	// SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** 保证只有�??个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始�??
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理�??
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理�??
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处�??
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			// �??出程�??
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处�??,收集错误信息 发�?�错误报告等操作均在此完�??.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信�??
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出程序."+ex.toString(), Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		// collectDeviceInfo(mContext);
		// 保存日志文件
		// saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	// public void collectDeviceInfo(Context ctx) {
	// try {
	// PackageManager pm = ctx.getPackageManager();
	// PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
	// PackageManager.GET_ACTIVITIES);
	// if (pi != null) {
	// String versionName = pi.versionName == null ? "null"
	// : pi.versionName;
	// String versionCode = pi.versionCode + "";
	// infos.put("versionName", versionName);
	// infos.put("versionCode", versionCode);
	// }
	// } catch (NameNotFoundException e) {
	// Log.e(TAG, "an error occured when collect package info", e);
	// }
	// Field[] fields = Build.class.getDeclaredFields();
	// for (Field field : fields) {
	// try {
	// field.setAccessible(true);
	// infos.put(field.getName(), field.get(null).toString());
	// Log.d(TAG, field.getName() + " : " + field.get(null));
	// } catch (Exception e) {
	// Log.e(TAG, "an error occured when collect crash info", e);
	// }
	// }
	// }
	//
	// /**
	// * 保存错误信息到文件中
	// *
	// * @param ex
	// * @return 返回文件名称,便于将文件传送到服务�??
	// */
	// private String saveCrashInfo2File(Throwable ex) {
	//
	// StringBuffer sb = new StringBuffer();
	// for (Map.Entry<String, String> entry : infos.entrySet()) {
	// String key = entry.getKey();
	// String value = entry.getValue();
	// sb.append(key + "=" + value + "\n");
	// }
	//
	// Writer writer = new StringWriter();
	// PrintWriter printWriter = new PrintWriter(writer);
	// ex.printStackTrace(printWriter);
	// Throwable cause = ex.getCause();
	// while (cause != null) {
	// cause.printStackTrace(printWriter);
	// cause = cause.getCause();
	// }
	// printWriter.close();
	// String result = writer.toString();
	// sb.append(result);
	// try {
	// long timestamp = System.currentTimeMillis();
	// String time = formatter.format(new Date());
	// String fileName = "crash-" + time + "-" + timestamp + ".log";
	// if (Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED)) {
	// String path = "/sdcard/crash/";
	// File dir = new File(path);
	// if (!dir.exists()) {
	// dir.mkdirs();
	// }
	// FileOutputStream fos = new FileOutputStream(path + fileName);
	// fos.write(sb.toString().getBytes());
	// fos.close();
	// }
	// return fileName;
	// } catch (Exception e) {
	// Log.e(TAG, "an error occured while writing file...", e);
	// }
	// return null;
	// }
}