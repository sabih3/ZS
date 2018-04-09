package netaq.com.zayedsons.views.registration;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.OnBackFromEducationInfo;
import netaq.com.zayedsons.eventbus.CitySelectEvent;
import netaq.com.zayedsons.eventbus.RegisterButtonEvent;
import netaq.com.zayedsons.eventbus.SponsorSelectEvent;
import netaq.com.zayedsons.model.Lookup;
import netaq.com.zayedsons.model.Lookups;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestLookup;
import netaq.com.zayedsons.utils.JSONUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScreenEducationalInfo extends Fragment implements Validator.ValidationListener {

    private View view;
    private Unbinder unbinder;
    @BindView(R.id.register_btn)Button registerButton;
    @BindView(R.id.back_layout)LinearLayout backLayout;
    @BindView(R.id.btn_back)ImageView backButton;

    @BindView(R.id.city_layout)TextInputLayout layoutCity;
    @BindView(R.id.university_layout)TextInputLayout layoutUniversity;
    @BindView(R.id.major_layout)TextInputLayout layoutMajor;
    @BindView(R.id.sponsor_layout)TextInputLayout layoutSponsor;

    @NotEmpty
    @BindView(R.id.search_city)AppCompatEditText fieldCity;

    @NotEmpty
    @BindView(R.id.field_university)AppCompatEditText fieldUniversity;

    @NotEmpty
    @BindView(R.id.field_major)AppCompatEditText fieldMajor;

    @NotEmpty
    @BindView(R.id.field_sponsor)TextView fieldSponsor;

    @BindView(R.id.field_emirates_id)AppCompatEditText fieldEmiratesID;

    private String selectedSponsor = "";
    private String selectedCity = "";

    private Validator validator;
    //private List<Lookup.Lookups> cities = new ArrayList<>();
    //private String selectedCityID = "";
    List<Lookups> sponsorList ;
    public ScreenEducationalInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_2, container, false);


        EventBus.getDefault().register(this);

        initViews();

        return view;
    }

    private void initViews() {
        unbinder = ButterKnife.bind(this, view);

        validator = new Validator(this);
        validator.setValidationListener(this);
        registerButton.setOnClickListener(new RegisterButtonListener());
        backButton.setOnClickListener(new BackButtonListener());
        backLayout.setOnClickListener(new BackButtonListener());

        fieldCity.setOnClickListener(new CityClickListener());
        fieldSponsor.setOnClickListener(new CityClickListener());


    }

    private void setSponsorSpinner() {
        String jsonString = JSONUtils.loadJSONFromAsset(getContext(),"sponsors.json");

        Gson gson = new Gson();
        Lookup sponsorLookup = gson.fromJson(jsonString,Lookup.class);

        sponsorList = sponsorLookup.getLookups();

        ArrayAdapter<Lookups> spinnerAdapter = new ArrayAdapter<>(getContext(),
                             R.layout.row_city_name,sponsorList);

//        fieldSponsor.setAdapter(spinnerAdapter);
//
//        fieldSponsor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//
//                if(position>0){
//                    sponsorList.get(position);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onValidationSucceeded() {
        EventBus.getDefault().post(new RegisterButtonEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCitySelected(CitySelectEvent citySelectEvent){
        String  cityName = citySelectEvent.getItem().getTitle();
        fieldCity.setText(cityName);

        if(citySelectEvent.getItem().getID().equals(Constants.EMPTY_GUID)){
            selectedCity = citySelectEvent.getItem().getIntID();
        }else{
            selectedCity = citySelectEvent.getItem().getID();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSponsorSelected(SponsorSelectEvent sponsorSelectEvent){
        String sponsorName = sponsorSelectEvent.getItem().getTitle();

        fieldSponsor.setText(sponsorName);

        if(sponsorSelectEvent.getItem().getID().equals(Constants.EMPTY_GUID)){

            selectedSponsor = sponsorSelectEvent.getItem().getIntID();

        }else{
            selectedSponsor = sponsorSelectEvent.getItem().getID();
        }

    }
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error:errors){
            View view = error.getView();

            switch (view.getId()){

                case R.id.search_city:
                    layoutCity.setErrorEnabled(true);
                    layoutCity.setError("Please choose your city");
                break;

                case R.id.field_university:
                    layoutUniversity.setErrorEnabled(true);
                    layoutUniversity.setError("Please specify your university");
                break;

                case R.id.field_major:
                    layoutMajor.setErrorEnabled(true);
                    layoutMajor.setError("Please provide your majors");
                break;

                case R.id.field_sponsor:
                    layoutSponsor.setError("Please choose your sponsor");
                break;
                
            }
        }
    }

    public HashMap<String, String> getEducationalData() {
        String major = fieldMajor.getText().toString();
        String university = fieldUniversity.getText().toString();
        String emiratesID = fieldEmiratesID.getText().toString();


        HashMap<String,String> valuesMap = new HashMap<>();
        valuesMap.put("major",major);
        valuesMap.put("university",university);
        valuesMap.put("emiratesID",emiratesID);
        valuesMap.put("sponsor",selectedSponsor);
        valuesMap.put("city",selectedCity);

        return valuesMap;
    }

    private class RegisterButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            validator.validate();}
    }


    private class BackButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(new OnBackFromEducationInfo());
        }
    }

    private void checkQueryFromServer() {
        RequestLookup requestLookup = new RequestLookup();
        requestLookup.setForID(Constants.FOR_ID_LOOKUP_COUNTRY);

        Call<Lookup> requestCities = RestClient.getAdapter().getCities(requestLookup);

        requestCities.enqueue(new Callback<Lookup>() {
            @Override
            public void onResponse(Call<Lookup> call, Response<Lookup> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        Lookup body = response.body();

//                        cities = body.getLookups();
//                        ArrayAdapter<Lookup.Lookups> adapter =
//                        new ArrayAdapter<>(getContext(),
//                                R.layout.row_city_name,cities);
//                        fieldCity.setAdapter(adapter);


                    }
                }

            }

            @Override
            public void onFailure(Call<Lookup> call, Throwable t) {

            }
        });
    }


    private class CityClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int caller = 0;
            switch (view.getId()){
                case R.id.search_city:

                    caller = Constants.CALLER_CITY;

                break;

                case R.id.field_sponsor:
                    caller = Constants.CALLER_SPONSOR;
                break;
            }
            NavigationController.showCityListScreen(getContext(),caller);
        }
    }
}
