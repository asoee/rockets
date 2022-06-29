package org.soee.rockets.persistence.repository;

import org.soee.rockets.persistence.model.RocketMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RocketMessageRepository extends CrudRepository<RocketMessage, Long> {

    @Query("SELECT DISTINCT m.channel FROM RocketMessage m")
    public List<String> findDistinctChannelIds();

    public List<RocketMessage> findByChannelOrderBySequenceNumber(String channel);

    public boolean existsByChannelAndSequenceNumber(String channel, int sequenceNumber);
}
