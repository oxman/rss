package parsers

import org.jsoup.Jsoup
import org.jsoup.nodes._
import scala.collection.JavaConversions._
import scala.xml.Node

class Clubic extends Default {

  override def encoding   = "ISO-8859-1"

  override def getContent(item: Node, page: Document) = {
    val result = (item \ "link").text.split("/")(3) match {
      case "video"       => parseVideo(item, page)
      case "diaporama"   => parseDiaporama(item, page)
      case _             => parseArticle(item, page)
    }

    result match {
      case "" => (item \ "description").text
      case result => result
    }
  }


  def parseVideo(item: Node, page: Document) = {
    page.select("div.blockVideoHigh").first() match {
      case content: Element => {
        content.select("p").first().remove()
        content.select("div.contenu_block_central").remove()
        content.select("div.headerVideo").remove()
        content.select("div.descriptionVideo").remove()
        content.select("div#block1").remove()
        content.select("div#commentaires").remove()

        content.html()
      }
      case null => ""
    }
  }


  def parseDiaporama(item: Node, page: Document) = {
    val slideshow = page.select("div.ss_viewport").get(1) match {
      case slideshow: Element => {
        "<ul>" +
        (for (a <- slideshow.select("ul.ss_main a.ss_aimg"))
            yield "<li><img src='" + a.attr("rel") + "'/></li>"
        ).mkString("") +
        "</ul>"
      }
      case _ => ""
    }

    val legend = page.select("div.ss_allparselegend").first() match {
      case legend: Element => legend.outerHtml()
      case _ => ""
    }

    slideshow + legend
  }


  def parseArticle(item: Node, page: Document) = {
    page.select("div.editorial").first() match {
      case content: Element => content.html()
      case null => ""
    }
  }


}