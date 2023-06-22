package tcf.com.tcp.curriculumdesign2.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.utils.ActivityCompatHelper;

import tcf.com.tcp.curriculumdesign2.R;

public class GlideEngine implements ImageEngine {
    @Override
    public void loadImage(Context context, String url, ImageView imageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
    @Override
    public void loadImage(Context context, ImageView imageView,
                          String url, int maxWidth, int maxHeight) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .override(maxWidth, maxHeight)
                .into(imageView);
    }
    @Override
    public void loadAlbumCover(Context context, String url, ImageView imageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .sizeMultiplier(0.5f)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }
    @Override
    public void loadGridImage(Context context, String url, ImageView imageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }
    @Override
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }
    @Override
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }
    private GlideEngine() {
    }
    private static final class InstanceHolder {
        static final GlideEngine instance = new GlideEngine();
    }
    public static GlideEngine createGlideEngine() {
        return InstanceHolder.instance;
    }
}