package pl.piotrek.tenants.service;


import pl.piotrek.tenants.model.entity.User;

public interface UserService {
    User addUser(User user);

    Double getUserRating(Long id);

    User getUserByEmail(String s);

    User getUserById(Long id);
}
