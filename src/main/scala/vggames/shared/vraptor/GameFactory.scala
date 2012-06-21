package vggames.shared.vraptor

import br.com.caelum.vraptor.ioc.Component
import br.com.caelum.vraptor.ioc.ComponentFactory
import vggames.regex.task.RegexGame
import vggames.regex.Descriptions
import vggames.shared.Game

@Component
class GameFactory(descriptions : Descriptions) extends ComponentFactory[Game] {

  def getInstance : Game = new RegexGame(descriptions)
}