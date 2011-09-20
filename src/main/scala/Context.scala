class Context private (val request: ServerRequest, val allParams : Map[String, Seq[String]]) {

  def params = {
    allParams.view.map(kv => (kv._1, kv._2.head)).toMap
  }
}

object Context {
  def apply(request:ServerRequest, pathParams: Map[String, String]) = {
    val allParams = pathParams.view.map(kv => (kv._1, List[String](kv._2))).toMap
    new Context(request, allParams)
  }
}

