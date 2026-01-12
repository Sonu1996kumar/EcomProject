package com.app.ecom.controller;

import com.app.ecom.bo.User;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    //private List<User> userList = new ArrayList<>();
    //@Autowired
    private final UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    //    @GetMapping("/api/users")
//    public List<User> getAllUser(){
//        return userList;
//    }
//    @GetMapping("/api/users")
//    public List<User> getAllUser(){
//        return userService.fetchAllUser();
//    }
    /*@GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.fetchAllUser(), HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUser());
    }*/
    @GetMapping("/api/users")  //by using dto
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return new ResponseEntity<>(userService.fetchAllUser(), HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUser());
    }

   /* @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getAllUser(@PathVariable Long id){
//        User user = userService.fetchAllUser(id);
//        if(user==null)
//            return ResponseEntity.notFound().build();
//            return  ResponseEntity.ok(user);
        //using steram
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }*/

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getAllUser(@PathVariable Long id){
//        User user = userService.fetchAllUser(id);
//        if(user==null)
//            return ResponseEntity.notFound().build();
//            return  ResponseEntity.ok(user);
        //using steram
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }


//    @PostMapping("/api/users")
//    public List<User> createUser(@RequestBody User user){
//        userList.add(user);
//        return userList;
//    }
    /*@PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User Added Succesfully");
    }*/
    //using dto
@PostMapping("/api/users")
public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
    userService.addUser(userRequest);
    return ResponseEntity.ok("User Added Succesfully");
}

    /*@PutMapping("/api/users/{id}")
    public ResponseEntity<String> createUser(@RequestBody User updatedUser, @PathVariable Long id){
        boolean updated = userService.updateUser(updatedUser, id);
        if(updated)
        return ResponseEntity.ok("User Updated Succesfully");
        return ResponseEntity.notFound().build();
    }*/

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> createUser(@RequestBody UserRequest updatedUserRequest, @PathVariable Long id){
        boolean updated = userService.updateUser(updatedUserRequest, id);
        if(updated)
            return ResponseEntity.ok("User Updated Succesfully");
        return ResponseEntity.notFound().build();
    }
}
