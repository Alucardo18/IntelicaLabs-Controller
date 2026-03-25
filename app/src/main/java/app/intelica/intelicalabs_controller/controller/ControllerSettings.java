package app.intelica.intelicalabs_controller.controller;

import android.content.pm.ActivityInfo;
import app.intelica.intelicalabs_controller.R;
import app.intelica.intelicalabs_controller.controller.fragments.ControllerSettingsFragment;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
