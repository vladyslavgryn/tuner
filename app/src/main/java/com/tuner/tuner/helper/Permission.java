package com.tuner.tuner.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

public class Permission {

    private Context context;
    private PermissionInterface permissionInterface;

    public Permission(Context context) {
        this.context = context;
    }

    public void setPermissionInterface(PermissionInterface permissionInterface) {
        this.permissionInterface = permissionInterface;
    }

    public void checkPermission(@NonNull String permission, @NonNull Activity activity) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                permissionInterface.requestPermissionRationale();
            } else {
                permissionInterface.requestPermission();
            }
        } else {
            permissionInterface.permissionGranted();
        }
    }

    public void requestPermission(@NonNull String[] permissions, @NonNull Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}
