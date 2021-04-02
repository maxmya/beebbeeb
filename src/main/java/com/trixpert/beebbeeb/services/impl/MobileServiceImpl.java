package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.*;
import com.trixpert.beebbeeb.data.mappers.AddressMapper;
import com.trixpert.beebbeeb.data.repositories.*;
import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.*;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MobileServiceImpl implements MobileService {

    private final AuditService auditService;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CarInstanceRepository carInstanceRepository;
    private final RolesRepository rolesRepository;
    private final BannerRepository bannerRepository;
    private final EssentialCarSpecsRepository essentialCarSpecsRepository;
    private final ExtraCarSpecsRepository extraCarSpecsRepository;

    private final TypeService typeService;
    private final BrandService brandService;
    private final UserService userService;
    private final SMSService smsService;

    @Qualifier("customers_queue")
    private final Map<String, CustomerMobileRegistrationRequest> customerEntities;


    private final ReporterService reporterService;

    private final AddressMapper addressMapper;

    public MobileServiceImpl(AuditService auditService,
                             UserRepository userRepository,
                             CustomerRepository customerRepository,
                             AddressRepository addressRepository,
                             CarInstanceRepository carInstanceRepository,
                             RolesRepository rolesRepository,
                             BannerRepository bannerRepository,
                             EssentialCarSpecsRepository essentialCarSpecsRepository,
                             ExtraCarSpecsRepository extraCarSpecsRepository,
                             TypeService typeService,
                             BrandService brandService,
                             UserService userService,
                             SMSService smsService,
                             @Qualifier("customers_queue") Map<String, CustomerMobileRegistrationRequest> customerEntities,
                             ReporterService reporterService,
                             AddressMapper addressMapper) {

        this.auditService = auditService;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.carInstanceRepository = carInstanceRepository;
        this.rolesRepository = rolesRepository;
        this.bannerRepository = bannerRepository;
        this.essentialCarSpecsRepository = essentialCarSpecsRepository;
        this.extraCarSpecsRepository = extraCarSpecsRepository;
        this.typeService = typeService;
        this.brandService = brandService;
        this.userService = userService;
        this.smsService = smsService;
        this.customerEntities = customerEntities;
        this.reporterService = reporterService;
        this.addressMapper = addressMapper;
    }


    @Override
    public ResponseWrapper<CustomerProfileResponse> profileResponse(HttpServletRequest request) {
        try {
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
                if (address.isMain()) {
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

            return reporterService.reportSuccess(response);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<AddressDTO>> getListOfAddresses(HttpServletRequest request) {
        try {
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
            addressEntities.forEach(address -> addresses.add(addressMapper.convertToDTO(address)));

            return reporterService.reportSuccess(addresses);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> saveAddress(AddressDTO address) {
        try {
            Optional<CustomerEntity> customerEntity = customerRepository.findById(address.getCustomerId());
            if (!customerEntity.isPresent()) {
                throw new NotFoundException("customer not found !");
            }

            AddressEntity addressEntity = addressMapper.convertToEntity(address);
            addressEntity.setCustomer(customerEntity.get());
            addressRepository.save(addressEntity);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CarItemResponse>> listCars(String page, String size, String query) {
        try {
            List<CarItemResponse> carItemResponses = new ArrayList<>();

            List<CarInstanceEntity> carInstanceEntityList;
            if (page != null) {
                if (size == null) {
                    size = "10";
                }
                PageRequest paging = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
                Page<CarInstanceEntity> carInstances = carInstanceRepository.findAllByActive(true, paging);
                carInstanceEntityList = carInstances.getContent();
            } else {
                carInstanceEntityList = carInstanceRepository.findAllByActive(true);
            }

            for (CarInstanceEntity carInstance : carInstanceEntityList) {

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

                if (query != null) {
                    // search in type , category , model
                    query = query.toLowerCase();
                    boolean desired =
                            (carInstance.getCar().getModel().getName().toLowerCase().contains(query))
                                    || (carInstance.getCar().getCategory().getName().toLowerCase().contains(query))
                                    || (carInstance.getCar().getCategory().getType().getName().toLowerCase().contains(query))
                                    || (carInstance.getCar().getBrand().getName().toLowerCase().contains(query));

                    if (!desired) {
                        continue;
                    }
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
            }

            return reporterService.reportSuccess(carItemResponses);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<OtpResponse> registerUser(CustomerMobileRegistrationRequest customerRegisterRequest) {
        try {
            String otp = otpGenerator();

            smsService.sendSMSMessage(" كود تفعيل حساب BeebBeeb هو ".concat(otp), customerRegisterRequest.getPhone());

            this.customerEntities.put(customerRegisterRequest.getPhone(), customerRegisterRequest);

            OtpResponse response = new OtpResponse();
            response.setOtp(otp);

            return reporterService.reportSuccess(response);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> verifyUser(String phone) {
        try {
            CustomerMobileRegistrationRequest customerRegisterRequest = this.customerEntities.get(phone);
            Optional<RolesEntity> customerRole = rolesRepository.findByName(Roles.ROLE_CUSTOMER);

            if (!customerRole.isPresent()) {
                throw new NotFoundException("Role customer Not Found");
            }

            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setName(customerRegisterRequest.getName());
            registrationRequest.setPhone(customerRegisterRequest.getPhone());
            registrationRequest.setPassword(customerRegisterRequest.getPassword());

            UserEntity savedUser = userService.registerUser(customerRegisterRequest.getPhone(),
                    customerRole.get(), registrationRequest, "", true).getData();

            CustomerEntity customerEntityRecord = CustomerEntity.builder()
                    .horoscope(customerRegisterRequest.getHoroscope())
                    .active(true)
                    .user(savedUser).build();

            customerRepository.save(customerEntityRecord);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<MobileHomeResponse> getMobileHome() {
        try {
            List<TypeDTO> types = typeService.listAllTypes(true).getData();
            List<BrandDTO> brands = brandService.getAllBrands(true).getData();
            List<LinkableImage> mainBanners = new ArrayList<>();
            List<LinkableImage> sliderBanners = new ArrayList<>();
            bannerRepository.findAllByActive(true)
                    .forEach(bannerEntity -> {
                        if (!bannerEntity.isMain()) {
                            LinkableImage linkableImage = new LinkableImage();
                            linkableImage.setId(bannerEntity.getId());
                            linkableImage.setUrl(bannerEntity.getPhoto().getPhotoUrl());
                            sliderBanners.add(linkableImage);
                        } else {
                            LinkableImage linkableImage = new LinkableImage();
                            linkableImage.setId(bannerEntity.getId());
                            linkableImage.setUrl(bannerEntity.getPhoto().getPhotoUrl());
                            mainBanners.add(linkableImage);
                        }
                    });

            List<CarItemResponse> carItemResponses = new ArrayList<>();
            carInstanceRepository.findAllByActive(true)
                    .forEach(carInstance -> {
                        if (carInstance.isBestSeller()) {
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

                            carItemResponses.add(CarItemResponse.builder()
                                    .id(carInstance.getId())
                                    .image(carPhoto)
                                    .currency("EGP")
                                    .name(carInstance.getCar().getModel().getName() + " " + carInstance.getCar().getCategory().getName())
                                    .price(carPrice)
                                    .rating(4)
                                    .build());
                        }
                    });

            MobileHomeResponse mobileHomeResponse = MobileHomeResponse
                    .builder()
                    .bestSellers(carItemResponses)
                    .carBrands(brands)
                    .carTypes(types)
                    .mainBanners(mainBanners)
                    .sliderImages(sliderBanners)
                    .build();

            return reporterService.reportSuccess(mobileHomeResponse);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    private String otpGenerator() {
        String numbers = "0123456789";
        StringBuilder x = new StringBuilder();
        Random random = new Random();
        char[] otp = new char[5];
        for (int i = 0; i < 5; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
            x.append(otp[i]);
        }
        return x.toString();
    }

    @Override
    public ResponseWrapper<CarDetailsResponse> getCarDetails(long carId) {
        try {
            Optional<CarInstanceEntity> carInstanceOptional = carInstanceRepository.findById(carId);

            if (!carInstanceOptional.isPresent()) {
                throw new NotFoundException("car with id " + carId + " not found !");
            }

            CarInstanceEntity carRecord = carInstanceOptional.get();

            CarDetailsResponse carDetailsResponse = new CarDetailsResponse();
            carDetailsResponse.setId(carId);

            carDetailsResponse.setCarName(
                    carRecord.getCar().getModel().getName());

            carDetailsResponse.setCarCategory(carRecord.getCar().getCategory().getName());
            carDetailsResponse.setCarType(carRecord.getCar().getCategory().getType().getName());
            carDetailsResponse.setOriginalPrice(carRecord.getOriginalPrice());

            List<PriceEntity> carPrices = carRecord.getPrices();

            if (!carPrices.isEmpty())
                carDetailsResponse.setVendorPrice(carPrices.get(carPrices.size() - 1).getAmount());


            carDetailsResponse.setColorFamily(carRecord.getCar().getColor().getParentColor().getName());
            carDetailsResponse.setModelYear(carRecord.getCar().getModel().getYear());
            carDetailsResponse.setBrandLogo(carRecord.getCar().getBrand().getLogoUrl());
            carDetailsResponse.setBrandName(carRecord.getCar().getBrand().getName());

            CommercialColorResponse commercialColorResponse = new CommercialColorResponse();
            commercialColorResponse.setColorName(carRecord.getCar().getColor().getName());
            commercialColorResponse.setColorCode(carRecord.getCar().getColor().getCode());

            carDetailsResponse.setCommercialColor(commercialColorResponse);

            LinkableImage mainPhoto = null;
            List<LinkableImage> exteriorImages = new ArrayList<>();
            List<LinkableImage> interiorImages = new ArrayList<>();
            for (PhotoEntity carPhoto : carRecord.getCar().getPhotos()) {
                if (carPhoto.isMainPhoto()) {
                    mainPhoto = new LinkableImage();
                    mainPhoto.setUrl(carPhoto.getPhotoUrl());
                    mainPhoto.setId(carPhoto.getId());
                } else if (carPhoto.isInterior()) {
                    LinkableImage image = new LinkableImage();
                    image.setId(carPhoto.getId());
                    image.setUrl(carPhoto.getPhotoUrl());
                    interiorImages.add(image);
                } else {
                    LinkableImage image = new LinkableImage();
                    image.setId(carPhoto.getId());
                    image.setUrl(carPhoto.getPhotoUrl());
                    exteriorImages.add(image);
                }
            }
            for (PhotoEntity carPhoto : carRecord.getCar().getModel().getPhotos()) {
                if (mainPhoto == null && carPhoto.isMainPhoto()) {
                    mainPhoto = new LinkableImage();
                    mainPhoto.setUrl(carPhoto.getPhotoUrl());
                    mainPhoto.setId(carPhoto.getId());
                } else if (carPhoto.isInterior()) {
                    LinkableImage image = new LinkableImage();
                    image.setId(carPhoto.getId());
                    image.setUrl(carPhoto.getPhotoUrl());
                    interiorImages.add(image);
                } else {
                    LinkableImage image = new LinkableImage();
                    image.setId(carPhoto.getId());
                    image.setUrl(carPhoto.getPhotoUrl());
                    exteriorImages.add(image);
                }
            }
            carDetailsResponse.setInteriorImages(interiorImages);
            carDetailsResponse.setExteriorImages(exteriorImages);
            carDetailsResponse.setMainPhoto(mainPhoto);

            List<EssentialSpecsEntity> essentialSpecs =
                    essentialCarSpecsRepository.findAllByCarAndActive(carRecord.getCar(), true);

            Map<String, String> essentialSpecsMap = new HashMap<>();

            for (EssentialSpecsEntity specsEntity : essentialSpecs) {
                essentialSpecsMap.put(specsEntity.getKey(), specsEntity.getValue());
            }

            List<ExtraSpecsEntity> extraSpecs =
                    extraCarSpecsRepository.findAllByCarAndActive(carRecord.getCar(), true);

            Map<String, String> extraSpecsMap = new HashMap<>();

            for (ExtraSpecsEntity specsEntity : extraSpecs) {
                extraSpecsMap.put(specsEntity.getKey(), specsEntity.getValue());
            }

            carDetailsResponse.setMainSpecs(essentialSpecsMap);
            carDetailsResponse.setExtraSpecs(extraSpecsMap);

            VendorResponse vendorResponse = new VendorResponse();
            vendorResponse.setName(carRecord.getVendor().getName());
            vendorResponse.setAddress(carRecord.getVendor().getMainAddress());
            carDetailsResponse.setVendor(vendorResponse);

            List<BranchResponse> branches = new ArrayList<>();
            for (BranchEntity branch : carRecord.getVendor().getBranches()) {
                if (!branch.isActive()) continue;
                BranchResponse branchResponse = new BranchResponse();
                branchResponse.setName(branch.getName());
                branchResponse.setAddress(branch.getAddress());
                branches.add(branchResponse);
            }
            carDetailsResponse.setBranches(branches);

            return reporterService.reportSuccess(carDetailsResponse);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }


}
