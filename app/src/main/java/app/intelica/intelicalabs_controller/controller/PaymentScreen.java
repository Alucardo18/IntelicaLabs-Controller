package app.intelica.intelicalabs_controller.controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import app.intelica.intelicalabs_controller.R;
import app.intelica.intelicalabs_controller.Util.GoogleConsole;
import app.intelica.intelicalabs_controller.Util.StaticMessage;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

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

        // LOAD VIEWS AND CHECK PURCHASED STATUS
        this.paymentAmount();
        this.checkAlreadyPurchasedProducts();

        findViewById(R.id.contactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        findViewById(R.id.restoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.loadOwnedPurchasesFromGoogle();
            }
        });
        findViewById(R.id.shareButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Facebook integrated along with the app
                // https://developers.facebook.com/docs/sharing/android/
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(PaymentScreen.this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });
        findViewById(R.id.reviewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playStoreReview();
            }
        });

    }

    public void back(View view) {
        finish();
    }

    public void paymentAmount() {
        this.billingBtOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                billingProcessor.purchase(PaymentScreen.this, "android.test.purchased");
                billingProcessor.purchase(PaymentScreen.this, GoogleConsole.productOneId);

            }
        });

        this.billingBtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, GoogleConsole.productTwoId);
//                billingProcessor.consumePurchase("android.test.purchased");
            }
        });

        this.billingBtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, GoogleConsole.productThreeId);
            }
        });

        this.billingBtFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, GoogleConsole.productFourId);
            }
        });

        this.billingBtFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.purchase(PaymentScreen.this, GoogleConsole.productFiveId);
            }
        });

    }

    protected void sendEmail() {

        String TO = "emanuel2464@gmail.com";
        String CC = "";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ CC});
        email.putExtra(
                Intent.EXTRA_SUBJECT,
                getResources().getText(R.string.donation_email_title)
        );
        email.putExtra(
                Intent.EXTRA_TEXT,
                getResources().getText(R.string.donation_email_text)
        );
        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(
                email,
                getResources().getText(R.string.donation_email_choose_message)
        ));

    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(
                this,
                getResources().getText(R.string.donation_thank_you_message),
                Toast.LENGTH_SHORT
        ).show();


        switch (productId) {
            case GoogleConsole.productOneId:
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
        Toast.makeText(
                PaymentScreen.this,
                getResources().getText(R.string.donation_billing_error),
                Toast.LENGTH_LONG
        ).show();
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

    public void playStoreReview() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + StaticMessage.FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + StaticMessage.FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return StaticMessage.FACEBOOK_URL; //normal web url
        }
    }

    private void checkAlreadyPurchasedProducts() {

        List<String> products = new ArrayList<>();
        products.add(GoogleConsole.productOneId);
        products.add(GoogleConsole.productTwoId);
        products.add(GoogleConsole.productThreeId);
        products.add(GoogleConsole.productFourId);
        products.add(GoogleConsole.productFiveId);
//        products.add("android.test.purchased");

        for (String id : products) {
            if (billingProcessor.isPurchased(id)) {
                switch (id) {
                    case GoogleConsole.productOneId:
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
                        Toast.makeText(PaymentScreen.this, "productId not found: " + id, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
