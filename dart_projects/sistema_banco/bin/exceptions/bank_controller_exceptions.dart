class SenderIdInvalidException implements Exception {
    static const String report = "SenderIdInvalidException";
    String idSender;
    SenderIdInvalidException({required this.idSender});

    @override
    String toString(){
        return "$report\nID Sender: $idSender\n";
    }
}

class RecieverIdInvalidException implements Exception {
    static const String report = "RecieverIdInvalidException";
    String idReciever;
    RecieverIdInvalidException({required this.idReciever});

    @override
    String toString(){
        return "$report\nID Reciever: $idReciever\n";
    }
}

class SenderNotAuthenticatedException implements Exception {
    static const String report = "SenderNotAuthenticatedException";
    String idSender;
    SenderNotAuthenticatedException({required this.idSender});

    @override
    String toString(){
        return "$report\nID Sender: $idSender\n";
    }
}

class SenderBalanceLowerThanAmountException implements Exception {
    static const String report = "SenderBalanceLowerThanAmountException";
    String idSender;
    double senderBalance;
    double amount;
    SenderBalanceLowerThanAmountException({required this.idSender, required this.senderBalance, required this.amount});

    @override
    String toString(){
        return "$report\nID Sender: $idSender\nSender Balance: $senderBalance\nAmount: $amount\n";
    }
}
