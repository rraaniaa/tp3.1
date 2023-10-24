package com.example.tp31

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var btnShare: Button
    private lateinit var textViewNom: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewClasse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        btnShare = findViewById(R.id.btnShare)
        textViewNom = findViewById(R.id.textViewNom)
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewClasse = findViewById(R.id.textViewClasse)

        val intent = intent
        val nom = intent.getStringExtra("nom")
        val email = intent.getStringExtra("email")
        val classe = intent.getStringExtra("classe")

        // Update the TextViews to display the confirmation data
        textViewNom.text = "Nom: $nom"
        textViewEmail.text = "Email: $email"
        textViewClasse.text = "Classe: $classe"

        btnShare.setOnClickListener {
            shareData(nom, email, classe)
        }
    }

    private fun shareData(nom: String?, email: String?, classe: String?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"

        val dataToShare = "Nom: $nom\nEmail: $email\nClasse: $classe"
        shareIntent.putExtra(Intent.EXTRA_TEXT, dataToShare)

        startActivity(Intent.createChooser(shareIntent, "Partager via"))
    }
}
