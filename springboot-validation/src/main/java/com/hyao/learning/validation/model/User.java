package com.hyao.learning.validation.model;

import com.hyao.learning.validation.valid.group.CreateGroup;
import com.hyao.learning.validation.valid.group.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class User {
    @Null(groups = CreateGroup.class)
    @NotBlank(groups = UpdateGroup.class)
    private String id;
}
