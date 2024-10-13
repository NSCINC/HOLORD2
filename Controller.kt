import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var inputTextField: UITextField!
    @IBOutlet weak var outputTextView: UITextView!
    @IBOutlet weak var sendButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        outputTextView.isEditable = false
    }

    @IBAction func sendMessage(_ sender: UIButton) {
        guard let message = inputTextField.text, !message.isEmpty else {
            return
        }
        
        sendRequest(message: message)
    }

    func sendRequest(message: String) {
        guard let url = URL(string: "http://localhost:8080/ask") else {
            print("URL inválida")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        let requestBody: [String: Any] = ["message": message]
        do {
            request.httpBody = try JSONSerialization.data(withJSONObject: requestBody, options: [])
        } catch {
            print("Erro ao serializar JSON: \(error)")
            return
        }

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Erro na requisição: \(error)")
                return
            }

            guard let data = data else {
                print("Dados não recebidos")
                return
            }

            do {
                if let jsonResponse = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any],
                   let responseMessage = jsonResponse["response"] as? String {
                    DispatchQueue.main.async {
                        self.outputTextView.text = responseMessage
                    }
                }
            } catch {
                print("Erro ao parsear resposta: \(error)")
            }
        }

        task.resume()
    }
}
import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var inputTextField: UITextField!
    @IBOutlet weak var outputTextView: UITextView!
    @IBOutlet weak var sendButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        outputTextView.isEditable = false
    }

    @IBAction func sendMessage(_ sender: UIButton) {
        guard let message = inputTextField.text, !message.isEmpty else {
            return
        }
        
        sendRequest(message: message)
    }

    func sendRequest(message: String) {
        guard let url = URL(string: "http://localhost:8080/ask") else {
            print("URL inválida")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        let requestBody: [String: Any] = ["message": message]
        do {
            request.httpBody = try JSONSerialization.data(withJSONObject: requestBody, options: [])
        } catch {
            print("Erro ao serializar JSON: \(error)")
            return
        }

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Erro na requisição: \(error)")
                return
            }

            guard let data = data else {
                print("Dados não recebidos")
                return
            }

            do {
                if let jsonResponse = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any],
                   let responseMessage = jsonResponse["response"] as? String {
                    DispatchQueue.main.async {
                        self.outputTextView.text = responseMessage
                    }
                }
            } catch {
                print("Erro ao parsear resposta: \(error)")
            }
        }

        task.resume()
    }
}
