package quizify.ajeet_meena.com.quizify.ActivityLogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quizify.ajeet_meena.com.quizify.R;

public class FragmentTwo extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(
                R.layout.fragment_two_login, container, false);
    }
}
