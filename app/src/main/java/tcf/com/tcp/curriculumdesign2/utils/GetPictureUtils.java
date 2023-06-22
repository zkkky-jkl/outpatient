package tcf.com.tcp.curriculumdesign2.utils;

import android.app.Activity;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

public class GetPictureUtils {
    public interface  OnSubmitListener{
        void onSubmit(List<String> fileList);
    }
    public static void getPics(Activity activity,OnSubmitListener l) {
        PictureSelector.create(activity)
                .openGallery(SelectMimeType.ofAll())
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        ArrayList<String> fileList=new ArrayList<>();
                        //取第0个数据，转为Uri资源，测试能否正常显示
                        for (LocalMedia localMedia : result) {
                            fileList.add(localMedia.getRealPath());
                        }
                        if(l!=null){
                            l.onSubmit(fileList);
                        }
                    }
                    @Override
                    public void onCancel() {
                        // onCancel Callback
                    }
                });
    }
}
