package com.droidlogic.neolink.server.api;

import com.droidlogic.neolink.server.NeolinkService;
import com.droidlogic.neolink.server.model.Command;
import com.droidlogic.neolink.server.model.PirCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NeolinkController {

    private final NeolinkService neolinkService;

    @GetMapping("/{cameraName}")
    public Mono<Void> executeCommand(@PathVariable String cameraName,
                                     @RequestParam String commandName,
                                     @RequestParam String commandValue) {
        Command commandToExecute;
        switch (commandName) {
            case "pir":
                commandToExecute = new PirCommand(commandValue);
                break;
            default:
                throw new IllegalArgumentException("Command " + commandName + " not supported");
        }
        log.info("Executing command {}={} for {}", commandToExecute.getName(), commandToExecute.getValue(), cameraName);
        return neolinkService.executeCommand(cameraName, commandToExecute);
    }
}
