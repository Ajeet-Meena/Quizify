package quizify.ajeet_meena.com.quizify.ActivityResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.CircularImageView;
import quizify.ajeet_meena.com.quizify.Utilities.DownloadImageTask;

/**
 * Created by Ajeet Meena on 24-06-2015.
 */
public class CustomAdapter extends BaseAdapter {


    ArrayList<SingleRowScore> list;
    Context context;
    CustomAdapter(Context context)
    {
        list = new ArrayList<>();
        this.context = context;
    }

    public void addSingleRow(SingleRowScore singleRowScore)
    {
        list.add(singleRowScore);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_result, parent,
                    false);
            holder = new ViewHolder(row);
            row.setTag(holder);
            holder.setViewHolder(list.get(position));

        } else {
            holder = (ViewHolder) row.getTag();
        }
        return row;
    }
}

class ViewHolder
{
    TextView textViewRank;
    TextView textViewScore;
    TextView textViewUserName;
    CircularImageView imageView;

    ViewHolder(View view)
    {
        this.textViewRank = (TextView) view.findViewById(R.id.rank);
        this.textViewScore = (TextView) view.findViewById(R.id.score);
        this.textViewUserName = (TextView) view.findViewById(R.id.user_name);
        this.imageView = (CircularImageView) view.findViewById(R.id.profile_pic);
    }

    public void setViewHolder(SingleRowScore singleRowScore)
    {
        textViewRank.setText("#"+Integer.toString(singleRowScore.rank));
        textViewScore.setText(Integer.toString(singleRowScore.score));
        textViewUserName.setText(singleRowScore.user_name);
       new DownloadImageTask(imageView).execute("https://graph.facebook.com/"+singleRowScore.user_id+"/picture");
    }
}
