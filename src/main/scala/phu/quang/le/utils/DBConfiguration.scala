package phu.quang.le.utils

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._
import reactivemongo.api.{ MongoDriver, MongoConnection }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait DBConfiguration extends Configuration {
  configure {
    val config = ConfigFactory.load()
    val driver = new MongoDriver
    val mongoUri = config.getString("mongoUri")
    
    val database = for {
      uri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
      con = driver.connection(uri)
      dn <- Future(uri.db.get)
      db <- con.database(dn)
    } yield db
    
    database
  }
}