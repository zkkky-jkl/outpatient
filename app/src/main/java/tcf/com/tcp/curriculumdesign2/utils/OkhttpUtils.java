package tcf.com.tcp.curriculumdesign2.utils;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {
    /** 需要在Module Gradle文件的dependencies标签中增加Okhttp组件
     implementation 'com.squareup.okhttp3:okhttp:4.9.3'
     */
    private static OkHttpClient client;
    private static  HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    //Cookie的存储对象

    //client是单例模式，始终只有1个对象
    private static OkHttpClient getClient() {
        synchronized (OkhttpUtils.class) {
            //加锁避免多个线程同时调用getClient()，在client==null时重复生成实例
            //对WebApiUtils.class加锁，多个线程只能有1个线程能访问此代码
            if (client == null) {

                //client 自动加载cookie和更新cookie

                client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJar() {
                            @Override
                            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                                cookieStore.put(httpUrl.host(), list);
                            }
                            @Override
                            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                                return cookies != null ? cookies : new ArrayList<Cookie>();
                            }
                        })
                        .build();
            }
            return client;
        }
    }
    public interface OnReadFinishedListener{
        //自定义接口，定义两个回调，分别用于读取成功和出错处理
        public void onFinished(String readOutList);
        //读取成功，将Json数据转换为类列表数据返回
        public void onFail(String e);
        //读取失败，回传错误信息
    }
    public static String uploadFileBlock(Activity activity,String url,String filePath) throws Exception{
        OkHttpClient c = getClient();
        File f=new File(filePath);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM).addFormDataPart("file",
                        f.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f))
                .build();
        Request request = new Request.Builder().url(url).post(multipartBody).build();
        Call call = c.newCall(request);
        Response response = call.execute();
        String s = response.body().string();
        com.alibaba.fastjson.JSONObject object = JSON.parseObject(s);
        if(object.getBoolean("ok")) {
            String path0 = object.getString("path");
            return path0;
        }else {
            throw new Exception(object.getString("msg"));
        }
    }

    public static void getApiDataAsync(Activity activity, String url,
                                       JSONObject jsonObject,
                                       OnReadFinishedListener l){
        //传入Activity对象，用于切换线程
        OkHttpClient c = getClient();//通过调用getClient()得到OkHttpClient单例
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String json_params = String.valueOf(jsonObject);
        String params0 = json_params.replaceAll("\\\\\"", "\"");
        String param1 = params0.replaceAll("\"\\{", "\\{");
        String params = param1.replaceAll("\"\\}", "\\}");

        RequestBody requestBody = RequestBody.create(mediaType,
                params);



        Request request = new Request.Builder().url(url).post(requestBody).build();
        //构造一个请求对象Request，url()传url网址，get()是GET请求的方法
        //请求方法有get()、post()、delete()、put()等方法
        Call call = client.newCall(request);//利用Request生成一个call对象
        //每一次访问对应一个call对象，异步访问时将call对象加入队列
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(new Runnable() {//切换到UI线程
                    @Override
                    public void run() {
                        l.onFail(e.toString());
                        //通过自定义接口将错误信息通过onFail()传递到UI线程
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call,
                                   @NonNull Response response){
                //修改原回调方法，去除throws异常语句，直接捕捉处理
                try {
                    String s = response.body().string();//得到响应的文本
                    //注意是string()方法，不是toString()方法


                    activity.runOnUiThread(new Runnable() {//切换到UI线程
                        @Override
                        public void run() {
                            l.onFinished(s);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {//切换到UI线程
                        @Override
                        public void run() {
                            l.onFail(e.toString());//错误信息通过接口回调传给调用者
                        }
                    });
                }
            }
        });
    }
    public static void getApiDataAsync(Activity activity, String url,
                                       String params,
                                       OnReadFinishedListener l){
        //传入Activity对象，用于切换线程
        OkHttpClient c = getClient();//通过调用getClient()得到OkHttpClient单例
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType,
                params);



        Request request = new Request.Builder().url(url).post(requestBody).build();
        //构造一个请求对象Request，url()传url网址，get()是GET请求的方法
        //请求方法有get()、post()、delete()、put()等方法
        Call call = client.newCall(request);//利用Request生成一个call对象
        //每一次访问对应一个call对象，异步访问时将call对象加入队列
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(new Runnable() {//切换到UI线程
                    @Override
                    public void run() {
                        l.onFail(e.toString());
                        //通过自定义接口将错误信息通过onFail()传递到UI线程
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call,
                                   @NonNull Response response){
                //修改原回调方法，去除throws异常语句，直接捕捉处理
                try {
                    String s = response.body().string();//得到响应的文本
                    //注意是string()方法，不是toString()方法
                    activity.runOnUiThread(new Runnable() {//切换到UI线程
                        @Override
                        public void run() {
                            l.onFinished(s);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {//切换到UI线程
                        @Override
                        public void run() {
                            l.onFail(e.toString());//错误信息通过接口回调传给调用者
                        }
                    });
                }
            }
        });
    }
    public static void getApiDataBackground( String url,
                                       String params,
                                       OnReadFinishedListener l){
        //直接在后台处理，没有进行线程切换，不对外部开放，仅用在内部
        OkHttpClient c = getClient();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, params);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                l.onFail(e.toString());
            }
            @Override
            public void onResponse(@NonNull Call call,
                                   @NonNull Response response){
                //修改原回调方法，去除throws异常语句，直接捕捉处理
                try {
                    String s = response.body().string();//得到响应的文本
                    l.onFinished(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    l.onFail(e.toString());
                }
            }
        });
    }


}

