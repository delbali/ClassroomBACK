package it.itresources.springtut.springtutorial.mapper;

import java.util.List;
import java.util.stream.Collectors;

import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.model.User;
import it.itresources.springtut.springtutorial.model.UserDetailImpl;

public class UserMapper {
    
    public static User entityToDto(UserEntity entity) {
        if (entity == null) {
            return new User();
        }
        List<String> roles = entity.getRoles().stream().map(i -> i.getName()).collect(Collectors.toList());
        return new User(entity.getId(), entity.getUsername(), roles);
    }

    public static User userDetailToDto(UserDetailImpl userDetail) {
        if (userDetail == null) {
            return null;
        }

        List<String> roles = userDetail.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());

        User user = new User(userDetail.getId(), userDetail.getUsername(), roles);

        return user;
    }


}
