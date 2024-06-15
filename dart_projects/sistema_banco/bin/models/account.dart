class Account {
	String name;
	double balance;
	bool isAuthenticated;

	Account(
		{required this.name,
		required this.balance,
		required this.isAuthenticated}):
    assert(name.isNotEmpty, "O nome deve ser informado"),
    assert(balance >= 0, "O saldo deve ser positivo");

	editBalance({required value}) {
		balance = balance + value;
	}
}
