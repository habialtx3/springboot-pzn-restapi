package programmerzamannow.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmerzamannow.restful.entity.Address;
import programmerzamannow.restful.entity.Contact;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {

    Optional<Address> findByContact(Contact contact);
    Optional<Address> findByContactAndId(Contact contact, String id);
}
