package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.PhotoEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.mappers.AddressMapper;
import com.trixpert.beebbeeb.data.mappers.CarInstanceMapper;
import com.trixpert.beebbeeb.data.mappers.CarMapper;
import com.trixpert.beebbeeb.data.repositories.AddressRepository;
import com.trixpert.beebbeeb.data.repositories.CarInstanceRepository;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.response.CarItemResponse;
import com.trixpert.beebbeeb.data.response.CustomerProfileResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.ReporterService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Api(tags = {"Customer API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/mobile")
public class MobileController {

    private final AuditService auditService;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CarInstanceRepository carInstanceRepository;

    private final ReporterService reporterService;

    private final AddressMapper addressMapper;


    public MobileController(AuditService auditService,
                            UserRepository userRepository,
                            CustomerRepository customerRepository,
                            AddressRepository addressRepository,
                            CarInstanceRepository carInstanceRepository,
                            ReporterService reporterService,
                            AddressMapper addressMapper) {

        this.auditService = auditService;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.carInstanceRepository = carInstanceRepository;
        this.reporterService = reporterService;
        this.addressMapper = addressMapper;
    }


    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<CustomerProfileResponse>> profileResponse(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String username = auditService.getUsernameForAudit(authHeader);
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        if (!userEntity.isPresent()) {
            throw new NotFoundException("user not found");
        }
        Optional<CustomerEntity> customerEntity = customerRepository.findByUser(userEntity.get());
        if (!customerEntity.isPresent()) {
            throw new NotFoundException("customer not found");
        }

        CustomerProfileResponse response = CustomerProfileResponse
                .builder()
                .id(customerEntity.get().getId())
                .address(addressMapper.convertToDTO(addressRepository.findByCustomer(customerEntity.get())))
                .profileUrl(userEntity.get().getPicUrl())
                .horoscope(customerEntity.get().getHoroscope())
                .displayName(userEntity.get().getName())
                .phone(userEntity.get().getPhone())
                .build();

        return ResponseEntity.ok(reporterService.reportSuccess(response));
    }


    @GetMapping("/list/cars")
    public ResponseEntity<ResponseWrapper<List<CarItemResponse>>> listCars() {
        List<CarItemResponse> carItemResponses = new ArrayList<>();
        List<CarInstanceEntity> carInstanceEntities = carInstanceRepository.findAllByActive(true);
        carInstanceEntities.forEach(carInstance -> {
            String carPhoto = "";
            for (PhotoEntity photoEntity : carInstance.getCar().getPhotos()) {
                if (photoEntity.isMainPhoto()) {
                    carPhoto = photoEntity.getPhotoUrl();
                    break;
                }
            }
            String carPrice = "";
            if (carInstance.getPrices().size() > 1) {
                carPrice = (carInstance.getPrices().get(carInstance.getPrices().size() - 1)).getAmount();
            } else {
                carPrice = carInstance.getPrices().get(0).getAmount();
            }

            carItemResponses.add(
                    CarItemResponse.builder()
                            .id(carInstance.getId())
                            .image(carPhoto)
                            .currency("EGP")
                            .name(carInstance.getCar().getModel().getName() + " " + carInstance.getCar().getCategory().getName())
                            .price(carPrice)
                            .rating(4)
                            .build()
            );
        });

        return ResponseEntity.ok(reporterService.reportSuccess(carItemResponses));
    }


}
