package android.intelica.intelicalabs_controller.controller.fragments;


import android.content.Context;
import android.graphics.Color;
import android.intelica.intelicalabs_controller.R;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.widget.TextView;

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

        int color = getContext().getResources().getColor(R.color.preference_text_color);

        TextView title = (TextView) vh.findViewById(android.R.id.title);
        title.setTextColor(color);

        TextView description = (TextView) vh.findViewById(android.R.id.summary);
        description.setTextColor(color);
    }


}