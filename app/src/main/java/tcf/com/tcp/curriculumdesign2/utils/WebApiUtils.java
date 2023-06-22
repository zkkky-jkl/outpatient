package tcf.com.tcp.curriculumdesign2.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tcf.com.tcp.curriculumdesign2.data.CommonStaticData;
import tcf.com.tcp.curriculumdesign2.server.model.BaseModel;
import tcf.com.tcp.curriculumdesign2.server.model.LoginUser;
import tcf.com.tcp.curriculumdesign2.server.model.Privacy;
import tcf.com.tcp.curriculumdesign2.server.model.User;


public class WebApiUtils {
    private static String BASE_URL = "";//在LoginActivity中被设置
    private static final String KEY_OK = "ok";
    private static final String KEY_MSG = "msg";

    public interface OnClassFinishedListener<T> {
        //访问成功后，直接转成类输出，利用泛型提高灵活性
        void onFinished(T out);
        void onFail(String e);
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static void loginWithPwd(Activity activity, LoginUser loginUser,
                                    OnClassFinishedListener<User> l) {
        String params = loginUser.toString();
        String url = String.format("%s/login", BASE_URL);
        OkhttpUtils.getApiDataBackground(url, params,
                new OkhttpUtils.OnReadFinishedListener() {
                    @Override
                    public void onFinished(String readOutList) {
                        try {
                            JSONObject jsonObject = JSON.parseObject(readOutList);
                            boolean status = jsonObject.getBoolean(KEY_OK);
                            if (status) {
                                User user = jsonObject.getObject("user", User.class);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        l.onFinished(user);
                                    }
                                });
                            } else {
                                String msg = jsonObject.getString(KEY_MSG);
                                onFail(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            onFail(e.toString());
                        }
                    }

                    @Override
                    public void onFail(String e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(activity, e);
                            }
                        });
                    }
                });
    }

    public static void getUserPrivacy(Activity activity, Long id,
                                      OnClassFinishedListener<Privacy> l){
        @SuppressLint("DefaultLocale") String params = String.format("{\n" +
                "    \"Privacy\": {\n" +
                "        \"id\": %d\n" +
                "    }\n" +
                "}", id);
        String url = String.format("%s/get", BASE_URL);
        OkhttpUtils.getApiDataBackground(url, params,
                new OkhttpUtils.OnReadFinishedListener() {
                    @Override
                    public void onFinished(String readOutList) {
                        try {
                            JSONObject jsonObject = JSON.parseObject(readOutList);
                            boolean status = jsonObject.getBoolean(KEY_OK);
                            if (status) {
                                Privacy privacy = jsonObject.getObject("Privacy", Privacy.class);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        l.onFinished(privacy);
                                    }
                                });
                            } else {
                                String msg = jsonObject.getString(KEY_MSG);
                                onFail(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            onFail(e.toString());
                        }
                    }

                    @Override
                    public void onFail(String e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(activity, e);
                            }
                        });
                    }
                });
    }

    public static void postData(Activity activity, String params, OnClassFinishedListener<String> l) {
        String url = String.format("%s/post", BASE_URL);
        doPostData(activity, params, url, l);
    }

    public static void modifyData(Activity activity, String params, OnClassFinishedListener<String> l) {
        String url = String.format("%s/put", BASE_URL);
        doPostData(activity, params, url, l);
    }

    public static void deleteData(Activity activity, String params, OnClassFinishedListener<String> l) {
        String url = String.format("%s/delete", BASE_URL);
        doPostData(activity, params, url, l);
    }

    public static void getDataById(Activity activity, long id, Class<? extends BaseModel> clazz, OnClassFinishedListener<Object> l) {
        String url = String.format("%s/get", BASE_URL);
        String className = clazz.getSimpleName();
        JSONObject obj0 = new JSONObject();
        obj0.put("id", id);
        JSONObject obj1 = new JSONObject();
        obj1.put(className, obj0);
        String params = obj1.toString();
        doPostData(activity, params, url, new OnClassFinishedListener<String>() {
            @Override
            public void onFinished(String out) {

                JSONObject object = JSON.parseObject(out);
                BaseModel data = object.getObject(className, clazz);
                if (l != null) {
                    l.onFinished(data);
                }

            }

            @Override
            public void onFail(String e) {

            }
        });

    }

    public static void uploadFile(Activity activity, String filePath, OnClassFinishedListener<String> l) {
        String url = String.format("%s/upload", BASE_URL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path0 = OkhttpUtils.uploadFileBlock(activity, url, filePath);
                    String path = BASE_URL + path0.substring(1);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            l.onFinished(path);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(activity, e.toString());
                        }
                    });
                }
            }
        }).start();
    }

    public static void uploadFiles(Activity activity, List<String> fileList, OnClassFinishedListener<List<String>> l) {
        String url = String.format("%s/upload", BASE_URL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> list=new ArrayList<>();
                    for (String file : fileList) {
                        String path0 = OkhttpUtils.uploadFileBlock(activity, url, file);
//                        String path = BASE_URL + path0.substring(1);
                        String path=path0;
                        list.add(path);
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            l.onFinished(list);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(activity, e.toString());
                        }
                    });
                }
            }
        }).start();
    }

    private static void doPostData(Activity activity, String params, String url, OnClassFinishedListener<String> l) {
        OkhttpUtils.getApiDataBackground(url, params,
                new OkhttpUtils.OnReadFinishedListener() {
                    @Override
                    public void onFinished(String readOutList) {
                        try {
                            JSONObject jsonObject = JSON.parseObject(readOutList);
                            boolean status = jsonObject.getBoolean(KEY_OK);
                            if (status) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        l.onFinished(readOutList);
                                    }
                                });
                            } else {
                                String msg = jsonObject.getString(KEY_MSG);
                                onFail(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            onFail(e.toString());
                        }
                    }

                    @Override
                    public void onFail(String e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(activity, e);
//                                l.onFail(e);
                            }
                        });
                    }
                });
    }


    private static void showToast(Activity activity, String e) {
        Toast.makeText(activity, e, Toast.LENGTH_LONG).show();
    }

}
