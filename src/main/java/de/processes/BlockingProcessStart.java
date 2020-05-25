package de.processes;

import de.threads.DaemonTreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockingProcessStart {
    private  static final Logger LOGGER = Logger.getLogger(BlockingProcessStart.class.getName());

    public static void main(String[] args) {

        if(args.length == 0) {
            LOGGER.log(Level.WARNING, "no command line given to call");
            System.exit(1);
        }
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(args);

        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
