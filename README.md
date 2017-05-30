# 小手Android SDK客户端说明文档
[![License MIT](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](https://raw.githubusercontent.com/seven/XSSDKDemo/master/LICENSE)&nbsp;
[![Support](https://img.shields.io/badge/support-iOS%208%2B%20-blue.svg?style=flat)](https://www.apple.com/nl/ios/)&nbsp;


演示项目
==============
查看并运行 `Android-SDKDemo/XSSDKDemo`


使用
==============


1. 下载 XSSDKDemo 文件夹内的所有内容。<br/>
<img src="https://github.com/xiaoshouhudong/iOSSDKDemo/blob/master/Snapshots/FrameworkPath.png"><br/>
2. 将 Frameworks 内的XSSDK.framework和XSSDK.xcassets添加(拖放)到你的工程目录中。
<img src="https://github.com/xiaoshouhudong/iOSSDKDemo/blob/master/Snapshots/Framework.png"><br/>
3. 在对应项目Targets下找到General。<br/>
   在Deployment Info下支持Device Orientation支持Portrail、Landscape Left、Landscape Right 3个方向。否则用户中心。游戏需自行限制横屏还是竖屏。<br/>
   并且在Embedded Binaries和Linked Frameworks and Libraries链接 frameworks:
<img src="https://github.com/xiaoshouhudong/iOSSDKDemo/blob/master/Snapshots/FrameworkLink.png"><br/>

5. 导入 `<XSSDK/XSSDK.h>`。
```
#import <XSSDK/XSSDK.h>
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
