package com.interview.aleksandr.skripkovich.model.request

import com.interview.aleksandr.skripkovich.util.PhoneUtil
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class PhoneUpdateRequest(
    @field:NotBlank(message = PhoneUtil.NOT_BLANK)
    @field:Max(
        value = 11,
        message = PhoneUtil.WRONG_FORMAT
    )
    @field:Pattern(
        regexp = PhoneUtil.PHONE_REGEXP,
        message = PhoneUtil.WRONG_FORMAT,
    )
    val newPhone: String,
    @field:NotBlank(message = PhoneUtil.NOT_BLANK)
    @field:Max(
        value = 11,
        message = PhoneUtil.WRONG_FORMAT
    )
    val oldPhone: String,
)
