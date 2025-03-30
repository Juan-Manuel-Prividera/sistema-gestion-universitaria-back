package com.api.materias.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
  private static PrintWriter fileWriter;
  private static boolean logToConsole;

  // Configurar el Logger
  public static void configure(String filePath, boolean logToConsole) throws IOException {
    Logger.fileWriter = new PrintWriter(new FileWriter(filePath, true), true);
    Logger.logToConsole = logToConsole;
  }

  public static void log(LogLevel level, String message) {
    if (fileWriter == null) {
      throw new IllegalStateException("Logger not configured. Call configure() first.");
    }

    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String logMessage = String.format("[%s] [%s] %s", timestamp, level, message);

    // Escribir en el archivo
    fileWriter.println(logMessage);

    // Escribir en consola si está habilitado
    if (logToConsole) {
      String coloredMessage = applyColor(level, logMessage);
      System.out.println(coloredMessage);
    }
  }

  private static String applyColor(LogLevel level, String message) {
    final String RESET = "\u001B[0m";
    final String GRAY = "\u001B[90m";
    final String WHITE = "\u001B[37m";
    final String GREEN = "\u001B[32m";
    final String RED = "\u001B[31m";

    return switch (level) {
      case TRACE -> GRAY + message + RESET;
      case DEBUG -> WHITE + message + RESET;
      case INFO -> GREEN + message + RESET;
      case ERROR -> RED + message + RESET;
      default -> message;
    };
  }

  public static void close() {
    if (fileWriter != null) {
      fileWriter.close();
    }
  }
}