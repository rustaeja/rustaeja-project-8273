package vggames.shared.player

import org.scalaquery.ql.extended.ExtendedTable
import br.com.caelum.vraptor.ioc.Component
import org.scalaquery.session.Database
import org.scalaquery.ql._
import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql.extended.SQLiteDriver.Implicit._
import org.scalaquery.session.Database.threadLocalSession

case class Player(id : Long, email : String, token : String, var lastTask : Option[String], var activeTime : Long = 0) {
  def getEmail : String = email
  def getLastTask : String = lastTask.getOrElse(null)
}

@Component
class Players {

  def find(token : String) : Option[Player] = {
    Database.forURL("jdbc:sqlite:games.db", driver = "org.sqlite.JDBC").withSession {
      val query = for (player <- Players if player.token is token) yield player.*
      query.firstOption.map(tuple2Player)
    }
  }

  def findByEmail(email : String) : Option[Player] = {
    Database.forURL("jdbc:sqlite:games.db", driver = "org.sqlite.JDBC").withSession {
      val query = for (player <- Players if player.email is email) yield player.*
      query.firstOption.map(tuple2Player)
    }
  }

  def +=(p : Player) : Player = {
    Database.forURL("jdbc:sqlite:games.db", driver = "org.sqlite.JDBC").withSession {
      Players.noId.insert((p.email, p.token, p.lastTask, p.activeTime))
    }
    findByEmail(p.email).get
  }

  def update(p : Player) {
    Database.forURL("jdbc:sqlite:games.db", driver = "org.sqlite.JDBC").withSession {
      val query = for (player <- Players if player.email is p.email) yield player.*
      query.update(Player.unapply(p).get)
    }
  }

  def finishGroup(p : Player, group : String) {
    Database.forURL("jdbc:sqlite:games.db", driver = "org.sqlite.JDBC").withSession {
      FinishedGroups.insert((p.id, group))
    }
  }

  def finishedGroups(p : Player) = {
    Database.forURL("jdbc:sqlite:games.db", driver = "org.sqlite.JDBC").withSession {
      val query = for (group <- FinishedGroups if group.playerId is p.id) yield group.group
      query.list
    }
  }

  def tuple2Player(t : (Long, String, String, Option[String], Long)) = Player(t._1, t._2, t._3, t._4, t._5)
}

object Players extends ExtendedTable[(Long, String, String, Option[String], Long)]("players") {

  def id = column[Long]("id")
  def email = column[String]("email")
  def token = column[String]("token")
  def lastTask = column[Option[String]]("lastTask")
  def activeTime = column[Long]("activeTime")

  def * = id ~ email ~ token ~ lastTask ~ activeTime
  def noId = email ~ token ~ lastTask ~ activeTime
}

object FinishedGroups extends ExtendedTable[(Long, String)]("finishedGroups") {

  def playerId = column[Long]("player_id")
  def group = column[String]("group")

  def * = playerId ~ group
}