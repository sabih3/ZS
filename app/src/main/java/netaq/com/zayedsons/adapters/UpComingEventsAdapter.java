package netaq.com.zayedsons.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import netaq.com.zayedsons.R;
import netaq.com.zayedsons.model.Event;

/**
 * Created by sabih on 12-Feb-18.
 */
public class UpComingEventsAdapter extends RecyclerView.Adapter<UpComingHolder> {
    private final Context context;
    private List<Event> items;
    private EventClickListener eventClickListener;

    public void setEventClickListener(EventClickListener eventClickListener) {
        this.eventClickListener = eventClickListener;
    }

    public UpComingEventsAdapter(List<Event> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public UpComingHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event, parent, false);
        return new UpComingHolder(v);
    }

    @Override
    public void onBindViewHolder(UpComingHolder holder, int position) {
        Event event = items.get(position);

        holder.bindData(event);
        holder.eventTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventClickListener != null){
                    eventClickListener.onEventClick(event);
                }

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

    public interface EventClickListener{
        void onEventClick(Event event);
    }
}