# 小手Android SDK客户端说明文档
[![License MIT](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](https://raw.githubusercontent.com/xiaoshouhudong/Android-SDKDemo/master/LICENSE)&nbsp;



演示项目
==============
查看并运行 `Android-SDKDemo/XSSDKDemo`


使用
==============



1. 将 libXSSDK-release.aar 添加(拖放)到你的工程目录下lib中。
<img src="https://github.com/xiaoshouhudong/Android-SDKDemo/blob/master/Snapshots/Framework.png"><br/>
2. 在对应项目下找到build.gradle添加以下库。<br/>
   
```java
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:24.0.0'
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.loopj.android:android-async-http:1.4.9'
    compile 'com.android.support:appcompat-v7:24.0.0'
    compile(name: 'libXSSDK-release', ext:'aar')
}
```


3. 导入 `com.xshd.sdk`。
```
import com.xshd.sdk.XSSDK;
import com.xshd.sdk.models.biz.output.XSOrder;
import com.xshd.sdk.models.biz.output.XSRole;
import com.xshd.sdk.models.biz.output.XSUser;
```

4. 在AndroidManifest.xml添加以下权限
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

5. 初始化SDK。并更改对应的参数

#### 初始化SDK

```java
XSSDK.getInstance().xsInit(MainActivity.this, "1", "8ccde908dd33ae301d26a65847505f70");
```

#### 登陆方法

```java
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

        //上报角色信息
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
```

#### 支付方法

```java
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
```


#### 用户注销回调方法

```java
XSSDK.getInstance().xsLogout();
```





系统要求
==============
该项目最低支持 `minSdkVersion 16`。



许可证
==============
XSSDK 使用 MIT 许可证，详情见 LICENSE 文件。
