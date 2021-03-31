import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import scala.concurrent.duration._

class Simulation1 extends Simulation {

  val scenarioName = System.getProperty("scenario", "scenario1")
  val requestName = System.getProperty("request", "get private space home")

  val baseUrl = System.getProperty("baseurl","https://app-delayed.shayan-ps1.qax.rtf.cloudhub.io/delayed") //http://54.188.186.175:30350
  val subUrl = System.getProperty("suburl", "/")

  val responseCode = Integer.getInteger("response", 200)
  val searchPattern = System.getProperty("search", "hello world")


  val usersPerSec = Integer.getInteger("users", 20).toDouble
  val duration = java.lang.Long.getLong("duration", 60)
  val maxResponse = Integer.getInteger("maxresponse", 800)
  val percentile94Response = Integer.getInteger("percentile4", 600)
  val assertFailedThreshold = java.lang.Long.getLong("failthreshold", 0)
  val assertSuccessThreshold = java.lang.Long.getLong("passpercentagethreshold", 100).toDouble

  before {
    println(scenarioName + " begin123")
  }

  after {
    println(scenarioName + " end")
  }

  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .baseUrl(baseUrl)

  val theScenarioBuilder: ScenarioBuilder = scenario(scenarioName)
    .exec(
      http(requestName)
        .get(subUrl)
        .check(
          status.find.in(responseCode),
          regex(searchPattern).count.is(1)
        )
    )

  setUp(
    // theScenarioBuilder.inject(rampUsers(20).during(5 seconds))
    theScenarioBuilder.inject(constantConcurrentUsers(20).during(duration seconds))
    //theScenarioBuilder.inject(constantUsersPerSec(usersPerSec).during(duration seconds))
  ).assertions(
    //global.responseTime.max.lte(maxResponse),
    //global.responseTime.percentile4.lte(percentile94Response),
    forAll.failedRequests.count.is(assertFailedThreshold),
    details(requestName).successfulRequests.percent.gte(assertSuccessThreshold)
  ).protocols(theHttpProtocolBuilder)

}
