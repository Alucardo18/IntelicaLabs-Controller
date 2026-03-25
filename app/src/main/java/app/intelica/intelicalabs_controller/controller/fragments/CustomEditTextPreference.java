package app.intelica.intelicalabs_controller.controller.fragments;


import android.content.Context;
import android.graphics.Color;
import app.intelica.intelicalabs_controller.R;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceViewHolder;

public class CustomEditTextPreference extends EditTextPreference {
    public CustomEditTextPreference(Context context) {
        super(context);
    }

    public CustomEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditTextPreference(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public final void onBindViewHolder(final PreferenceViewHolder vh) {
        super.onBindViewHolder(vh);

        int color = androidx.core.content.ContextCompat.getColor(getContext(), R.color.preference_text_color);

        TextView title = (TextView) vh.findViewById(android.R.id.title);
        title.setTextColor(color);

        TextView description = (TextView) vh.findViewById(android.R.id.summary);
        description.setTextColor(color);
    }


}
