import kotlin.math.exp
import kotlin.random.Random

// Constantes
const val NUM_INPUT = 10         // Número de entradas
const val NUM_HIDDEN = 20        // Número de neurônios na camada oculta
const val NUM_OUTPUT = 1         // Número de saídas
const val MAX_EPOCHS = 1000      // Máximo de épocas de treinamento
const val ERROR_THRESHOLD = 0.01  // Limite de erro para treinamento

data class NeuralNetwork(
    var weightsInputHidden: Array<DoubleArray> = Array(NUM_INPUT) { DoubleArray(NUM_HIDDEN) },
    var weightsHiddenOutput: Array<DoubleArray> = Array(NUM_HIDDEN) { DoubleArray(NUM_OUTPUT) },
    var hiddenLayer: DoubleArray = DoubleArray(NUM_HIDDEN),
    var outputLayer: DoubleArray = DoubleArray(NUM_OUTPUT),
    var learningRate: Double = 0.01
)

// Função de ativação Sigmoid
fun sigmoid(x: Double): Double {
    return 1 / (1 + exp(-x))
}

// Derivada da função Sigmoid
fun sigmoidDerivative(x: Double): Double {
    return x * (1 - x)
}

// Inicializa a rede neural com pesos aleatórios
fun initializeNetwork(nn: NeuralNetwork) {
    for (i in 0 until NUM_INPUT) {
        for (j in 0 until NUM_HIDDEN) {
            nn.weightsInputHidden[i][j] = Random.nextDouble(-1.0, 1.0) // Peso aleatório entre -1 e 1
        }
    }
    for (j in 0 until NUM_HIDDEN) {
        for (k in 0 until NUM_OUTPUT) {
            nn.weightsHiddenOutput[j][k] = Random.nextDouble(-1.0, 1.0) // Peso aleatório entre -1 e 1
        }
    }
}

// Função de treinamento da rede neural
fun train(nn: NeuralNetwork, input: Array<DoubleArray>, output: Array<DoubleArray>, numSamples: Int) {
    for (epoch in 0 until MAX_EPOCHS) {
        var totalError = 0.0
        for (s in 0 until numSamples) {
            // Feedforward
            for (j in 0 until NUM_HIDDEN) {
                var activation = 0.0
                for (i in 0 until NUM_INPUT) {
                    activation += input[s][i] * nn.weightsInputHidden[i][j]
                }
                nn.hiddenLayer[j] = sigmoid(activation)
            }

            for (k in 0 until NUM_OUTPUT) {
                var activation = 0.0
                for (j in 0 until NUM_HIDDEN) {
                    activation += nn.hiddenLayer[j] * nn.weightsHiddenOutput[j][k]
                }
                nn.outputLayer[k] = sigmoid(activation)
            }

            // Cálculo do erro
            val error = output[s][0] - nn.outputLayer[0]
            totalError += error * error

            // Backpropagation
            val outputDelta = error * sigmoidDerivative(nn.outputLayer[0])
            for (j in 0 until NUM_HIDDEN) {
                nn.weightsHiddenOutput[j][0] += nn.learningRate * outputDelta * nn.hiddenLayer[j]
            }

            for (j in 0 until NUM_HIDDEN) {
                val hiddenDelta = outputDelta * nn.weightsHiddenOutput[j][0] * sigmoidDerivative(nn.hiddenLayer[j])
                for (i in 0 until NUM_INPUT) {
                    nn.weightsInputHidden[i][j] += nn.learningRate * hiddenDelta * input[s][i]
                }
            }
        }

        totalError /= numSamples
        if (totalError < ERROR_THRESHOLD) {
            println("Treinamento interrompido precocemente na época $epoch")
            break
        }
    }
}

// Função para prever com a rede neural treinada
fun predict(nn: NeuralNetwork, input: DoubleArray) {
    for (j in 0 until NUM_HIDDEN) {
        var activation = 0.0
        for (i in 0 until NUM_INPUT) {
            activation += input[i] * nn.weightsInputHidden[i][j]
        }
        nn.hiddenLayer[j] = sigmoid(activation)
    }

    for (k in 0 until NUM_OUTPUT) {
        var activation = 0.0
        for (j in 0 until NUM_HIDDEN) {
            activation += nn.hiddenLayer[j] * nn.weightsHiddenOutput[j][k]
        }
        nn.outputLayer[k] = sigmoid(activation)
    }

    println("Previsão: ${nn.outputLayer[0]}")
}

// Função principal
fun main() {
    val nn = NeuralNetwork()
    initializeNetwork(nn)

    // Dados de treinamento fictícios
    val input = arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    )
    val output = arrayOf(
        doubleArrayOf(1.0),
        doubleArrayOf(1.0)
    )

    // Treinar a rede neural
    train(nn, input, output, numSamples = 2)

    // Realizar previsões
    val newInput = doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    predict(nn, newInput)
}

// Chamada da função principal
main()

