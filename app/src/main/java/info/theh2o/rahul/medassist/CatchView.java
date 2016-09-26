package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/25/2016.
 */
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
public class CatchView extends View {

    Paint paint;
    Path path,path1;
    float length,length1;
    public CatchView(Context context) {
        super(context);
        init();
    }

    public CatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CatchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.argb(100, 125, 145, 0));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.moveTo(350, 300);
        path.lineTo(0, 300);
        path.moveTo(350, 250);
        path.lineTo(0, 250);

        PathMeasure measure = new PathMeasure(path, false);


        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        ObjectAnimator animator = ObjectAnimator.ofFloat(CatchView.this, "phase", 1.0f, 0.0f);
        animator.setDuration(3000);
        animator.start();


    }



    public void setPhase(float phase)
    {
        Log.d("pathview", "setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();//will calll onDraw
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset)
    {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                Math.max(phase * pathLength, offset));
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

    }

}
