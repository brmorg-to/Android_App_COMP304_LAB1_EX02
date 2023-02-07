package com.hfad.brunomorgado_comp304section002_lab01_ex02

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val SMALL = "small"
    private val MEDIUM = "medium"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Select views for manipulation
        val spinner = findViewById<Spinner>(R.id.spinner)
        val summary = findViewById<EditText>(R.id.summary)

        // Instantiate an array to hold selected toppings
        val toppings = arrayListOf<String>()


        // Set event listener on Order Button
        val orderButton = findViewById<Button>(R.id.placeOrder)

        //Set event listener om 'PLace Order' button
        orderButton.setOnClickListener {
            //store values of input fields
            val customerName = findViewById<EditText>(R.id.customerName).text
            val email = findViewById<EditText>(R.id.email).text
            val telephone = findViewById<EditText>(R.id.phoneNumber).text
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            val selectedRadioButton = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val selectedRadioValue = selectedRadioButton.text.toString()

            // Initialize the toppings array and add toppings cost to Total Cost
            if (findViewById<CheckBox>(R.id.topping1).isChecked) {
                toppings.add("onion")
            }
            if (findViewById<CheckBox>(R.id.topping2).isChecked) {
                toppings.add("pepper")
            }
            if (findViewById<CheckBox>(R.id.topping3).isChecked) {
                toppings.add("tomato")
            }
            if (findViewById<CheckBox>(R.id.topping4).isChecked) {
                toppings.add("olive")
            }

            val sizePrice = getSizePrice(spinner.selectedItem.toString())
            val totalCost = sumTotal(sizePrice, toppings, selectedRadioValue == "TAKE OUT")

            val message = "Hi $customerName! \nemail: $email \nPhone Number: $telephone  " +
                    "\nTOTAL COST: $$totalCost \n(Size = $$sizePrice / Toppings = $${toppings.count()} / " +
                    "Delivery = $${if(selectedRadioValue == "TAKE OUT") "5.00" else "0.00"})"

            summary.setText(message)
            summary.textSize = 14f
            summary.typeface = Typeface.create(summary.typeface, Typeface.BOLD)

            val toast = Toast.makeText(applicationContext, "$customerName / phone: $telephone / cost: $$totalCost", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0,0)
            toast.show()
        }

    }

    /**
     * Return the calculated price associated to the size of the pizza
     * @param   size The input size of the pizza selected from the spinner
     * @return Size Price
     */
    private fun getSizePrice(size: String): Double {
        var sizePrice = 0.0
        val selectedSize = size.substringBefore("->").lowercase()

        sizePrice += if(selectedSize == SMALL){
            10.0
        } else if (selectedSize == MEDIUM) {
            12.0
        } else {
            14.0
        }

        return sizePrice
    }

    /**
     * Return the total price of the order in a double formatted string
     * @param   sizePrice The selected size price
     * @param   toppings a list of toppings selected from check boxes
     * @param   delivery    A boolean indicating whether the Take out radio button was selected
     * @return  Formatted string with total price
     */
    private fun sumTotal(sizePrice: Double, toppings: List<String>, delivery: Boolean): String {
        val total =  sizePrice + toppings.count() * 1.0

        if (delivery) {
            return String.format("%.2f", total + 5.0)
        }

        return String.format("%.2f", total)
    }
}