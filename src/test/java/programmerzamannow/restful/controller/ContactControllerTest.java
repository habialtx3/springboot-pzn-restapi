package programmerzamannow.restful.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import programmerzamannow.restful.entity.Contact;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.ContactResponse;
import programmerzamannow.restful.model.CreateContactRequest;
import programmerzamannow.restful.model.UpdateContactRequest;
import programmerzamannow.restful.model.WebResponse;
import programmerzamannow.restful.repository.ContactRepository;
import programmerzamannow.restful.repository.UserRepository;
import programmerzamannow.restful.security.Bcrypt;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setPassword(Bcrypt.hashpw("password", Bcrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000);
        userRepository.save(user);
    }

    @Test
    void createContactBadRequest() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void createContactUnauthorized() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Bambang");
        request.setLastName("Bambang");
        request.setPhone("0808080");
        request.setEmail("reza@gmail.com");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void createContactSuccess() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Bambang");
        request.setLastName("Bambang");
        request.setPhone("0808");
        request.setEmail("bambang@gmail.com");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContactResponse>>() {
                    });
                    assertNull(response.getErrors());
                    assertEquals("Bambang", response.getData().getFirstName());
                    assertEquals("Bambang", response.getData().getLastName());
                    assertEquals("bambang@gmail.com", response.getData().getEmail());
                    assertEquals("0808", response.getData().getPhone());

                    assertTrue(contactRepository.existsById(response.getData().getId()));
                }
        );
    }

    @Test
    void getContactBadRequest() throws Exception {
        mockMvc.perform(
                get("/api/contacts/waduh")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void getContactSuccess() throws Exception {

        User user = userRepository.findById("test").orElse(null);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setFirstName("test");
        contact.setLastName("test");
        contact.setUser(user);
        contact.setPhone("test");
        contact.setEmail("test@gmail.com");

        contactRepository.save(contact);

        mockMvc.perform(
                get("/api/contacts/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getErrors());
                    assertEquals("test", response.getData().getFirstName());
                    assertEquals("test", response.getData().getLastName());
                    assertEquals("test", response.getData().getId());
                    assertEquals("test", response.getData().getPhone());
                    assertEquals("test@gmail.com", response.getData().getEmail());
                }
        );

    }

    @Test
    void updateContactBadRequest() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                put("/api/contacts/1234")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void updateContactSuccess() throws Exception {

        User user = userRepository.findById("test").orElse(null);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setFirstName("test");
        contact.setLastName("test");
        contact.setUser(user);
        contact.setPhone("test");
        contact.setEmail("test@gmail.com");

        contactRepository.save(contact);

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("bambang");
        request.setEmail("salah@gmail.com");

        mockMvc.perform(
                put("/api/contacts/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getErrors());
                    assertEquals("bambang", response.getData().getFirstName());
                    assertEquals("salah@gmail.com", response.getData().getEmail());
                }
        );
    }

    @Test
    void deleteContactNotFound() throws Exception {
        User user = userRepository.findById("test").orElse(null);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setFirstName("test");
        contact.setLastName("test");
        contact.setUser(user);
        contact.setPhone("test");
        contact.setEmail("test@gmail.com");

        contactRepository.save(contact);

        mockMvc.perform(
                delete("/api/contacts/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteContactSuccess() throws Exception {
        User user = userRepository.findById("test").orElse(null);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setFirstName("test");
        contact.setLastName("test");
        contact.setUser(user);
        contact.setPhone("test");
        contact.setEmail("test@gmail.com");

        contactRepository.save(contact);

        mockMvc.perform(
                delete("/api/contacts/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            Contact contactExist = contactRepository.findByUserAndId(user, "test").orElse(null);

            assertNull(contactExist);
            assertEquals("OK", response.getData());

        });
    }
}
