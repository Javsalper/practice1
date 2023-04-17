package com.alten.javsalper.practice1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switch1 = findViewById<Switch>(R.id.switch1)
        val button = findViewById<Button>(R.id.button)
        switch1.setOnCheckedChangeListener { _, isChecked ->
            button.isEnabled = isChecked
        }

        val emailEditText: EditText = findViewById(R.id.editTextTextEmailAddress)
        val passwordEditText: EditText = findViewById(R.id.editTextTextPassword)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        emailEditText.requestFocus()
        emailEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                passwordEditText.requestFocus()
                true
            } else {
                false
            }
        }
        emailEditText.isFocusableInTouchMode = true
        emailEditText.nextFocusDownId = passwordEditText.id

        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun validatePassword(password: String): Boolean {
            val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,8}$")
            return regex.matches(password)
        }

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                val isEmailValid = isValidEmail(email)
                val isPasswordValid = validatePassword(passwordEditText.text.toString())
                button.isEnabled = isEmailValid && isPasswordValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                button.performClick()
                true
            } else {
                false
            }
        }

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                val isPasswordValid = validatePassword(password)
                val isEmailValid = isValidEmail(emailEditText.text.toString())
                button.isEnabled = isEmailValid && isPasswordValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        button.setOnClickListener {

            button.isEnabled = false
            progressBar.visibility = View.VISIBLE

            // Simulate login request for 1 second
            Handler(Looper.getMainLooper()).postDelayed({
                // Hide the progress bar
                progressBar.visibility = View.GONE

                // Check if the email and password are correct
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                if (email == "example@gmail.com" && password == "Password1") {
                    // Go to the welcome screen
                  //  val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Show an error message
                    val errorMessage = if (!isValidEmail(email)) {
                        "Email is invalid"
                    } else if (!validatePassword(password)) {
                        "Password is invalid"
                    } else {
                        "Email or password is incorrect"
                    }
                    AlertDialog.Builder(this)
                        .setTitle("Login Error")
                        .setMessage(errorMessage)
                        .setPositiveButton("OK", null)
                        .show()

                    // Re-enable the button
                    button.isEnabled = true
                }
            }, 1000)
        }
    }
}

