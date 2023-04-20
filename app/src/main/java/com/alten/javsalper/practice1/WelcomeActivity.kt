package com.alten.javsalper.practice1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var buttonLogOut: Button



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        nameTextView=findViewById(R.id.welcomeActivityLabelTitle)
        emailTextView=findViewById(R.id.mainActivityInputEmail)
        profileImageView = findViewById(R.id.imgWelcomeActivityUserView)
        buttonLogOut=findViewById(R.id.welcomeActivityBtnLogOut)

        }


       /* sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val rememberMe = sharedPreferences.getBoolean("rememberMe", false)

        if (rememberMe) {
            val name = sharedPreferences.getString("name", "")
            val email = sharedPreferences.getString("email", "")
            val profileImageUri = sharedPreferences.getString("profileImageUri", "")

            nameTextView.text = name
            emailTextView.text = email
            Glide.with(this).load(profileImageUri).into(profileImageView)
        } else {
            nameTextView.text = ""
            emailTextView.text = ""
            profileImageView.setImageDrawable(null)
        }

        val logOutButton = findViewById<Button>(R.id.logout_button)
        logOutButton.setOnClickListener {
            showLogOutConfirmationDialog()
        }
    }
*/
    private fun showLogOutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("¿Estás seguro de que quieres cerrar sesión?")
        alertDialogBuilder.setPositiveButton("Sí") { _, _ ->

            val intent = Intent(this, LogInActivity::class.java)
            val username= intent.getStringExtra("listUsur1")
            val userActually=intent.getStringExtra("userActually")
            val email=intent.getStringExtra("email")
            val password=intent.getStringExtra("password")

            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }
}
