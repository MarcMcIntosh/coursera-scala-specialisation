val f: PartialFunction[String, String] = { case "ping" => "pong"}

f.isDefinedAt("ping") // true
f.isDefinedAt("abc") // false

f("ping")