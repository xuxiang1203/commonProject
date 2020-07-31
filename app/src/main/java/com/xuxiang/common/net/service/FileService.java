package com.xuxiang.common.net.service;


import com.xuxiang.common.net.callback.CommonResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface FileService {
    /**
     * 批量上传图片
     * */
    @Multipart
    @POST("file/manyupload")
    Observable<CommonResult<List<String>>> updateImg(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part... file);

    /**
     * 上传图片
     * */
    @Multipart
    @POST("file/upload")
    Observable<CommonResult<String>> updateImg(@Part MultipartBody.Part... file);

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);

}
