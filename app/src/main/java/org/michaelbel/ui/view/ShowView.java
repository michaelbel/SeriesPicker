package org.michaelbel.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.michaelbel.app.AndroidExtensions;
import org.michaelbel.app.Theme;
import org.michaelbel.app.rest.ApiFactory;
import org.michaelbel.material.annotation.NotTested;
import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.app.LayoutHelper;
import org.michaelbel.app.ScreenUtils;
import org.michaelbel.shows.R;

import java.util.Locale;

/**
 * Date: 23 MAR 2018
 * Time: 18:25 MSK
 *
 * @author Michael Bel
 */

@SuppressLint("ClickableViewAccessibility")
public class ShowView extends FrameLayout {

    private ImageView posterImage;
    private TextView titleText;
    private TextView overviewText;
    private CardView cardView;
    private RatingView ratingView;
    private DateView dateView;
    private ImageView watchIcon;

    private Rect rect = new Rect();

    public ShowView(Context context) {
        super(context);

        cardView = new CardView(context);
        cardView.setUseCompatPadding(false);
        cardView.setPreventCornerOverlap(false);
        cardView.setRadius(ScreenUtils.dp(2));
        cardView.setCardElevation(ScreenUtils.dp(1.0F));
        cardView.setForeground(Extensions.selectableItemBackgroundDrawable(context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.Color.foreground()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 5.5F, 5.5F, 5.5F, 0));
        addView(cardView);

//--------------------------------------------------------------------------------------------------

        FrameLayout contentLayout = new FrameLayout(context);
        contentLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        cardView.addView(contentLayout);

//--------------------------------------------------------------------------------------------------

        CardView posterLayout = new CardView(context);
        posterLayout.setCardElevation(0);
        posterLayout.setUseCompatPadding(false);
        posterLayout.setPreventCornerOverlap(false);
        posterLayout.setRadius(ScreenUtils.dp(2));
        posterLayout.setCardBackgroundColor(ContextCompat.getColor(context, Theme.Color.foreground()));
        posterLayout.setLayoutParams(LayoutHelper.makeFrame(110, 170, Gravity.START | Gravity.CENTER_VERTICAL, 4, 4, 0, 4));
        contentLayout.addView(posterLayout);

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        posterLayout.addView(posterImage);

//--------------------------------------------------------------------------------------------------

        int posterPadding = 110;

        LinearLayout infoLayout = new LinearLayout(context);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, posterPadding + 8 + 4, 8, 8, 8));
        contentLayout.addView(infoLayout);

//--------------------------------------------------------------------------------------------------

        FrameLayout layout1 = new FrameLayout(context);
        layout1.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        infoLayout.addView(layout1);

        titleText = new TextView(context);
        titleText.setLines(1);
        titleText.setMaxLines(2);
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.Color.primaryText()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 0, 20, 0));
        layout1.addView(titleText);

        watchIcon = new ImageView(context);
        watchIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_eye, ContextCompat.getColor(context, Theme.Color.iconActive())));
        watchIcon.setLayoutParams(LayoutHelper.makeFrame(16, 16, Gravity.END | Gravity.TOP, 0, 6, 0, 0));
        layout1.addView(watchIcon);

//--------------------------------------------------------------------------------------------------

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 28));
        infoLayout.addView(frameLayout);

//--------------------------------------------------------------------------------------------------

        LinearLayout rateAndDateLinear = new LinearLayout(context);
        rateAndDateLinear.setOrientation(LinearLayout.HORIZONTAL);
        rateAndDateLinear.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        frameLayout.addView(rateAndDateLinear);

        ratingView = new RatingView(context);
        ratingView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL));
        //rateAndDateLinear.addView(ratingView);

        dateView = new DateView(context);
        dateView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL, 0, 0, 0, 0));
        rateAndDateLinear.addView(dateView);

//--------------------------------------------------------------------------------------------------

        overviewText = new TextView(context);
        overviewText.setLines(1);
        overviewText.setMaxLines(5);
        overviewText.setEllipsize(TextUtils.TruncateAt.END);
        overviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        overviewText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        overviewText.setTextColor(ContextCompat.getColor(context, Theme.Color.secondaryText()));
        overviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        infoLayout.addView(overviewText);
    }

    public void setPoster(String path) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, ApiFactory.TMDB_IMAGE, "w200", path))
               .into(posterImage);
    }

    public void setTitle(String title) {
        if (title != null) {
            titleText.setText(title);
        }
    }

    public void setRating(String voteAverage) {
        ratingView.setRating(voteAverage);
    }

    public void setOverview(String overview) {
        if (TextUtils.isEmpty(overview)) {
            overviewText.setText(getResources().getString(R.string.NoOverview));
        } else {
            overviewText.setText(overview);
        }
    }

    public void setReleaseDate(String releaseDate) {
        dateView.setDate(TextUtils.isEmpty(releaseDate) ? getContext().getString(R.string.UnknownDate) : AndroidExtensions.formatDate(releaseDate));
    }

    @NotTested
    public void setWatch(boolean watch) {
        if (watch) {
            watchIcon.setVisibility(VISIBLE);
            titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 0, 20, 0));
        } else {
            watchIcon.setVisibility(GONE);
            titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 0, 0, 0));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (cardView.getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                cardView.getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }

    private class RatingView extends LinearLayout {

        private TextView ratingText;

        public RatingView(Context context) {
            super(context);

            setOrientation(LinearLayout.HORIZONTAL);

            ImageView averageIcon = new ImageView(context);
            averageIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_star_circle, ContextCompat.getColor(context, Theme.Color.iconActive())));
            averageIcon.setLayoutParams(LayoutHelper.makeLinear(16, 16, Gravity.START | Gravity.CENTER_VERTICAL));
            addView(averageIcon);

            ratingText = new TextView(context);
            ratingText.setLines(1);
            ratingText.setMaxLines(1);
            ratingText.setSingleLine();
            ratingText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            ratingText.setTextColor(ContextCompat.getColor(context, Theme.Color.secondaryText()));
            ratingText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            ratingText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 1, 0, 0, 0));
            addView(ratingText);
        }

        public void setRating(String voteAverage) {
            ratingText.setText(voteAverage);
        }
    }

    private class DateView extends LinearLayout {

        private TextView dateText;

        public DateView(Context context) {
            super(context);

            setOrientation(LinearLayout.HORIZONTAL);

            ImageView dateIcon = new ImageView(context);
            dateIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_event, ContextCompat.getColor(context, Theme.Color.iconActive())));
            dateIcon.setLayoutParams(LayoutHelper.makeLinear(16, 16, Gravity.START | Gravity.CENTER_VERTICAL));
            addView(dateIcon);

            dateText = new TextView(context);
            dateText.setLines(1);
            dateText.setMaxLines(1);
            dateText.setSingleLine();
            dateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            dateText.setTextColor(ContextCompat.getColor(context, Theme.Color.secondaryText()));
            dateText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            dateText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 2, 0, 0, 0));
            addView(dateText);
        }

        public void setDate(String releaseDate) {
            dateText.setText(releaseDate);
        }
    }
}