package com.example.clear_solutions_test_assignment.util.mapper;

import com.example.clear_solutions_test_assignment.dto.UserDto;
import com.example.clear_solutions_test_assignment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends Mappable<User, UserDto> {

}
