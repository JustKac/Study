import 'package:introducao/introducao.dart' as introducao;

void main(List<String> arguments) {
  print('Hello world!');

  /// - - - - - - - - - - Trabalhando com números inteiros - - - - - - - - - -

  int idade = 29;
  int idadeHex = 0x00001D;

  /// O Dart também permite trabalhar em Hexadecimal

  print('Minha idade é $idade anos.');
  print('Minha idade é $idadeHex anos.');

  /// - - - - - - - - - - Trabalhando com números decimais - - - - - - - - - -

  double altura = 1.77;

  print('Minha altura é $altura metros.');

  /// - - - - - - - - - - Trabalhando com exponencial - - - - - - - - - -

  double anosTerrestres = 4543e9;

  /// Exponenciais precisam ser 'double'

  print('A terra tem cerca de $anosTerrestres anos.');

  /// - - - - - - - - - - Trabalhando com booleano - - - - - - - - - -

  bool gamer = true;

  print('Você gosta de jogos? ${gamer ? 'Sim!' : 'Não!'}');

  print('Você tem 30 anos? ${30 == idade ? 'Sim!' : 'Não!'}');

  /// - - - - - - - - - - Trabalhando com textos - - - - - - - - - -

  String nome = 'Caio';
  String frase = 'Olá, eu me chamo $nome '
      'e tenho $idade anos de idade.\n' // Para quebrar linha, utilizar o '\n'
      'Você sabia que a terra tem cerca de $anosTerrestres anos de existencia?';

  print(frase);

  /// - - - - - - - - - - Trabalhando com Listas de tipo único - - - - - - - - - -

  List<String> listaDeNomes = ['Carol', 'Brayan', 'Lanna', 'Paty', 'Dema'];

  print(listaDeNomes);

  listaDeNomes.add(nome);

  print(listaDeNomes);

  listaDeNomes.remove('Dema');

  print(listaDeNomes);

  /// - - - - - - - - - - Trabalhando com Listas dinâmicas - - - - - - - - - -

  List<dynamic> listaDeTudo = [idade, nome, altura, gamer, listaDeNomes];

  print(listaDeTudo);

  /// - - - - - - - - - - Var, Const e Final - - - - - - - - - -

  /// const são imutaveis e o seu valor deve ser atribuido na sua criação
  const String imutavel = 'Não mudarei';

  /// final são imutaveis e o seu valor pode ser atribuido após sua criação
  final String fim;
  fim = 'Esse é o meu valor final!';

  /// var são variaveis mutaveis de tipo variado(pode ser qualquer tipo)
  var variavel1 = true;
  var variavel2 = 12;
  var variavel3 = [];

  print('$imutavel | $fim | $variavel1 | $variavel2 | $variavel3');

  /// - - - - - - - - - - Trabalhando com Condicionais - - - - - - - - - -

  bool maiorDeIdade;
  if (idade >= 18) {
    maiorDeIdade = true;
  } else {
    maiorDeIdade = false;
  }

  /// - - - - - - - - - - Trabalhando com Laços de Repetição - - - - - - - - - -

  for (int i = 0; i < 5; i++){
    print(i);
  }

  for (String nome in listaDeNomes){
    print(nome);
  }

  /// - - - - - - - - - - Trabalhando com While - - - - - - - - - -

  int energia = 10;

  while(energia > 0){
    energia--;
    print('Uma energia foi consumida. Energia restante - $energia');
  }

  energia = 10;
  do {
    energia--;
    print('Uma energia foi consumida. Energia restante - $energia');
  } while (energia > 0);

  /// - - - - - - - - - - Operando com Listas - - - - - - - - - -

  String reduceFunction = listaDeNomes.reduce((value, element) {
    return '$value $element';
  });

  print(reduceFunction);

  List<String> whereFunction = listaDeNomes.where((element) => element.length > 5).toList();

  print(whereFunction);

  String firtsWhereFunction = listaDeNomes.firstWhere((element) => element.length > 5);

  print(firtsWhereFunction);

  /// - - - - - - - - - - Trabalhando com Funcões - - - - - - - - - -

  print('2 x 2 = ${introducao.calculate(2, 2)}');
}
