package com.krish.practice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
public class UserDto {

    private Integer id;
    private String name;
    private String email;

}
