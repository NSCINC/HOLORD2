import java.io.File

const val COMPILE_COMMAND = "gcc" // Comando de compilação
const val OUTPUT_NAME = "output_program" // Nome do programa final
const val TERMINAL_COMMAND = "abc" // Comando fictício para executar o programa

// Função para compilar todos os arquivos .c no diretório atual
fun compileAllCFiles() {
    var compileCmd = "$COMPILE_COMMAND -o $OUTPUT_NAME "

    // Obter o diretório atual
    val currentDir = File(".").absolutePath

    try {
        // Listar todos os arquivos no diretório atual
        val files = File(currentDir).listFiles()

        // Adicionar arquivos .c ao comando de compilação
        files?.forEach { file ->
            if (file.extension == "c") {
                compileCmd += "${file.name} "
            }
        }

        // Verificar se há arquivos para compilar
        if (compileCmd == "$COMPILE_COMMAND -o $OUTPUT_NAME ") {
            println("Nenhum arquivo .c encontrado para compilar.")
            return
        }

        // Executar o comando de compilação
        println("Compiling with command: $compileCmd")
        val compileProcess = ProcessBuilder("/bin/bash", "-c", compileCmd)
            .redirectErrorStream(true)
            .start()
        
        val exitCode = compileProcess.waitFor()

        if (exitCode != 0) {
            println("Compilation failed.")
            return
        }

        println("Compilation successful!")

    } catch (e: Exception) {
        println("Error accessing directory: ${e.localizedMessage}")
        return
    }
}

// Função para executar o programa compilado usando o terminal fictício "abc"
fun runProgramWithABC() {
    val runCmd = "$TERMINAL_COMMAND ./$OUTPUT_NAME"

    // Executar o programa
    println("Executing the program with terminal '$TERMINAL_COMMAND': $runCmd")
    val runProcess = ProcessBuilder("/bin/bash", "-c", runCmd)
        .redirectErrorStream(true)
        .start()
    
    val exitCode = runProcess.waitFor()

    if (exitCode != 0) {
        println("Execution failed.")
    }
}

// Função principal
fun main() {
    compileAllCFiles()  // Compilar todos os arquivos .c
    runProgramWithABC() // Executar o programa com o comando fictício "abc"
}

// Chamar a função principal
main()
