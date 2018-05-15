package br.com.challenge.ca.repository;

import br.com.challenge.ca.entity.BankslipsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankslipsRepository extends JpaRepository<BankslipsEntity, Long> {

    Optional<BankslipsEntity> findByCode(final String code);

}
