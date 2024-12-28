#include <iostream>
#include <cstdlib> // Para malloc e free no C++
#include <stdio.h>
#include <ctype.h>
using namespace std;

class Posicao
{
    public:
        int x;
        int y;

        Posicao(int x, int y){
            this->x = x;
            this->y = y;
        }
};

enum Tipo
{
    VAZIO = ' ',
    X = 'X',
    O = 'O'
};

char* buffer;
Tipo** tabuleiro;
Tipo jogador = Tipo::X;

// Função para inicializar o tabuleiro e o buffer
void inicializar()
{
    buffer = new char[3];
    tabuleiro = new Tipo*[3];
    for (int i = 0; i < 3; i++)
    {
        tabuleiro[i] = new Tipo[3];
        for (int j = 0; j < 3; j++)
        {
            tabuleiro[i][j] = Tipo::VAZIO;
        }
    }
}

// Função para desenhar o tabuleiro
void desenhar()
{
    system("cls"); // Limpa a tela no Windows
    cout << "  0   1   2" << endl;
    cout << "0 " << (char)tabuleiro[0][0] << " | " << (char)tabuleiro[0][1] << " | " << (char)tabuleiro[0][2] << endl;
    cout << "  ----------" << endl;
    cout << "1 " << (char)tabuleiro[1][0] << " | " << (char)tabuleiro[1][1] << " | " << (char)tabuleiro[1][2] << endl;
    cout << "  ----------" << endl;
    cout << "2 " << (char)tabuleiro[2][0] << " | " << (char)tabuleiro[2][1] << " | " << (char)tabuleiro[2][2] << endl;
}

// Função para liberar a memória alocada para o tabuleiro
void liberar()
{
    for (int i = 0; i < 3; i++)
    {
        delete[] tabuleiro[i]; // Libera as colunas
    }
    
    delete[] tabuleiro; // Libera as linhas
}

// Obter posição da jogada
Posicao obterJogada()
{
    int x, y;
    cout << "Digite a linha e a coluna (x,y): ";
    cin >> buffer;
    cout << buffer;

    if(isdigit(buffer[0]) && isdigit(buffer[2]))
    {
        x = buffer[0] - '0';
        y = buffer[2] - '0';

        return Posicao(x, y);
    }
    else
    {
        cout << "Entrada inválida!" << endl;
        return obterJogada();
    }
}

// Atualiza o tabuleiro com a jogada
bool atualizar(Posicao posicao)
{
    if(tabuleiro[posicao.x][posicao.y] == Tipo::VAZIO || posicao.x < 0 || posicao.x > 2 || posicao.y < 0 || posicao.y > 2)
    {
        tabuleiro[posicao.x][posicao.y] = jogador;
        return true;
    }
    else
    {
        cout << "Posição inválida!" << endl;
        return false;
    }
}

// Verifica se o jogador atual ganhou
bool verificarGanho() {
    // Verifica as linhas
    for (int i = 0; i < 3; i++) {
        if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0] != Tipo::VAZIO) {
            return true;
        }
    }

    // Verifica as colunas
    for (int i = 0; i < 3; i++) {
        if (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i] && tabuleiro[0][i] != Tipo::VAZIO) {
            return true;
        }
    }

    // Verifica a diagonal principal
    if (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0] != Tipo::VAZIO) {
        return true;
    }

    // Verifica a diagonal secundária
    if (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0] && tabuleiro[0][2] != Tipo::VAZIO) {
        return true;
    }

    return false;
}

// Verificar empate
bool verificarEmpate()
{
    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
            if(tabuleiro[i][j] == Tipo::VAZIO){
                return false;
            }
        }
    }

    return true;
}

// Função principal
int main()
{
    // Inicializa o tabuleiro
    inicializar();
    desenhar();

    // Loop do jogo
    while(true){
        cout << "Jogador " << (char)jogador << endl;
        Posicao posicao = obterJogada();
        if(atualizar(posicao)){
            desenhar();
            if(verificarGanho()){
                cout << "Jogador " << (char)jogador << " ganhou!" << endl;
                break;
            }
            else if(verificarEmpate()){
                cout << "Empate!" << endl;
                break;
            }
            jogador = jogador == Tipo::X ? Tipo::O : Tipo::X;
        }
    }

    liberar();
    return EXIT_SUCCESS;
}
