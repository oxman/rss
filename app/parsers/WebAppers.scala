package parsers

import org.jsoup.nodes._
import scala.xml.Node

class WebAppers extends Default {

  override def hasFullContent = true

  override def getContent(item: Node, page: Document) = {
    page.select("div.post").first() match {
      case content: Element => {
        content.html()
      }
      case null => ""
    }
  }

}
