package com.pengpeng.elifeapplication.newactivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pengpeng.elifeapplication.R;

/**
 * Created by pengpeng on 16-1-15.
 */
public class ExerciseFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise, null);
        return view;
    }
}
