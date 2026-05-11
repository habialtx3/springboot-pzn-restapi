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
import programmerzamannow.restful.repository.AddressRepository;
import programmerzamannow.restful.repository.ContactRepository;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findByUserAndId(user,request.getContactId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact is not found")
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


    public AddressResponse get(User user, String id){
        Contact contact = contactRepository.findByUserAndId(user,id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact not found")
        );

        Address address = addressRepository.findByContact(contact).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address not created")
        );

        return  toAddressResponse(address);
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

}
