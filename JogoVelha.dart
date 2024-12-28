
import 'dart:io';

enum Tipo
{
  X,
  O,
  Vazio
}

Tipo vez = Tipo.X;

extension TipoExtention on Tipo{
  String get simbolo
  {
    switch(this)
    {
      case Tipo.X: return "X";
      case Tipo.O: return "O";
      case Tipo.Vazio: return " ";
    }
  }
}

class Posicao{
  int x;
  int y;

  Posicao(this.x, this.y);
}

List<List<Tipo>> tabuleiro = [
  [Tipo.Vazio, Tipo.Vazio, Tipo.Vazio],
  [Tipo.Vazio, Tipo.Vazio, Tipo.Vazio],
  [Tipo.Vazio, Tipo.Vazio, Tipo.Vazio]
];

void desenharTabuleiro(){
  print("  0   1   2");
  print("0 ${tabuleiro[0][0].simbolo} | ${tabuleiro[0][1].simbolo} | ${tabuleiro[0][2].simbolo}");
  print("  ---------");
  print("1 ${tabuleiro[1][0].simbolo} | ${tabuleiro[1][1].simbolo} | ${tabuleiro[1][2].simbolo}");
  print("  ---------");
  print("2 ${tabuleiro[2][0].simbolo} | ${tabuleiro[2][1].simbolo} | ${tabuleiro[2][2].simbolo}");
}

bool isNumeric(String? s) {
  if(s == null) {
    return false;
  }
  return double.tryParse(s) != null;
}

Posicao obterPosicao(){
  stdout.write("Vez do jogador ${vez.simbolo}\n");
  stdout.write("Digite a posição (x, y): ");
  String valor = stdin.readLineSync() ?? "";

  if(valor.length != 3 || !isNumeric(valor[0]) || !isNumeric(valor[2])){
    print("Posição inválida");
    return obterPosicao();
  }

  int x = int.parse(valor[0]);
  int y = int.parse(valor[2]);

  return Posicao(x, y);
}

bool atualizarTabuleiro(Posicao pos){

  if(pos.x < 0 || pos.x > 2 || pos.y < 0 || pos.y > 2){
    print("Posição inválida");
    return false;
  }

  if(tabuleiro[pos.x][pos.y] != Tipo.Vazio){
    print("Posição ocupada");
    return false;
  }

  tabuleiro[pos.x][pos.y] = vez;
  return true;
}

bool verificarVitoria(){
  // verificar linhas e colunas 
  for(int i = 0; i < 3; i++){
    if(tabuleiro[i][0] == vez && tabuleiro[i][1] == vez && tabuleiro[i][2] != Tipo.Vazio){
      return true;
    }

    if(tabuleiro[0][i] == vez && tabuleiro[1][i] == vez && tabuleiro[2][i] != Tipo.Vazio){
      return true;
    }
  }

  // verificar diagonais
  if(tabuleiro[0][0] == vez && tabuleiro[1][1] == vez && tabuleiro[2][2] != Tipo.Vazio){
    return true;
  }

  if(tabuleiro[0][2] == vez && tabuleiro[1][1] == vez && tabuleiro[2][0] != Tipo.Vazio){
    return true;
  }

  return false;
}

bool verificarEmpate(){
  for(int i = 0; i < 3; i++){
    for(int j = 0; j < 3; j++){
      if(tabuleiro[i][j] == Tipo.Vazio){
        return false;
      }
    }
  }

  return true;
}

void limparTerminal(){
  print("\x1B[2J\x1B[0;0H");
}

void main(){
  while(true){
    limparTerminal();
    desenharTabuleiro();

    Posicao pos = obterPosicao();
    if(atualizarTabuleiro(pos)){
      if(verificarVitoria()){
        limparTerminal();
        desenharTabuleiro();
        print("Vitória do jogador ${vez.simbolo}");
        break;
      }

      if(verificarEmpate()){
        limparTerminal();
        desenharTabuleiro();
        print("Empate");
        break;
      }

      vez = vez == Tipo.X ? Tipo.O : Tipo.X;
    }
  }
}