package pl.piotrek.tenants.service;


import pl.piotrek.tenants.model.entity.User;

public interface UserService {
    User addUser(User user);

    Double getRating(Long id);

    User getByEmail(String s);
}
