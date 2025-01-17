package com.nexym.clinic.infra.user.repository;

import com.nexym.clinic.domain.user.model.User;
import com.nexym.clinic.domain.user.port.UserPersistence;
import com.nexym.clinic.infra.user.dao.UserDao;
import com.nexym.clinic.infra.user.mapper.UserEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRepository implements UserPersistence {

    private final UserDao userDao;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> getUserById(Long userId) {
        return userDao.findById(userId).map(userEntityMapper::mapToModel);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.findByEmail(email).map(userEntityMapper::mapToModel);
    }

    @Override
    public Long registerUser(User user) {
        var savedUser = userDao.save(userEntityMapper.mapToEntity(user));
        return savedUser.getId();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public List<User> getUserList() {
        var userEntityList = userDao.findAll();
        return userEntityMapper.mapToModelList(userEntityList);
    }
}
