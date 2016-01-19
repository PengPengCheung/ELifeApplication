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
    private ImageButton part2;
    private onPartDividedListener pdListener;

    public interface onPartDividedListener{
        public void onPartDivided(Bundle bundle);
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
        part2 = (ImageButton)view.findViewById(R.id.part2);
    }

    public void setListener(){
        part2.setOnClickListener(this);
    }


    @Override
    public void onPause() {
//        bundle = null;
        super.onPause();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.part2:
                Bundle b = new Bundle();
                b.putInt("part", 2);
                pdListener.onPartDivided(b);
                ((NetworkAudioPlayerActivity)getActivity()).getViewPager().setCurrentItem(1);
                break;
        }
    }
}
