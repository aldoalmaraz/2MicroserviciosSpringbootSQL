package com.tutorial.userservice.feignclients;

import com.tutorial.userservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8002")
@RequestMapping("/product")
public interface ProductFeignClient {

    @PostMapping()
    Product save(@RequestBody Product car);

    @GetMapping("/byuser/{userId}")
    List<Product> getProducts(@PathVariable("userId") int userId);
}
