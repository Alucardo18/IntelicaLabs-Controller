package app.intelica.intelicalabs_controller.controller;

import android.content.pm.ActivityInfo;
import app.intelica.intelicalabs_controller.R;
import app.intelica.intelicalabs_controller.controller.fragments.ControllerSettingsFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ControllerSettings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }

        if (savedInstanceState == null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.pref_container, new ControllerSettingsFragment());
            ft.commit();
        }
    }

    public void back(View view){

        finish();
    }
}
