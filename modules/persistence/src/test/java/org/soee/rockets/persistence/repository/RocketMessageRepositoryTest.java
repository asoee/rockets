package org.soee.rockets.persistence.repository;

import org.junit.jupiter.api.Test;
import org.soee.rockets.persistence.PersistenceTestConfig;
import org.soee.rockets.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

//@SpringBootTest(classes = RocketMessageRepository.class)
@ContextConfiguration(classes = {PersistenceTestConfig.class})
@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:~/liquibase;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.liquibase.enabled=true",
        "spring.jpa.show-sql=true",
        "spring.jpa.hibernate.ddl-auto=none"
})
class RocketMessageRepositoryTest {

    @Autowired
    RocketMessageRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findDistinctIdsTwoSimpleChannels() {
        String channel1 = UUID.randomUUID().toString();
        repository.save(new RocketLaunched()
                .setRocketType("A")
                .setLaunchSpeed(100)
                .setMission("X")
                .setChannel(channel1)
                .setSequenceNumber(1)
                .setMessageTimestamp(Instant.now()));

        String channel2 = UUID.randomUUID().toString();
        repository.save(new RocketLaunched()
                .setRocketType("A")
                .setLaunchSpeed(100)
                .setMission("X")
                .setChannel(channel2)
                .setSequenceNumber(1)
                .setMessageTimestamp(Instant.now()));


        List<String> distinctIds = repository.findDistinctChannelIds();
        assertThat(distinctIds).isNotEmpty();
        assertThat(distinctIds).hasSize(2);
        assertThat(distinctIds).containsOnly(channel1, channel2);
    }

    @Test
    void findDistinctIdsTwoChannelsMultipleMessages() {
        String channel1 = UUID.randomUUID().toString();
        repository.save(new RocketLaunched()
                .setRocketType("A")
                .setLaunchSpeed(100)
                .setMission("X")
                .setChannel(channel1)
                .setSequenceNumber(1)
                .setMessageTimestamp(Instant.now()));

        repository.save(new RocketSpeedIncreased()
                .setSpeedChange(500)
                .setChannel(channel1)
                .setSequenceNumber(2)
                .setMessageTimestamp(Instant.now()));
        repository.save(new RocketSpeedDecreased()
                .setSpeedChange(200)
                .setChannel(channel1)
                .setSequenceNumber(3)
                .setMessageTimestamp(Instant.now()));

        String channel2 = UUID.randomUUID().toString();
        repository.save(new RocketLaunched()
                .setRocketType("A")
                .setLaunchSpeed(100)
                .setMission("X")
                .setChannel(channel2)
                .setSequenceNumber(1)
                .setMessageTimestamp(Instant.now()));
        repository.save(new RocketMissionChanged()
                .setMission("Y")
                .setChannel(channel2)
                .setSequenceNumber(2)
                .setMessageTimestamp(Instant.now()));


        List<String> distinctIds = repository.findDistinctChannelIds();

        assertThat(distinctIds).isNotEmpty();
        assertThat(distinctIds).hasSize(2);
        assertThat(distinctIds).containsOnly(channel1, channel2);
    }

    @Test
    void shouldFailOnDuplicateSquenceNumbers() {
        String channel1 = UUID.randomUUID().toString();
        repository.save(new RocketLaunched()
                .setRocketType("A")
                .setLaunchSpeed(100)
                .setMission("X")
                .setChannel(channel1)
                .setSequenceNumber(1)
                .setMessageTimestamp(Instant.now()));

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    repository.save(new RocketSpeedIncreased()
                            .setSpeedChange(500)
                            .setChannel(channel1)
                            .setSequenceNumber(1)
                            .setMessageTimestamp(Instant.now()));
                });
    }

    @Test
    void findByChannel() {
        String channel1 = UUID.randomUUID().toString();
        repository.save(new RocketLaunched()
                .setRocketType("A")
                .setLaunchSpeed(100)
                .setMission("X")
                .setChannel(channel1)
                .setSequenceNumber(1)
                .setMessageTimestamp(Instant.now()));

        repository.save(new RocketSpeedIncreased()
                .setSpeedChange(500)
                .setChannel(channel1)
                .setSequenceNumber(2)
                .setMessageTimestamp(Instant.now()));
        repository.save(new RocketSpeedDecreased()
                .setSpeedChange(200)
                .setChannel(channel1)
                .setSequenceNumber(3)
                .setMessageTimestamp(Instant.now()));

        entityManager.flush();
        entityManager.clear();

        List<RocketMessage> messages = repository.findByChannelOrderBySequenceNumber(channel1);

        assertThat(messages).hasSize(3);
        assertThat(messages.get(0).getSequenceNumber()).isEqualTo(1);
        assertThat(messages.get(2).getSequenceNumber()).isEqualTo(3);
        assertThat(messages.get(0)).isInstanceOf(RocketLaunched.class);
        assertThat(messages.get(1)).isInstanceOf(RocketSpeedIncreased.class);
        assertThat(messages.get(2)).isInstanceOf(RocketSpeedDecreased.class);


    }
}