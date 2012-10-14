package vggames.shared.vraptor

import br.com.caelum.vraptor.ioc.Component
import br.com.caelum.vraptor.ioc.ComponentFactory
import vggames.regex.RegexGame
import vggames.shared.Game
import vggames.shared.task.Descriptions
import vggames.scala.ScalaGame
import vggames.css.CssGame
import br.com.caelum.vraptor.ioc.Component
import br.com.caelum.vraptor.ioc.ApplicationScoped

@Component
class GameFactory(cached : GameFactoryCache, data : RequestData) extends ComponentFactory[Game] {

  def getInstance : Game = {
    data.game match {
      case "regex" => cached regexGame
      case "scala" => cached scalaGame
      case "css" => cached cssGame
      case other => throw new RuntimeException("Não foi possível criar o jogo [" + other + "]. Talvez " +
        "seja necessário registrá-lo na GameFactory")
    }
  }
}

@Component
@ApplicationScoped
class GameFactoryCache(cache : DescriptionsCache) {
  val regexGame = new RegexGame(cache.get("regex"))
  val scalaGame = new ScalaGame(cache.get("scala"))
  val cssGame = new CssGame(cache.get("css"))
}