import scala.io.Source

// "7225247386" = "scala is fun"



  val mnemonics = Map(
    '2' -> "ABC",
    '3' -> "DEF",
    '4' -> "GHI",
    '5' -> "JKL",
    '6' -> "MNO",
    '7' -> "PQRS",
    '8' -> "TUV",
    '9' -> "WXYZ"
  )

  val in = Source.fromFile("/Users/marc/Projects/coursera/linuxwords.txt")

  val words = in.getLines().toList.filter(word => word.forall(_.isLetter))

  val charCode: Map[Char, Char] = for ((digit, str) <- mnemonics; ltr <- str) yield ltr -> digit

  def wordCode(word: String): String = word.toUpperCase.map(charCode)

  val wordsFromNum: Map[String, Seq[String]] = words.groupBy(wordCode)

  def encode(number: String): Set[List[String]] = {
    if(number.isEmpty) Set(List())
    else {
      for {
        split <- 1 to number.length
        word <- wordsFromNum(number.take(split))
        rest <- encode(number drop split)
      } yield word :: rest
    }.toSet





