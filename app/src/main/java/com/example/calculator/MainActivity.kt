package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var display: TextView
    private var lastResult: Int? = null
    private var currentInput: String = ""
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Kết nối các nút
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEquals, R.id.btnClear
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn0 -> appendDigit("0")
            R.id.btn1 -> appendDigit("1")
            R.id.btn2 -> appendDigit("2")
            R.id.btn3 -> appendDigit("3")
            R.id.btn4 -> appendDigit("4")
            R.id.btn5 -> appendDigit("5")
            R.id.btn6 -> appendDigit("6")
            R.id.btn7 -> appendDigit("7")
            R.id.btn8 -> appendDigit("8")
            R.id.btn9 -> appendDigit("9")
            R.id.btnPlus -> setOperator("+")
            R.id.btnMinus -> setOperator("-")
            R.id.btnMultiply -> setOperator("*")
            R.id.btnDivide -> setOperator("/")
            R.id.btnEquals -> calculateResult()
            R.id.btnClear -> clearInput()
        }
    }

    private fun appendDigit(digit: String) {
        currentInput += digit
        if (lastResult != null && operator != null) {
            display.text = "${lastResult.toString()} $operator $currentInput"
        } else {
            display.text = currentInput
        }
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            val inputValue = currentInput.toInt()
            if (lastResult == null) {
                lastResult = inputValue
            } else {
                calculateResult()
            }
            operator = op


            currentInput = ""
            display.text = "${lastResult.toString()} $operator "
        }
    }

    private fun calculateResult() {
        if (lastResult != null && operator != null && currentInput.isNotEmpty()) {
            val secondOperand = currentInput.toInt()
            lastResult = when (operator) {
                "+" -> lastResult!! + secondOperand
                "-" -> lastResult!! - secondOperand
                "*" -> lastResult!! * secondOperand
                "/" -> if (secondOperand != 0) lastResult!! / secondOperand else null
                else -> null
            }

            display.text = lastResult?.toString()

            currentInput = ""
            operator = null
        } else if (lastResult != null && operator != null && currentInput.isEmpty()) {
            display.text = lastResult?.toString()
        }
    }

    private fun clearInput() {
        currentInput = ""
        lastResult = null
        operator = null
        display.text = "0"
    }
}
