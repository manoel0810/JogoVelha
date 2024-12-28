
enum class Tipo(val tipo: Char){
    X('X'),
    O('O'),
    VAZIO(' ')
}

class Posicao(x : Int, y : Int){
    val x = x
    val y = y
}

val tabuleiro = Array(3) { Array(3) { Tipo.VAZIO } }
var vez = Tipo.X

fun main() {
    println(Tipo.X.tipo)
}