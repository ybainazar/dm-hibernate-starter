package com.hibernate.service;

import com.hibernate.dao.UserRepository;
import com.hibernate.dto.UserCreateDto;
import com.hibernate.dto.UserReadDto;
import com.hibernate.entity.User;
import com.hibernate.mapper.Mapper;
import com.hibernate.mapper.UserCreateMapper;
import com.hibernate.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public Long create(UserCreateDto userCreateDto) {
        // validation
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var validationResult = validator.validate(userCreateDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        // map Dto to object
        var userEntity = userCreateMapper.mapFrom(userCreateDto);
        return userRepository.save(userEntity).getId();
    }
    @Transactional
    public Optional<UserReadDto> findById(Long id) {
        return findById(id, userReadMapper);
    }

    @Transactional
    public <T> Optional<T> findById(Long id, Mapper<User, T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), userRepository.getEntityManager().getEntityGraph("WithCompany")
        );
        return userRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
