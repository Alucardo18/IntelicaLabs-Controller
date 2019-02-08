package android.intelica.intelicalabs_controller.controller;

import android.content.pm.ActivityInfo;
import android.intelica.intelicalabs_controller.R;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PaymentScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findViewById(R.id.contactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"has presionado el boton",Toast.LENGTH_SHORT).show();

            }
        });




    }



    public void paymentAmount(){
    findViewById(R.id.firstButton).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),"GRACIAS POR SU COMPRA!",Toast.LENGTH_SHORT).show();
            v.setEnabled(false);
        }
    });

    }


}
