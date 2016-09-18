package gmail.styles.megan.justjava4;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This increases the quantity of coffees.
     *
     * @param view displays the quantity.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "No more coffee, please!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This decreases the quantity of coffees.
     *
     * @param view displays the quantity.
     */
    public void decrement (View view) {
        if (quantity == 1) {
            Toast.makeText(this, "Are you sure you don't want coffee?", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

     /**
     * This method is called when the order button is clicked.
     *
     *@param view don't know what this does
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox marshmallowCheckBox = (CheckBox) findViewById(R.id.marshmallow_check_box);
        boolean hasMarshmallow = marshmallowCheckBox.isChecked();
        //Log.v("MainActivity", "Has Whipped Cream?" + hasWhippedCream);

        EditText editName = (EditText) findViewById(R.id.customer_name);
        String customerName = editName.getText().toString();
        //Log.d("MainActivity", "Customer Name?" + customerName);

        String priceMessage = createOrderSummary(customerName, hasWhippedCream, hasMarshmallow);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     *
     * @param numberOfCoffees yep
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether the user wants whipped cream
     * @param hasMarshmallow is whether the user wants a marshmallow
     *
     * @return calculates price of coffee order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasMarshmallow) {
        int price = 5 * quantity;

        //add $1 if user wants whipped cream
        if (hasWhippedCream == true) {
            price += 1;
        }
        //add $2 if the user wants a marshmallow
        if (hasMarshmallow == true) {
            price += 2;
        }
        //Log.v("MainActivity", "Price?" + price);
        return price;

    }
    /**
     * This method creates an order summary
     *
     * @param hasWhippedCream will tell you if the whipped cream check box is ticked or not.
     * @param hasMarshmallow will inform if you have a marshmallow.
     * @param customerName adds customer name fro editText field.
     *
     * @return returns string containing quantity and price of order
     */
    private String createOrderSummary (String customerName, boolean hasWhippedCream, boolean hasMarshmallow) {

        String orderSummary = getString(R.string.order_summary_name, customerName);
        orderSummary += "\n" + getString(R.string.order_summary_cream) + hasWhippedCream;
        orderSummary += "\n" + getString(R.string.order_summary_marshmallow) + hasMarshmallow;
        orderSummary += "\n" + getString(R.string.Order_summary_quantity, quantity);
        orderSummary += "\n" + getString(R.string.order_summary_total) + calculatePrice(hasWhippedCream, hasMarshmallow);
        orderSummary += "\n" + getString(R.string.thank_you);

        return orderSummary;
    }
}
