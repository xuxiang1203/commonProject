package com.xuxiang.common.app.base;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xuxiang.common.R;
import com.xuxiang.common.util.transform.GlideCircleTransform;

@GlideExtension
public class XyGlideExtension {
    /**
     * 封面图加载错误 显示的默认图片
     */
    private static final int CoverImg = R.mipmap.ic_launcher;

    /**
     * 头像加载错误 显示的默认图片
     */
    private static final int HeadImg = R.mipmap.ic_launcher;

    /**
     * 将构造方法设为私有，作为工具类使用
     */
    private XyGlideExtension() {
    }

    /**
     * 加载 圆形 图片
     *
     * @param options
     * @return
     */
    @GlideOption
    public static BaseRequestOptions<?> circleHead(BaseRequestOptions<?> options) {
        MultiTransformation multiTransformation = new MultiTransformation(new CenterCrop(), new GlideCircleTransform());
        options.apply(RequestOptions.bitmapTransform(multiTransformation));
        return options;
    }


}
