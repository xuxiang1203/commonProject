package com.xuxiang.common.adapter.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuxiang.common.R;

import java.util.ArrayList;
import java.util.List;

public class ChosePicTipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> picPaths;
    private final static int TYPE_PIC = 0;//图片
    private final static int TYPE_ADD = 1;//add
    public OnAddPicListener listener;
    private int maxCount;

    public void setListener(OnAddPicListener listener) {
        this.listener = listener;
    }

    public ChosePicTipAdapter(Context context, List<String> picPaths, int maxCount) {
        this.context = context;
        this.picPaths = picPaths == null ? new ArrayList<>() : picPaths;
        this.maxCount = maxCount;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_PIC:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chose_pic, parent, false);
                return new PicViewHolder(view);
            case TYPE_ADD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chose_pic_tip, parent, false);
                return new AddViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_PIC:
                PicViewHolder picViewHolder = (PicViewHolder) holder;
                Glide.with(context).load(picPaths.get(position)).into(picViewHolder.imageView);
                break;
            case TYPE_ADD:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return picPaths.size() == maxCount ? picPaths.size() : picPaths.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (picPaths.size() == maxCount) {
            return TYPE_PIC;
        } else {
            return position == picPaths.size() ? TYPE_ADD : TYPE_PIC;
        }
    }

    class PicViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView delete;

        public PicViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            delete = itemView.findViewById(R.id.img_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picPaths.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
    }


    class AddViewHolder extends RecyclerView.ViewHolder {
        LinearLayout imageView;

        public AddViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAddClick();
                    }
                }
            });
        }
    }


    public interface OnAddPicListener {
        void onAddClick();
    }
}
