package com.xshd.xssdkdemo;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


import com.xshd.sdk.XSSDK;
import com.xshd.sdk.models.biz.output.XSOrder;
import com.xshd.sdk.models.biz.output.XSRole;
import com.xshd.sdk.models.biz.output.XSUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imbg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        final Button loginButton = (Button) findViewById(R.id.login);
        final Button payButton = (Button) findViewById(R.id.pay);
        final Button centerButton = (Button) findViewById(R.id.center);
        final Button logoutButton = (Button) findViewById(R.id.logout);

        imbg = (ImageView) findViewById(R.id.imbg);

        XSSDK.getInstance().xsInit(MainActivity.this, "1", "8ccde908dd33ae301d26a65847505f70");

        XSSDK.getInstance().setSdkLogoutCallback(new XSSDK.IXSSDKLogoutCallback() {
            @Override
            public void logout() {
                Log.e("XSSDKDemo", "注销成功");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XSSDK.getInstance().xsLogin(new XSSDK.IXSSDKLoginCallback() {
                    @Override
                    public void loginSuccess(XSUser user) {
                        String username = user.getUsername();
                        String token = user.getToken();
                        String userId = user.getUsername();
                        String text = "userId = " + userId + ";username = " + username + ";token = " + token;
                        Log.e("XSSDKDemo", "登陆成功" + text);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                        Date curDate =  new Date(System.currentTimeMillis());
                        String timeStr = formatter.format(curDate);

                        //登陆成功后上报角色信息
                        XSRole role = new XSRole();
                        role.setRoleId("9527");
                        role.setRoleName("凯特琳");
                        role.setServerId("server1");
                        role.setServerName("紫陌红尘");
                        role.setRoleLevel(1);
                        role.setLoginTime(timeStr);
                        XSSDK.getInstance().xsSaveRole(role);
                    }

                    @Override
                    public void loginFail(String errorString) {
                        Log.e("XSSDKDemo", "登陆失败" + errorString);
                    }
                });
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XSOrder orderModel = new XSOrder();
                orderModel.setServerId("serverId1");
                orderModel.setServerName("紫陌红尘");
                orderModel.setAmount(1);
                orderModel.setRoleId("9527");
                orderModel.setRoleName("GG20思密达");
                orderModel.setProductId("ProductId1");
                orderModel.setProductName("拉克丝小姐姐");
                orderModel.setProductDescription("真是一个深思熟虑的选择");
                orderModel.setOrderId(getOrderStringByTime());
                orderModel.setCustomInfo("自定义字段");
                Log.e("HGSDKDemo","" + orderModel.toString());
                XSSDK.getInstance().xsPay(orderModel);
            }
        });

        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XSSDK.getInstance().xsUserCenter();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XSSDK.getInstance().xsLogout();
            }
        });

    }

    public String getOrderStringByTime(){
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeNowString = dateFormat.format(nowDate) + getRandom();
        return timeNowString;
    }


    public String getRandom() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    @Override
    protected void onResume() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            imbg.setImageResource(R.drawable.bgp);
        }
        else
        {
            imbg.setImageResource(R.drawable.bgl);
        }

        XSSDK.getInstance().getFloatView().showFloatView();
        super.onResume();
    }

    @Override
    protected void onStop() {
        XSSDK.getInstance().getFloatView().removeFloatView();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        XSSDK.getInstance().getFloatView().removeFloatView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        XSSDK.getInstance().xsExit(new XSSDK.IXSSDKExitCallback() {
            @Override
            public void exit(boolean isExist) {
                Log.e("HGSDKDemo","" + isExist);
            }
        });
    }
}
