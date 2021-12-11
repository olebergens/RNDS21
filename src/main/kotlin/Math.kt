import java.lang.Exception
import kotlin.math.*

data class PrimFactor(val q: Long, val p: Long)

class MathTool {

    fun ggT(num1: Int, num2: Int): Int {
        return if (num2 == 0) num1
        else ggT(num2, num1 % num2)
    }

    fun ggT(num1: Long, num2: Long): Long {
        return if (num2 == 0.toLong()) num1
        else ggT(num2, num1 % num2)
    }

    fun eulerPHI(n: Int): Int {
        return 1 + (2..n).count { ggT(it, n) == 1 }
    }

    fun eulerPrimPHI(prim1: Long, prim2: Long): Int {
        if (!(checkPrim(prim1) || checkPrim(prim2))) throw Exception("p/q sind keine Primzahlen")
        return 1 + (2..(prim1 * prim2)).count { ggT(it, (prim1 * prim2)) == 1.toLong() }
    }

    /**
     * Ich schreibe hier lieber einen größeren Primzahltester, da dieser bei höheren Zahlen im Bereich ab
     * 10E13 um ≈300% effizienter ist.
     */
    private fun checkPrim(input: Long): Boolean {
        val zero: Long = 0.toLong()
        if (input <= 16)
            return (input == 2.toLong() ||
                input == 3.toLong() ||
                input == 5.toLong() ||
                input == 7.toLong() ||
                input == 11.toLong() ||
                input == 13.toLong())

        if (input % 2.toLong() == zero ||
            input % 3.toLong() == zero ||
            input % 5.toLong() == zero ||
            input % 7.toLong() == zero) return false

        var i: Long = 10
        while (i*i <= input) {
            if (input.mod(input + 1) == zero) return false
            if (input.mod(input + 3) == zero) return false
            if (input.mod(input + 7) == zero) return false
            if (input.mod(input + 9) == zero) return false
            i += 10
        }
        return true
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
     * val math = MathTool()
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
     */
    fun calModInv(e: Int, p: Int, q: Int): Int {
        if (!(checkPrim(p.toLong()) || checkPrim(q.toLong()))) throw Exception("Q/P sind keine Primzahlen.")

        return 0
    }
}

fun main(args: Array<String>) {
    val math = MathTool()
    val prim = math.primFactor(263713)
    println("p: ${prim.p} \nq: ${prim.q}")
}