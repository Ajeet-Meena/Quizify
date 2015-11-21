package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.Message;


public class OnlineEventAdapter extends BaseAdapter
{
    ArrayList<SingleRowEvent> list;
    FragmentManager mManager;
    Context context;
    int lastPosition = -1;

    SharedPreferences sharedPreferences;
    Set<Integer> set;


    public OnlineEventAdapter(Context context, FragmentManager mManager)
    {
        this.mManager = mManager;
        list = new ArrayList<>();
        this.context = context;
        set = new HashSet<>();
        sharedPreferences = context.getSharedPreferences("state", Context.MODE_PRIVATE);
    }

    // Make list empty
    public void clearList()
    {
        list.clear();
    }

    // Add rows to list
    public void add_singleRow(SingleRowEvent singleRowEvent)
    {
        list.add(singleRowEvent);
    }

    // get list
    public ArrayList<SingleRowEvent> getList()
    {
        return list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ViewHolder holder;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_event, parent,
                    false);
            holder = new ViewHolder(row);
            row.setTag(holder);


        } else
        {
            holder = (ViewHolder) row.getTag();
        }
        holder.setHolder(list.get(position));
        holder.buttonParticipate.setTag(position);
        final ViewHolder finalHolder = holder;
        list.get(position).loadButtonState(sharedPreferences);
        finalHolder.setButtonParticipateState(list.get(position).getButton_state());
        //deleteExtraPref();

        /**
         * Participate Button listener
         * Open single_dialog.
         * If event is participated then show decline dialog else participate dialog.
         */
        holder.buttonParticipate.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (list.get(position).getButton_state())
                {
                    Single_Dialog newDialog = Single_Dialog.newInstance(context,
                            "DETAILS", list.get(position), "CANCEL", "DECLINE", finalHolder);
                    newDialog.show(mManager, "dialog");
                } else
                {
                    Single_Dialog newDialog = Single_Dialog.newInstance(context, "DETAILS", list.get(position), "CANCEL", "PARTICIPATE", finalHolder);
                    newDialog.show(mManager, "dialog");
                }
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.abc_slide_in_bottom : R.anim.abc_slide_in_top);
        row.startAnimation(animation);
        lastPosition = position;
        return row;
    }

    public void deleteExtraPref()
    {
        Message.message(context, "Here");
        SharedPreferences sharedPreferences = context.getSharedPreferences("event_start_time", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet())
        {
            if (!list.contains(entry.getKey()))
            {
                sharedPreferences.edit().remove(entry.getKey());
            }
        }
    }

}

