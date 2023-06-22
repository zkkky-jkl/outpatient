package tcf.com.tcp.curriculumdesign2.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class PermissionUtils {
    public static void requestPermissions(AppCompatActivity activity,
                                          String ... permissions){
        //String... 为可变参数，可接受1个或多个同类型的参数，取数时按数组处理
        ArrayList<String> list=new ArrayList<>();
        for(String s:permissions){//先检测哪些权限已被申请成功
            if(ContextCompat.checkSelfPermission(activity,s)!=
                    PackageManager.PERMISSION_GRANTED) {
                list.add(s);
                //将未申请成功的权限加到list
            }
        }
        if(list.isEmpty()){
            return;//不需要申请权限，直接返回
        }
        String[] neededPermissions = list.toArray(new String[list.size()]);
        //将列表list转换为数组
        ActivityResultContracts.RequestMultiplePermissions contract =
                new ActivityResultContracts.RequestMultiplePermissions();
        ActivityResultLauncher<String[]> launcher =
                activity.registerForActivityResult(contract, result -> {
                    //result为结果回调，以Map形式存储各请求结果，key为权限，value为结果
                    //value为true，权限申请成功；value为false，权限申请失败
                    for (String p : neededPermissions) {
                        if (result.get(p)) {
                            showToast(activity,p+"申请成功");
                        }else {
                            if(p.contentEquals(Manifest.permission.MANAGE_EXTERNAL_STORAGE)){
                                requestAllFilesAcess(activity);
                                continue;
                            }
                            showToast(activity,p + "无权限，请到设置中手动开启");
                        }
                    }
                });
        launcher.launch(neededPermissions);//申请未授权的权限
    }
    private static void showToast(Activity activity,String s) {
        Toast.makeText(activity,s,Toast.LENGTH_LONG).show();
    }
    private static void requestAllFilesAcess(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            boolean highPermission = false;
            highPermission = Environment.isExternalStorageManager();
            if (!highPermission) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        }
    }
}

