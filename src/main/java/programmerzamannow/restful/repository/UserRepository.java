package programmerzamannow.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmerzamannow.restful.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
