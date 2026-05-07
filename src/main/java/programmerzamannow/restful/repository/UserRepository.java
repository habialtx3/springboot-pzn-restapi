package programmerzamannow.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmerzamannow.restful.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByToken(String token);
}
