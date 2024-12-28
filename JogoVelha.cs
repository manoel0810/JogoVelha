
// Compilar usando o .NET Core 8 ou superior!!!

namespace JogoVelha
{
    public enum Tipo : int
    {
        Vazio = ' ',
        X = 'X',
        O = 'O'
    }

    public struct Posicao
    {
        public int X;
        public int Y;

        public Posicao()
        {
            X = Y = 0;
        }

        public override readonly string ToString()
        {
            return $"({X}, {Y})";
        }
    }

    public class Program
    {
        private static readonly Tipo[,] Tabuleiro = new Tipo[3, 3];
        private static Tipo _vez = Tipo.X;

        private static void CarregarTabuleiro()
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    Tabuleiro[i, j] = Tipo.Vazio;
                }
            }
        }

        private static void DesenharTabuleiro()
        {
            Console.Clear();
            Console.WriteLine("Colunas (Y); Linha (X)\n");
            Console.WriteLine("   0   1   2");
            Console.WriteLine($" 0 {(char)Tabuleiro[0, 0]} | {(char)Tabuleiro[0, 1]} | {(char)Tabuleiro[0, 2]} ");
            Console.WriteLine("   ----------");
            Console.WriteLine($" 1 {(char)Tabuleiro[1, 0]} | {(char)Tabuleiro[1, 1]} | {(char)Tabuleiro[1, 2]} ");
            Console.WriteLine("   ----------");
            Console.WriteLine($" 2 {(char)Tabuleiro[2, 0]} | {(char)Tabuleiro[2, 1]} | {(char)Tabuleiro[2, 2]} ");
        }

        private static bool AtualizarTabuleiro(Posicao pos, Tipo vez)
        {
            if (pos.X > 2 || pos.X < 0 || pos.Y > 2 || pos.Y < 0)
            {
                Console.WriteLine($"A posição {pos} não é válida!");
                return false;
            }

            if (Tabuleiro[pos.X, pos.Y] != Tipo.Vazio)
            {
                Console.WriteLine("A posição já está ocupada!");
                return false;
            }
            else
                Tabuleiro[pos.X, pos.Y] = vez;

            return true;
        }

        private static void JogadaVez()
        {
            _vez = _vez == Tipo.X ? Tipo.O : Tipo.X;
            Console.WriteLine($"O jogador da vez é: {(char)_vez}");
        }

        private static bool VerificarVitoria()
        {
            //Verificar linhas
            for (int i = 0; i < 3; i++)
            {
                if (Tabuleiro[i, 0] == Tabuleiro[i, 1] && Tabuleiro[i, 1] == Tabuleiro[i, 2] && Tabuleiro[i, 0] != Tipo.Vazio)
                    return true;
            }

            //Verificar colunas
            for (int j = 0; j < 3; j++)
            {
                if (Tabuleiro[0, j] == Tabuleiro[1, j] && Tabuleiro[1, j] == Tabuleiro[2, j] && Tabuleiro[0, j] != Tipo.Vazio)
                    return true;
            }

            //Verificar diagonais principais
            if (Tabuleiro[0, 0] == Tabuleiro[1, 1] && Tabuleiro[1, 1] == Tabuleiro[2, 2] && Tabuleiro[0, 0] != Tipo.Vazio)
                return true;

            if (Tabuleiro[0, 2] == Tabuleiro[1, 1] && Tabuleiro[1, 1] == Tabuleiro[2, 0] && Tabuleiro[0, 2] != Tipo.Vazio)
                return true;

            return false;
        }

        private static bool VerificarFimJogo()
        {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (Tabuleiro[i, j] == Tipo.Vazio)
                        return false;

            return true;
        }

        private static Posicao ObterPosicao()
        {
            Console.Write("Informe a posição para jogar (x,y): ");
            var valor = Console.ReadLine();

            if (string.IsNullOrEmpty(valor) || valor.Length != 3)
            {
                Console.WriteLine("Informe um valor válido!");
                return ObterPosicao();
            }

            if (!char.IsNumber(valor[0]) || !char.IsNumber(valor[2]))
            {
                Console.WriteLine("Informe um valor válido!");
                return ObterPosicao();
            }

            return new Posicao { X = int.Parse(valor[0].ToString()), Y = int.Parse(valor[2].ToString()) };
        }

        private static void RandomizarJogadorInicial()
        {
            var vezRandom = new Random().Next(1, 10);
            _vez = vezRandom <= 5 ? Tipo.X : Tipo.O;
        }

        public static void Main()
        {
            RandomizarJogadorInicial();
            CarregarTabuleiro();
            while (!VerificarFimJogo())
            {
                DesenharTabuleiro();
                JogadaVez();

                Posicao posicao;
                do
                {
                    posicao = ObterPosicao();

                } while (!AtualizarTabuleiro(posicao, _vez));

                if (VerificarVitoria())
                {
                    DesenharTabuleiro();
                    Console.WriteLine($"O jogador {(char)_vez} venceu!");
                    break;
                }
            }

            Console.Clear();
            Console.WriteLine("Fim de jogo! Nenhum jogador ganhou.");
            Console.ReadKey();
        }
    }
}