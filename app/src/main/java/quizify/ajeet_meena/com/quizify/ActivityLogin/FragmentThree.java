package quizify.ajeet_meena.com.quizify.ActivityLogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quizify.ajeet_meena.com.quizify.R;

/**
 * Created by Ajeet Meena on 28-06-2015.
 */
public class FragmentThree extends Fragment
{


        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.fragment_three_login, container, false);
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }
}
