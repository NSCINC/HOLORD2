import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

// Estrutura para armazenar dados de resposta do chat
data class ChatResponse(val id: Int, val question: String, val answer: String)

// Classe para gerenciar operações com SQLite
class DatabaseManager {
    private var connection: Connection? = null

    init {
        connectDatabase()
        initializeDatabase()
    }

    // Função para conectar ao banco de dados SQLite
    private fun connectDatabase() {
        val url = "jdbc:sqlite:ApolloHoloFi.sqlite"
        try {
            connection = DriverManager.getConnection(url)
            println("Banco de dados aberto com sucesso.")
        } catch (e: SQLException) {
            println("Erro ao abrir o banco de dados: ${e.message}")
        }
    }

    // Função para inicializar o banco de dados e criar a tabela Responses
    private fun initializeDatabase() {
        val createTableString = """
            CREATE TABLE IF NOT EXISTS Responses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question TEXT NOT NULL,
                answer TEXT NOT NULL
            );
        """.trimIndent()

        try {
            connection?.createStatement()?.execute(createTableString)
            println("Tabela Responses criada ou já existe.")
        } catch (e: SQLException) {
            println("Erro ao criar a tabela: ${e.message}")
        }
    }

    // Função para inserir uma resposta de chat no banco de dados
    fun insertResponse(response: ChatResponse) {
        val insertStatementString = "INSERT INTO Responses (question, answer) VALUES (?, ?);"

        try {
            val preparedStatement: PreparedStatement? = connection?.prepareStatement(insertStatementString)
            preparedStatement?.setString(1, response.question)
            preparedStatement?.setString(2, response.answer)

            if (preparedStatement?.executeUpdate() == 1) {
                println("Resposta inserida com sucesso.")
            } else {
                println("Erro ao inserir resposta.")
            }
        } catch (e: SQLException) {
            println("Erro ao preparar a declaração: ${e.message}")
        }
    }

    // Função para ler uma resposta com base na pergunta
    fun getResponse(question: String): String? {
        val queryStatementString = "SELECT answer FROM Responses WHERE question = ?;"
        var answer: String? = null

        try {
            val preparedStatement: PreparedStatement? = connection?.prepareStatement(queryStatementString)
            preparedStatement?.setString(1, question)

            val resultSet: ResultSet? = preparedStatement?.executeQuery()
            if (resultSet?.next() == true) {
                answer = resultSet.getString("answer")
            } else {
                println("Nenhuma resposta encontrada para a pergunta.")
            }
        } catch (e: SQLException) {
            println("Erro ao preparar a declaração: ${e.message}")
        }

        return answer
    }

    // Fechar o banco de dados
    fun closeDatabase() {
        try {
            connection?.close()
            println("Banco de dados fechado.")
        } catch (e: SQLException) {
            println("Erro ao fechar o banco de dados: ${e.message}")
        }
    }
}

// Função principal para executar a aplicação
fun main() {
    val dbManager = DatabaseManager()

    // Inserir respostas de exemplo
    dbManager.insertResponse(ChatResponse(1, "What is HoloFi?", "HoloFi is a blockchain-based platform."))
    dbManager.insertResponse(ChatResponse(2, "How does it work?", "It uses smart contracts for transactions."))

    // Exemplo de consulta a uma resposta
    val response = dbManager.getResponse("What is HoloFi?")
    if (response != null) {
        println("Chatbot: $response")
    } else {
        println("Chatbot: I don't know the answer to that.")
    }

    // Fechar o banco de dados
    dbManager.closeDatabase()
}

