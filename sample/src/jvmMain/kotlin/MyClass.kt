object MyContext

context(MyContext)
  @Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
  @kotlin.internal.HidesMembers
  operator fun Int.unaryMinus(): Int = 42

fun main() {
  println(-5)
  with(MyContext) { println(-5) }
}
