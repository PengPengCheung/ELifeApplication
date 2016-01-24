package com.pengpeng.elifeapplication.newactivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.pengpeng.elifeapplication.R;

/**
 * Created by pengpeng on 16-1-15.
 */
public class ClassificationFragment extends Fragment implements View.OnClickListener{

    private View view;
    private ImageButton part1;
    private ImageButton part2;
    private ImageButton part3;
    private onPartDividedListener pdListener;

    public interface onPartDividedListener{
         void onPartDivided(Bundle bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        pdListener = (onPartDividedListener)activity;
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classification, null);
        initView();
        setListener();
        return view;
    }

    private void initView() {
        part1 = (ImageButton)view.findViewById(R.id.part1);
        part2 = (ImageButton)view.findViewById(R.id.part2);
        part3 = (ImageButton)view.findViewById(R.id.part3);
    }

    public void setListener(){
        part1.setOnClickListener(this);
        part2.setOnClickListener(this);
        part3.setOnClickListener(this);
    }


    @Override
    public void onPause() {
//        bundle = null;
        super.onPause();

    }

    @Override
    public void onClick(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.part2:
                b.putInt("part", 2);
                break;
            case R.id.part1:
                b.putInt("part", 1);
                break;
            case R.id.part3:
                b.putInt("part", 3);
                break;
        }
        pdListener.onPartDivided(b);
        ((NetworkAudioPlayerActivity)getActivity()).getViewPager().setCurrentItem(1);
    }
}
