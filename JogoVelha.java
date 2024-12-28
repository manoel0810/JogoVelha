import java.util.Scanner;

public class JogoVelha
{
    public enum Tipo
    {
        VAZIO(' '), 
        X('X'), 
        O('O');

        private final int valor;

        Tipo(int valor) {
            this.valor = valor;
        }
    
        public char getValor() {
            return (char)valor;
        }
    }

    class Posicao
    {
        int x;
        int y;

        Posicao(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    private Tipo[][] tabuleiro = new Tipo[3][3];
    private Tipo jogadorAtual = Tipo.X;
    Scanner scanner = new Scanner(System.in);

    private void inicializarTabuleiro(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                tabuleiro[i][j] = Tipo.VAZIO;
            }
        }
    }

    public final static void limparConsole()
    {   
        try
        {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            System.out.println("Erro ao limpar o console.");
        }
    }

    private void desenharTabuleiro(){
        System.out.println("\n  0   1   2");
        System.out.println("0 " + tabuleiro[0][0].getValor() + " | " + tabuleiro[0][1].getValor() + " | " + tabuleiro[0][2].getValor());
        System.out.println("  ----------");
        System.out.println("1 " + tabuleiro[1][0].getValor() + " | " + tabuleiro[1][1].getValor() + " | " + tabuleiro[1][2].getValor());
        System.out.println("  ----------");
        System.out.println("2 " + tabuleiro[2][0].getValor() + " | " + tabuleiro[2][1].getValor() + " | " + tabuleiro[2][2].getValor());
    }

    private Posicao obterJogada(){
        
        System.out.print("Digite a linha e a coluna que deseja jogar (x,y): ");
        
        String entrada = scanner.nextLine();
        if(entrada.length() != 3 || !Character.isDigit(entrada.charAt(0)) || !Character.isDigit(entrada.charAt(2))){
            System.out.println("Entrada inválida. Digite a linha e a coluna separadas por vírgula.");
            return obterJogada();
        }

        int x = Character.getNumericValue(entrada.charAt(0));
        int y = Character.getNumericValue(entrada.charAt(2));

        return new Posicao(x, y);
    }

    private boolean atualizarTabuleiro(Posicao pos){
        if(pos.x < 0 || pos.x > 2 || pos.y < 0 || pos.y > 2){
            System.out.println("Posição inválida. Tente novamente.");
            return false;
        }

        if(tabuleiro[pos.x][pos.y] != Tipo.VAZIO){
            System.out.println("Posição já ocupada. Tente novamente.");
            return false;
        }

        tabuleiro[pos.x][pos.y] = jogadorAtual;
        return true;
    }

    private boolean verificarVencedor(){
        for(int i = 0; i < 3; i++){
            if(tabuleiro[i][0] == jogadorAtual && tabuleiro[i][1] == jogadorAtual && tabuleiro[i][2] == jogadorAtual){
                return true;
            }
            if(tabuleiro[0][i] == jogadorAtual && tabuleiro[1][i] == jogadorAtual && tabuleiro[2][i] == jogadorAtual){
                return true;
            }
        }

        if(tabuleiro[0][0] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][2] == jogadorAtual){
            return true;
        }

        if(tabuleiro[0][2] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][0] == jogadorAtual){
            return true;
        }

        return false;
    }

    private boolean verificarEmpate(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(tabuleiro[i][j] == Tipo.VAZIO){
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        JogoVelha jogo = new JogoVelha();
        jogo.inicializarTabuleiro();
        jogo.desenharTabuleiro();

        while(true){
            System.out.println("Jogador " + jogo.jogadorAtual.getValor() + " é sua vez.");
            Posicao pos = jogo.obterJogada();
            if(jogo.atualizarTabuleiro(pos)){
                limparConsole();
                jogo.desenharTabuleiro();
                if(jogo.verificarVencedor()){
                    System.out.println("Jogador " + jogo.jogadorAtual.getValor() + " venceu!");
                    break;
                }
                if(jogo.verificarEmpate()){
                    System.out.println("Empate!");
                    break;
                }
                jogo.jogadorAtual = jogo.jogadorAtual == Tipo.X ? Tipo.O : Tipo.X;
            }
        }

        jogo.scanner.close();
    }
}