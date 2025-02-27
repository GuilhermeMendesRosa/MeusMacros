package br.com.roselabs.auth_meus_macros.repositories;

import br.com.roselabs.auth_meus_macros.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);

}
