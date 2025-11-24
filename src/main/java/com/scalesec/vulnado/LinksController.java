package com.scalesec.vulnado;

import org.springframework.boot.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@EnableAutoConfiguration
public class LinksController {
  @RequestMapping(value = "/links", produces = "application/json")
  List<String> links(@RequestParam String url) throws IOException {
    try {
      validateUrl(url);
      return LinkLister.getLinks(url);
    } catch (MalformedURLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL", e);
    }
  }

  @RequestMapping(value = "/links-v2", produces = "application/json")
  List<String> linksV2(@RequestParam String url) throws BadRequest {
    try {
      validateUrl(url);
      return LinkLister.getLinksV2(url);
    } catch (MalformedURLException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL", e);
    }
  }

  private void validateUrl(String url) throws MalformedURLException {
    new URL(url); // This will throw MalformedURLException if the URL is invalid
  }
}
