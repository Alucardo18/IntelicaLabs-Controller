package android.intelica.intelicalabs_controller.controller;

import android.intelica.intelicalabs_controller.R;
import android.intelica.intelicalabs_controller.controller.fragments.ControllerSettingsFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ControllerSettings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        if (savedInstanceState == null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.pref_container, new ControllerSettingsFragment());
            ft.commit();
        }
    }
}
