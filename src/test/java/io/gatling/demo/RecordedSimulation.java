package io.gatling.demo;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class RecordedSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://petstore.octoperf.com")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:125.0) Gecko/20100101 Firefox/125.0");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "none"),
    Map.entry("Sec-Fetch-User", "?1")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Origin", "https://petstore.octoperf.com"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1")
  );


  private ScenarioBuilder scn = scenario("RecordedSimulation")
    .exec(
      // Acceuil,
      http("acceuil")
        .get("/actions/Catalog.action")
        .headers(headers_0)
              .check(header("Set-Cookie").saveAs("Jsessionid")),
      pause(5),

      // Login,
      http("click_login")
        .get("/actions/Account.action;jsessionid=${Jsessionid}?signonForm=")
        .headers(headers_1),
      pause(5),

      // Identification,
      http("authentification")
        .post("/actions/Account.action")
        .headers(headers_2)
        .formParam("username", "demokomlanjep")
        .formParam("password", "GatlingTest1")
        .formParam("signon", "Login")
        .formParam("_sourcePage", "Vyf19lciKqBTCSts_bDQBP8Q7JCdSqKYbkPtvjh40oAKTPOlIRbEie4RY7YYCGVru7v0lUp5TVzxkLBHvQ4akj7x9c6D_z8Hu-EQWmBki0A=")
        .formParam("__fp", "v-xGR2ebfbVGrrM3mbNN91qum-zV9iGtGdVgBtgI0MMA2zodvvRPjmzcN4YnYE7v"),
      pause(5),

      // Choix_Fish,
      http("choix_animaux")
        .get("/actions/Catalog.action?viewCategory=&categoryId=FISH")
        .headers(headers_1),
      pause(5),

      // Choix_Race,
      http("choix_race")
        .get("/actions/Catalog.action?viewProduct=&productId=FI-SW-01")
        .headers(headers_1),
      pause(5),
      // Choix item,

      http("choix_item")
        .get("/actions/Catalog.action?viewItem=&itemId=EST-1")
        .headers(headers_1),
      pause(5),

      // Add cart,
      http("ajout_panier")
        .get("/actions/Cart.action?addItemToCart=&workingItemId=EST-1")
        .headers(headers_1),

      pause(5),
      // process checkout,
      http("checkout")
        .get("/actions/Order.action?newOrderForm=")
        .headers(headers_1),

      pause(5),
      // continue,
      http("saisie_carte")
        .post("/actions/Order.action")
        .headers(headers_2)
        .formParam("order.cardType", "Visa")
        .formParam("order.creditCard", "999 9999 9999 9999")
        .formParam("order.expiryDate", "12/03")
        .formParam("order.billToFirstName", "GatlingTest1")
        .formParam("order.billToLastName", "GatlingTest1")
        .formParam("order.billAddress1", "18 rue de gatling")
        .formParam("order.billAddress2", "18 rue de gatling")
        .formParam("order.billCity", "Niort")
        .formParam("order.billState", "sevres")
        .formParam("order.billZip", "79000")
        .formParam("order.billCountry", "France")
        .formParam("newOrder", "Continue")
        .formParam("_sourcePage", "wZVloTQ_fYV6oDkizgkZFzdNsnV-x2x7dmUecmqCn9uZyAHFpYu6adCGGU5t8es7hbcsPZH-eHvogmcfMw7-4OdHr_ezuT6Klbm0QG2HwUg=")
        .formParam("__fp", "92k6-NisW-k5R0vG34NF19pz4pUXUaXMAa7vP8oTLs4VhNnXbQBWAuzbbW5lIIA_goiEhKutqZrdYxobO_7oO6ZBgS5kY7IraBCBKc8P4gYWz97D-mXt-w=="),
      pause(5),

      // confirm,
      http("confirmer")
        .get("/actions/Order.action?newOrder=&confirmed=true")
        .headers(headers_1),
      pause(5),

      // return menu,
      http("retour_menu")
        .get("/actions/Catalog.action")
        .headers(headers_1)
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
