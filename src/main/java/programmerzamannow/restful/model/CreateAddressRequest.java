package programmerzamannow.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAddressRequest {

    @JsonIgnore
    @NotBlank
    private String contactId;

    @Size(max = 100)
    private  String street;

    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 100)
    private String country;

    @Size(max = 100)
    private String province;

    @Size(max = 10)
    private String postalCode;
}
