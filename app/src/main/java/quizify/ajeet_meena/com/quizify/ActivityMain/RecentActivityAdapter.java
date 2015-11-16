package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import quizify.ajeet_meena.com.quizify.R;

/**
 * Created by Ajeet Meena on 29-06-2015.
 */
public class RecentActivityAdapter extends BaseAdapter
{
    Context context;
    ArrayList<SingleRowRecentActivity> list;

    RecentActivityAdapter(Context context)
    {
        this.context = context;
        list = new ArrayList<>();
    }

    public void clearList()
    {
        list.clear();
    }

    public void addSingleRow(SingleRowRecentActivity singleRowRecentActivity)
    {
        list.add(singleRowRecentActivity);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public SingleRowRecentActivity getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ViewHolderRecentActivity holder;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_recent_activity, parent, false);
            holder = new ViewHolderRecentActivity(row);
            row.setTag(holder);


        } else
        {
            holder = (ViewHolderRecentActivity) row.getTag();
        }
        list.get(position).setMessages();
        holder.setViewHolderRecentActivity(list.get(position));
        return row;
    }
}

class ViewHolderRecentActivity
{
    TextView message;
    TextView announcement;
    TextView date;

    ViewHolderRecentActivity(View view)
    {
        message = (TextView) view.findViewById(R.id.message);
        announcement = (TextView) view.findViewById(R.id.annoncement);
        date = (TextView) view.findViewById(R.id.date);
    }

    void setViewHolderRecentActivity(SingleRowRecentActivity singleRowRecentActivity)
    {
        this.message.setText(singleRowRecentActivity.message);
        this.announcement.setText(singleRowRecentActivity.announcementMessage);
        this.date.setText(singleRowRecentActivity.date.toString());
    }
}
