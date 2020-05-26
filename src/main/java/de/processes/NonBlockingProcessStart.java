package de.processes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NonBlockingProcessStart {
    private static final Logger LOGGER = Logger.getLogger(NonBlockingProcessStart.class.getName());


    public static void main(String[] args) {
        if (args.length == 0) {
            LOGGER.log(Level.WARNING, "no command line given to call");
            System.exit(1);
        }
        ExecutorService pool = Executors.newSingleThreadExecutor();
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(args);

        try {

            LOGGER.log(Level.INFO, "process {0} starting ...", args[0]);
            Process process = processBuilder.start();
            ProcessReadTask task = new ProcessReadTask(process.getInputStream());
            Future<List<String>> future = pool.submit(task);

            // no block, starting another process here
            String[] ls = {"ls", "-l", "/"};
            BlockingProcessStart.main(ls);

            List<String> result = future.get(5, TimeUnit.MINUTES);
            for (String s : result) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    private static class ProcessReadTask implements Callable<List<String>> {
        private InputStream inputStream;

        public ProcessReadTask(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public List<String> call() {
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
