package netaq.com.zayedsons.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import netaq.com.zayedsons.R;


/**
 * Created by sabih on 01-Nov-17.
 */

public class UIUtils {

    /**Method used to show / hide progress
     *
     * @param rootView
     * @param show
     */
    public static void showProgress(View rootView, boolean show){

        View progressView = rootView/**.findViewById(R.id.progress)**/;

        if(progressView !=null){
            if(show){
                progressView.setVisibility(View.VISIBLE);
            }else{
                progressView.setVisibility(View.GONE);
            }
        }else{
            Log.e(UIUtils.class.getSimpleName(),"The Root view does not contain the progress bar, " +
                    "make sure you have added it in xml");
        }
    }

    public static void showProgressDialog(Context context, String msg, ProgressDialog dialog){
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void hideProgressDialog(ProgressDialog dialog){
        dialog.hide();
    }
    /**Method for showing Snack bar
     *
     * @param coordinatorLayout
     * @param message
     */
    public static void showSnackBar(CoordinatorLayout coordinatorLayout,
                                    String message) {

        Snackbar snackBar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View view = snackBar.getView();
        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.show();
    }

    /**This method shows the snack bar with action
     *
     * @param coordinatorLayout
     * @param message
     * @param actionTitle
     * @param listener
     */
    public static void showSnackBar(CoordinatorLayout coordinatorLayout,
                                    String message, String actionTitle,
                                    final SnackBarActionListener listener) {

        Snackbar snackBar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE).
                            setAction(actionTitle, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSnackBarAction();
            }
        });

        snackBar.setActionTextColor(Color.WHITE);
        View view = snackBar.getView();

        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.show();
    }

    public static void showToast(Context context, String resolvedError) {
        Toast.makeText(context,resolvedError,Toast.LENGTH_LONG).show();
    }

    public interface SnackBarActionListener{
        void onSnackBarAction();
    }

    /**This method shows a confirmation dialog
     *
     * @param context
     * @param message
     * @param positiveButtonTitle
     * @param negativeButtonTitle
     * @param dialogButtonListener
     */
    public static void showMessageDialog(Context context, String message, String positiveButtonTitle,
                                         String negativeButtonTitle,
                                         final DialogButtonListener dialogButtonListener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);


        builder.setMessage(message).
                setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogButtonListener.onPositiveButtonClicked();
                    }
                }).setNegativeButton(negativeButtonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                dialogButtonListener.onNegativeButtonClicked();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
/*
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
*/
    }


    public interface DialogButtonListener {
        void onPositiveButtonClicked();
        void onNegativeButtonClicked();
    }

}
