import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var inputText: EditText
    private lateinit var saveButton: Button
    private lateinit var displayText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando as views
        inputText = findViewById(R.id.inputText)
        saveButton = findViewById(R.id.saveButton)
        displayText = findViewById(R.id.displayText)

        // Inicializando SharedPreferences
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)

        // Carregando dados existentes
        loadSavedData()

        // Configurando o botão para salvar dados
        saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val editor = sharedPreferences.edit()
        val textToSave = inputText.text.toString()
        editor.putString("savedText", textToSave)
        editor.apply() // Salva os dados de forma assíncrona
        displayText.text = "Dados salvos: $textToSave"
        inputText.text.clear() // Limpa o campo de entrada
    }

    private fun loadSavedData() {
        val savedText = sharedPreferences.getString("savedText", "")
        displayText.text = if (!savedText.isNullOrEmpty()) {
            "Dados salvos: $savedText"
        } else {
            "Nenhum dado salvo."
        }
    }
}
