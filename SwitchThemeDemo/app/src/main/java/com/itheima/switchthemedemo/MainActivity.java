package com.itheima.switchthemedemo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private static int currentTheme=R.style.BlueTheme;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv_copied;
	private ClipboardManager cm;
	private TextView tv_copy;
	//	private BroadcastReceiver re=new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			if("接到夜间模式的广播"){
//				init夜间模式();
//			}
//		}
//	};
//
//	public void initNight(){
//		tv1.setTextColor(Black);
//		tv2.setText(Black);
//		tv3.setText(Black);
//	}

	//配置文件
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置主题必须在setContentView之前
		setTheme(currentTheme);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		initView();
		initData();
	}

	private void initData() {
		initText1();
		initText2();
		initText3();
		initText4();
		initClipManager();
		//将Cookie添加到请求头中
	}
	private void initClipManager() {
		//获取剪切板管理者
		cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		tv_copied.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				cm.setText(tv_copied.getText());
				ClipData cd = ClipData.newPlainText("tag", tv_copied.getText());
				cm.setPrimaryClip(cd);
				return false;
			}
		});

		tv_copy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				tv_copy.setText(cm.getText());
				ClipData cd = cm.getPrimaryClip();
				ClipData.Item item = cd.getItemAt(0);
				tv_copy.setText(item.getText());
			}
		});

	}

	private void initText4() {
		String text="老王,老李,老张觉得很赞";
		MyURLSpan us=new MyURLSpan("url+action");
		SpannableString ss=new SpannableString(text);
		ss.setSpan(us,0,text.indexOf(","),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		tv4.setText(ss);
		tv4.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private class MyURLSpan extends URLSpan{

		public MyURLSpan(String url) {
			super(url);
		}

		@Override
		public void onClick(View widget) {
//			Intent intent=new Intent();
//			intent.setAction("laowangaction");
//			intent.addCategory(Intent.CATEGORY_DEFAULT);
//			startActivity(intent);
			Toast.makeText(MainActivity.this,"跳转到老王的主页:"+getURL(),Toast.LENGTH_SHORT).show();
		}

	}


	private void initText3() {

		//超链接
		String text="<a href='http://www.baidu.com'>百度</a>";
		Spanned spanned = Html.fromHtml(text);
		tv3.setText(spanned);
		//设置超链接生效
		tv3.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void initText2() {
		String text="老王,老李,老张觉得很赞";
		ForegroundColorSpan fcs=new ForegroundColorSpan(Color.RED);
		SpannableString ss=new SpannableString(text);
		ss.setSpan(fcs,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//		fcs=new ForegroundColorSpan(Color.RED);
		ss.setSpan(fcs,3,4,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//		fcs=new ForegroundColorSpan(Color.RED);
		ss.setSpan(fcs,6,9,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		tv2.setText(ss);
	}

	private void initText1() {
		String text="我是[机器人]";
		Drawable drawable=getResources().getDrawable(R.drawable.ic_launcher);
		drawable.setBounds(0,0,100,100);
		ImageSpan is=new ImageSpan(drawable);
		//富文本处理,让文字和图片一起显示
		SpannableString ss=new SpannableString(text);
		//改变原有的文本为富文本
		ss.setSpan(is,text.indexOf("["),text.indexOf("]")+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		tv1.setText(ss);
		//代码版本适配
//		if(Build.VERSION.SDK_INT>24){
//
//		}else{
//
//		}
	}

	private void initView() {
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv_copied = (TextView) findViewById(R.id.tv_copied);
		tv_copy = (TextView) findViewById(R.id.tv_copy);
	}


	public void switchTheme(View view){
		currentTheme=R.style.RedTheme;
		//让Activity重启
		recreate();
	}

}
