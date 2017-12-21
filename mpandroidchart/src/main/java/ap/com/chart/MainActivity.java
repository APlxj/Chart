package ap.com.chart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LineChart chart;
    PieChart mPieChart;
    //显示百分比
    private Button btn_show_percentage;
    //显示类型
    private Button btn_show_type;
    //x轴动画
    private Button btn_anim_x;
    //y轴动画
    private Button btn_anim_y;
    //xy轴动画
    private Button btn_anim_xy;
    //保存到sd卡
    private Button btn_save_pic;
    //显示中间文字
    private Button btn_show_center_text;
    //旋转动画
    private Button btn_anim_rotating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (LineChart) findViewById(R.id.chart);
        mPieChart = (PieChart) findViewById(R.id.piechart);
        initView();

        ChartUtils.initChart(mPieChart, ChartUtils.Type.PIE);
        ChartUtils.notifyDataSetChanged(mPieChart, getData(), ChartUtils.weekValue, ChartUtils.Type.PIE);
    }

    private void initView() {
        btn_show_percentage = (Button) findViewById(R.id.btn_show_percentage);
        btn_show_percentage.setOnClickListener(this);
        btn_show_type = (Button) findViewById(R.id.btn_show_type);
        btn_show_type.setOnClickListener(this);
        btn_anim_x = (Button) findViewById(R.id.btn_anim_x);
        btn_anim_x.setOnClickListener(this);
        btn_anim_y = (Button) findViewById(R.id.btn_anim_y);
        btn_anim_y.setOnClickListener(this);
        btn_anim_xy = (Button) findViewById(R.id.btn_anim_xy);
        btn_anim_xy.setOnClickListener(this);
        btn_save_pic = (Button) findViewById(R.id.btn_save_pic);
        btn_save_pic.setOnClickListener(this);
        btn_show_center_text = (Button) findViewById(R.id.btn_show_center_text);
        btn_show_center_text.setOnClickListener(this);
        btn_anim_rotating = (Button) findViewById(R.id.btn_anim_rotating);
        btn_anim_rotating.setOnClickListener(this);
    }

    private List<Entry> getData() {
        /*List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 15));
        values.add(new Entry(1, 15));
        values.add(new Entry(2, 15));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 25));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 20));
        return values;*/
        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new PieEntry(2, "满分"));
        entries.add(new PieEntry(13, "优秀"));
        entries.add(new PieEntry(25, "优良"));
        entries.add(new PieEntry(24, "良"));
        entries.add(new PieEntry(30, "及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(1, "缺席"));
        return entries;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //显示百分比
            case R.id.btn_show_percentage:
                for (IDataSet<?> set : mPieChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mPieChart.invalidate();
                break;
            //显示类型
            case R.id.btn_show_type:
                if (mPieChart.isDrawHoleEnabled())
                    mPieChart.setDrawHoleEnabled(false);
                else
                    mPieChart.setDrawHoleEnabled(true);
                mPieChart.invalidate();
                break;
            //x轴动画
            case R.id.btn_anim_x:
                mPieChart.animateX(1400);
                break;
            //y轴动画
            case R.id.btn_anim_y:
                mPieChart.animateY(1400);
                break;
            //xy轴动画
            case R.id.btn_anim_xy:
                mPieChart.animateXY(1400, 1400);
                break;
            //保存到sd卡
            case R.id.btn_save_pic:
                mPieChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            //显示中间文字
            case R.id.btn_show_center_text:
                if (mPieChart.isDrawCenterTextEnabled())
                    mPieChart.setDrawCenterText(false);
                else
                    mPieChart.setDrawCenterText(true);
                mPieChart.invalidate();
                break;
            //旋转动画
            case R.id.btn_anim_rotating:
                mPieChart.spin(1000, mPieChart.getRotationAngle(), mPieChart.getRotationAngle() + 360, Easing.EasingOption
                        .EaseInCubic);
                break;
        }
    }
}
