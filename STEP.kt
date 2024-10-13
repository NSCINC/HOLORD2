import Foundation
import PostgresClientKit

struct Planilha {
    var id: Int
    var nome: String
    var dataCriacao: Date
    var dados: String
}

class DatabaseController {
    var connection: Connection?

    init() {
        let connectionString = "host=seu_host_do_banco user=seu_usuario password=sua_senha dbname=seu_banco"
        
        do {
            self.connection = try Connection(connectionString)
        } catch {
            print("Erro ao conectar ao banco de dados: \(error)")
        }
    }

    func fetchAllPlanilhas() {
        guard let connection = connection else { return }

        do {
            let result = try connection.execute("SELECT * FROM planilhas ORDER BY data_criacao DESC;")
            for row in result {
                let id = try row.get(0) as Int
                let nome = try row.get(1) as String
                let dataCriacao = try row.get(2) as Date
                let dados = try row.get(3) as String
                print("ID: \(id), Nome: \(nome), Data de Criação: \(dataCriacao), Dados: \(dados)")
            }
        } catch {
            print("Erro ao buscar planilhas: \(error)")
        }
    }

    func fetchPlanilhaByName(nome: String) {
        guard let connection = connection else { return }

        do {
            let result = try connection.execute("SELECT * FROM planilhas WHERE nome = $1;", [nome])
            for row in result {
                let id = try row.get(0) as Int
                let nome = try row.get(1) as String
                let dataCriacao = try row.get(2) as Date
                let dados = try row.get(3) as String
                print("ID: \(id), Nome: \(nome), Data de Criação: \(dataCriacao), Dados: \(dados)")
            }
        } catch {
            print("Erro ao buscar planilha pelo nome: \(error)")
        }
    }

    func fetchQuantidadeProdutoA() {
        guard let connection = connection else { return }

        do {
            let result = try connection.execute("SELECT dados->'vendas'->0->>'quantidade' AS quantidade_produto_a FROM planilhas WHERE nome = 'Vendas Q1';")
            for row in result {
                let quantidadeProdutoA = try row.get(0) as String
                print("Quantidade do Produto A: \(quantidadeProdutoA)")
            }
        } catch {
            print("Erro ao buscar quantidade do Produto A: \(error)")
        }
    }

    func fetchAllPlanilhasOrderedByName() {
        guard let connection = connection else { return }

        do {
            let result = try connection.execute("SELECT * FROM planilhas ORDER BY nome ASC;")
            for row in result {
                let id = try row.get(0) as Int
                let nome = try row.get(1) as String
                let dataCriacao = try row.get(2) as Date
                let dados = try row.get(3) as String
                print("ID: \(id), Nome: \(nome), Data de Criação: \(dataCriacao), Dados: \(dados)")
            }
        } catch {
            print("Erro ao buscar planilhas ordenadas pelo nome: \(error)")
        }
    }
}

// Uso do DatabaseController
let dbController = DatabaseController()
dbController.fetchAllPlanilhas()
dbController.fetchPlanilhaByName(nome: "Vendas Q1")
dbController.fetchQuantidadeProdutoA()
dbController.fetchAllPlanilhasOrderedByName()
