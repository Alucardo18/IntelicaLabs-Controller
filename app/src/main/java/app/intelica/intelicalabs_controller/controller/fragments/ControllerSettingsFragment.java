package app.intelica.intelicalabs_controller.controller.fragments;

import android.content.Intent;
import app.intelica.intelicalabs_controller.R;
import app.intelica.intelicalabs_controller.controller.PaymentScreen;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class ControllerSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.controller_mappings);

        Preference button = findPreference("about_button");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent gotToPay = new Intent(getContext().getApplicationContext(), PaymentScreen.class);
                startActivity(gotToPay);
                return true;
            }
        });
    }
}
