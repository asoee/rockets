package org.soee.rockets.rest.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.soee.rockets.services.MessageMetadata;
import org.soee.rockets.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

@RestController()
@RequestMapping(path = "/messages",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class MessagesController {



    private final MessageService messageService;

    @Autowired
    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Object> postMessage(
            @RequestBody String bodyJson
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(bodyJson);

            System.out.println("json = " + json);

            MessageMetadata metadata = parseMetadata(json);
            JsonNode messageJson = json.at("/message");

            switch (metadata.getMessageType()) {
                case "RocketLaunched": {
                    handleRocketLaunched(metadata, messageJson);
                    break;
                }
                case "RocketSpeedIncreased": {
                    handleRocketSpeedIncreased(metadata, messageJson);
                    break;
                }
                case "RocketSpeedDecreased": {
                    handleRocketSpeedDecreased(metadata, messageJson);
                    break;
                }
                case "RocketExploded": {
                    handleRocketExploded(metadata, messageJson);
                    break;
                }
                case "RocketMissionChanged": {
                    handleRocketMissionChanged(metadata, messageJson);
                    break;
                }
                default: {
                    return ResponseEntity.badRequest()
                            .body("Unknown message type: " + metadata.getMessageType());
                }
            }
            return ResponseEntity.ok().build();

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid timestamp format :" + e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private void handleRocketLaunched(MessageMetadata metadata, JsonNode messageJson) {
        String rocketType = messageJson.at("/type").asText();
        long launchSpeed = messageJson.at("/launchSpeed").asLong();
        String mission = messageJson.at("/mission").asText();
        messageService.rocketLaunched(metadata, rocketType,mission, launchSpeed);
    }

   private void handleRocketSpeedIncreased(MessageMetadata metadata, JsonNode messageJson) {
        long speedChange = messageJson.at("/by").asLong();
        messageService.rocketSpeedIncreased(metadata, speedChange);
    }

   private void handleRocketSpeedDecreased(MessageMetadata metadata, JsonNode messageJson) {
        long speedChange = messageJson.at("/by").asLong();
        messageService.rocketSpeedDecreased(metadata, speedChange);
    }

   private void handleRocketExploded(MessageMetadata metadata, JsonNode messageJson) {
        String reason = messageJson.at("/reason").asText();
        messageService.rocketExploded(metadata, reason);
    }

   private void handleRocketMissionChanged(MessageMetadata metadata, JsonNode messageJson) {
        String newMission = messageJson.at("/newMission").asText();
        messageService.rocketMissionChanged(metadata, newMission);
    }

    private MessageMetadata parseMetadata(JsonNode json) {
        String channel = json.at("/metadata/channel").asText();
        int messageNumber = json.at("/metadata/messageNumber").asInt();
        String messageTime = json.at("/metadata/messageTime").asText();
        Instant messageTimestamp = OffsetDateTime.parse(messageTime, ISO_OFFSET_DATE_TIME).toInstant();
        String messageType = json.at("/metadata/messageType").asText();

        MessageMetadata metadata = new MessageMetadata()
                .setChannel(channel)
                .setSequenceNumber(messageNumber)
                .setTimestamp(messageTimestamp)
                .setMessageType(messageType);
        return metadata;
    }

}
