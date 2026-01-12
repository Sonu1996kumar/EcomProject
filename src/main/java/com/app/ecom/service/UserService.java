package com.app.ecom.service;

import com.app.ecom.bo.Address;
import com.app.ecom.bo.User;
import com.app.ecom.dto.AddressDto;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    //private List<User> userList = new ArrayList<>();
    private final UserRepository userRepository;
    Long nextId = 1L;

    /*public List<User> fetchAllUser(){

        //return userList;
        return userRepository.findAll();
    }*/
    public List<UserResponse> fetchAllUser(){

        //return userList;
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }


//    public List<User> addUser(User user){
//        userList.add(user);
//        return userList;
//    }
    /*public void addUser(User user){
//        user.setId(nextId++);
//        userList.add(user);
        userRepository.save(user);
    }*/
    //using dto
public void addUser(UserRequest userRequest){
//        user.setId(nextId++);
//        userList.add(user);
    //User user =mapToUser(userRequest);  this is added by my logic
    User user = new User();
    updateUserFromRequest(user, userRequest);
    userRepository.save(user);
}

    private void updateUserFromRequest(User user, UserRequest request) {

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        //user.setRole(request.getRole());

        if (request.getAddress() != null) {

            Address address = new Address();

            address.setCountry(request.getAddress().getCountry());
            address.setState(request.getAddress().getState());
            address.setCity(request.getAddress().getCity());
            address.setStreet(request.getAddress().getStreet());
            address.setZipcode(request.getAddress().getZipcode());

            user.setAddress(address);
        }
    }


//    public User fetchAllUser(Long id) {
//        for(User user: userList){
//         if(user.getId().equals(id))
//             return user;
//        }
//        return  null;
//    }
//}

    /*public Optional<User> fetchUser(Long id) {
        //return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
        return userRepository.findById(id);
    }*/
    public Optional<UserResponse> fetchUser(Long id) {
        //return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

//    public User updateUser(User user, Long id) {
//        for(User existinguser:userList){
//        if(existinguser.getId().equals(id)){
//            existinguser.setFirstName(user.getFirstName());
//            existinguser.setLastName(user.getLastName());
//            return existinguser;
//        }
//        }
//        return null;
//    }

    /*public boolean updateUser(User user, Long id) {
//        return userList.stream()
//                .filter(user1 -> user1.getId().equals(id))
//                .findFirst().map(existingUser->{
//                    existingUser.setFirstName(user.getFirstName());
//                    existingUser.setLastName(user.getLastName());
//                    return true;
//                }).orElse(false);
        return userRepository.findById(id)
                .map(existingUser->{
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }*/

    public boolean updateUser(UserRequest updateUserRequest, Long id) {
//        return userList.stream()
//                .filter(user1 -> user1.getId().equals(id))
//                .findFirst().map(existingUser->{
//                    existingUser.setFirstName(user.getFirstName());
//                    existingUser.setLastName(user.getLastName());
//                    return true;
//                }).orElse(false);
        return userRepository.findById(id)
                .map(existingUser->{
                    updateUserFromRequest(existingUser, updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private User mapToUser(UserRequest request) {
        User user = new User();

        // If you use this for update, set ID also
        // user.setId(request.getId());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        //user.setRole(request.getRole());

        if (request.getAddress() != null) {
            Address address = new Address();
            address.setCountry(request.getAddress().getCountry());
            address.setState(request.getAddress().getState());
            address.setCity(request.getAddress().getCity());
            address.setStreet(request.getAddress().getStreet());
            address.setZipcode(request.getAddress().getZipcode());

            user.setAddress(address);
        }

        return user;
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        //response.setRole(user.getRole());

        if(user.getAddress() !=null){
            AddressDto addressDto = new AddressDto();
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDto);
        }
        return response;
    }
}
