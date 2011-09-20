import com.jayway.jsonpath.JsonPath

class JSONResource (val json:Object) {
  def string(path:String) : Option[String] = {
    val result:String = JsonPath.read(json, path)
    if (result == null)  None else Some(result)
  }
}
