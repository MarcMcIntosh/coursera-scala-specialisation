import org.scalameter
import org.scalameter._

val time = config(
  Key.exec.minWarmupRuns = 20,
  Key.exec.maxWarmupRuns = 60,
  Key.verbose -> true
).withWarmer(new scalameter.Warmer.Default) measure {
  (0 until 1000000).toArray
}

println(s"Array initialization time: $time ms")