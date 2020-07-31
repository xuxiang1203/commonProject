package com.xuxiang.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.xuxiang.common.net.HttpConfig;
import com.xuxiang.common.net.HttpHeper;
import com.xuxiang.common.net.callback.CommonResult;
import com.xuxiang.common.util.compress.PhotoCompressUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdataFileUtil {



    /**
     * 上传单图片
     * @param path
     * @return
     */
    public static Observable<CommonResult<String>> upLoadObservable(String path) {
        List<String> compress = new ArrayList<>();
        PhotoCompressUtils photoCompressUtils = new PhotoCompressUtils();
        try {
            compress.add(photoCompressUtils.compress(path));
        } catch (IOException e) {
            CommonResult commonResult = new CommonResult<String>("");
            commonResult.setMessage("上传失败");
            commonResult.setStatus(201);
            return Observable.just(commonResult);
        }
        String[] paths = compress.toArray(new String[compress.size()]);
        MultipartBody.Part[] parts = filesToMutipartBody("file", paths);
        return HttpHeper.get().updateFileService().updateImg(parts);
    }


    /**
     * 上传http图片并压缩
     *
     * @param folder
     * @param paths
     * @return
     */
    @SuppressLint("CheckResult")
    public static Observable<CommonResult<List<String>>> upLoadObservable(String folder, List<String> paths) {

        if (paths == null || paths.isEmpty()) {
            List<String> backList = new ArrayList<>();
            CommonResult commonResult = new CommonResult<List<String>>(backList);
            commonResult.setStatus(HttpConfig.HTTP_SUCCESS);
            return Observable.just(commonResult);
        }

        //处理网络图片
        List<String> localPaths = new ArrayList<>();//本地图片（需要上传）
        List<String> netPaths = new ArrayList<>();//网络图片（直接返回）
        for (int i = 0; i < paths.size(); i++) {
            if (!paths.get(i).startsWith("http")) {
                localPaths.add(paths.get(i));
            } else {
                netPaths.add(paths.get(i));
            }
        }
        CommonResult commonResult = new CommonResult<List<String>>(netPaths);
        commonResult.setStatus(HttpConfig.HTTP_SUCCESS);
        Observable<CommonResult<List<String>>> Net = Observable.just(commonResult);
        if (localPaths.size() == 0) {
            CommonResult commonResult1 = new CommonResult<List<String>>(netPaths);
            commonResult1.setStatus(HttpConfig.HTTP_SUCCESS);
            return Observable.just(commonResult1);
        }

        if (netPaths.size() != 0) {
            List<String> allPaths = new ArrayList<>();//（返回两类图片）
            //        //通过merge（）合并事件 & 同时发送事件
            return initGetAllPath(folder, localPaths, allPaths, Net);
        } else {
            return Observable.just(localPaths)
                    .subscribeOn(Schedulers.io())
                    .flatMap(list -> {
                        List<String> compress = new ArrayList<>();
                        PhotoCompressUtils photoCompressUtils = new PhotoCompressUtils();
                        for (String filePath : list) {
                            compress.add(photoCompressUtils.compress(filePath));
                        }
                        Map<String, RequestBody> map = new HashMap<>();
                        map.put("folder", getRequestBody(folder));
                        String[] path = compress.toArray(new String[compress.size()]);
                        MultipartBody.Part[] parts = filesToMutipartBody("files", path);
                        return HttpHeper.get().updateFileService().updateImg(map, parts);
                    });
        }
    }

    private static Observable<CommonResult<List<String>>> initGetAllPath(String folder, List<String> localPaths, List<String> allPaths, Observable<CommonResult<List<String>>> net) {
        return Observable.merge(Observable.just(localPaths)
                .flatMap(list -> {
                    List<String> compress = new ArrayList<>();
                    PhotoCompressUtils photoCompressUtils = new PhotoCompressUtils();
                    for (String filePath : list) {
                        compress.add(photoCompressUtils.compress(filePath));
                    }
                    Map<String, RequestBody> map = new HashMap<>();
                    map.put("folder", getRequestBody(folder));
                    String[] path = compress.toArray(new String[compress.size()]);
                    MultipartBody.Part[] parts = filesToMutipartBody("files", path);
                    return HttpHeper.get().updateFileService().updateImg(map, parts);
                }), net)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<CommonResult<List<String>>>() {
                    @Override
                    public void accept(CommonResult<List<String>> listCommonResult) throws Exception {
                        allPaths.addAll(listCommonResult.getData());
                    }
                })
                .flatMap(new Function<CommonResult<List<String>>, ObservableSource<CommonResult<List<String>>>>() {
                    @Override
                    public ObservableSource<CommonResult<List<String>>> apply(CommonResult<List<String>> listCommonResult) throws Exception {
                        CommonResult commonResult = new CommonResult<List<String>>(allPaths);
                        commonResult.setStatus(HttpConfig.HTTP_SUCCESS);
                        return Observable.just(commonResult);
                    }
                }).takeLast(1);
    }


    private static MultipartBody.Part[] filesToMutipartBody(String file, String[] path) {
        MultipartBody.Part[] parts = new MultipartBody.Part[path.length];
        int i = 0;
        for (String StringPath : path) {
            if (TextUtils.isEmpty(StringPath)) {
                continue;
            }
            MultipartBody.Part part = getFilePart(StringPath, file);
            if (part == null) {
                continue;
            }
            parts[i] = part;
            i++;
        }
        return parts;
    }

    private static MultipartBody.Part getFilePart(String filePathStr, String key) {
        File file = new File(filePathStr);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
        return part;
    }

    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    public interface onGetListLitener {
        void onListener(List<String> list);
    }

}
