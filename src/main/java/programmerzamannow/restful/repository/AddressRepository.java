package programmerzamannow.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmerzamannow.restful.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String> {

}
