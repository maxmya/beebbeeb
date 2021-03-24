package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.entites.*;
import com.trixpert.beebbeeb.data.mappers.AddressMapper;
import com.trixpert.beebbeeb.data.repositories.AddressRepository;
import com.trixpert.beebbeeb.data.repositories.CarInstanceRepository;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.response.CarItemResponse;
import com.trixpert.beebbeeb.data.response.CustomerProfileResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.ReporterService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(tags = {"All Mobile APIs"})
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
        Optional<UserEntity> userEntity = userRepository.findByPhone(username);
        if (!userEntity.isPresent()) {
            throw new NotFoundException("user not found");
        }
        Optional<CustomerEntity> customerEntity = customerRepository.findByUser(userEntity.get());
        if (!customerEntity.isPresent()) {
            throw new NotFoundException("customer not found");
        }

        List<AddressEntity> addressEntities = addressRepository.findByCustomer(customerEntity.get());
        AddressEntity addressEntity = null;
        for (AddressEntity address : addressEntities) {
            if (address.isPrimary()) {
                addressEntity = address;
            }
        }

        CustomerProfileResponse response = CustomerProfileResponse
                .builder()
                .id(customerEntity.get().getId())
                .address(addressMapper.convertToDTO(addressEntity))
                .profileUrl(userEntity.get().getPicUrl())
                .horoscope(customerEntity.get().getHoroscope())
                .displayName(userEntity.get().getName())
                .phone(userEntity.get().getPhone())
                .build();

        return ResponseEntity.ok(reporterService.reportSuccess(response));
    }

    @GetMapping("/address/list")
    public ResponseEntity<ResponseWrapper<List<AddressDTO>>> getListOfAddresses(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String username = auditService.getUsernameForAudit(authHeader);
        Optional<UserEntity> userEntity = userRepository.findByPhone(username);
        if (!userEntity.isPresent()) {
            throw new NotFoundException("user not found");
        }
        Optional<CustomerEntity> customerEntity = customerRepository.findByUser(userEntity.get());
        if (!customerEntity.isPresent()) {
            throw new NotFoundException("customer not found");
        }
        List<AddressEntity> addressEntities = addressRepository.findByCustomer(customerEntity.get());
        List<AddressDTO> addresses = new ArrayList<>();
        addressEntities.forEach(address -> {
            addresses.add(addressMapper.convertToDTO(address));
        });
        return ResponseEntity.ok(reporterService.reportSuccess(addresses));
    }

    @PostMapping("/address/add")
    public ResponseEntity<ResponseWrapper<Boolean>> saveAddress(@RequestBody AddressDTO address) {

        Optional<CustomerEntity> customerEntity = customerRepository.findById(address.getCustomerId());
        if (!customerEntity.isPresent()) {
            throw new NotFoundException("customer not found !");
        }

        AddressEntity addressEntity = addressMapper.convertToEntity(address);
        addressEntity.setCustomer(customerEntity.get());
        addressRepository.save(addressEntity);

        return ResponseEntity.ok(reporterService.reportSuccess());
    }

    @GetMapping("/list/cars")
    public ResponseEntity<ResponseWrapper<List<CarItemResponse>>> listCars(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) String size
    ) {

        List<CarItemResponse> carItemResponses = new ArrayList<>();

        List<CarInstanceEntity> carInstanceEntityList;
        if (page != null) {
            if (size == null) {
                size = "5";
            }
            PageRequest paging = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
            Page<CarInstanceEntity> carInstances = carInstanceRepository.findAllByActive(true, paging);
            carInstanceEntityList = carInstances.getContent();
        } else {
            carInstanceEntityList = carInstanceRepository.findAllByActive(true);
        }

        carInstanceEntityList.forEach(carInstance -> {
            String carPhoto = "";
            for (PhotoEntity photoEntity : carInstance.getCar().getPhotos()) {
                if (photoEntity.isMainPhoto()) {
                    carPhoto = photoEntity.getPhotoUrl();
                    break;
                }
            }

            if ("".equals(carPhoto)) {
                for (PhotoEntity photoEntity : carInstance.getCar().getModel().getPhotos()) {
                    if (photoEntity.isMainPhoto()) {
                        carPhoto = photoEntity.getPhotoUrl();
                        break;
                    }
                }
            }

            String carPrice = "0";
            if (carInstance.getPrices() != null && carInstance.getPrices().size() > 1) {
                carPrice = (carInstance.getPrices().get(carInstance.getPrices().size() - 1)).getAmount();
            } else if (carInstance.getPrices() != null && carInstance.getPrices().size() == 1) {
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
