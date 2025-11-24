package com.scalesec.vulnado;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.*;


public class LinkLister {
  public static List<String> getLinks(String url) throws IOException {
    validateUrl(url); // Validate the URL to prevent injection risks
    List<String> result = new ArrayList<String>();
    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a");
    for (Element link : links) {
      result.add(link.absUrl("href"));
    }
    return result;
  }

  public static List<String> getLinksV2(String url) throws BadRequest {
    try {
      validateUrl(url); // Validate the URL to prevent injection risks
      URL aUrl = new URL(url);
      String host = aUrl.getHost();
      System.out.println(host);
      if (host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.") || host.equals("localhost") || host.equals("127.0.0.1")) {
        throw new BadRequest("Use of Private IP");
      } else {
        return getLinks(url);
      }
    } catch (Exception e) {
      throw new BadRequest(e.getMessage());
    }
  }

  private static void validateUrl(String url) throws BadRequest {
    try {
      new URL(url); // Check if the URL is valid
    } catch (MalformedURLException e) {
      throw new BadRequest("Invalid URL: " + e.getMessage());
    }
  }
}
