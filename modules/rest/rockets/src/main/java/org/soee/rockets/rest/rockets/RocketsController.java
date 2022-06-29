package org.soee.rockets.rest.rockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soee.rockets.services.RocketService;
import org.soee.rockets.services.RocketState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController()
@RequestMapping(path = "/rockets",
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class RocketsController {
    public static final Logger logger = LoggerFactory.getLogger(RocketsController.class);

    private final RocketService rocketService;

    @Autowired
    public RocketsController(RocketService rocketService) {
        this.rocketService = rocketService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<JsonChannelList> getRocketList(
    ) {
        logger.debug("get channel list");
        List<String> allChannels = rocketService.findAllChannels();
        allChannels.sort(Comparator.naturalOrder());
        JsonChannelList jsonResult = new JsonChannelList(allChannels);
        return ResponseEntity.ok(jsonResult);
    }

    @GetMapping(path = "/{rocketId}")
    public ResponseEntity<JsonRocketState> getRocketInfo(
            @PathVariable("rocketId") String rocketId
    ) {
        logger.debug("show rocket info for " + rocketId);
        RocketState rocketState = rocketService.getRocketState(rocketId);
        JsonRocketState jsonRocketState = new JsonRocketState()
                .setChannel(rocketState.getChannel())
                .setLaunchTime(rocketState.getLaunchTime())
                .setType(rocketState.getType())
                .setMission(rocketState.getMission())
                .setSpeed(rocketState.getSpeed())
                .setExploded(rocketState.isExploded())
                .setExplosionReason(rocketState.getExplosionReason());
        return ResponseEntity.ok(jsonRocketState);
    }


}
