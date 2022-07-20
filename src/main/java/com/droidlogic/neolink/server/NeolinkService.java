package com.droidlogic.neolink.server;

import com.droidlogic.neolink.server.error.NeolinkRuntimeException;
import com.droidlogic.neolink.server.model.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.util.List;

@Slf4j
public class NeolinkService {

    private static final int RETRY_NUMBER = 5;
    @Value("${neolink.path}")
    private String neolinkPath;

    @Value("${neolink.config}")
    private String neolinkConfig;

    public Mono<Void> executeCommand(String cameraName, Command command) {
        Mono<Void> blockingWrapper = Mono.fromCallable(() -> {
                    ProcessBuilder processBuilder = new ProcessBuilder(commandToArgsList(cameraName, command));
                    processBuilder.redirectErrorStream(true);
                    processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    Process process = processBuilder.start();
                    boolean succeeded = process.waitFor() == 0;
                    if (!succeeded) {
                        throw new NeolinkRuntimeException("Command failed");
                    }
                    log.info("Success!");
                    return null;
                })
                .retry(RETRY_NUMBER)
                .then();
        return blockingWrapper.subscribeOn(Schedulers.boundedElastic()).then();
    }

    public List<String> commandToArgsList(String cameraName, Command command) {
        return List.of(neolinkPath, "-c", neolinkConfig, command.getName(), cameraName, command.getValue());
    }
}
