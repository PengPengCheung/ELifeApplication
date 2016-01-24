package com.pengpeng.elifeapplication.newactivities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpeng.elifeapplication.R;

public class ExerciseActivity extends ActionBarActivity {

    private WordWrapView wordWrapView;
    private String[] strs = new String[]{"哲学系", "新疆维吾尔族自治区", "helloworld", "心理学",
            "犯罪心理学", "明明白白", "西方文学史", "计算机", "掌声", "心太软", "生命",
            "程序开发"};
    private int[] blanks = new int[]{2, 4, 5, 8, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        wordWrapView = (WordWrapView) findViewById(R.id.ed_view);

        int i = 0;
        int b = 0;
        for (i = 0; i < strs.length; i++) {

                if (b < blanks.length && blanks[b] == i) {
                    EditText editText = new EditText(this);
//                    editText.setText(strs[i]);
//                    Paint paint = new Paint();
//                    float width = paint.measureText(editText.getText().toString());
//                    Log.i("width", String.valueOf(width));
                    editText.setWidth(150);

//                    editText.setText(null);
//                    editText.setInputType();
//                    int width = editText.getWidth();
//                    Log.i("getwidth1", String.valueOf(editText.getWidth()));
//                    ViewGroup.LayoutParams wlParams = new WordWarpView.LayoutParams((int)width, ViewGroup.LayoutParams.MATCH_PARENT);
//                    editText.setLayoutParams(wlParams);
//                    ViewGroup.LayoutParams wp = editText.getLayoutParams();
//                    wp.height = 80;
//                    editText.setLayoutParams(wp);
//                    editText.setHeight(80);
//                    TextView tv = new TextView(this);
//                    editText.setTextSize(28.0f);

//                    Log.i("getPaddingTop", String.valueOf(editText.getPaddingTop()));
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(strs[i].length())});
                    editText.setSingleLine(true);
//                    editText.setText(strs[i]);
//                    Log.i("getwidth2", String.valueOf(editText.getWidth()));
                    wordWrapView.addView(editText);
//                    Log.i("actualMeasuredwidth", String.valueOf(editText.getMeasuredWidth()));
//                    Log.i("actualwidth", String.valueOf(editText.getWidth()));
                    Log.i("getLineHeight", String.valueOf(editText.getLineHeight()));
                    Log.i("getHeight", String.valueOf(editText.getHeight()));
                    Log.i("getMeasuredHeight", String.valueOf(editText.getMeasuredHeight()));
                    Log.i("getTextSize", String.valueOf(editText.getTextSize())); //36.0f

                    Log.i("getPaddingTop", String.valueOf(editText.getPaddingTop()));
                    Log.i("getPaddingLeft", String.valueOf(editText.getPaddingLeft()));
                    Log.i("getPaddingRight", String.valueOf(editText.getPaddingRight()));
                    Log.i("getPaddingBottom", String.valueOf(editText.getPaddingBottom()));

//                    Log.i("getIncludePaddingTop", String.valueOf(editText.getIncludeFontPadding()));

                    b++;

                } else {
                    TextView textView = new TextView(this);
                    textView.setText(strs[i]);
                    textView.setHeight(79);
                    textView.setPadding(10, 14, 14, 10);

                    textView.setTextSize(18.0f);
//                    Log.i("textSize", String.valueOf(textView.getTextSize()));
                    wordWrapView.addView(textView);

                }
        }
        Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
    }

}
