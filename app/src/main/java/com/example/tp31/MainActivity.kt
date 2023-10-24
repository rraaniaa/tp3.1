package com.example.tp31

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var editTextNom: EditText
    private lateinit var editTextDateNaissance: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var spinnerClasse: Spinner
    private lateinit var btnNext: Button

    private val classList = arrayOf("DSI2", "DSI3", "RSI2", "RSI3", "SEM2", "SEM3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNom = findViewById(R.id.editTextNom)
        editTextDateNaissance = findViewById(R.id.editTextDateNaissance)
        editTextEmail = findViewById(R.id.editTextEmail)
        spinnerClasse = findViewById(R.id.spinnerClasse)
        btnNext = findViewById(R.id.btnNext)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerClasse.adapter = adapter

        // Set a click listener to open the DatePickerDialog
        editTextDateNaissance.setOnClickListener {
            showDatePickerDialog()
        }

        btnNext.setOnClickListener {
            val nom = editTextNom.text.toString().trim()
            val dateNaissance = editTextDateNaissance.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val classe = spinnerClasse.selectedItem.toString()

            if (nom.isNotEmpty() && dateNaissance.isNotEmpty() && email.isNotEmpty() && classe.isNotEmpty()) {
                showConfirmationDialog(nom, email, classe, dateNaissance)
            } else {
                showErrorMessage()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            // Set the chosen date in the EditText
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            editTextDateNaissance.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showConfirmationDialog(nom: String, email: String, classe: String, dateNaissance: String) {
        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Nom: $nom\nDate de Naissance: $dateNaissance\nEmail: $email\nClasse: $classe")
            .setPositiveButton("OK") { _, _ ->
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("nom", nom)
                intent.putExtra("email", email)
                intent.putExtra("classe", classe)
                intent.putExtra("dateNaissance", dateNaissance)
                startActivity(intent)
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun showErrorMessage() {
        val errorMessage = "Veuillez remplir tous les champs."
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        Snackbar.make(btnNext, errorMessage, Snackbar.LENGTH_SHORT).show()
    }
}
