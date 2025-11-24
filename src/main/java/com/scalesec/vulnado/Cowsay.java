package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  private static final Logger logger = LoggerFactory.getLogger(Cowsay.class);

  private Cowsay() {
    // Private constructor to prevent instantiation
  }
    // Private constructor to prevent instantiation
  }
  public static String run(String input) {
    logger.info("Executing command: {}", cmd);
    // Sanitize user input to prevent command injection
    String sanitizedInput = StringEscapeUtils.escapeJava(input);
    String cmd = "/usr/games/cowsay '" + sanitizedInput + "'";
    System.out.println(cmd);
    processBuilder.command("bash", "-c", cmd);

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return output.toString();
  }
}
