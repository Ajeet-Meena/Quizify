package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.DisplayNotification;
import quizify.ajeet_meena.com.quizify.Utilities.Message;
import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;

/**
 * Dialog showing Detail information of an event.
 */

public class Single_Dialog extends DialogFragment implements
        View.OnClickListener, ListView.OnItemClickListener
{

    private static final String KEY_TITLE = "title";
    private static final String KEY_LIST = "list";
    private static final String KEY_NEGATIVEBUTTON = "negativeButton";
    private static final String KEY_POSITIVEBUTTON = "positiveButton";

    private TextView dialogTitle;
    private ListView dialogList;
    private Button dialogNegativeButton;
    private Button dialogPositiveButton;
    private Button inviteFriends;
    CallbackManager callbackManager;

    static ViewHolder holder;
    static SingleRowEvent singleRowEvent;
    static DisplayNotification displayNotification;
    static OnlineHelper onlineHelper;

    public static Single_Dialog newInstance(Context context, String title,
                                            SingleRowEvent singleRowEvent, String negativeButton,
                                            String positiveButton, ViewHolder holder)
    {
        Single_Dialog f = new Single_Dialog();
        Single_Dialog.holder = holder;
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_NEGATIVEBUTTON, negativeButton);
        args.putString(KEY_POSITIVEBUTTON, positiveButton);
        f.setArguments(args);
        Single_Dialog.singleRowEvent = singleRowEvent;
        displayNotification = new DisplayNotification(context);
        onlineHelper = new OnlineHelper(context);
        return f;
    }

    public Single_Dialog()
    {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder;


        builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_dialog_event, null);

        dialogTitle = (TextView) dialogView.findViewById(R.id.dialogTitle);
        dialogList = (ListView) dialogView.findViewById(R.id.dialogContent);

        dialogNegativeButton = (Button) dialogView
                .findViewById(R.id.dialogButtonNegative);
        dialogPositiveButton = (Button) dialogView
                .findViewById(R.id.dialogButtonPositive);
        inviteFriends = (Button) dialogView.findViewById(R.id.invite);

        dialogTitle.setText(getArguments().getString(KEY_TITLE));
        dialogNegativeButton.setText(getArguments().getString(
                KEY_NEGATIVEBUTTON));
        dialogPositiveButton.setText(getArguments().getString(
                KEY_POSITIVEBUTTON));

        dialogList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, singleRowEvent.getArrayList()));
        dialogList.setOnItemClickListener(this);

        dialogNegativeButton.setOnClickListener(this);
        dialogPositiveButton.setOnClickListener(this);
        inviteFriends.setOnClickListener(this);


        builder.setView(dialogView);
        Dialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;

        return dialog;
    }

    /**
     * When Dialog Button is clicked. If it is cancel then close dialog
     * If it is Participate/Decline generate or close an notification and setup/cancel an alarm
     *
     * @param v
     */

    @Override
    public void onClick(View v)
    {
        // Button cancel

        if(v.getId() == R.id.invite)
        {
            String appLinkUrl, previewImageUrl;

            callbackManager = CallbackManager.Factory.create();;
            appLinkUrl = "https://fb.me/1583566078576862";
            previewImageUrl = "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xfa1/t31.0-8/10321163_730863163688856_7119383836800287767_o.jpg";

            if (AppInviteDialog.canShow()) {
                AppInviteContent content = new AppInviteContent.Builder()
                        .setApplinkUrl(appLinkUrl)
                        .setPreviewImageUrl(previewImageUrl)
                        .build();
                AppInviteDialog appInviteDialog = new AppInviteDialog(getActivity());
                appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>()
                {
                    @Override
                    public void onSuccess(AppInviteDialog.Result result)
                    {
                        Message.message(getActivity(),"Successfully Invited!");
                    }

                    @Override
                    public void onCancel()
                    {
                    }

                    @Override
                    public void onError(FacebookException e)
                    {
                        Message.message(getActivity(),"An error Occurred! Please Try Again Latter");
                    }
                });
                appInviteDialog.show(content);
            }




        }

        if (v.getId() == R.id.dialogButtonNegative)
        {
            dismiss();
        }
        // Button Participate/Cancel
        if (v.getId() == R.id.dialogButtonPositive)
        {
            // If event is already participate then cancel it else participate
            if(!singleRowEvent.isEventColliding(getActivity()))
            {
                if (singleRowEvent.button_state == false)
                {
                    onlineHelper.incDecParticipant(singleRowEvent,holder,"increment","in-app", 0);
                }
                // If event is declined then close notification and cancel alarm
                else
                {
                    onlineHelper.incDecParticipant(singleRowEvent,holder,"decrement","in-app", 0);
                }
            }
            else
            {
                Message.message(getActivity(),"An event is colliding");
            }
            dismiss();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)
    {

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}