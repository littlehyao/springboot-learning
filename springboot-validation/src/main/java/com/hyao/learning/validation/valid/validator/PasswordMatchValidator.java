package com.hyao.learning.validation.valid.validator;

import com.hyao.learning.validation.model.UserRegister;
import com.hyao.learning.validation.valid.annotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegister> {
    @Override
    public boolean isValid(UserRegister userRegister, ConstraintValidatorContext context) {
        if (userRegister == null) {
            return true;
        }
        String password = userRegister.getPassword();
        String confirmPassword = userRegister.getConfirmPassword();
        if (password == null || confirmPassword == null) {
            return true;
        }
        if (password.equals(confirmPassword)) {
            return true;
        }
        // 手动构建错误, 将默认的类级别的错误改成 confirmPassword 字段级别的错误
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("confirmPassword")
                .addConstraintViolation();
        return false;
    }
}
