package net.yixin.witty_vet.repository;

import net.yixin.witty_vet.model.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
