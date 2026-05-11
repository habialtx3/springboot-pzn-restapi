package programmerzamannow.restful.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import programmerzamannow.restful.entity.Address;
import programmerzamannow.restful.entity.Contact;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.*;
import programmerzamannow.restful.repository.AddressRepository;
import programmerzamannow.restful.repository.ContactRepository;
import programmerzamannow.restful.repository.UserRepository;
import programmerzamannow.restful.service.AddressService;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private AddressService addressService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setPassword("test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setFirstName("test");
        contact.setLastName("test");
        contact.setUser(user);
        contact.setPhone("test");
        contact.setEmail("test@gmail.com");

        contactRepository.save(contact);
    }

    @Test
    void createAddressBadRequest() throws Exception {

        CreateAddressRequest request = new CreateAddressRequest();
        request.setCity("Bambangg");

        mockMvc.perform(
                        post("/api/contacts/test/addresses")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isBadRequest()
                )
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                });
    }

    @Test
    void createAddressSuccess() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);
        CreateAddressRequest request = new CreateAddressRequest();
        request.setContactId(contact.getId());
        request.setCity("Bambangg");
        request.setCountry("Bambangg");
        request.setPostalCode("Bambangg");
        request.setProvince("Bambangg");
        request.setStreet("Bambangg");

        mockMvc.perform(
                        post("/api/contacts/test/addresses")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isOk()
                )
                .andDo(result -> {
                    WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<AddressResponse>>() {
                    });
                    assertNull(response.getErrors());

                    AddressResponse addressResponse = response.getData();

                    Address dbCheck = addressRepository.findById("bambang").orElse(null);
                    assertEquals("Bambangg", addressResponse.getCity());
                });
    }


    @Test
    void getAddressSuccess() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        Address address = new Address();
        address.setContact(contact);
        address.setId("Bambangg");
        address.setCity("Bambangg");
        address.setCountry("Bambangg");
        address.setPostalCode("Bambangg");
        address.setProvince("Bambangg");
        address.setStreet("Bambangg");

        addressRepository.save(address);

        mockMvc.perform(
                        get("/api/contacts/test/addresses/Bambangg")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isOk()
                )
                .andDo(result -> {
                    WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<AddressResponse>>() {
                    });
                    assertNull(response.getErrors());
                });
    }

    @Test
    void getBadRequest() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        mockMvc.perform(
                        get("/api/contacts/test/addresses")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isNotFound()
                )
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                });
    }

    @Test
    void updateAddressSuccess() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        Address address = new Address();
        address.setContact(contact);
        address.setId("Bambangg");
        address.setCity("Bambangg");
        address.setCountry("Bambangg");
        address.setPostalCode("Bambangg");
        address.setProvince("Bambangg");
        address.setStreet("Bambangg");

        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCity("Waduh");
        request.setStreet("Moment");

        mockMvc.perform(
                        put("/api/contacts/test/addresses/Bambangg")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isOk()
                )
                .andDo(result -> {
                    WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<AddressResponse>>() {
                    });
                    assertNull(response.getErrors());
                    assertEquals("Waduh", response.getData().getCity());
                    assertEquals("Moment", response.getData().getStreet());
                });
    }

    @Test
    void updateAddressNotFound() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        Address address = new Address();
        address.setContact(contact);
        address.setId("Bambangg");
        address.setCity("Bambangg");
        address.setCountry("Bambangg");
        address.setPostalCode("Bambangg");
        address.setProvince("Bambangg");
        address.setStreet("Bambangg");

        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCity("Waduh");
        request.setStreet("Moment");

        mockMvc.perform(
                        put("/api/contacts/test/addresses/Naegae")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isNotFound()
                )
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                });
    }

    @Test
    void updateUnauthorized() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        Address address = new Address();
        address.setContact(contact);
        address.setId("Bambangg");
        address.setCity("Bambangg");
        address.setCountry("Bambangg");
        address.setPostalCode("Bambangg");
        address.setProvince("Bambangg");
        address.setStreet("Bambangg");

        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCity("Waduh");
        request.setStreet("Moment");

        mockMvc.perform(
                        put("/api/contacts/test/addresses/Naegae")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpectAll(
                        status().isUnauthorized()
                )
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                });
    }

    @Test
    void removeAddressSuccess() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        Address address = new Address();
        address.setContact(contact);
        address.setId("Bambangg");
        address.setCity("Bambangg");
        address.setCountry("Bambangg");
        address.setPostalCode("Bambangg");
        address.setProvince("Bambangg");
        address.setStreet("Bambangg");

        addressRepository.save(address);

        mockMvc.perform(
                        delete("/api/contacts/test/addresses/Bambangg")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isOk()
                )
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNull(response.getErrors());

                    Address addressCheck = addressRepository.findById("Bambangg").orElse(null);
                    assertNull(addressCheck);
                });
    }

    @Test
    void removeAddressNotFound() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        Address address = new Address();
        address.setContact(contact);
        address.setId("Bambangg");
        address.setCity("Bambangg");
        address.setCountry("Bambangg");
        address.setPostalCode("Bambangg");
        address.setProvince("Bambangg");
        address.setStreet("Bambangg");

        addressRepository.save(address);

        mockMvc.perform(
                        delete("/api/contacts/test/addresses/Yoru")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isNotFound()
                )
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());

                    Address addressCheck = addressRepository.findById("Bambangg").orElse(null);
                    assertNotNull(addressCheck);
                });
    }

    @Test
    void getListAddressSuccess() throws Exception {

        User user1 = userRepository.findById("test").orElse(null);
        Contact contact = contactRepository.findByUserAndId(user1, "test").orElse(null);

        for (int i = 0; i < 10; i++) {
            Address address = new Address();
            address.setContact(contact);
            address.setId("Bambangg-" + i);
            address.setCity("Bambangg");
            address.setCountry("Bambangg");
            address.setPostalCode("Bambangg");
            address.setProvince("Bambangg");
            address.setStreet("Bambangg");
            addressRepository.save(address);
        }

        mockMvc.perform(
                        get("/api/contacts/test/addresses")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isOk()
                )
                .andDo(result -> {
                    WebResponse<List<AddressResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<List<AddressResponse>>>() {
                    });
                    assertNull(response.getErrors());
                    assertEquals(10,response.getData().size());
                });
    }
}