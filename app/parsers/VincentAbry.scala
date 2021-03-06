package parsers

import org.jsoup.nodes._
import org.jsoup.select._
import scala.collection.JavaConversions._
import scala.xml.Node

class VincentAbry extends Default {

  override def hasFullContent = true

  override def getContent(item: Node, page: Document) = {
    page.select(".post_text").first() match {
      case content: Element => {
        for(img <- content.select("img")) {
          val src = if (img.hasAttr("pagespeed_high_res_src") == true) {
              img.attr("pagespeed_high_res_src")
            } else if (img.hasAttr("pagespeed_lazy_src") == true) {
              img.attr("pagespeed_lazy_src")
            } else {
              img.attr("src")
            }
          img.attr("src", src)
        }

        content.html()
      }
      case null => ""
    }
  }

}
