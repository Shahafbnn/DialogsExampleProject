package com.example.dialogsexampleproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dialogsexampleproject.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    final static String TAG = "DIALOGS_DEMO";
    final static int DIALOG_ID_MULTIPLE_SELECTION = 1010;

    private Button 	btnDate, btnMultiple, btnAlert, btnCustom;
    private TextView tvResult;

    private final CharSequence[] items = {"Mushrooms", "Green Olives", "Extra cheese", "Pineapple"};
    private boolean[] selected = {false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMultiple = (Button) findViewById(R.id.btnMultiple);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnAlert = (Button) findViewById(R.id.btnAlert);
        btnCustom = findViewById(R.id.btnCustom);
        tvResult = (TextView) findViewById(R.id.tvResult);

        btnMultiple.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnAlert.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
    }

    // onClick for the Activity Buttons
    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.btnMultiple) {
            ShowMultipleSelectionDialog();
        } else if (viewId == R.id.btnDate) {
            showMyDateDialog(); // easy method
        } else if (viewId == R.id.btnAlert) {
            showMyAlertDialog(); // easy method
        } else if (viewId == R.id.btnCustom) {
            showCustomDialog();
        } else {
            Log.e(TAG, "Invalid item clicked");
        }
    }


    // implements a custom dialog
    private void showCustomDialog() {
        // Toast.makeText(this, "Custom", Toast.LENGTH_LONG).show();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        // Set the custom dialog components - text, image and button
        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);

        tvTitle.setText("Do you want to dive?");

        CustomDialogClickListener dcl = new CustomDialogClickListener(dialog);
        btnYes.setOnClickListener(dcl);
        btnNo.setOnClickListener(dcl);

        // Center the dialog on the screen
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.show();
    }

    // implements a multiple selection dialog
    // dialog = MyMultipleSelectionDialog();
    // Implement the Multiple Selection dialog
    private void ShowMultipleSelectionDialog()
    {
        Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What would you like on your pizza?");
        // final boolean[] itemChecked = {false,false,false, false};
        for (int i = 0; i < selected.length; i++)
            selected[i] = false;
        builder.setMultiChoiceItems(items, selected, new OnMultiChoiceClickListener());
        builder.setPositiveButton("OK", new OnMultiChoiceDialogClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // implements simple Alert Dialog


    private void showMyAlertDialog()
    {
        Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Msg: Are you sure?");
        builder.setTitle("Title: Are you sure?");
        builder.setIcon(R.drawable.question_mark);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new OnAlertDialogClickListener());
        builder.setNegativeButton("No", new OnAlertDialogClickListener());
        builder.setNeutralButton("Maybe", new OnAlertDialogClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // implmenet the date Dialog - the easy way
    private void showMyDateDialog()
    {
        OnDateSetListener listener = new MyDateSetListener();  // see class implementation below

        DatePickerDialog dpd = new DatePickerDialog(this,listener,2012,11,11); // ðáðä àú äúéáä îñåâ úàøéê, ðøùí ìîàæéï, åð÷áò àú äúàøéê ääúçìúé ùéåöã ìà ìúú çåãù âãåì î- 11
        dpd.show();
    }

    // a nested class that implements the DateSetListener Interface
    private class MyDateSetListener implements OnDateSetListener
    {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            String msg = String.format("Selected date is %s/%s/%s", dayOfMonth, 1 + monthOfYear, year);
            tvResult.setText(msg);
        }
    } // private class MyDateSetListener





    // used for simple alert dialog
    class OnAlertDialogClickListener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which) // implements onclick for positive button
        {
            switch (which)
            {
                case Dialog.BUTTON_NEGATIVE:
                    // int which = -2
                    tvResult.setText("Non");
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    // int which = -3
                    tvResult.setText("Peut etre");
                    break;
                case Dialog.BUTTON_POSITIVE:
                    // int which = -1
                    tvResult.setText("Oui");
                    break;
            }
            dialog.dismiss();
        }
    }


    // used for multiple selection - the OK button
    private class OnMultiChoiceDialogClickListener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which) // ðîîù àú äôòåìä onclick
        {
            String selText = "";
            for (int i = 0; i < items.length; i++)
                if (selected[i])
                    selText += items[i] + "\n";
            tvResult.setText(selText);
            dialog.dismiss();
        }
    }


    // used for multiple selection - for every change in the selection
    // note that it doesn't really do anything - we just need it to be there
    public class OnMultiChoiceClickListener implements DialogInterface.OnMultiChoiceClickListener
    {
        public void onClick(DialogInterface dialog, int clicked, boolean status)
        {
            String msg = items[clicked] + " selected: " + status;
            Log.i(TAG, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    } // public class OnMultiChoiceClickListener

    private class CustomDialogClickListener implements View.OnClickListener
    {
        Dialog dialog;

        public CustomDialogClickListener(Dialog _dialog)
        {
            this.dialog = _dialog;
        }

        @Override
        public void onClick(View v)
        {
            int id = v.getId();
            String reply;
            if (id == R.id.btnYes)
                reply = "Yes";
            else
                reply = "No";
            Toast.makeText(getApplicationContext(), reply, Toast.LENGTH_LONG).show();
            tvResult.setText(reply);
            dialog.dismiss();
        }
    }

}