package com.mmsaap.controller;

import com.mmsaap.entity.Property;
import com.mmsaap.repository.PropertyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addProperty")
    public  String addProperty(){
        return "added";
    }

    @DeleteMapping("/deleteProperty")
    public  String deleteProperty(){
        return "delete";
    }

    //http://localhost:8080/api/v1/property/{searchParam}
    @GetMapping("/{searchParam}")

    public List<Property>searchProperties(
            @PathVariable String searchParam
    ){
       return   propertyRepository.searchProperty(searchParam);
    }
}
