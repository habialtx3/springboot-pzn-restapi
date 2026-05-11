package programmerzamannow.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.AddressResponse;
import programmerzamannow.restful.model.CreateAddressRequest;
import programmerzamannow.restful.model.WebResponse;
import programmerzamannow.restful.service.AddressService;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(
            path = "/api/contacts/{contactId}/addresses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<AddressResponse> create(User user,
                                        @RequestBody CreateAddressRequest request,
                                        @PathVariable(name = "contactId") String contactId) {
        request.setContactId(contactId);
        AddressResponse addressResponse = addressService.create(user, request);

        return WebResponse.<AddressResponse>builder()
                .data(addressResponse)
                .build();
    }

    @GetMapping(
            path = "/api/contacts/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<AddressResponse> get(User user,
                                     @PathVariable(name = "contactId") String contactId) {
        AddressResponse addressResponse = addressService.get(user,contactId);
        return WebResponse.<AddressResponse>builder()
                .data(addressResponse)
                .build();
    }


}
