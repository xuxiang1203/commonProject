package com.xuxiang.common.activity.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.xuxiang.common.R;
import com.xuxiang.common.app.base.ActivityInfo;
import com.xuxiang.common.app.base.BaseActivity;
import com.xuxiang.common.util.DisplayUtil;
import com.xuxiang.common.util.takepicture.FileUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * 查看大图界面
 */
@ActivityInfo(layout = R.layout.activity_look_pic)
public class LookPicActivity extends BaseActivity {
    public final static String POSITION = "startPos";
    public final static String IMGURLS = "imgUrls";
    public final static String SHOW_DOWN = "show_down";

    private List<View> guideViewList = new ArrayList<View>();
    @BindView(R.id.guideGroup)
    LinearLayout guideGroup;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.img_dwon)
    ImageView img_dwon;

    int startPos;
    ArrayList<String> imgUrls;

    @BindView(R.id.pager)
    ViewPager viewPager;


    @Override
    protected void init() {
        setTitle("大图查看");
        startPos = getIntent().getIntExtra(POSITION, 0);
        imgUrls = getIntent().getStringArrayListExtra(IMGURLS);
        int intExtra = getIntent().getIntExtra(SHOW_DOWN, 0);
        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(imgUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < guideViewList.size(); i++) {
                    guideViewList.get(i).setSelected(i == position ? true : false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);


        addGuideView(guideGroup, startPos, imgUrls);

        if(intExtra == 1) {
            img_dwon.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.img_dwon)
    void down() {
        if (imgUrls.get(viewPager.getCurrentItem()).contains("jpg")
                || imgUrls.get(viewPager.getCurrentItem()).contains("jpeg")
                || imgUrls.get(viewPager.getCurrentItem()).contains("png")) {
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                File file = Glide.with(LookPicActivity.this)
                        .load(imgUrls.get(viewPager.getCurrentItem()))
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                if (file != null) {
                    FileUtils.saveImageToGallery(LookPicActivity.this, file);
                    emitter.onNext(file.getPath());
                }
            }).compose(getThread())
                    .compose(bindToLifecycle())
                    .subscribe(integer -> showToast("保存成功")
                            , throwable -> Toast.makeText(getApplicationContext(), "该图片地址错误或不存在", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getApplicationContext(), "该图片地址错误或不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if (imgUrls != null && imgUrls.size() > 0) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_point);
                view.setSelected(i == startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(this, 6), DisplayUtil.dip2px(this, 6));
                layoutParams.setMargins(20, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }

        }
    }

    public static class ImageSize implements Serializable {

        private int width;
        private int height;

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    private class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<String>();
        private LayoutInflater inflater;
        private Context context;
        private ImageSize imageSize;
        private ImageView smallImageView = null;

        public void setDatas(List<String> datas) {
            if (datas != null)
                this.datas = datas;
        }

        public void setImageSize(ImageSize imageSize) {
            this.imageSize = imageSize;
        }

        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            if (view != null) {
                final ImageView imageView = (ImageView) view.findViewById(R.id.image);
                imageView.setOnClickListener(v -> {
                    finish();
                });
                if (imageSize != null) {
                    //预览imageView
                    smallImageView = new ImageView(context);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageSize.getWidth(), imageSize.getHeight());
                    layoutParams.gravity = Gravity.CENTER;
                    smallImageView.setLayoutParams(layoutParams);
                    smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ((FrameLayout) view).addView(smallImageView);
                }
                //loading
                final ProgressBar loading = new ProgressBar(context);
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;
                loading.setLayoutParams(loadingLayoutParams);
                ((FrameLayout) view).addView(loading);
                final String imgurl = datas.get(position);
                loading.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(imgurl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存多个尺寸
                        .into(new DrawableImageViewTarget(imageView) {
                            @Override
                            public void onLoadStarted(@Nullable Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                loading.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                String message = "图片加载出错";
                                Toast.makeText(LookPicActivity.this, message, Toast.LENGTH_SHORT).show();
                                imageView.setBackgroundResource(R.color.color1E);
                                loading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                super.onResourceReady(resource, transition);
                                loading.setVisibility(View.GONE);
                            }
                        });
                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

}
