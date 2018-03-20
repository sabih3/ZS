package netaq.com.zayedsons.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.model.Lookup;

/**
 * Created by sabih on 20-Mar-18.
 */

class SearchAbleListHolder extends RecyclerView.ViewHolder{


    @BindView(R.id.row_city_cityName) public TextView cityTextView;

    public SearchAbleListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindData(Lookup.Lookups item) {
        cityTextView.setText(item.getTitle());
    }
}
