/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        CheckBox choclateChecBox = (CheckBox)findViewById(R.id.choclate_checkbox);
        boolean hasChoclate = choclateChecBox.isChecked();
        //Log.v("MainActivity","The price is"+ price);
        EditText namefield = (EditText) findViewById(R.id.name_field);
        String value = namefield.getText().toString();
        int price = calculatePrice(hasWhippedCream,hasChoclate);
        String priceMessage = createOrderSummary(price,hasWhippedCream,hasChoclate,value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this;
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava orders for"+ value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private String createOrderSummary(int number,boolean hasWhippedCream,boolean hasChoclate,String value){
        String priceMessage = "Name: " + value + "\n";
        priceMessage = priceMessage + "Add Whipped Cream?"+ hasWhippedCream + "\n";
        priceMessage = priceMessage + "Add Choclate?" + hasChoclate + "\n";
        priceMessage = priceMessage + "Quantity: " + quantity + "\n";
        priceMessage = priceMessage + "Total: $"+ number;
        priceMessage = priceMessage + "\n" + "Thank You!";
        return priceMessage;
    }
    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean hasWhippedCream,boolean hasChoclate) {
        int pricePerCup = 5;
        if(hasWhippedCream){
            pricePerCup = pricePerCup +1;
        }
        if(hasChoclate){
            pricePerCup = pricePerCup + 2;
        }
        int price = quantity * pricePerCup;
        return price;
    }
    public void increment(View view) {
        if(quantity<=99) {
            quantity = quantity + 1;
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Cannot order more than 100 coffee!!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
            toast.show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity>=2) {
            quantity = quantity - 1;
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Cannot order less than 1 coffee!!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
            toast.show();
        }
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}