package com.interview.aleksandr.skripkovich.model.request

import com.interview.aleksandr.skripkovich.util.PhoneUtil
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class PhoneDeleteRequest(
    @field:NotBlank(message = PhoneUtil.NOT_BLANK)
    @field:Pattern(
        regexp = PhoneUtil.PHONE_REGEXP,
        message = PhoneUtil.WRONG_FORMAT
    )
    @field:Max(
        value = 11,
        message = PhoneUtil.WRONG_FORMAT
    )
    val phone: String,
)
