package com.xuxiang.common.activity.tool;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xuxiang.common.R;
import com.xuxiang.common.app.AppCode;
import com.xuxiang.common.app.base.ActivityInfo;
import com.xuxiang.common.app.base.BaseActivity;
import com.xuxiang.common.app.base.GlideApp;
import com.xuxiang.common.util.LoadingUtil;
import com.xuxiang.common.util.SelectFileUtil;
import com.xuxiang.common.util.UpdataFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件选择
 */
@ActivityInfo(layout = R.layout.activity_file_select)
public class FileSelectActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btn_title_bar_right)
    TextView sure;

    private ExcelAdapter excelAdapter;
    private ArrayList<FileInfo> xlsData = new ArrayList<>();

    @Override
    protected void init() {
        setTitle("文件选择");
        sure.setText("确定");
        sure.setVisibility(View.VISIBLE);
        sure.setOnClickListener(view -> {
            int position = excelAdapter.getPosition();
            if(position < 0) {
                return;
            }
            FileInfo fileInfo = xlsData.get(position);
            Intent intent = new Intent();
            intent.putExtra(AppCode.INTENT_OBJECT, fileInfo);
            setResult(1002, intent);
            finish();
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        excelAdapter = new ExcelAdapter(null);
        recycler.setAdapter(excelAdapter);
        initFile();
    }



    private void initFile() {
        LoadingUtil.showLoading(this, "正在查找Excel文件");
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//                getExcel();
                scanDirNoRecursion(Environment.getExternalStorageDirectory().toString());
                emitter.onNext(new Object());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    excelAdapter.setList(xlsData);
                    LoadingUtil.hide();
                });
    }

    private void getExcel() {
        String[] columns = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MIME_TYPE, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.DATA};
        String select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.xls'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.xlsx'" + ")";
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select, null, null);
        int columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(columnIndexOrThrow_DATA);
                FileInfo document = SelectFileUtil.getFileInfoFromFile(new File(path));
                xlsData.add(document);
            }
            cursor.close();
        }
    }


    /**
     * 非递归
     *
     * @param path
     */
    public void scanDirNoRecursion(String path) {
        LinkedList list = new LinkedList();
        File dir = new File(path);
        File file[] = dir.listFiles();
        if (file == null) return;
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory())
                list.add(file[i]);
            else {
                System.out.println(file[i].getAbsolutePath());
            }
        }
        File tmp;
        while (!list.isEmpty()) {
            tmp = (File) list.removeFirst();//首个目录
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    if (file[i].isDirectory())
                        list.add(file[i]);//目录则加入目录列表，关键
                    else {
//                        System.out.println(file[i]);
                        if (file[i].getName().endsWith(".xls") || file[i].getName().endsWith(".xlsx")) {
                            //往图片集合中 添加图片的路径
                            FileInfo document = SelectFileUtil.getFileInfoFromFile(new File(file[i].getAbsolutePath()));
                            xlsData.add(document);
                        }
                    }
                }
            } else {
                System.out.println(tmp);
            }
        }
    }

    class ExcelAdapter extends BaseQuickAdapter<FileInfo, BaseViewHolder>{
        private int position = -1;

        public ExcelAdapter(List<FileInfo> data) {
            super(R.layout.item_file_excel, data);
        }

        public int getPosition() {
            return position;
        }

        @Override
        protected void convert(BaseViewHolder holder, FileInfo fileInfo) {
            ImageView icon = holder.getView(R.id.icon);
            ImageView select_icon = holder.getView(R.id.select_icon);
            GlideApp.with(FileSelectActivity.this)
                    .load(SelectFileUtil.getFileTypeImageId(FileSelectActivity.this, fileInfo.getFilePath()))
                    .into(icon);
            holder.setText(R.id.name, fileInfo.getFileName());
            holder.setText(R.id.length, SelectFileUtil.FormetFileSize(fileInfo.getFileSize()));
            holder.setText(R.id.date, fileInfo.getTime());

            if(position == holder.getAdapterPosition()) {
                select_icon.setVisibility(View.VISIBLE);
            } else {
                select_icon.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(view -> {
                position = holder.getAdapterPosition();
                sure.setEnabled(true);
                sure.setSelected(true);
                notifyDataSetChanged();
            });
        }
    }
}
