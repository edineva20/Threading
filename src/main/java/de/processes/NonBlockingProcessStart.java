package de.processes;

import java.io.BufferedReader;
import java.io.IOException;
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

            LOGGER.log(Level.INFO, "process {0} starting ...", args[0]);
        Process process = null;
        Future<List<String>> future = null;
        try {
            process = processBuilder.start();
            ProcessReadTask task = new ProcessReadTask(process.getInputStream());
            // no block, starting another process here
            String[] ls = {"ls", "-l", "/etc"};
            BlockingProcessStart.main(ls);

            future = pool.submit(task);
            //future.cancel(true);
            List<String> result = future.get(1, TimeUnit.NANOSECONDS);

            for (String s : result) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            if(future != null) {
                future.cancel(true);
            }
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
