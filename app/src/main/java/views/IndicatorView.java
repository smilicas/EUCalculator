package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.milica.eucalculator.R;


/**
 * Created by Milica on 19-Feb-17.
 */

// was attended to be used as indicator for how many days left in the EU, but removed since it is not better to do it with drawable!!
public class IndicatorView extends View {

    private int circleCol, labelcol;
    private String circleText;
    public Paint circlePaint;

    public IndicatorView(Context context, AttributeSet attrs) {

        super(context, attrs);

        circlePaint = new Paint();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.IndicatorView,0,0);

        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.IndicatorView_circleLabel);
            circleCol = a.getInteger(R.styleable.IndicatorView_circleColor, 0);//0 is default
            labelcol = a.getInteger(R.styleable.IndicatorView_labelColor, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewHeight = 130;
        int viewWidth = 130;
        int radius = 100;

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleCol);
        canvas.drawCircle(viewWidth,viewHeight, radius, circlePaint);

    }

}
