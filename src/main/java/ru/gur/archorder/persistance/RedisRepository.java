package ru.gur.archorder.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gur.archorder.entity.IdempKey;

@Repository
public interface RedisRepository extends CrudRepository<IdempKey, String> {
}
