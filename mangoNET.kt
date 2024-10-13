import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.core.methods.response.EthBlockNumber
import org.web3j.protocol.core.DefaultBlockParameterName
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

// Função para verificar o número do bloco atual para uma dada instância Web3
fun checkBlockNumber(coreID: Int, rpcURL: String) {
    val web3 = Web3j.build(HttpService(rpcURL))

    // Busca o número do bloco atual
    val future: CompletableFuture<EthBlockNumber> = web3.ethBlockNumber().sendAsync()

    future.thenAccept { result ->
        println("Core $coreID - Número do bloco atual: ${result.blockNumber}")
    }.exceptionally { error ->
        println("Core $coreID - Erro ao buscar o número do bloco: ${error.message}")
        null
    }
}

// Função para iniciar todos os núcleos (3 núcleos HoloSea para Mainnet)
fun startHoloSeaCores() {
    val rpcURLs = listOf(
        "https://mainnet.infura.io/v3/YOUR_INFURA_PROJECT_ID", // Core 1
        "https://mainnet.infura.io/v3/YOUR_INFURA_PROJECT_ID", // Core 2
        "https://mainnet.infura.io/v3/YOUR_INFURA_PROJECT_ID"  // Core 3
    )

    val futures = rpcURLs.mapIndexed { index, rpcURL ->
        CompletableFuture.runAsync {
            checkBlockNumber(coreID = index + 1, rpcURL = rpcURL)
        }
    }

    // Aguardando a conclusão de todos os núcleos
    CompletableFuture.allOf(*futures.toTypedArray()).join()
    println("Todos os núcleos terminaram de verificar os números dos blocos.")
}

// Inicia os núcleos HoloSea para Mainnet
fun main() {
    startHoloSeaCores()
}
