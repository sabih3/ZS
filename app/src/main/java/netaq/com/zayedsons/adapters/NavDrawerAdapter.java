package netaq.com.zayedsons.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;

/**
 * Created by sabih on 15-Feb-18.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.NavDrawerHolder> {
    private final Context context;
    private List<String> items;
    private onItemClickListener listener;


    public NavDrawerAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    @Override
    public NavDrawerHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_nav_drawer, parent, false);


        return new NavDrawerHolder(v);
    }

    @Override
    public void onBindViewHolder(NavDrawerHolder holder, final int position) {

        String item = items.get(position);

        holder.itemTitle.setText(item);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onListItemClick(position);
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



    public class NavDrawerHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.row_parent)RelativeLayout parent;

        @BindView(R.id.menu_item_title) TextView itemTitle;
        public NavDrawerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface onItemClickListener{

        void onListItemClick(int position);
    }
}