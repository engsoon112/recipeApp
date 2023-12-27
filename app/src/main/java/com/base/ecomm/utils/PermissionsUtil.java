package com.base.ecomm.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PermissionsUtil {

    // try {
    //     PermissionsUtil.requestPermissions(this, PermissionsUtil.PERMISSIONS_GROUP);
    // } catch (Exception e) {
    //     e.printStackTrace();
    // }

    // 状态码、标志位
    public static final int REQUEST_STATUS_CODE = 0x001;
    public static final int REQUEST_PERMISSION_SETTING = 0x002;

    //常量字符串数组，将需要申请的权限写进去，同时必须要在Androidmanifest.xml中声明。
    public final static String[] PERMISSIONS_ALL = PERMISSIONS_ALL();

    private static String[] PERMISSIONS_ALL() {
        return new String[]{
                Manifest.permission.READ_CONTACTS
        };
    }

    public final static String[] PERMISSIONS_DEFAULT = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
    };

    public final static String[] PERMISSIONS_EXTERNAL_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public final static String[] PERMISSIONS_EXTERNAL_STORAGE_CAMERA = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA , Manifest.permission.RECORD_AUDIO
    };
    public final static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public final static String[] CONTACT = {
            Manifest.permission.READ_CONTACTS
    };

    //Google Remove Photo
    public static String[] PERMISSIONS_UPLOAD_DATA() {
        return new String[]{
                Manifest.permission.READ_CONTACTS
        };
    }

    public static String[] PERMISSIONS_LOCATION() {
        return new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        };
    }


    public static boolean checkAndRequestPermissions(final Activity activity, final View view, String[] PERMISSIONS_GROUP) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        // 一个list，用来存放没有被授权的权限
        ArrayList<String> denidArray = new ArrayList<>();

        // 遍历PERMISSIONS_GROUP，将没有被授权的权限存放进denidArray
        for (String permission : PERMISSIONS_GROUP) {
            int grantCode = ActivityCompat.checkSelfPermission(activity, permission);
            if (grantCode == PackageManager.PERMISSION_DENIED) {
                denidArray.add(permission);
            }
        }

        // 将denidArray转化为字符串数组，方便下面调用requestPermissions来请求授权
        String[] denidPermissions = denidArray.toArray(new String[denidArray.size()]);

        // 如果该字符串数组长度大于0，说明有未被授权的权限
        if (denidPermissions.length > 0) {
            // 遍历denidArray，用showRationaleUI来判断，每一个没有得到授权的权限是否是用户手动拒绝的
            for (String permission : denidArray) {
                // 如果permission是用户手动拒绝的，则用SnackBar来引导用户进入App设置页面，手动授予权限
                if (!showRationaleUI(activity, permission)) {
                    // 判断App是否是首次启动
                    if (!isAppFirstRun(activity)) {
                        Snackbar snackbar = Snackbar.make(view, "Permissions in need", Snackbar.LENGTH_LONG);
                        snackbar.setAction("Go to icon_settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 进入App设置页面
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                intent.setData(uri);
                                activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            }
                        });
                        snackbar.show();
                    }
                }
                break;
            }
            requestPermissions(activity, denidPermissions);
        } else {
            return true;
        }
        return false;
    }

    /**
     * 关于shouldShowRequestPermissionRationale函数的一点儿注意事项：
     * ***1).应用安装后第一次访问，则直接返回false；
     * ***2).第一次请求权限时，用户Deny了，再次调用shouldShowRequestPermissionRationale()，则返回true；
     * ***3).第二次请求权限时，用户Deny了，并选择了“never ask again”的选项时，再次调用shouldShowRequestPermissionRationale()时，返回false；
     * ***4).设备的系统设置中，禁止了应用获取这个权限的授权，则调用shouldShowRequestPermissionRationale()，返回false。
     */
    public static boolean showRationaleUI(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * 对权限字符串数组中的所有权限进行申请授权，如果用户选择了“never ask again”，则不会弹出系统的Permission申请授权对话框
     */
    public static void requestPermissions(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_STATUS_CODE);
    }

    /**
     * 用来判断，App是否是首次启动：
     * ***由于每次调用shouldShowRequestPermissionRationale得到的结果因情况而变，因此必须判断一下App是否首次启动，才能控制好出现Dialog和SnackBar的时机
     */
    public static boolean isAppFirstRun(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (sp.getBoolean("first_run", true)) {
            editor.putBoolean("first_run", false);
            editor.commit();
            return true;
        } else {
            editor.putBoolean("first_run", false);
            editor.commit();
            return false;
        }
    }
}