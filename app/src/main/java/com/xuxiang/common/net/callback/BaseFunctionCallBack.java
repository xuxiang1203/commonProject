package com.xuxiang.common.net.callback;


import com.xuxiang.common.net.HttpConfig;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @param <COME> 接受的类型
 * @param <BACK> 返回的类型
 * @describe 用于RxJava-Retrofit链式调用 flatMap 操作符统一处理
 */
public abstract class BaseFunctionCallBack<COME, BACK> implements Function<CommonResult<COME>, ObservableSource<CommonResult<BACK>>> {
    @Override
    public ObservableSource<CommonResult<BACK>> apply(CommonResult<COME> tCommonResult) throws Exception {
        if (HttpConfig.HTTP_SUCCESS == tCommonResult.getStatus()){
            return back(tCommonResult.getData());
        }
        return Observable.error(new Throwable(tCommonResult.getMessage()));
    }
    public abstract ObservableSource<CommonResult<BACK>> back(COME result);
}
