package com.droidlogic.neolink.server.api;

import com.droidlogic.neolink.server.NeolinkService;
import com.droidlogic.neolink.server.model.Command;
import com.droidlogic.neolink.server.model.PirCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NeolinkController {

    private final NeolinkService neolinkService;

    @GetMapping("/{cameraName}")
    public void executeCommand(@PathVariable String cameraName,
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
        neolinkService.executeCommand(cameraName, commandToExecute);
    }
}
