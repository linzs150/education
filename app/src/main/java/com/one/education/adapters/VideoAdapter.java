package com.one.education.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.common.framework.infra.Handlers;
import com.one.education.beans.BitmapBean;
import com.one.education.beans.VideoBean;
import com.one.education.education.R;
import com.one.education.utils.Utilts;
import com.one.education.widget.CircleRectangleImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @创建者 Administrator
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<String> videoBeanList;
    private Map<Integer, Bitmap> bitmapMap = new HashMap<>();
    private SearchListener listener;
    private Bitmap bitmap;

    public interface SearchListener {
        void click(int position);
    }

    public void setSearchListener(SearchListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateTeacherInfo(List<String> videoBeenList) {
        this.videoBeanList = videoBeenList;
        notifyDataSetChanged();
    }

    public VideoAdapter(Context context, List<String> list) {
        mCtx = context;
        videoBeanList = list;
        layoutInflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemCount() {
        return videoBeanList == null ? 0 : videoBeanList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        HolderView holderView = (HolderView) holder;
        if (!TextUtils.isEmpty(videoBeanList.get(position))) {
//            loadVideo(holderView, videoBeanList.get(position));
            //            ImageLoader.loadAdImage(videoBeanList.get(position).getVideoUrl(), holderView.imge_video);

//            holderView.imge_video.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mCtx)
                    .setDefaultRequestOptions(
                            new RequestOptions()
                                    .frame(1000000)
                                    .centerCrop()
                    )
                    .load(videoBeanList.get(position))
                    .into(holderView.imge_video);
        }

        holderView.video_fragmelayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.click(position);
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.video_item, parent, false);
        HolderView holder = new HolderView(inflate);

        return holder;
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    BitmapBean bitmapBean = (BitmapBean) msg.obj;
                    bitmapBean.getHolderView().imge_video.setImageBitmap(bitmapBean.getBitmap());
                    break;
            }

            super.handleMessage(msg);
        }
    };


    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            video_fragmelayout = itemView.findViewById(R.id.video_fragmelayout);
            imge_video = itemView.findViewById(R.id.criv_image);
            content = itemView.findViewById(R.id.content);
            second_content = itemView.findViewById(R.id.second_content);
        }

        FrameLayout video_fragmelayout;
        CircleRectangleImageView imge_video;
        TextView content;
        TextView second_content;

    }

    //
    private void loadVideo(HolderView holderView, String video) {

        if (video.contains(".mp4") || video.contains(".avi") || video.contains(".rmvb") || video.contains(".3gp")) {
            if (bitmapMap.containsKey(holderView.getAdapterPosition())) {
                bitmap = bitmapMap.get(holderView.getAdapterPosition());
                holderView.imge_video.setImageBitmap(bitmap);
            }
        } else {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    try {
                        retriever.setDataSource(video, new HashMap<>());
                        bitmap = retriever.getFrameAtTime();
                        bitmapMap.put(holderView.getAdapterPosition(), bitmap);
                        if (bitmap != null) {
                            Message message = new Message();
                            BitmapBean bitmapBean = new BitmapBean();
                            message.what = 1;
                            message.obj = bitmapBean;
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        retriever.release();
                    }
                }
            });
        }

    }


}
