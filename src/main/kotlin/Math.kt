import java.lang.Exception
import kotlin.math.*

class Math {

    private fun ggT(num1: Int, num2: Int): Int {
        return if (num2 == 0) num1
        else ggT(num2, num1 % num2)
    }

    private fun ggT(num1: Long, num2: Long): Long {
        return if (num2 == 0.toLong()) num1
        else ggT(num2, num1 % num2)
    }

    fun eulerPHI(n: Int): Int {
        return 1 + (2..n).count { ggT(it, n) == 1 }
    }

    fun eulerPrimPHI(prim1: Long, prim2: Long): Int {
        when {
            !(checkPrim(prim1) || checkPrim(prim2)) -> throw Exception("p/q sind keine Primzahlen")
            else -> return 1 + (2..(prim1 * prim2)).count { ggT(it, (prim1 * prim2)) == 1.toLong() }
        }
    }

    /**
     * Ich schreibe hier lieber einen größeren Primzahltester, da dieser bei höheren Zahlen im Bereich ab
     * 10E13 um ≈300% effizienter ist.
     */
    fun checkPrim(input: Long): Boolean {
        val zero: Long = 0.toLong()
        when {
            input <= 16 -> return (input == 2.toLong() ||
                    input == 3.toLong() ||
                    input == 5.toLong() ||
                    input == 7.toLong() ||
                    input == 11.toLong() ||
                    input == 13.toLong())
            else -> when (zero) {
                input % 2.toLong(), input % 3.toLong(), input % 5.toLong(), input % 7.toLong() -> return false
                else -> {
                    var i: Long = 10
                    while (i * i <= input) {
                        if (input.mod(input + 1) == zero) return false
                        if (input.mod(input + 3) == zero) return false
                        if (input.mod(input + 7) == zero) return false
                        if (input.mod(input + 9) == zero) return false
                        i += 10
                    }
                    return true
                }
            }
        }

    }

    /**
     * Da aus der Aufgabenstellung hervorgehoben wird, dass die eingegebene Zahl aus genau zwei Primfaktoren p und q besteht,
     * so habe ich mir überlegt, dies über eine eigene data class zu realiseren.
     * Die Funktion liefert also dann eine Data-Class zurück, welche genau diese beiden Werte gespeichert hat.
     * Um auf diese Werte zurückzugreifen, nutzt man die Funktion in Kotlin wie folgt:
     *
     * math.primFactor(numInput).p
     * math.primFactor(numInput).q
     *
     * Beispiel aus Aufgabenstellung:
     *
     * val math = Math()
     * val prim = math.primFactor(263713)
     * println("p: ${prim.p} \nq: ${prim.q}")
     *
     * --------------------------------
     * Output:
     * p: 859
     * q: 307
     *
     */
    fun primFactor(num: Int): PrimFactor {
        var numT: Long = num.toLong()
        val max: Int = ceil(log10(numT.toDouble()) / log10(2.toDouble())).roundToInt()
        val tmp = LongArray(max)
        var numFactors = 0
        var it: Long = 2

        while (it <= numT) {
            if (numT.mod(it) == 0.toLong()) {
                tmp[numFactors++] = it
                numT /= it
                it = 1
            }
            it++
        }

        if (numFactors > 2) throw Exception("Ergebnis hat eine höhere Primfaktorzerlegung als p und q!")

        val out = LongArray(numFactors)
        for (i in 0 until numFactors) out[i] = tmp[i]
        return PrimFactor(out[0], out[1])
    }

    /**
     * Der Rückgabewert soll multiplikativ invers zu e mod phi(N) -> N ist dabei wieder das Produkt zweier unbekannter Primzahlen
     * e mod phi(N) = d --> das d soll berechnet werden
     */
    fun calModInv(e: Int, n: Int): Int {
        val p: Int = primFactor(n).p.toInt()
        val q: Int = primFactor(n).q.toInt()
        return e % eulerPHI(n)
    }
}

data class PrimFactor(val q: Long, val p: Long)

fun main() {
    println(Math().checkPrim(999983))
}