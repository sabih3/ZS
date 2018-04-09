package netaq.com.zayedsons.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.adapters.SearchableListAdapter;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.CitySelectEvent;
import netaq.com.zayedsons.eventbus.GenderSelectEvent;
import netaq.com.zayedsons.eventbus.SponsorSelectEvent;
import netaq.com.zayedsons.model.Lookups;
import netaq.com.zayedsons.network.Constants;

public class ScreenSearchableList extends Activity implements LookupListView,
                                    SearchableListAdapter.OnSelectionListener {

    @BindView(R.id.city_list_parent)
    RelativeLayout cityParentLayout;
    @BindView(R.id.city_search_view)
    SearchView searchField;
    @BindView(R.id.city_list)
    RecyclerView cityListView;

    @BindView(R.id.searchable_screen_progress)ContentLoadingProgressBar progressBar;

    private SearchableListPresenter searchableListPresenter;
    private SearchableListAdapter cityAdapter;
    private int caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_city_list);

        ButterKnife.bind(this);
        searchableListPresenter = new SearchableListPresenter(this, this);
        initViews();

         caller = getIntent().getIntExtra(NavigationController.KEY_CALLER_SEARCHABLE_LIST, 0);

         if(caller != Constants.CALLER_GENDER){
             searchableListPresenter.getDataFromNetwork(caller);
         }

         else{
             searchField.setVisibility(View.GONE);

             Lookups lookupMale = new Lookups();
             Lookups lookupFemale = new Lookups();
             Lookups lookupOther = new Lookups();

             lookupMale.setTitle("M");
             lookupMale.setID("10");

             lookupFemale.setTitle("F");
             lookupFemale.setID("20");

             lookupOther.setTitle("Other");
             lookupOther.setID("30");

             List <Lookups> lookups = new ArrayList<>();
             lookups.add(lookupMale);
             lookups.add(lookupFemale);
             lookups.add(lookupOther);

             setListView(lookups);
         }

    }

    private void initViews() {
        searchField.setIconified(false);
        searchField.setIconifiedByDefault(false);

        ImageView searchIcon = searchField.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_search_24px));
        searchField.setActivated(true);
        searchField.onActionViewExpanded();

    }

    @Override
    public void showProgress () {
        progressBar.show();
    }

    @Override
    public void hideProgress () {
        progressBar.hide();
    }

    @Override
    public void onLookupDataFetched(List <Lookups> lookups) {
        setListView(lookups);
        searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                    cityAdapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    cityAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onNetworkUnAvailable () {
        //TODO:Handle Network Unavailable in Searchable View
    }

    @Override
    public void onError (String resolvedError){
        //TODO:Handle Exception in Searchable View
    }


    private void setListView (List <Lookups> lookups) {
        cityListView.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter = new SearchableListAdapter(this, lookups);
        cityListView.setAdapter(cityAdapter);

        cityAdapter.setOnSelectionListener(this);
    }

    @Override
    public void onDataSelect(Lookups item) {

        if(caller == Constants.CALLER_CITY){
            EventBus.getDefault().post(new CitySelectEvent(item));
        }else if(caller == Constants.CALLER_SPONSOR){
            EventBus.getDefault().post(new SponsorSelectEvent(item));
        }else if (caller == Constants.CALLER_GENDER){
            EventBus.getDefault().post(new GenderSelectEvent(item));
        }

        this.finish();
    }
}

