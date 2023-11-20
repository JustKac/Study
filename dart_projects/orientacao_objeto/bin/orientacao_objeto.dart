import 'package:orientacao_objeto/funcoes.dart' as funcoes;

void main(List<String> arguments) {

  Fruta laranja = Fruta("Laranja", 100.2, "Verde e Amarela",
      "Doce e Cítrica", 25);

  Fruta fruta2 = Fruta("Uva", 40.5, "Roxa",
      "Doce e Azeda", 50);

  laranja.estaMadura(30);
  fruta2.estaMadura(25);

  Legume macaxeira = Legume('Macaxeira', 1200, 'Marrom', true);
  Nozes macadamia = Nozes('Macadâmia', 2, 'Branco Amarelado', 'Doce', 20, 35);
  Citricas limao = Citricas('Limão', 50.0, 'Verde', 'Azedo', 5, 9);


  laranja.printAlimento();
  macaxeira.printAlimento();
  macadamia.printAlimento();
  limao.printAlimento();

  laranja.fazerSuco();
  macaxeira.cozinhar();
  limao.existeRefri(true);

  limao.fazerMassa();
  macadamia.fazerMassa();

  funcoes.mostraMadura(laranja.diasDesdeColheita, nome: laranja.nome);
  // O argumento 'Cor' é opicional
  // Parâmetros opcionais não tem ordenacao específica
  funcoes.mostraMadura(fruta2.diasDesdeColheita, nome: fruta2.nome, cor: fruta2.cor);
}

class Alimento{
  String nome;
  double peso;
  String cor;

  Alimento(this.nome, this.peso, this.cor);

  void printAlimento(){
    print('Este(a) $nome pesa $peso gamas e é $cor.');
  }
}

class Legume extends Alimento implements Bolo{
  bool isPrecisaCozinhar;

  Legume(super.nome, super.peso, super.cor, this.isPrecisaCozinhar);

  void cozinhar(){
    print(isPrecisaCozinhar ? 'O $nome está cozinhando.' : 'Não precisa cozinhar.');
  }

  @override
  void assar() {
    print('Colocando no forno...');
  }

  @override
  void fazerMassa() {
    print('Preparando a massa...');
  }

  @override
  void separarIngredientes() {
    print('Separando ingredientes...');
  }
}

class Fruta extends Alimento implements Bolo{
  String sabor;
  int diasDesdeColheita;
  bool? isMadura;

  Fruta(super.nome, super.peso, super.cor, this.sabor,
      this.diasDesdeColheita, {this.isMadura});

  void estaMadura(int diasParaMadura) {
    isMadura = diasDesdeColheita >= diasParaMadura;
  }

  void fazerSuco(){
    print('Preparando o suco de $nome.');
  }

  @override
  void assar() {
    print('Colocando no forno...');
  }

  @override
  void fazerMassa() {
    print('Preparando a massa...');
  }

  @override
  void separarIngredientes() {
    print('Separando ingredientes...');
  }
}

class Citricas extends Fruta{
  double nivelAzedo;

  Citricas(super.nome, super.peso, super.cor, super.sabor,
      super.diasDesdeColheita, this.nivelAzedo, {super.isMadura});

  void existeRefri(bool existeRefri){
    print(existeRefri ? 'Existe Refri de $nome.' : 'Não Existe Refri de $nome.');
  }
}

class Nozes extends Fruta{
  double porcentagemOleoNatural;

  Nozes(super.nome, super.peso, super.cor, super.sabor,
      super.diasDesdeColheita, this.porcentagemOleoNatural, {super.isMadura});

  @override
  void fazerMassa() {
    print('Preparando a massa para nozes...');
  }
}

// Classe abstrata Bolo
abstract class Bolo{

  void separarIngredientes();
  void fazerMassa();
  void assar();

}