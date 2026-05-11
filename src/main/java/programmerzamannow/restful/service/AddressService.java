package programmerzamannow.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import programmerzamannow.restful.entity.Address;
import programmerzamannow.restful.entity.Contact;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.AddressResponse;
import programmerzamannow.restful.model.CreateAddressRequest;
import programmerzamannow.restful.model.UpdateAddressRequest;
import programmerzamannow.restful.repository.AddressRepository;
import programmerzamannow.restful.repository.ContactRepository;
import programmerzamannow.restful.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;
    @Autowired
    private AuthService authService;

    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findByUserAndId(user, request.getContactId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact is not found")
        );

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setProvince(request.getProvince());

        addressRepository.save(address);

        return toAddressResponse(address);
    }


    public AddressResponse get(User user, String contactId, String addressId) {
        Contact contact = contactRepository.findByUserAndId(user, contactId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found")
        );

        Address address = addressRepository.findByContactAndId(contact,addressId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not created")
        );

        return toAddressResponse(address);
    }

    @Transactional
    public AddressResponse update(User user, UpdateAddressRequest request, String contactId, String addressId) {

        validationService.validate(request);

        Contact contact = contactRepository.findByUserAndId(user, contactId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found")
        );

        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found")
        );

        address.setContact(contact);

        if (Objects.nonNull(request.getCity())) {
            address.setCity(request.getCity());
        }

        if (Objects.nonNull(request.getStreet())) {
            address.setStreet(request.getStreet());
        }

        if (Objects.nonNull(request.getCountry())) {
            address.setCountry(request.getCountry());
        }

        if (Objects.nonNull(request.getPostalCode())) {
            address.setPostalCode(request.getPostalCode());
        }

        if (Objects.nonNull(request.getProvince())) {
            address.setProvince(request.getProvince());
        }

        addressRepository.save(address);

        return toAddressResponse(address);
    }

    public void remove(User user, String contactId, String addressId) {
        Contact contact = contactRepository.findByUserAndId(user, contactId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found")
        );

        Address address = addressRepository.findByContactAndId(contact,addressId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address not Found")
        );

        addressRepository.delete(address);
    }

    private AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .city(address.getCity())
                .country(address.getStreet())
                .province(address.getProvince())
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .build();
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> list(User user, String contactId) {
        Contact contact = contactRepository.findByUserAndId(user,contactId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact is not found")
        );

        List<Address> addresses = addressRepository.findByContact(contact);
        return addresses.stream()
                .map(address -> AddressResponse.builder()
                        .street(address.getStreet())
                        .city(address.getCity())
                        .province(address.getProvince())
                        .country(address.getCountry())
                        .postalCode(address.getPostalCode())
                        .build())
                .toList();
    }


}
