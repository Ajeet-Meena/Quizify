package quizify.ajeet_meena.com.quizify.ActivityQuiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import quizify.ajeet_meena.com.quizify.R;

public class NavigationAdapter extends BaseAdapter
{

    ArrayList<SingleRowNavigation> list;
    Context context;

    NavigationAdapter(Context context)
    {
        this.context = context;
        list = new ArrayList<>();
    }

    public void addSingleRow(SingleRowNavigation singleRowNavigation)
    {
        list.add(singleRowNavigation);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public SingleRowNavigation getItem(int position)
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.singlerow_row_quiz, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);


        } else
        {
            holder = (ViewHolder) row.getTag();
        }
        holder.setHolder(list.get(position).Question_no + 1, list.get(position).resource_id);
        RelativeLayout layout = (RelativeLayout) row.findViewById(R.id.frame);
        layout.bringToFront();
        holder.button.setTag(position);
        holder.button.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                UiHelper.jump(position);
                UiHelper.close();
            }
        });

        return row;
    }

    int getResId(int position)
    {
        return list.get(position).resource_id;
    }
}

class SingleRowNavigation
{

    int Question_no;
    int resource_id;

    SingleRowNavigation(int question_no, int resource_id)
    {
        this.resource_id = resource_id;
        this.Question_no = question_no;
    }

    public void changeResourceId(int resource_id)
    {
        this.resource_id = resource_id;
    }
}

class ViewHolder
{

    Button button;
    ImageView imageView;

    ViewHolder(View view)
    {
        button = (Button) view.findViewById(R.id.textView1);
        imageView = (ImageView) view.findViewById(R.id.imageView1);
    }

    public void setHolder(int questionNumber, int resourceId)
    {
        button.setText("Q" + questionNumber);
        imageView.setImageResource(resourceId);
    }

}
