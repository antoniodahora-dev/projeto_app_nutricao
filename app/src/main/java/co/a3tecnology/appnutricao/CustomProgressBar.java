package co.a3tecnology.appnutricao;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class CustomProgressBar extends View {

    private final float stroke = 40.0f;
    private final RectF backgroundArc = new RectF();
    private final Paint bgPaint = new Paint();

    private final Paint paint = new Paint();

    private int progressBarValue = 75;
    private int progressBarColor;
    private int progressBarBgColor;

    private final Rect bounds = new Rect();
    private final RectF barArc = new RectF();

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);

        progressBarValue = typedArray.getInt(R.styleable.CustomProgressBar_my_progress,0);
        progressBarColor = typedArray.getColor(R.styleable.CustomProgressBar_my_progress_color, 0);
        progressBarBgColor = typedArray.getColor(R.styleable.CustomProgressBar_my_progress_bg_color, Color.LTGRAY);

        typedArray.recycle();
    }

    public void setValue(int progressBarValue) {
        this.progressBarValue = progressBarValue;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //ira converter para uma medida universal para todos os tamanhos de tela
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        //ira aplicar as dimensoes para converter as DisplayMetrics
        float stroke = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                this.stroke, metrics);

        backgroundArc.set(stroke, stroke, getWidth() - stroke,
                getHeight() - stroke);

        bgPaint.setColor(progressBarBgColor);
        bgPaint.setStyle(Paint.Style.STROKE); // pintar apena as bordas
        bgPaint.setStrokeWidth(60.0f); // bordas
        bgPaint.setAntiAlias(true); // suavizar as bordas

        canvas.drawArc(backgroundArc, 0.0f, 360.0f,
                false, bgPaint);


        /* Novo circulo
         */

        paint.setColor(progressBarColor);
        paint.setStyle(Paint.Style.STROKE); // pintar apena as bordas
        paint.setStrokeWidth(stroke - 40.0f); // bordas
        paint.setStrokeCap(Paint.Cap.ROUND); //irá deixar as pontas redondas
        paint.setAntiAlias(true); // suavizar as bordas
        paint.setDither(true); // melhorar a cor do circulo

        float progress = (360.0f / 100) * progressBarValue;
        canvas.getClipBounds(bounds); //propriedades de w x h

        barArc.set(
         stroke, stroke, bounds.right - stroke, bounds.bottom - stroke);
        canvas.drawArc(barArc,270.0f, progress, false, paint );
    }
}
