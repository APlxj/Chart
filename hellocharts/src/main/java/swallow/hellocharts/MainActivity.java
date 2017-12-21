package swallow.hellocharts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity {
    lecho.lib.hellocharts.view.LineChartView lineChart;
    lecho.lib.hellocharts.view.PieChartView chart;
    lecho.lib.hellocharts.view.ColumnChartView colum;
    lecho.lib.hellocharts.view.ComboLineColumnChartView comboChart;
    lecho.lib.hellocharts.view.BubbleChartView bubbleChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setData();
    }

    private void initView() {
        lineChart = (LineChartView) findViewById(R.id.LineChartView);
        chart = (PieChartView) findViewById(R.id.PieChartView);
        colum = (ColumnChartView) findViewById(R.id.ColumnChartView);
        comboChart = (ComboLineColumnChartView) findViewById(R.id.ComboLineColumnChartView);
        bubbleChart = (BubbleChartView) findViewById(R.id.BubbleChartView);

        lineChart.setVisibility(View.VISIBLE);
        chart.setVisibility(View.GONE);
        colum.setVisibility(View.GONE);
        comboChart.setVisibility(View.GONE);
        bubbleChart.setVisibility(View.GONE);
    }

    private void setData() {
        //折线图
        initLineChart();
        //圆饼图
        generateData();
        //柱状图
        dataInit(0);
        //柱状图+折线图
        initComboLineColumnChartView();
        //气泡图
        initBubbleChartView();
    }

    public void btnOnClock(View view) {
        lineChart.setVisibility(View.GONE);
        chart.setVisibility(View.GONE);
        colum.setVisibility(View.GONE);
        comboChart.setVisibility(View.GONE);
        bubbleChart.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.btn1:
                dataInit(0);
                colum.setVisibility(View.VISIBLE);
                break;
            case R.id.btn2:
                dataInit(1);
                colum.setVisibility(View.VISIBLE);
                break;
            case R.id.btn3:
                dataInit(3);
                colum.setVisibility(View.VISIBLE);
                break;
            case R.id.LineChart:
                lineChart.setVisibility(View.VISIBLE);
                break;
            case R.id.PieChart:
                chart.setVisibility(View.VISIBLE);
                break;
            case R.id.ColumnChart:
                colum.setVisibility(View.VISIBLE);
                break;
            case R.id.ComboLineColumnChart:
                comboChart.setVisibility(View.VISIBLE);
                break;
            case R.id.BubbleChart:
                bubbleChart.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initLineChart() {
        List<PointValue> mPointValues;
        List<AxisValue> mAxisXValues;
        String[] date;
        int[] score;
        date = new String[]{"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};//X轴的标注
        score = new int[]{0, 50, 42, 90, 33, 10, 74, 22, 100, 18, 79, 20, 80};//图表的数据点
        mPointValues = new ArrayList<PointValue>();
        mAxisXValues = new ArrayList<AxisValue>();
        //设置X 轴的显示
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
        //设置X 轴的显示
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
        Line line = new Line(mPointValues).setColor(Color.parseColor("#30B2B2"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        line.setPointColor(Color.parseColor("#ff0000"));//圆点及备注颜色
        line.setStrokeWidth(2);//线条粗细
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName("日期");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(mAxisXValues.size()); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisY.setTextColor(Color.BLACK);  //设置字体颜色
        axisY.setName("金额");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setHasLines(true); //x 轴分割线
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 6;
        lineChart.setCurrentViewport(v);
    }

    private void generateData() {
        int numValues = 8;

        float[] floats = new float[]{10, 9, 35, 10, 13, 10, 2, 11};
        String[] strings = new String[]{"#8646FA", "#0646FA", "#8606FA", "#0606FA", "#86460A", "#76F6FA"
                , "#06460A", "#FFFF00"};

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue(floats[i], Color.parseColor(strings[i]));
            values.add(sliceValue);
        }

        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);// 是否显示数据
        data.setHasLabelsOnlyForSelected(false);// 是否选中显示数据，一般为false
        data.setHasLabelsOutside(false);// 数据是否显示在外面
        data.setValueLabelsTextColor(Color.parseColor("#FFFFFF"));
        data.setHasCenterCircle(true);// 是否含有中圈，显示下面的内容这个必须为true
        // 设置不显示数据的背景颜色
        data.setValueLabelBackgroundEnabled(false);

        if (true) {// 是否爆破形式
            data.setSlicesSpacing(1);
        }

        if (true) {// 圆中是否含有内容1
            data.setCenterText1("2.14%");

            // Get roboto-italic font.
//            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
            data.setCenterText1Color(getResources().getColor(R.color.gray));
        }

        if (true) {// 圆中是否含有内容2
            data.setCenterText2("利率");

            //Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
            //data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
            data.setCenterText2Color(getResources().getColor(R.color.gray));
        }

        chart.setPieChartData(data);
    }

    //初始化数据并显示在图表上
    private void dataInit(int k) {

        //声明所需变量
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",};
        String[] monthsy = new String[]{"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110",};
        float[] f0 = new float[]{5, (float) 20.5, 10, 55, 68, 77, (float) 26.9, 40, 87, 69, (float) 7.5, 11, 54};
        float[] f1 = new float[]{15, 77, (float) 26.9, 40, 87, 69, (float) 7.5, 11, 54, (float) 20.5, 10, 55, 68};
        int numSubcolumns = 1;
        int numColumns = months.length;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<AxisValue> axisValuesy = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                if (k == 0 || k == 1) {
                    values.add(new SubcolumnValue(k == 0 ? f0[i] : f1[i], ChartUtils.pickColor()));
                } else if (k == 3) {
                    values.add(new SubcolumnValue((f0[i]), ChartUtils.pickColor()));
                    values.add(new SubcolumnValue((f1[i]), ChartUtils.pickColor()));
                }
            }
            // 点击柱状图就展示数据量
            axisValues.add(new AxisValue(i).setLabel(months[i]));
            axisValuesy.add(new AxisValue(i * 10).setLabel(monthsy[i]));
            Column column = new Column(values);
            /*column.setHasLabelsOnlyForSelected(true);*///设置后数值不显示
            columns.add(column);
            column.setHasLabels(true);
        }
        Axis axisX = new Axis(axisValues); //将自定义x轴显示值传入构造函数
        axisX.setName("Axis X");    //设置横轴名称
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(9); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
//        axisX.setHasLines(true); //x 轴分割线

        Axis axisY = new Axis(axisValuesy); //setHasLines是设置线条
        axisY.setName("Axis Y");    //设置竖轴名称
        axisY.setHasLines(true); //x 轴分割线
        axisY.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisY.setTextColor(Color.BLACK);  //设置字体颜色
        axisY.setTextSize(10);//设置字体大小
        axisY.setMaxLabelChars(15);

        ColumnChartData columnData = new ColumnChartData(columns);
        columnData.setAxisXBottom(axisX
                .setTextColor(Color.BLACK));
        columnData.setAxisYLeft(axisY
                .setTextColor(Color.BLACK).setMaxLabelChars(3));
        colum.setColumnChartData(columnData);
        // Set value touch listener that will trigger changes for chartTop.
        colum.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
                Toast.makeText(MainActivity.this, "i=" + i + "    i1=" + i1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });
        // Set selection mode to keep selected month column highlighted.
        colum.setValueSelectionEnabled(true);
        colum.setZoomType(ZoomType.HORIZONTAL);
    }

    private void initComboLineColumnChartView() {
        comboChart.setZoomEnabled(true);//设置是否支持缩放
        //为图表设置值得触摸事件
        //设置值触摸侦听器，将触发图表顶部的变化。
        comboChart.setOnValueTouchListener(new ComboLineColumnChartOnValueSelectListener() {
            @Override
            public void onColumnValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {

            }

            @Override
            public void onPointValueSelected(int i, int i1, PointValue pointValue) {

            }

            @Override
            public void onValueDeselected() {

            }
        });
        //设置图表是否可以与用户互动
        comboChart.setInteractive(true);
        //设置图表数据是否选中进行显示
        comboChart.setValueSelectionEnabled(true);
        //定义组合数据对象
        ComboLineColumnChartData comboLineColumnChartData = new ComboLineColumnChartData();
        //为图表设置数据，数据类型为ComboLineColumnChartData
        comboChart.setComboLineColumnChartData(comboLineColumnChartData);

        //为组合图设置折现图数据
        List<Line> dataLine = initDataLine();
        LineChartData lineCharData = initLineCharData(dataLine);
        lineCharData.setLines(dataLine);
        comboLineColumnChartData.setLineChartData(lineCharData);

        //为组合图设置柱形图数据
        List<Column> dataColumn = initColumnLine();
        ColumnChartData columnChartData = initColumnCharData(dataColumn);
        columnChartData.setColumns(dataColumn);
        comboLineColumnChartData.setColumnChartData(columnChartData);

        comboLineColumnChartData.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
        comboLineColumnChartData.setValueLabelTextSize(25);// 设置数据文字大小
        comboLineColumnChartData.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式

        Axis axisX = new Axis()/*.setHasLines(true)*/;
        Axis axisY = new Axis().setHasLines(true);
        axisX.setValues(axisValues);
        axisY.setTextColor(Color.BLACK);
        axisY.setTextColor(Color.BLACK);
        comboLineColumnChartData.setAxisYLeft(axisY);
        comboLineColumnChartData.setAxisXBottom(axisX);
        //comboLineColumnChartData.setAxisYRight(axisYRight);//设置右边显示的轴
        //comboLineColumnChartData.setAxisXTop(axisXTop);//设置顶部显示的轴
        comboChart.setComboLineColumnChartData(comboLineColumnChartData);//为组合图添加数据

        Viewport viewport = initViewPort();
        comboChart.setMaximumViewport(viewport);
        comboChart.setCurrentViewport(viewport);
    }

    private Viewport initViewPort() {
        Viewport viewport = new Viewport();
        viewport.top = 200;
        viewport.bottom = 0;
        viewport.left = -1;
        viewport.right = 12;

        return viewport;
    }

    public final static String[] months = new String[]{"Jan", "Feb", "Mar",
            "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",};
    public static List<AxisValue> axisValues;

    //设置折线图,添加设置好的数据
    public static LineChartData initLineCharData(List<Line> dataLine) {
        LineChartData lineCharData = new LineChartData(dataLine);
        //初始化轴
        Axis axisX = new Axis()/*.setHasLines(true)*/;
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("时间");
        axisY.setName("销量");
        lineCharData.setAxisYLeft(axisY);
        lineCharData.setAxisXBottom(axisX);
        return lineCharData;
    }

    //定义方法设置折线图中数据
    public static List<Line> initDataLine() {
        float[] f0 = new float[]{5, (float) 20.5, 10, 55, 68, 77, (float) 26.9, 40, 87, 69, (float) 7.5, 11, 54};
        List<Line> lineList = new ArrayList<>();
        List<PointValue> pointValueList = new ArrayList<>();

        int numLines = months.length;
        axisValues = new ArrayList<>();
        for (int i = 0; i < numLines; ++i) {
            pointValueList.add(new PointValue(i, f0[i] + 100));
            axisValues.add(new AxisValue(i).setLabel(months[i]));
        }

        Line line = new Line(pointValueList);
        line.setColor(Color.RED);
        line.setShape(ValueShape.CIRCLE);
        /*line.setHasLabelsOnlyForSelected(true);*/
        line.setHasLabels(false);
        line.setHasPoints(false);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lineList.add(line);

        return lineList;
    }

    //定义方法设置柱状图中数据
    public ColumnChartData initColumnCharData(List<Column> dataLine) {
        ColumnChartData columnData = new ColumnChartData(dataLine);

        columnData.setAxisXBottom(new Axis(axisValues)/*.setHasLines(true)*/
                .setTextColor(Color.BLACK));
        columnData.setAxisYLeft(new Axis().setHasLines(true)
                .setTextColor(Color.BLACK).setMaxLabelChars(2));
        // Set selection mode to keep selected month column highlighted.
        comboChart.setValueSelectionEnabled(true);
        comboChart.setZoomType(ZoomType.HORIZONTAL);

        return columnData;
    }

    //定义方法设置柱状图中数据
    public static List<Column> initColumnLine() {
        float[] f0 = new float[]{5, (float) 20.5, 10, 55, 68, 77, (float) 26.9, 40, 87, 69, (float) 7.5, 11, 54};
        List<Column> list = new ArrayList<>();
        if (false) {
            //是否显示柱状图
            return list;
        }
        List<SubcolumnValue> subcolumnValueList;
        axisValues = new ArrayList<AxisValue>();
        int numSubcolumns = 1;
        int numColumns = months.length;
        for (int i = 0; i < numColumns; ++i) {
            subcolumnValueList = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                subcolumnValueList.add(new SubcolumnValue(f0[i],
                        ChartUtils.pickColor()));
            }
            // 点击柱状图就展示数据量
            axisValues.add(new AxisValue(i).setLabel(months[i]));
            list.add(new Column(subcolumnValueList).setHasLabels(true)/*是否显示数值*/);
        }
        return list;
    }

    private void initBubbleChartView() {
/*bubbleChart.setZoomEnabled(boolean isZoomEnabled)//设置是否支持缩放
    bubbleChart.setOnValueTouchListener(LineChartOnValueSelectListener touchListener);//为图表设置值得触摸事件
    bubbleChart.setInteractive(boolean isInteractive);// 用户是否对其可以进行互动
    bubbleChart.setValueSelectionEnabled(boolean isSelection);//设置值选中后进行显示*/

    /*List<BubbleValue> pointValues = new ArrayList<BubbleValue>();// 节点数据结合
    Axis axisY = new Axis().setHasLines(true);// Y轴属性
    Axis axisX = new Axis();// X轴属性
    axisY.setName(String yName);//设置Y轴显示名称
    axisX.setName(String xName);//设置X轴显示名称
    ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();//定义X轴刻度值的数据集合
    ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();//定义Y轴刻度值的数据集合
    axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
    axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
    axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
    axisX.setTextColor(Color color);// 设置X轴文字颜色
    axisY.setTextColor(Color color);// 设置Y轴文字颜色
    axisX.setTextSize(14);// 设置X轴文字大小
    axisX.setTypeface(Typeface.DEFAULT);// 设置文字样式，此处为默认
    axisX.setHasTiltedLabels(bolean isHasTit);// 设置X轴文字向左旋转45度
    axisX.setHasLines(boolean isHasLines);// 是否显示X轴网格线
    axisY.setHasLines(boolean isHasLines);// 是否显示Y轴网格线
    axisX.setHasSeparationLine(boolean isHasSeparationLine);// 设置是否有分割线
    axisX.setInside(boolean isInside);// 设置X轴文字是否在X轴内部
    BubbleValue v=new BubbleValue();//定义气泡
    v.set(float x,float y,float z);//设置气泡的横纵坐标x、y，z为气泡的半径
    v.setColor(int color);//设置气泡的颜色
    v.setLabel(String label);//设置气泡中显示的文本
    v.setShape(ValueShape shape);//设置气泡的形状*/
    }
}
