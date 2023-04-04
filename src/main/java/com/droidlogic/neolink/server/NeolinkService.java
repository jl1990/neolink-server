package com.droidlogic.neolink.server;

import com.droidlogic.neolink.server.error.NeolinkRuntimeException;
import com.droidlogic.neolink.server.model.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
public class NeolinkService {

    private static final int RETRY_NUMBER = 5;
    public static final int RETRY_WAIT_SECONDS = 5;

    @Value("${neolink.path}")
    private String neolinkPath;

    @Value("${neolink.config}")
    private String neolinkConfig;

    public void executeCommand(String cameraName, Command command) {

        ProcessBuilder processBuilder = new ProcessBuilder(commandToArgsList(cameraName, command));
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        int numRetries = 0;
        boolean succeeded = false;
        do {
            try {
                if (numRetries != 0) {
                    Thread.sleep(RETRY_WAIT_SECONDS * 1000);
                    log.info("Retrying (retry number {})", numRetries);
                }
                Process process = processBuilder.start();
                succeeded = process.waitFor() == 0;
                if (!succeeded) {
                    throw new NeolinkRuntimeException("Command failed");
                }
                log.info("Success!");
            } catch (InterruptedException ex) {
                numRetries++;
                log.error("Command failed (thread interrupted)", ex);
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                numRetries++;
                log.error("Command failed", ex);
            }
        } while (!succeeded && numRetries < RETRY_NUMBER);
    }

    public List<String> commandToArgsList(String cameraName, Command command) {
        return List.of(neolinkPath, "-c", neolinkConfig, command.getName(), cameraName, command.getValue());
    }
}
