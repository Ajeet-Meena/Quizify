package quizify.ajeet_meena.com.quizify.ActivityMainPage;

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

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.Single_Dialog;


public class Adapter extends BaseAdapter
{
    ArrayList<SingleRowEvent> list;
    private static FragmentManager mManager;
    private Context context;
    boolean temp = false;
    SharedPreferences prefs;
    int lastPosition = -1;

    // prefs to save status of participate event. i.e participate or decline
    public static final String STATUS = "prefs";

    public Adapter(Context context, FragmentManager mManager)
    {
        Adapter.mManager = mManager;
        list = new ArrayList<>();
        this.context = context;
        prefs = context.getSharedPreferences(STATUS, context.MODE_PRIVATE);
    }

    public void clearList()
    {
        list.clear();
    }

    public void add_singleRow(SingleRowEvent singleRowEvent)
    {
        list.add(singleRowEvent);
    }


    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        View row = convertView;
        ViewHolder holder = null;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_online_quizes, parent,
                    false);
            holder = new ViewHolder(row);
            row.setTag(holder);
            holder.set_text(list.get(position).Event_name, "Loaing...");

        } else
        {
            holder = (ViewHolder) row.getTag();

        }


        holder.buttonParticipate.setTag(position);

        if (list.get(position).event_id == prefs.getInt("event", -1))
            if (prefs.getInt("event", -1) != -1)
            {
                list.get(position).setButton_state(1);
                temp = true;
            }
        if (position + 1 == list.size())
        {
            if (temp == false)
            {
                prefs.edit().putInt("event", -1).commit();
            }
        }


        final ViewHolder finalHolder = holder;

        finalHolder.setButtonParticipateState(list.get(position).button_state);

        holder.buttonParticipate.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (list.get(position).event_id == prefs.getInt("event", -1))
                {
                    if (prefs.getInt("event", -1) != -1)
                    {

                        Single_Dialog newDialog = Single_Dialog.newInstance(context,
                                "DETAILS", list.get(position), "CANCEL", "DECLINE", finalHolder, 1);
                        newDialog.show(mManager, "dialog");
                    }

                } else
                {

                    Single_Dialog newDialog = Single_Dialog.newInstance(context, "DETAILS", list.get(position), "CANCEL", "PARTICIPATE", finalHolder, 0);
                    newDialog.show(mManager, "dialog");
                }
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.abc_slide_in_bottom : R.anim.abc_slide_in_top);
        row.startAnimation(animation);
        lastPosition = position;

        return row;
    }

    public ArrayList<SingleRowEvent> getList()
    {
        return list;
    }


}