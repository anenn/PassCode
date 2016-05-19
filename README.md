# PassCode

现在，大多数应用都会有相关的安全码验证操作，其主要的目的就是为了保证用户隐私和数据安全。为此，我也小试牛刀，封装一个 PassCode Library， 其核心类为 PassCodeView，同时支持接入系统软键盘和自定义键盘两种方式。

## 一. 运行效果
#### 1. 自定义键盘
![](https://cloud.githubusercontent.com/assets/7321351/15382298/91eeece0-1dbb-11e6-9e5f-48bc09053578.gif)

#### 2. 系统键盘
![](https://cloud.githubusercontent.com/assets/7321351/15383567/9610b7e4-1dc6-11e6-874a-26a55c99c35d.gif)

## 二. 使用介绍
#### 1. 快速入门

* 创建布局文件 activity_main.xml，同时添加 PassCodeView 控件

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/shape_bg"
    android:fitsSystemWindows="true"
    tools:context="com.anenn.demo.SimplePassCodeActivity">

    <TextView
        android:id="@+id/tvTip"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="92dp"
        android:text="@string/pass_code"
        android:textColor="#ffffff"
        android:textSize="18sp" />

    <com.anenn.passcode.PassCodeView
        android:id="@+id/passCodeView"
        android:layout_width="120dp"
        android:layout_height="25dp"
        android:layout_below="@+id/tvTip"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="42dp"
        android:gravity="center_vertical"
        app:circleColor="@color/passCode_circle_color"
        app:circleRadius="5dp"
        app:passCodeLength="4"
        app:strokeColor="@color/passCode_stroke_color"
        app:strokeWidth="1.5dp" />
</RelativeLayout>

```

* 打开 Actiivty，申明 PassCode 控件并设置监听事件

```
PassCodeView mPassCodeView = (PassCodeView) findViewById(R.id.passCodeView);
mPassCodeView.setOnPassCodeFillListener(new OnPassCodeFillListener() {
    @Override
    public void passCodeFill(String passCode) {
        if (mPassCode == null) {
            mPassCode = passCode;
        } else if (mPassCode.equals(passCode)) {
            mPassCode = null;
            Toast.makeText(SimplePassCodeActivity.this, "Passcode is matched, welcome!", Toast.LENGTH_LONG).show();
        } else {
            mPassCode = null;
            Toast.makeText(SimplePassCodeActivity.this, "Passcode is not matched, please try again!", Toast.LENGTH_LONG).show();
        }
    }
});
```

#### 2. 功能分析
**PassCodeView** 控件具有如下一些自定义的属性：

```
// Custom Attributes
private int strokeColor; // 边框颜色
private int strokeWidth; // 边框厚度
private int circleColor; // 实心圆的颜色
private int circleRadius; // 实心圆的半径
private int passCodeLength; // 密码长度
private boolean autoCleanPassCode; // 是否允许自动清除
private boolean isEditable; // 是否可编辑
```
这里对两个属性这一下说明：

1. autoCleanPassCode: 表示当用户输入的密码长度等于设置的密码长度时，此时是否触发清除数据事件
2. isEditable: 表示当使用手机默认的输入法时，默认弹出软键盘；在使用自定义输入法时，该位必须手动置为 false。

**PassCodeView** 包含有一个事件回调接口 OnPassCodeFillListener，其作用是当用户输入的密码位数等于指定密码长度时，如果设置了 OnPassCodeFillListener，则会调用其 passcodeFill(String passCode) 方法。

**InputMethodView** 是一个自定义的键盘控件，在该类中已经封装了事件触发及响应方法，开发者只需通过 InputMethodView 的 setupPassCode() 方法，将 PassCodeView 对象与 InputMethodView 进行关联就可以正常使用了。


