package phu.quang.le.utils

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._
import reactivemongo.api.MongoDriver
import scala.concurrent.ExecutionContext.Implicits.global

trait DBConfiguration extends Configuration {
  configure {
    val config = ConfigFactory.load()
    val db = config.getString("mongodb.database")
    val servers = config.getStringList("mongodb.servers").asScala
    val driver = new MongoDriver
    val connection = driver.connection(servers)
    connection(db)
  }
}