package com.example.dialogsexampleproject;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    final static String TAG = "DIALOGS_DEMO";
    final static int DIALOG_ID_MULTIPLE_SELECTION = 1010;

    private Button 	btnDate, btnMultiple, btnAlert, btnCustom, btnHour, btnCustomCelebs, btnSingle;
    private TextView tvResult;

    private final CharSequence[] items = {"Mushrooms", "Green Olives", "Extra cheese", "Pineapple", "A spoonful of salt"};
    private boolean[] selected = {false, false, false, false, false};

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
        btnHour = findViewById(R.id.btnHour);
        btnCustomCelebs = findViewById(R.id.btnCustomCelebs);
        btnSingle = findViewById(R.id.btnSingle);

        btnMultiple.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnAlert.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        btnHour.setOnClickListener(this);
        btnCustomCelebs.setOnClickListener(this);
        btnSingle.setOnClickListener(this);

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
        } else if (viewId == R.id.btnHour) {
            showMyTimeDialog();
        } else if (viewId == R.id.btnCustomCelebs) {
            showCustomCelebsDialog();
        } else if (viewId == R.id.btnSingle){
            ShowSingleSelectionDialog();
        }
        else {
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

    private void showCustomCelebsDialog() {
        // Toast.makeText(this, "Custom", Toast.LENGTH_LONG).show();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_celebs_dialog);

        // Set the custom dialog components - text, image and button
        ImageButton btnLeeham = dialog.findViewById(R.id.btnLeeham);
        ImageButton btnGabee = dialog.findViewById(R.id.btnGabee);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);

        tvTitle.setText("Choose yow faviz celb!");

        CustomCelebsDialogClickListener dcl = new CustomCelebsDialogClickListener(dialog);
        btnLeeham.setOnClickListener(dcl);
        btnGabee.setOnClickListener(dcl);

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

    private void ShowSingleSelectionDialog()
    {
        Builder builder = new AlertDialog.Builder(this);
        int posInSingleChoice = 0;
        builder.setTitle("What would you like on your pizza?").setSingleChoiceItems(items, posInSingleChoice, new OnSingleChoiceClickListener());
        builder.setPositiveButton("OK", new OnSingleChoiceDialogClickListener());

        // final boolean[] itemChecked = {false,false,false, false};
//        for (int i = 0; i < selected.length; i++)
//            selected[i] = false;
//        int posInSingleChoice = 0;
//        builder.setSingleChoiceItems(items, posInSingleChoice, (DialogInterface.OnClickListener) new OnSingleChoiceClickListener());
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

    private void showMyTimeDialog()
    {
        TimePickerDialog.OnTimeSetListener listener = new MyTimeSetListener();  // see class implementation below

        TimePickerDialog dpd = new TimePickerDialog(this,listener,0,0,true);
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

    private class MyTimeSetListener implements TimePickerDialog.OnTimeSetListener
    {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String msg = String.format("Selected time is %s:%s", hourOfDay, minute);
            tvResult.setText(msg);
        }
    } // private class MyTimeSetListener


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

    private class OnSingleChoiceDialogClickListener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    }

    public class OnSingleChoiceClickListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String msg = items[which] + " selected: " + which;
            tvResult.setText("Topping selected: " + items[which]);
            Log.i(TAG, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    } // public class OnSingleChoiceClickListener
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
    private class CustomCelebsDialogClickListener implements View.OnClickListener
    {
        Dialog dialog;

        public CustomCelebsDialogClickListener(Dialog _dialog)
        {
            this.dialog = _dialog;
        }

        @Override
        public void onClick(View v)
        {
            int id = v.getId();
            String reply;
            if (id == R.id.btnLeeham)
                reply = "Lee Ham!";
            else
                reply = "Gabee!";
            Toast.makeText(getApplicationContext(), reply, Toast.LENGTH_LONG).show();
            tvResult.setText(reply);
            dialog.dismiss();
        }
    }

}