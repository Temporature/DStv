package za.co.dstv.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;

import za.co.dstv.R;


public class RoundedCornerCardView extends LinearLayout {

    private ImageView ivImpactImage;
    private TextView tvTitleText;
    private CardView mCardView;
    private TextView tvsubTitleText;
    private RelativeLayout mLinearLayout;

    Drawable impactImage;
    CharSequence titleText;
    CharSequence subTitleText;
    int titleTextColor;

    public RoundedCornerCardView(Context context, AttributeSet arrSet) {
        super(context,arrSet);
        initView(context, arrSet);
    }

    private void initView(Context mContext, AttributeSet arrSet) {
        inflate(mContext, R.layout.component_rounded_corner_card_view, this);
        ivImpactImage = findViewById(R.id.ivImpactImage);
        tvTitleText = findViewById(R.id.tvTitleText);
        mCardView = findViewById(R.id.cvMood);
        mLinearLayout = findViewById(R.id.llMoodLayout);
        tvsubTitleText = findViewById(R.id.tvsubTitleText);

        TypedArray typedArray = mContext.obtainStyledAttributes(arrSet, za.co.dstv.R.styleable.RoundedCornerCardView, 0, 0);
        impactImage = typedArray.getDrawable(R.styleable.RoundedCornerCardView_impactImage);
        titleText = typedArray.getText(R.styleable.RoundedCornerCardView_titleText);
        subTitleText = typedArray.getText(R.styleable.RoundedCornerCardView_subTitleText);
        titleTextColor = typedArray.getColor(R.styleable.RoundedCornerCardView_titleTextColor,getResources().getColor(R.color.text_color_grey));

        typedArray.recycle();

        setImpactImage(impactImage);
        setTitleText(titleText);
        setSubTitleText(subTitleText);
        setTitleTextColor(titleTextColor);
    }


    public Drawable getImpactImage() {
        return ivImpactImage.getDrawable();
    }

    public void setImpactImage(Drawable impactImage) {
        ivImpactImage.setImageDrawable(impactImage);
    }

    public CharSequence getTitleText() {
        return tvTitleText.getText();
    }

    public void setTitleText(CharSequence titleText) {
       tvTitleText.setText(titleText);
    }

    public CharSequence getSubTitleText() {
        return tvsubTitleText.getText();
    }

    public void setSubTitleText(CharSequence subTitleText) {
        tvsubTitleText.setText(subTitleText);
    }


    public void setTitleTextColor(int titleTextColor) {
        setCardBoarder(titleTextColor);
    }

    private void setCardBoarder(int color){
        GradientDrawable background = (GradientDrawable) mLinearLayout.getBackground();
        background.setStroke(4, color);

    }
}
