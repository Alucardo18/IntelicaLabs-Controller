package android.intelica.intelicalabs_controller.controller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.intelica.intelicalabs_controller.R;
import android.intelica.intelicalabs_controller.Util.StaticMessage;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class PaymentScreen extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private BillingProcessor billingProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        billingProcessor = new BillingProcessor(this, "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);
        billingProcessor.initialize();

        this.paymentAmount();

        findViewById(R.id.contactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // we might need a general email or any other point of contact for users
                sendEmail();
            }
        });
        findViewById(R.id.restoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // implement Restore purchase method from in-App Billing API 3
                Toast.makeText(view.getContext(), "has presionado el boton", Toast.LENGTH_SHORT).show();
                billingProcessor.loadOwnedPurchasesFromGoogle();
            }
        });
        findViewById(R.id.shareButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Facebook integrated along with the app
                // https://developers.facebook.com/docs/sharing/android/
                Toast.makeText(view.getContext(), "has presionado el boton", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.reviewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this requires the application to be already in the google play store
                Toast.makeText(view.getContext(), "has presionado el boton", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void paymentAmount() {
        findViewById(R.id.firstButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false);
                billingProcessor.purchase(PaymentScreen.this,"android.test.purchased");
            }
        });

        findViewById(R.id.secondButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, StaticMessage.PRODUCT_TWO);
                view.setEnabled(false);
            }
        });

        findViewById(R.id.thirdButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, StaticMessage.PRODUCT_THREE);
                view.setEnabled(false);
            }
        });

        findViewById(R.id.fourthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, StaticMessage.PRODUCT_FOUR);
                view.setEnabled(false);
            }
        });

        findViewById(R.id.fifthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, StaticMessage.PRODUCT_FIVE);
                view.setEnabled(false);
            }
        });

    }

    protected void sendEmail() {

        String TO = "emanuel2464@gmail.com";
        String CC = "";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ TO});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ CC});
        email.putExtra(Intent.EXTRA_SUBJECT, "INSERTA TU TITULO AQUI");
        email.putExtra(Intent.EXTRA_TEXT, "INSERTA TU SUGERENCIA AQUI");
        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "ESCOGE TU CORREO DE PREFERENCIA"));

    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(this, "GRACIAS POR SU DONACION!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (billingProcessor != null) {
            billingProcessor.release();
        }
        super.onDestroy();
    }
}
