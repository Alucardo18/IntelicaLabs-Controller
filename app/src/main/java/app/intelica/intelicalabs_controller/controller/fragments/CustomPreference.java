package app.intelica.intelicalabs_controller.controller.fragments;

import android.content.Context;
import app.intelica.intelicalabs_controller.R;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomPreference extends Preference {
    public CustomPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPreference(Context context) {
        super(context);
    }

    @Override
    public final void onBindViewHolder(final PreferenceViewHolder vh) {
        super.onBindViewHolder(vh);

        int color = getContext().getResources().getColor(R.color.preference_text_color);

        TextView title = (TextView) vh.findViewById(android.R.id.title);
        title.setTextColor(color);

        TextView description = (TextView) vh.findViewById(android.R.id.summary);
        description.setTextColor(color);
    }
}
