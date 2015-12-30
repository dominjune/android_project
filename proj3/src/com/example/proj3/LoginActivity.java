package com.example.proj3;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button login;
    private EditText stuId,stuPsw,checkNum;
    private ImageView checkPic;
    Bitmap bitmap;
    static final int GET_PICTURE = 125;
    static final int LOG_IN = 110;
    
    
    String id,password;//调试数据

    OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			id = "";
			password = "";
			
			tryLogin();
			
			if(stuId.getText().toString().equals(id)
					&&stuPsw.getText().toString().equals(password)){
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);    
			}
		}
	};
	
    
    Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == GET_PICTURE) {// 显示从网上下载的图片
				checkPic.setImageBitmap(bitmap);
			} else if(msg.what == LOG_IN) {// 登录
				String returnMsg = msg.obj.toString();
				checkNum.setText(returnMsg);
				if(returnMsg.equals("登录成功")){
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
				} else{
					//Toast.makeText(LoginActivity.this, returnMsg, Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		login = (Button)findViewById(R.id.btn_log);
		stuId = (EditText)findViewById(R.id.student_id);
		stuPsw = (EditText)findViewById(R.id.student_password);
		checkNum = (EditText)findViewById(R.id.student_check);
		checkPic = (ImageView)findViewById(R.id.login_checkPic);
		
		login.setOnClickListener(listener);//设置登录按钮点击监听器
		
		ShowPic();//显示验证码
	}
    
	//创建并启动一个新线程用于获取验证码图片
	private void ShowPic() {
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					URL url = new URL("http://uems.sysu.edu.cn/jwxt/jcaptcha/");
					InputStream is = url.openStream();
					// 把InputStream 转化成ByteArrayOutputStream
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len;
					while ((len = is.read(buffer)) > -1) {
						baos.write(buffer, 0, len);
					}
					baos.flush();
					is.close(); // 关闭输入流
					// 将ByteArrayOutputStream 转化成InputStream
					is = new ByteArrayInputStream(baos.toByteArray());
					// 将InputStream 解析成Bitmap
					bitmap = BitmapFactory.decodeStream(is);
					// 通知UI 线程显示图片
					handler.sendEmptyMessage(GET_PICTURE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		thread.start();
	}
    
	//创建并启动一个新线程用于登录
	private void tryLogin() {
		Thread thread = new Thread(){
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try{
					//使用URL建立连接
					String url1 = "http://121.42.197.116/login";
					connection = (HttpURLConnection)(new URL(url1).openConnection());

					//设置方法
					connection.setRequestMethod("POST");
					connection.setConnectTimeout(3000000);
					connection.setReadTimeout(3000000);
					connection.setDoOutput(true);
					connection.setDoInput(true);
					
					//POST数据
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes("user=" + stuId.getText().toString()+
							"&password=" + stuPsw.getText().toString());
					
					//获取数据
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null) {
						response.append(line);
					}
					
					//handler发送消息
					Message message = new Message();
					message.what = LOG_IN;
				    message.obj = response.toString();
				    handler.sendMessage(message);
				} catch (IOException e){
					e.printStackTrace();
				} finally {
					if(connection != null) {
						connection.disconnect();
					}
				}
			}
			
		};
		thread.start();
	}
	
	//重新进入该页面，清空密码
	@Override
	protected void onResume() {
		super.onResume();
		stuPsw.setText("");   //清空密码
		if(!stuId.getText().toString().equals(""))
			stuPsw.requestFocus();//密码输入框获取焦点
		checkNum.setText(""); //清空验证码
		ShowPic();            //刷新验证码
	}
}
