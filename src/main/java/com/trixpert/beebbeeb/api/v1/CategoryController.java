package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CategoryRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.CategoryDTO;
import com.trixpert.beebbeeb.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Categories API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list/active")
    @ApiOperation("Get Categories List")
    public ResponseEntity<ResponseWrapper<List<CategoryDTO>>> getActiveCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get Categories List")
    public ResponseEntity<ResponseWrapper<List<CategoryDTO>>> getInactiveCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories(false));
    }

    @PutMapping("/update/{categoryId}")
    @ApiOperation("Update an existing Category with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateCategory(
            @Valid @RequestBody CategoryRegistrationRequest categoryRegistrationRequest,
            @PathVariable("categoryId") long categoryId
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(categoryService.updateCategory(categoryRegistrationRequest ,
                categoryId, authorizationHeader));
    }

    @PostMapping("/add")
    @ApiOperation("Add New Category")
    public ResponseEntity<ResponseWrapper<Boolean>> addCategory(
            @Valid @RequestBody CategoryRegistrationRequest categoryRegistrationRequest
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(categoryService.registerCategory(categoryRegistrationRequest ,
                authorizationHeader));
    }

    @PutMapping("/delete/{categoryId}")
    @ApiOperation("Remove Category By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteCategory(
            @PathVariable("categoryId") Long categoryId,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(categoryService.deleteCategory(categoryId, authorizationHeader));
    }

    @GetMapping("/cars/list/active/{categoryId}")
    @ApiOperation("list All active cars for specific category")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> listActiveCarsForCategory(
            @PathVariable("categoryId") long categoryId){
        return ResponseEntity.ok(categoryService.listCarsForCategory(true, categoryId));
    }

    @GetMapping("/cars/list/inactive/{categoryId}")
    @ApiOperation("list All inactive cars for specific category")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> listInactiveCarsForCategory(
            @PathVariable("categoryId") long categoryId){
        return ResponseEntity.ok(categoryService.listCarsForCategory(false, categoryId));
    }
    @GetMapping("/get/{categoryId}")
    @ApiOperation("Get category by Id ")
    public ResponseEntity<ResponseWrapper<CategoryDTO>> getCategory(@PathVariable("categoryId")
                                                                    long categoryId){
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }
}
