package netaq.com.zayedsons;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.network.model.responses.ResponseAttendeeInfo;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.views.MarkAttendancePresenter;
import netaq.com.zayedsons.views.MarkAttendanceView;

public class ScreenAttendance extends Activity implements MarkAttendanceView{

    private ResponseAttendeeInfo attendeeInfo;
    @BindView(R.id.attendance_event_name)TextView tvEventName;
    @BindView(R.id.attendance_attendee_name)TextView tvAttendeeName;
    @BindView(R.id.attendance_attendee_image)ImageView ivAttendeeImage;
    @BindView(R.id.attendance_action_layout)RelativeLayout actionLayout;
    @BindView(R.id.btn_attendance_attend)Button btnMarkAttendance;
    @BindView(R.id.btn_attendance_dismiss)Button btnCancel;
    @BindView(R.id.btn_attendance_result)Button attendanceResultView;
    @BindView(R.id.btn_result_dismiss)Button attendanceResultDismiss;
    @BindView(R.id.attendance_progress)ProgressBar progressBar;

    @BindView(R.id.attendance_result_layout) RelativeLayout resultLayout;

    private MarkAttendancePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_attendance_info);

        ButterKnife.bind(this);

        attendeeInfo = (ResponseAttendeeInfo) getIntent().
                                    getSerializableExtra(NavigationController.KEY_ATTENDEE_INFO);

        presenter = new MarkAttendancePresenter(this,this);

        initViews();
    }

    private void initViews() {
        tvEventName.setText(attendeeInfo.getEventTitle());
        tvAttendeeName.setText(attendeeInfo.getFullName());

        try {
            Picasso.with(this).load(attendeeInfo.getProfilePic()).into(ivAttendeeImage);
        }catch (Exception exc){

        }

        btnCancel.setOnClickListener(new CancelButtonListener());
        btnMarkAttendance.setOnClickListener(new MarkAttendanceListener());

        attendanceResultDismiss.setOnClickListener(new ResultDismissListener());

    }

    @Override
    public void onNetworkUnAvailable() {
        //TODO: Handle No network in Attendance Screen
    }

    @Override
    public void onAttendanceMarked() {

        UIUtils.showMessageDialog(this, "Successfully Marked",
                "Exit",
                "", new UIUtils.DialogButtonListener() {
                    @Override
                    public void onPositiveButtonClicked() {
                        ScreenAttendance.this.finish();
                    }

                    @Override
                    public void onNegativeButtonClicked() {

                    }
                });
    }

    @Override
    public void onError(String resolvedError) {

        actionLayout.setVisibility(View.GONE);
        //resultLayout.setVisibility(View.VISIBLE);

        //attendanceResultView.setText(resolvedError);

        UIUtils.showMessageDialog(this, resolvedError,
                "Dismiss",
                "", new UIUtils.DialogButtonListener() {
            @Override
            public void onPositiveButtonClicked() {

                ScreenAttendance.this.finish();
            }

            @Override
            public void onNegativeButtonClicked() {

            }
        });

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private class CancelButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            UIUtils.showMessageDialog(ScreenAttendance.this,
                    "Are you sure to cancel ?",
                    "No",
                    "Yes", new UIUtils.DialogButtonListener() {
                @Override
                public void onPositiveButtonClicked() {

                }

                @Override
                public void onNegativeButtonClicked() {

                    ScreenAttendance.this.finish();
                }
            });

        }
    }

    private class MarkAttendanceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            requestMarkAttendance();
        }
    }

    private void requestMarkAttendance() {
        String eventID = attendeeInfo.getEventID();
        String eventDay = attendeeInfo.getEventDay();
        String eventTime = attendeeInfo.getEventTime();

        presenter.requestMarkAttendance(eventID,eventDay,eventTime);
    }

    private class ResultDismissListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ScreenAttendance.this.finish();
        }
    }
}
