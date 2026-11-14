package com.hyao.learning.validation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class SampleRequestDTO {

    @Positive(message = "整数必须为正数")
    @Max(value = 150, message = "整数不能大于150")
    private Integer intValue;

    @PositiveOrZero(message = "小数必须为非负数")
    @DecimalMax(value = "100.00", message = "小数不能大于100.00")
    private BigDecimal decimalValue;

    @NotBlank(message = "名称不能为空")
    @Size(min = 2, max = 10, message = "名称长度在2到10个字符之间")
    @Pattern(regexp = "^\\W+$", message = "字符串必须为非单词字符")
    private String stringValue;

    @Email(message = "邮箱格式不正确")
    private String emailValue;

    @NotNull(message = "日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Past(message = "日期必须是过去的时间")
    private LocalDate dateValue;

    @NotEmpty(message = "列表不能为空")
    @Size(min = 1, max = 5, message = "列表长度在1到5之间")
    private List<@NotBlank(message = "列表元素不能为空") String> list;

    @NotEmpty(message = "映射不能为空")
    @Size(min = 1, max = 5, message = "映射长度在1到5之间")
    private Map<@NotBlank String, @NotNull @Valid User> map;

    private LocalDate beginDate;

    private LocalDate endDate;

    @AssertTrue(message = "日期范围不正确")
    public boolean isDateRangeValid() {
        return beginDate == null || endDate == null || !beginDate.isAfter(endDate);
    }
}
