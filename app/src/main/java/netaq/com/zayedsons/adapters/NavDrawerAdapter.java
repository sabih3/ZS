package netaq.com.zayedsons.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import netaq.com.zayedsons.R;

/**
 * Created by sabih on 15-Feb-18.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.NavDrawerHolder> {
    private final Context context;
    private List<String> items;

    public NavDrawerAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public NavDrawerHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_nav_drawer, parent, false);
        return new NavDrawerHolder(v);
    }

    @Override
    public void onBindViewHolder(NavDrawerHolder holder, int position) {
        //String item = items.get(position);
        //TODO Fill in your logic for binding the view.
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 6;
        }
        return items.size();
    }

    public class NavDrawerHolder extends RecyclerView.ViewHolder{

        public NavDrawerHolder(View itemView) {
            super(itemView);
        }
    }
}