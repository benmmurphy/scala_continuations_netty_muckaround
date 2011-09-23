class Context private (val request: ServerRequest, val params : Map[String, String]) {

}

object Context {
  def apply(request:ServerRequest, pathParams: Map[String, String]) = {
    val params = request.params.view.map(kv => (kv._1, kv._2.head)).toMap ++ pathParams
    new Context(request, params)
  }
}

