package quizify.ajeet_meena.com.quizify.Utilities;

/**
 * Created by Ajeet Meena on 28-06-2015.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.R;



public class Suggest_Dialog extends DialogFragment implements View.OnClickListener
{

    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_NEGATIVEBUTTON = "negativeButton";
    private static final String KEY_POSITIVEBUTTON = "positiveButton";

    private TextView dialogTitle;
    private Button dialogNegativeButton;
    private Button dialogPositiveButton;
    private EditText editText1;
    private EditText editText2;

    public static Suggest_Dialog newInstance(String title, String message, String negativeButton, String positiveButton)
    {
        Suggest_Dialog f = new Suggest_Dialog();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        args.putString(KEY_NEGATIVEBUTTON, negativeButton);
        args.putString(KEY_POSITIVEBUTTON, positiveButton);
        f.setArguments(args);

        return f;
    }

    public Suggest_Dialog()
    {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.suggest_dialog, null);

        dialogTitle = (TextView) dialogView.findViewById(R.id.dialogTitle);

        editText1 = (EditText) dialogView.findViewById(R.id.dialogEditText1);
        editText2 = (EditText) dialogView.findViewById(R.id.dialogEditText2);

        dialogNegativeButton = (Button) dialogView.findViewById(R.id.dialogButtonNegative);
        dialogPositiveButton = (Button) dialogView.findViewById(R.id.dialogButtonPositive);
        dialogTitle.setText(getArguments().getString(KEY_TITLE));

        dialogNegativeButton.setText(getArguments().getString(KEY_NEGATIVEBUTTON));
        dialogPositiveButton.setText(getArguments().getString(KEY_POSITIVEBUTTON));

        dialogNegativeButton.setOnClickListener(this);
        dialogPositiveButton.setOnClickListener(this);

        builder.setView(dialogView);
        Dialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;

        return dialog;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.dialogButtonNegative)
        {
            dismiss();
        }
        if (v.getId() == R.id.dialogButtonPositive)
        {
            String text1 = editText1.getText().toString();
            String text2 = editText2.getText().toString();
            if(!text1.equals(""))
            {
                Message.message(getActivity(), "Thanks, We admire your efforts! ");
                new OnlineHelper(getActivity()).reportBugSuggest("MESSAGE: "+ text1+ " FROM: " + text2 );
                dismiss();
            }
            else
            {
                Message.message(getActivity(),"Bug/Suggestion should not be empty");
            }
        }

    }
}