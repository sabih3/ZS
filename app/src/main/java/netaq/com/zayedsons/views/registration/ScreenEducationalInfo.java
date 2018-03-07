package netaq.com.zayedsons.views.registration;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.eventbus.OnBackFromEducationInfo;
import netaq.com.zayedsons.eventbus.RegisterButtonEvent;
import netaq.com.zayedsons.model.Lookup;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.RestClient;
import netaq.com.zayedsons.network.model.requests.RequestLookup;
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
    @BindView(R.id.search_city)AppCompatAutoCompleteTextView fieldCity;

    @NotEmpty
    @BindView(R.id.field_university)AppCompatEditText fieldUniversity;

    @NotEmpty
    @BindView(R.id.field_major)AppCompatEditText fieldMajor;

    @NotEmpty
    @BindView(R.id.field_sponsor)AppCompatEditText fieldSponsor;

    @BindView(R.id.field_emirates_id)AppCompatEditText fieldEmiratesID;

    private Validator validator;
    private List<Lookup.Lookups> cities = new ArrayList<>();
    private String selectedCityID = "";
    public ScreenEducationalInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_2, container, false);


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
        fieldCity.addTextChangedListener(new CityTextListener());
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

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error:errors){
            View view = error.getView();

            switch (view.getId()){

                case R.id.search_city:
                    layoutCity.setErrorEnabled(true);
                    layoutCity.setError("City is mandatory");
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
                    layoutSponsor.setErrorEnabled(true);
                    layoutSponsor.setError("Sponsor is required to be filled");
                break;

            }
        }
    }

    public HashMap<String, String> getEducationalData() {
        String major = fieldMajor.getText().toString();
        String university = fieldUniversity.getText().toString();
        String emiratesID = fieldEmiratesID.getText().toString();
        String sponsor = fieldSponsor.getText().toString();

        HashMap<String,String> valuesMap = new HashMap<>();
        valuesMap.put("major",major);
        valuesMap.put("university",university);
        valuesMap.put("emiratesID",emiratesID);
        valuesMap.put("sponsor",sponsor);
        valuesMap.put("city",selectedCityID);

        return valuesMap;
    }

    private class RegisterButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            validator.validate();
        }
    }

    private class BackButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(new OnBackFromEducationInfo());
        }
    }

    private class CityTextListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        @Override
        public void afterTextChanged(Editable editable) {
            String query = fieldCity.getText().toString();
            if(query.length() >= 3){
                //issue a background call with query
                // get the names
                //and set the adapter

                checkQueryFromServer();
            }
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

                        cities = body.getLookups();
                        ArrayAdapter<Lookup.Lookups> adapter =
                        new ArrayAdapter<>(getContext(),
                                R.layout.row_city_name,cities);

//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                                simple_dropdown_item_1line, cities.toArray());
//                        CityAdapter cityAdapter = new CityAdapter(getContext(),cities);
                        //fieldCity.setAdapter(cityAdapter);
                        fieldCity.setAdapter(adapter);

                        fieldCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Lookup.Lookups city = cities.get(i);
                                selectedCityID = city.getID();
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<Lookup> call, Throwable t) {

            }
        });
    }


}
