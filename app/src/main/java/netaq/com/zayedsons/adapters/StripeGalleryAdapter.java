package netaq.com.zayedsons.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.network.model.responses.ResponseEventGallery;
import netaq.com.zayedsons.utils.Utils;

/**
 * Created by sabih on 25-Mar-18.
 */
public class StripeGalleryAdapter extends RecyclerView.Adapter<StripeGalleryAdapter.StripeGalleryHolder> {
    private final Context mContext;
    private List<ResponseEventGallery.Albums.Gallery> items;
    OnGalleryItemClickListener galleryClickListener;

    public void setGalleryClickListener(OnGalleryItemClickListener galleryClickListener) {
        this.galleryClickListener = galleryClickListener;
    }



    public StripeGalleryAdapter(Context context, List<ResponseEventGallery.Albums.Gallery> items) {
        this.mContext = context;
        this.items = items;
    }


    @Override
    public StripeGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_event_gallery,
                                                parent, false);
        StripeGalleryHolder holder = new StripeGalleryHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(StripeGalleryHolder holder, int position) {
        ResponseEventGallery.Albums.Gallery galleryImage = items.get(position);

        String resolvedURL = Utils.getResolvedURL(galleryImage.getFileURL());

        Picasso.with(mContext).load(resolvedURL).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryClickListener.onGalleryItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    class StripeGalleryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.strip_image_view)ImageView imageView;

        public StripeGalleryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface OnGalleryItemClickListener{
        void onGalleryItemClicked(int position);
    }
}