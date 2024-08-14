package net.yixin.witty_vet.repository;

import net.yixin.witty_vet.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}
