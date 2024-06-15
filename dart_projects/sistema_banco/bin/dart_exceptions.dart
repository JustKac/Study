import 'controllers/bank_controller.dart';
import 'exceptions/bank_controller_exceptions.dart';
import 'models/account.dart';

void main() {
	// Criando o banco
	BankController bankController = BankController();

	// Adicionando contas
	bankController.addAccount(
	id: "Ricarth",
	account:
		Account(
			name: "Ricarth Lima", 
			balance: 400, 
			isAuthenticated: true)
		);

	bankController.addAccount(
	id: "Kako",
	account:
		Account(
			name: "Caio Couto", 
			balance: 600, 
			isAuthenticated: true)
		);

	// Fazendo transferência
	try {
		bool result = bankController.makeTransfer(
		idSender: "Kako", idReceiver: "Ricarth", amount: 200);
		// Observando resultado
		if (result) {
		  print("Transferência realizada com sucesso!");
		}
	} on SenderIdInvalidException catch (e) {
		print(e);
		print("O ID '${e.idSender}' do remetente não é um ID valido.");
	} on RecieverIdInvalidException catch (e) {
		print(e);
		print("O ID '${e.idReciever}' do destinatário não é um ID valido.");
	} on SenderNotAuthenticatedException catch (e) {
		print(e);
		print("O usuario remetente de ID '${e.idSender}' não está autenticado.");
	} on SenderBalanceLowerThanAmountException catch (e) {
		print(e);
		print("O usuario remetente de ID '${e.idSender}' tentou enviar ${e.amount} mas possui apenas ${e.senderBalance}.");
	} on Exception {
		print("Ocorreu um erro inesperado.");
	}
}
