package android.intelica.intelicalabs_controller.controller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.intelica.intelicalabs_controller.R;
import android.intelica.intelicalabs_controller.Util.GoogleConsole;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import mehdi.sakout.fancybuttons.FancyButton;

import static android.intelica.intelicalabs_controller.Util.GoogleConsole.*;

public class PaymentScreen extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    BillingProcessor billingProcessor;
    FancyButton billingBtOne;
    FancyButton billingBtTwo;
    FancyButton billingBtThree;
    FancyButton billingBtFour;
    FancyButton billingBtFive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        billingProcessor = new BillingProcessor(this, GoogleConsole.applicationId, this);
        billingProcessor.initialize();

        this.billingBtOne = (FancyButton) findViewById(R.id.firstButton);
        this.billingBtTwo = (FancyButton) findViewById(R.id.secondButton);
        this.billingBtThree = (FancyButton) findViewById(R.id.thirdButton);
        this.billingBtFour = (FancyButton) findViewById(R.id.fourthButton);
        this.billingBtFive = (FancyButton) findViewById(R.id.fifthButton);

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
//                billingProcessor.loadOwnedPurchasesFromGoogle();
                billingProcessor.consumePurchase("android.test.purchased");

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
        this.billingBtOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                billingProcessor.purchase(PaymentScreen.this, "android.test.purchased");
                billingProcessor.purchase(PaymentScreen.this, productOneId);

            }
        });

        this.billingBtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, productTwoId);

            }
        });

        this.billingBtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, productThreeId);
            }
        });

        this.billingBtFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, productFourId);
            }
        });

        this.billingBtFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, productFiveId);
            }
        });

    }

    protected void sendEmail() {

        String TO = "emanuel2464@gmail.com";
        String CC = "";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
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
//        if (billingProcessor.isPurchased(productId)) {
//            this.billingBtOne.setEnabled(false);
//        }

        switch (productId) {
            case GoogleConsole.productOneId :
                this.billingBtOne.setEnabled(false);
                break;
            case GoogleConsole.productTwoId:
                this.billingBtTwo.setEnabled(false);
                break;
            case GoogleConsole.productThreeId:
                this.billingBtThree.setEnabled(false);
                break;
            case GoogleConsole.productFourId:
                this.billingBtFour.setEnabled(false);
                break;
            case GoogleConsole.productFiveId:
                billingBtFive.setEnabled(false);
                break;
            default:
                Toast.makeText(PaymentScreen.this, "productId not found: " + productId, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(PaymentScreen.this,"¡SOMETHING WENT WRONG!",Toast.LENGTH_LONG).show();
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
