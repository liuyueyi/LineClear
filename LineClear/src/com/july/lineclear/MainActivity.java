package com.july.lineclear;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import cn.dm.android.DMOfferWall;
import cn.domob.android.ads.DomobAdView;
import cn.domob.android.ads.DomobInterstitialAd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {

	public static final String PUBLISHER_ID = "56OJwoVIuN9FBwtEAl";
	public static final String InlinePPID = "16TLuUEoApDmYNUIc9J8nr4k";
	public static final String chaPinID = "16TLuUEoApDmYNUIcSXO53Oz"; // 插屏

	static DomobAdView MyAdview320x50;
	static DomobInterstitialAd mInterstitialAd; // 插屏广告
	static RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DMOfferWall.init(this, PUBLISHER_ID);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new MainGame(this), cfg);

		layout = new RelativeLayout(this);
		// 设置窗口无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 去除强制屏幕装饰（如状态条）弹出设置
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		MyAdview320x50 = new DomobAdView(this, PUBLISHER_ID, InlinePPID);
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		layout.addView(gameView);
		layout.addView(MyAdview320x50, adParams);

		mInterstitialAd = new DomobInterstitialAd(this, PUBLISHER_ID, chaPinID,
				DomobInterstitialAd.INTERSITIAL_SIZE_300X250);
		mInterstitialAd.loadInterstitialAd(); // 加载

		setContentView(layout);

		mContext = this;
		// initialize(new MainGame(this), config);
	}

	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(final Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.CHAPIN:
				// 显示插屏广告
				// 判断插屏广告是否已初始化完成，用于确定是否能成功调用插屏广告
				if (mInterstitialAd.isInterstitialAdReady()) {
					mInterstitialAd.showInterstitialAd(mContext);
					Log.i("chapin", "显示");
				} else {
					mInterstitialAd.loadInterstitialAd(); // 再加载一个
					Log.i("chapin", "不显示");
				}
				break;
			case Constants.MORE:
				// 展示所有无积分推荐墙
				DMOfferWall.getInstance(mContext).showOfferWall(mContext);
				break;
			case Constants.EXIT: // 退出
				// 创建退出对话框
				AlertDialog isExit = new AlertDialog.Builder(mContext).create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("确定要退出吗");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener);
				isExit.setButton2("取消", listener);
				// 显示对话框
				isExit.show();
				break;
			}

		}
	};

	private static Context mContext;

	/** 监听对话框里面的button点击事件 */
	static DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				Gdx.app.exit();
				((Activity) mContext).finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	public void showAdStatic(int adTag) {
		// TODO Auto-generated method stub
		Message msg = handler.obtainMessage();
		msg.what = adTag; // 私有静态的整型变量，开发者请自行定义值
		handler.sendMessage(msg);
	}

}
