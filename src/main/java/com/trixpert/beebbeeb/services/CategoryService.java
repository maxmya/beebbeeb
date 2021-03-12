package com.trixpert.beebbeeb.services;


import com.trixpert.beebbeeb.data.request.CategoryRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.CategoryDTO;

import java.util.List;

public interface CategoryService {

    ResponseWrapper<Boolean> registerCategory(CategoryRegistrationRequest categoryRegistrationRequest,
     String authHeader);

    ResponseWrapper<Boolean> deleteCategory(long categoryId , String authHeader);

    ResponseWrapper<Boolean> updateCategory(CategoryDTO categoryDTO , String authHeader);

    ResponseWrapper<List<CategoryDTO>> getAllCategories(boolean active);

    ResponseWrapper<List<CarDTO>> listCarsForCategory(boolean active, long categoryId);

}
