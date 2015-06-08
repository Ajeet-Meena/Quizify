package quizify.ajeet_meena.com.quizify.ActivityExamination;

import android.content.Context;
import android.util.Log;
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
        list = new ArrayList<SingleRowNavigation>();
    }

    public void add(SingleRowNavigation singleRowNavigation)
    {
        list.add(singleRowNavigation);
    }

    public void updateView(int position, int Resource_id)
    {
        list.get(position).resource_id = Resource_id;

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
        ViewHolderNavigation holder = null;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.singlerow_nav_drawer, parent,
                    false);
            holder = new ViewHolderNavigation(row);
            row.setTag(holder);


        } else
        {
            holder = (ViewHolderNavigation) row.getTag();

        }
        //Button myButton = (Button) row.findViewById(R.id.textView1);

        //myButton.setText("Q"+(list.get(position).Question_no+1));
        //myButton.setTag(position);
        holder.change_text_view(list.get(position).Question_no + 1);
        holder.change_image_view(list.get(position).resource_id);
        Button myButton = holder.myButton;
        ImageView imageView = holder.imageView;
        RelativeLayout layout = (RelativeLayout) row.findViewById(R.id.frame);
        layout.bringToFront();
        myButton.setTag(position);
        myButton.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Log.d("Message", "" + (Integer) v.getTag());
                UiHelper.jump(position);
                UiHelper.close();
            }
        });

        // new ArrayList<String>();

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
}

class ViewHolderNavigation
{

    Button myButton;
    ImageView imageView;

    ViewHolderNavigation(View view)
    {

        myButton = (Button) view.findViewById(R.id.textView1);
        imageView = (ImageView) view.findViewById(R.id.imageView1);


    }

    void change_text_view(int question_number)
    {
        myButton.setText("Q" + question_number);
    }

    void change_image_view(int resource_id)
    {
        imageView.setImageResource(resource_id);
    }
}
