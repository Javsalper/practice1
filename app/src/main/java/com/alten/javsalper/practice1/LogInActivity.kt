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
import android.widget.ProgressBar
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class LogInActivity : AppCompatActivity() {

    lateinit var users: MutableList<User>

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeUsers()
        val switch1 = findViewById<Switch>(R.id.loginActivitySwitchRemember)
        val button = findViewById<Button>(R.id.loginActivityBtnRemember)
        switch1.setOnCheckedChangeListener { _, isChecked ->
            button.isEnabled = isChecked
        }

        val emailInput: TextInputLayout = findViewById(R.id.loginActivityInputEmail)
        val passwordInput: TextInputLayout = findViewById(R.id.loginActivityInputPassword)
        val progressBar: ProgressBar = findViewById(R.id.mainActivityProgressIndicator1Secound)

        emailInput.requestFocus()
        emailInput.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                passwordInput.requestFocus()
                true
            } else {
                false
            }
        }
        emailInput.isFocusableInTouchMode = true
        emailInput.nextFocusDownId = passwordInput.id


        emailInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                val isEmailValid = isValidEmail(email)
                val isPasswordValid = isValidPassword(passwordInput.editText?.text.toString())
                button.isEnabled = isEmailValid && isPasswordValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        passwordInput.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                button.performClick()
                true
            } else {
                false
            }
        }

        passwordInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                val isPasswordValid = isValidPassword(password)
                val isEmailValid = isValidEmail(emailInput.editText?.text.toString())
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
                val email = emailInput.editText?.text.toString()
                val password = passwordInput.editText?.text.toString()
                if (isValidEmail(email) && isValidPassword(password)) {
                    // Go to the welcome screen
                    val intent = Intent(
                        this,
                        WelcomeActivity::class.java
                    ) //pasar los argumentos por parametro
                    startActivity(intent)
                    finish()
                } else {
                    // Show an error message
                    val errorMessage = if (!isValidEmail(email)) {
                        "Email is invalid"
                    } else if (!isValidPassword(password)) {
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
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,8}$")
        return regex.matches(password)
    }

    fun initializeUsers() {
        users = mutableListOf()
        val user1 = User(
            "juanjo9@gmail,com",
            "Star90",
            "Juanjo",
            "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/b826e129797915.560414c48512e.jpg"
        )
        val user2 = User(
            "elena65@gmail.com",
            "Elen65",
            "Helena",
            "https://static.wikia.nocookie.net/esstarwars/images/9/9b/Princessleiaheadwithgun.jpg/revision/latest?cb=20150117214124"
        )
        val user3 = User(
            "Paco12@gmail.com",
            "Paco12",
            "Paco",
            "https://us.123rf.com/450wm/jemastock/jemastock1608/jemastock160802715/60756136-hombre-dise%C3%B1o-plano-usando-el-tel%C3%A9fono-celular-de-la-ilustraci%C3%B3n-del-vector-del-icono.jpg"
        )
        val user4 = User(
            "Jessica",
            "Jess92",
            "Jessica",
            "https://www.facebook.com/photo/?fbid=2101305979880267&set=ecnf.100000026550482"
        )
        users.add(user1)
        users.add(user2)
        users.add(user3)
        users.add(user4)
    }

    fun verifyCredential(
        email: String,
        password: String,
        listUsur: MutableList<User>
    ): Boolean {
        val usuario = listUsur.find { it.email == email && it.password == password }
        if (usuario != null) {
            val userActually =
                listUsur.find { it.email == email && it.password == password }

            val intent = Intent(this, LogInActivity::class.java)
            intent.putExtra("listUsur1", ArrayList(listUsur))
            intent.putExtra("userActually", userActually)
            intent.putExtra("safe", password)

            if (userActually != null) {
                intent.putExtra("urlPhoto", userActually.photo)
            }
            startActivity(intent)
            return true
        }
        return false
    }

}

