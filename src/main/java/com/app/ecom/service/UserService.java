package com.app.ecom.service;

import com.app.ecom.bo.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    //private List<User> userList = new ArrayList<>();
    private final UserRepository userRepository;
    Long nextId = 1L;

    public List<User> fetchAllUser(){

        //return userList;
        return userRepository.findAll();
    }


//    public List<User> addUser(User user){
//        userList.add(user);
//        return userList;
//    }
    public void addUser(User user){
//        user.setId(nextId++);
//        userList.add(user);
        userRepository.save(user);
    }

//    public User fetchAllUser(Long id) {
//        for(User user: userList){
//         if(user.getId().equals(id))
//             return user;
//        }
//        return  null;
//    }
//}

    public Optional<User> fetchUser(Long id) {
        //return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
        return userRepository.findById(id);
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

    public boolean updateUser(User user, Long id) {
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
    }
}
