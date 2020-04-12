package za.co.dstv.component;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.dstv.R;
import za.co.dstv.utils.AppHelper;


/*
 * Expertly crafted by Sharad W.
 */

public abstract class DSTVProgressView extends ConstraintLayout {

    private String progressInactiveText;
    private SpannableStringBuilder progressActiveText;
    private float currentProgress;
    private float maximumProgress;

    @BindView(R.id.click_area)
    protected ConstraintLayout rootLayout;
    @BindView(R.id.call_to_action_text)
    protected TextView progressInactiveTextView;
    @BindView(R.id.progress_subject)
    protected TextView progressActiveTextView;
    @BindView(R.id.progress_bar)
    protected RoundCornerProgressBar progressBar;
    private boolean isProgressActive;

    public DSTVProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            previewLayout(context);
        } else {
            setup(context, attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DSTVProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isProgressActive() {
        return isProgressActive;
    }

    private void previewLayout(Context context) {
        TextView tv = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(getClass().getSimpleName());
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.GRAY);
        addView(tv);
    }

    public void setup(Context context, AttributeSet attrs) {
        setupStyleable(context, attrs);
        ConstraintLayout rootLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.multiply_progress_view, this);
        ButterKnife.bind(rootLayout);
    }

    private void fillViews() {
        if(progressActiveText.length() == 0) {
            this.setActive(false);
        }
        progressInactiveTextView.setText(progressInactiveText);
        progressActiveTextView.setText(progressActiveText);
        progressActiveTextView.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        progressBar.setMax(maximumProgress);
        progressBar.setProgress(currentProgress);
    }


    public void setOnProgressClickListener(OnClickListener listener) {
        rootLayout.setOnClickListener(listener);
    }

    public void setProgress(float progress, float maximumProgress) {
        currentProgress = progress;
        this.maximumProgress = maximumProgress;
        fillViews();
    }

    public void setInactiveText(String text) {
        progressInactiveText = text;
        fillViews();
    }

    public void setActiveText( String text) {
        String activeText = text;
        progressActiveText = AppHelper.substringBoldDark(getContext(),activeText, activeText.indexOf(text), activeText.length(), false, false);
        fillViews();
    }

    public void setInactiveActiveText( String text) {
        progressInactiveText = text;
        fillViews();
    }

    public void setActive(boolean isActive) {
        this.isProgressActive = isActive;
        progressInactiveTextView.setVisibility(isActive ? GONE : VISIBLE);
        progressInactiveTextView.setText(progressInactiveText);
        progressBar.setVisibility(isActive ? VISIBLE : GONE);
        progressActiveTextView.setVisibility(isActive ? VISIBLE : GONE);
    }

    public void setupStyleable(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DSTVProgressView);
        currentProgress = typedArray.getFloat(R.styleable.DSTVProgressView_multiply_progress, 0);
        maximumProgress = typedArray.getFloat(R.styleable.DSTVProgressView_multiply_progress_max, 100);
    }

}
