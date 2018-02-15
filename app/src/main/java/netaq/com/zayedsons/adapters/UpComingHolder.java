package netaq.com.zayedsons.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;

/**
 * Created by sabih on 12-Feb-18.
 */

class UpComingHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.field_start_date)TextView startDate;
    @BindView(R.id.field_end_date)TextView endDate;
    @BindView(R.id.field_event_title)TextView eventTitle;
    @BindView(R.id.field_location) TextView eventLocation;

    public UpComingHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
