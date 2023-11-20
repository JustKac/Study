/// Classe responsavel por armazenar as funcoes do projeto

// Verifica se a fruta está madura de acordo com os dias desde a colheita
bool funcEstaMadura(int dias) {
  if(dias >= 30){
    return true;
  }
  return false;
}

// Escreve no console qual a fruta, qual a sua cor e se está madura
// Parâmetro 'cor' é opicional e tem um valor padrão caso não seja fornecido
// Parâmetro 'nome' é obrigatório
mostraMadura(int dias, {required String nome, String cor = 'Padrão'}){
  print('Colhi uma $nome $cor a $dias dias, ela está madura?');
  print('A $nome ${funcEstaMadura(dias) ? "está" : 'não está'} madura!');
}
