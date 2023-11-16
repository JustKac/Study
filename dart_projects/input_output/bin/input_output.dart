import 'dart:io';

/// Trecho de código para treinar input e output
void main(List<String> arguments) {
  print('Olá! Qual o seu nome?');
  String? nome = stdin.readLineSync();

  // Cria um texto concatenando a variável com o valor do input com nullSafety
  print('Prazer em conhece-lo, ${nome!}.\n'
      'Qual a sua idade?');

  String? idade = stdin.readLineSync();

  // Cria um texto concatenando a variável com o valor do input com nullSafety
  // Além disso, faz o Cast para int e utiliza um ternário em seu valor,
  // modificando o texto final.
  print('Interessante! Então quer dizer que você '
      '${int.parse(idade!) >= 18 ? 'é' : 'não é'} de maior!');
}
