package com.apk.aboutrasty

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.apk.aboutrasty.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setNumberClickListeners()
        setOperatorClickListeners()
        setFunctionClickListeners()
    }

    private fun playClickSound() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.sound)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    private fun setNumberClickListeners() {
        val numberIds = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        numberIds.forEach {
                btn ->
            btn.setOnClickListener {
                if (isNewInput) {
                    currentInput = ""
                    isNewInput = false
                }
                currentInput += btn.text.toString()
                updateDisplay()
                playClickSound()
            }
        }
    }

    private fun setOperatorClickListeners() {
        val operatorButtons = listOf(
            binding.btnPlus, binding.btnMinus, binding.btnMultiply, binding.btnDivide
        )

        operatorButtons.forEach {
                btn ->
            btn.setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    if (operator.isNotEmpty()) {
                        calculateResult()
                    }
                    firstNumber = currentInput.toDouble()
                    operator = btn.text.toString()
                    isNewInput = true
                    updateDisplay()
                    playClickSound()
                }
            }
        }
    }

    private fun setFunctionClickListeners() {
        binding.btnEquals.setOnClickListener {
            calculateResult()
            playClickSound()
        }
        binding.btnClear.setOnClickListener {
            clearDisplay()
            playClickSound()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateDisplay() {
        if (operator.isNotEmpty() && isNewInput) {
            binding.tvResult.text = "$firstNumber $operator"
        } else {
            binding.tvResult.text = currentInput
        }
    }

    private fun calculateResult() {
        if (currentInput.isEmpty() || operator.isEmpty()) return

        secondNumber = currentInput.toDouble()
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else {
                binding.tvResult.text = "Error"
                return
            }
            else -> 0.0
        }

        binding.tvResult.text = result.toString()
        currentInput = result.toString()
        operator = ""
        isNewInput = true
    }

    private fun clearDisplay() {
        currentInput = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        binding.tvResult.text = "0"
        isNewInput = true
    }
}
