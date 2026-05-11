package programmerzamannow.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmerzamannow.restful.entity.Address;
import programmerzamannow.restful.entity.Contact;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {

    Optional<Address> findFirstByContact(Contact contact);
    Optional<Address> findByContactAndId(Contact contact, String id);
    List<Address> findByContact(Contact contact);
}
