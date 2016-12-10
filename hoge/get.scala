package hoge

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class Get extends Simulation { 

  val baseURL = scala.sys.env.getOrElse("URL","https://samp.lu")
  val targetPath = scala.sys.env.getOrElse("TARGET","/hoge")
  val hogeCookie = scala.sys.env.getOrElse("hoge","ThisisCookie")
  val ps: String = scala.sys.env.getOrElse("PS","5")
  val lt: String = scala.sys.env.getOrElse("LT","60")

  val httpConf = http
    .baseURL(baseURL)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("ja,en-US;q=0.8,en;q=0.6")
    .acceptEncodingHeader("gzip, deflate, sdch")
    .userAgentHeader("Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257/spass-app/4.1.0")
    .disableCaching
  
  val scn = scenario("GetHoge").exec(
      exec(addCookie(Cookie("hoge",hogeCookie))),
      exec(http("Hoge").get(targetPath))
    )

  setUp(
    scn.inject(
	  rampUsersPerSec(1) to (ps.toInt) during(10 seconds),
	  constantUsersPerSec(ps.toInt) during(lt.toInt seconds)
    ).protocols(httpConf)
  )

}
