package parsers


import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat
import org.jsoup.Jsoup
import org.jsoup.nodes._
import scala.collection.JavaConversions._
import scala.xml.Node

class Ubergizmo extends Default {

  override def dateFormat = "d MMM yyyy HH:mm:ss Z"

  override def getContent(item: Node, page: Document) = {
    page.select("span#intelliTxt").first() match {
      case content: Element => content.html()
      case null => ""
    }
  }

}
