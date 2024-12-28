from enum import Enum
import os

class Posicao:
    def __init__(self, x, y):
        self.x = x
        self.y = y


class Tipo(Enum):
    VAZIO = ' '
    X = 'X'
    O = 'O'

tabuleiro = []
vez = Tipo.X

def cria_tabuleiro():
    for i in range(3):
        tabuleiro.append([Tipo.VAZIO] * 3)


def limpar_terminal(): 
    if os.name == 'nt': # Windows
        os.system('cls') 
    else:               # Linux/Mac
        os.system('clear')


def imprime_tabuleiro():
    limpar_terminal()
    for linha in tabuleiro:
        print(' | '.join([str(c.value) for c in linha]))
        print('-' * 10)


def obter_posicao() -> tuple[Posicao, bool]:
    print(f'Vez do jogador {vez.value}')
    xValue = input('Digite a linha (0 ~ 2): ')
    yValue = input('Digite a coluna(0 ~ 2): ')
    if xValue.isdigit() and yValue.isdigit():
        return [Posicao(int(xValue), int(yValue)), True]
    else:
        return [Posicao(-1, -1), False]


def jogada(pos: Posicao, jogador: Tipo) -> bool:
    if pos.x < 0 or pos.x > 2 or pos.y < 0 or pos.y > 2:
        return False
    
    if tabuleiro[pos.x][pos.y] != Tipo.VAZIO:
        return False
    
    tabuleiro[pos.x][pos.y] = jogador
    return True


def verifica_vitoria() -> bool:
    for i in range(3):
        if tabuleiro[i][0].value == tabuleiro[i][1].value == tabuleiro[i][2].value != Tipo.VAZIO.value:
            return True
        
        if tabuleiro[0][i].value == tabuleiro[1][i].value == tabuleiro[2][i].value != Tipo.VAZIO.value:
            return True
        
    if tabuleiro[0][0].value == tabuleiro[1][1].value == tabuleiro[2][2].value != Tipo.VAZIO.value:
        return True
    
    if tabuleiro[0][2].value == tabuleiro[1][1].value == tabuleiro[2][0].value != Tipo.VAZIO.value:
        return True
    
    return False


def verificar_empate() -> bool:
    for linha in tabuleiro:
        for celula in linha:
            if celula == Tipo.VAZIO:
                return False
            
    return True

def main():
    cria_tabuleiro()
    imprime_tabuleiro()
    global vez
    while(True):
        pos = obter_posicao()
        if pos[1] == True and jogada(pos[0], vez):
            imprime_tabuleiro()
            if verifica_vitoria():
                print(f'Jogador {vez.value} venceu!')
                break
            if verificar_empate():
                print('Empate!')
                break
            vez = Tipo.X if vez == Tipo.O else Tipo.O
        else:
            limpar_terminal()
            imprime_tabuleiro()
            print('Jogada inválida!')


if __name__ == '__main__':
    main()