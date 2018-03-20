package netaq.com.zayedsons.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import netaq.com.zayedsons.R;
import netaq.com.zayedsons.model.Lookup;

/**
 * Created by sabih on 20-Mar-18.
 */
public class SearchableListAdapter extends RecyclerView.Adapter<SearchAbleListHolder> implements Filterable{

    private final Context context;
    private List<Lookup.Lookups> items;
    private Filter filter;
    private List<Lookup.Lookups> mFilteredList ;
    private OnSelectionListener onSelectionListener;

    public SearchableListAdapter(Context context, List<Lookup.Lookups> items) {
        this.context = context;
        this.items = items;
        mFilteredList = new ArrayList<>();


    }

    @Override
    public SearchAbleListHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_city, parent, false);
        return new SearchAbleListHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchAbleListHolder holder, int position) {
        Lookup.Lookups item = items.get(position);
        holder.bindData(item);

        holder.cityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectionListener.onDataSelect(item);
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

    @Override
    public Filter getFilter() {
        if(filter == null){
            mFilteredList.clear();
            mFilteredList.addAll(this.items);
            filter = new CityFilter(this,mFilteredList);
        }
        return filter;
    }

    public void setOnSelectionListener(OnSelectionListener onSelectionListener) {
        this.onSelectionListener = onSelectionListener;
    }

    private class CityFilter extends Filter{

        private SearchableListAdapter adapter;
        private List<Lookup.Lookups> originalDataset;
        private List<Lookup.Lookups> filteredList;

        public CityFilter(SearchableListAdapter adapter , List<Lookup.Lookups> originalList) {
            this.adapter = adapter;
            this.originalDataset = originalList;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String query = charSequence.toString();

            filteredList.clear();
            FilterResults results = new FilterResults();

            if(query.isEmpty()){
                filteredList.addAll(originalDataset);
            }else{

                for(Lookup.Lookups data: originalDataset){
                    if(data.getTitle().toLowerCase().contains(query)){
                        filteredList.add(data);
                    }
                }

            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.items.clear();
            adapter.items.addAll((List< Lookup.Lookups>)filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnSelectionListener{
        void onDataSelect(Lookup.Lookups item);
    }
}