import Foundation

// Capacidade inicial
let initialCapacity = 10

// Estrutura para o plano de investimento
struct InvestmentPlan {
    var planName: String
    var initialInvestment: Int
    var monthlyReturn: Int
    var annualReturn: Int
    var netAnnualReturn: Int
    var slots: Int
}

// Gerenciador de planos de investimento
class InvestmentPlanManager {
    private var plans: [InvestmentPlan]
    private var count: Int
    private var capacity: Int

    init() {
        self.count = 0
        self.capacity = initialCapacity
        self.plans = []
    }

    // Função para adicionar um plano de investimento
    func addInvestmentPlan(planName: String, initialInvestment: Int, monthlyReturn: Int, annualReturn: Int, netAnnualReturn: Int, slots: Int) {
        // Verifica se é necessário aumentar a capacidade
        if count >= capacity {
            capacity *= 2 // Dobra a capacidade
            plans.reserveCapacity(capacity) // Reserva a nova capacidade sem precisar preencher novos valores
        }

        // Adiciona o novo plano
        let newPlan = InvestmentPlan(planName: planName, initialInvestment: initialInvestment, monthlyReturn: monthlyReturn, annualReturn: annualReturn, netAnnualReturn: netAnnualReturn, slots: slots)
        plans.append(newPlan) // Usa append para adicionar o novo plano
        count += 1 // Incrementa a contagem de planos
    }

    // Função para exibir os planos de investimento
    func displayInvestmentPlans() {
        for plan in plans {
            print("Plano: \(plan.planName)")
            print("Investimento Inicial: \(plan.initialInvestment)")
            print("Retorno Mensal: \(plan.monthlyReturn)")
            print("Retorno Anual: \(plan.annualReturn)")
            print("Retorno Anual Líquido: \(plan.netAnnualReturn)")
            print("Slots: \(plan.slots)")
            print("--------------") // Separador para melhor legibilidade
        }
    }
}

// Função principal
func main() {
    let manager = InvestmentPlanManager()

    // Adicionando alguns planos de investimento como exemplo
    manager.addInvestmentPlan(planName: "Plano A", initialInvestment: 1000, monthlyReturn: 100, annualReturn: 1200, netAnnualReturn: 1000, slots: 5)
    manager.addInvestmentPlan(planName: "Plano B", initialInvestment: 2000, monthlyReturn: 200, annualReturn: 2400, netAnnualReturn: 2000, slots: 10)

    // Exibir os planos de investimento
    manager.displayInvestmentPlans()
}

// Chamada da função principal
main()
// Capacidade inicial
const val INITIAL_CAPACITY = 10

// Classe para o plano de investimento
data class InvestmentPlan(
    val planName: String,
    val initialInvestment: Int,
    val monthlyReturn: Int,
    val annualReturn: Int,
    val netAnnualReturn: Int,
    val slots: Int
)

// Gerenciador de planos de investimento
class InvestmentPlanManager {
    private var plans: MutableList<InvestmentPlan>
    private var count: Int
    private var capacity: Int

    init {
        count = 0
        capacity = INITIAL_CAPACITY
        plans = mutableListOf()
    }

    // Função para adicionar um plano de investimento
    fun addInvestmentPlan(planName: String, initialInvestment: Int, monthlyReturn: Int, annualReturn: Int, netAnnualReturn: Int, slots: Int) {
        // Verifica se é necessário aumentar a capacidade
        if (count >= capacity) {
            capacity *= 2 // Dobra a capacidade
            // Reserva a nova capacidade, se necessário
        }

        // Adiciona o novo plano
        val newPlan = InvestmentPlan(planName, initialInvestment, monthlyReturn, annualReturn, netAnnualReturn, slots)
        plans.add(newPlan) // Usa add para adicionar o novo plano
        count++ // Incrementa a contagem de planos
    }

    // Função para exibir os planos de investimento
    fun displayInvestmentPlans() {
        for (plan in plans) {
            println("Plano: ${plan.planName}")
            println("Investimento Inicial: ${plan.initialInvestment}")
            println("Retorno Mensal: ${plan.monthlyReturn}")
            println("Retorno Anual: ${plan.annualReturn}")
            println("Retorno Anual Líquido: ${plan.netAnnualReturn}")
            println("Slots: ${plan.slots}")
            println("--------------") // Separador para melhor legibilidade
        }
    }
}

// Função principal
fun main() {
    val manager = InvestmentPlanManager()

    // Adicionando alguns planos de investimento como exemplo
    manager.addInvestmentPlan("Plano A", 1000, 100, 1200, 1000, 5)
    manager.addInvestmentPlan("Plano B", 2000, 200, 2400, 2000, 10)

    // Exibir os planos de investimento
    manager.displayInvestmentPlans()
}

// Chamada da função principal
main()
