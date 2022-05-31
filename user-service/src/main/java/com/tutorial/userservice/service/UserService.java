package com.tutorial.userservice.service;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.feignclients.ProductFeignClient;
import com.tutorial.userservice.model.Product;
import com.tutorial.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductFeignClient productFeignClient;

    
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        User userNew = userRepository.save(user);
        return userNew;
    }

    public List<Product> getCars(int userId) {
        List<Product> cars = restTemplate.getForObject("http://localhost:8002/car/byuser/" + userId, List.class);
        return cars;
    }

  

    public Product saveCar(int userId, Product car) {
        car.setUserId(userId);
        Product carNew = productFeignClient.save(car);
        return carNew;
    }


    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        List<Product> products = productFeignClient.getProducts(userId);
        if(products.isEmpty())
            result.put("Mensaje:", "ese user no tiene productos registrados");
        else
            result.put("Mensaje:", products);
        return result;
    }
}
