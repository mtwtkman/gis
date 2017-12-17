package mtwtkman

import scala.io.Source
import scala.util.Try
import java.lang.Math._

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

case class Point(lat: Double, lng: Double) {
  val r = 6378.137

  def distance(other: Point): Double = {
    r * acos(
      sin(lat) * sin(other.lat) +
      cos(lat) * cos(other.lat) * cos(other.lng - lng)
    )
  }
}

case class Shop(
  name: String,
  address: String,
  point: Point
)

object Mj {
  def toDouble(x: String): Option[Double] =
    try {
      Some(x.toDouble)
    } catch {
      case e: java.lang.NumberFormatException => None
    }

  lazy val shopSrc: Either[ParsingFailure, Json] =
    parse(
      Source
        .fromResource("jirou.json")
        .getLines
        .toList.mkString
    )

  lazy val shops: Option[List[Shop]] = shopSrc match {
    case Right(json) => json.as[List[Shop]] match {
      case Right(shops) => Some(shops)
      case Left(_) =>
        println("invalid format")
        None
    }
    case Left(_) =>
      println("invalid json")
      None
  }

  def search(point: Point): Unit = {
    val nearest: Shop = shops.get.minBy(x => point.distance(x.point))
    println(s"最寄りの二郎は ${nearest.name}")
  }

  implicit class Radian(val n: Double) {
    def radian: Double = toRadians(n)
  }

  implicit val decodeShop: Decoder[Shop] = new Decoder[Shop] {
    final def apply(c: HCursor): Decoder.Result[Shop] =
      for {
        name <- c.downField("name").as[String]
        address <- c.downField("address").as[String]
        lat <- c.downField("lat").as[Double]
        lng <- c.downField("lng").as[Double]
      } yield new Shop(name, address, Point(lat.radian, lng.radian))
  }

  def main(args: Array[String]): Unit = {
    if (args.size != 2) {
      throw new Exception("oh")
    }
    val Array(slat, slng): Array[String] = args
    List(slat, slng).map(toDouble) match {
      case List(Some(lat), Some(lng)) => search(Point(lat.radian, lng.radian))
      case _ => throw new Exception("invalid arguments")
    }
  }
}
