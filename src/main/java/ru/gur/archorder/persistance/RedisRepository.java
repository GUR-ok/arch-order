package ru.gur.archorder.persistance;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gur.archorder.entity.IdempKey;

@Repository
@Profile("!hw09")
public interface RedisRepository extends CrudRepository<IdempKey, String> {
}
