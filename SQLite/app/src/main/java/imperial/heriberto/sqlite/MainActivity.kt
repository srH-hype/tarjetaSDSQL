package imperial.heriberto.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import imperial.heriberto.sqlite.databinding.ActivityMainBinding
import android.database.sqlite.SQLiteDatabase



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var amigosDBHelper: miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        amigosDBHelper = miSQLiteHelper(this)
        binding.btnGuardar.setOnClickListener {
            if (binding.etNombre.text.isNotBlank() && binding.etEmail.text.isNotBlank()) {
                amigosDBHelper.anadirDato(binding.etNombre.text.toString(), binding.etEmail.text.toString())
                binding.etNombre.text.clear()
                binding.etEmail.text.clear()
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se ha podido guardar", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnConcultar.setOnClickListener {
            binding.tvContenido.text = ""
            val db: SQLiteDatabase = amigosDBHelper.readableDatabase
            val cursor= db.rawQuery(
                "SELECT * FROM amigos",
                null
            )
            if (cursor.moveToFirst()){
                do {
                    binding.tvContenido.append(cursor.getInt(0).toString() + ": ")
                    binding.tvContenido.append(cursor.getString(1).toString() + ", ")
                    binding.tvContenido.append(cursor.getString(2).toString() + "\n")
                } while (cursor.moveToNext())
            }
        }
    }
}