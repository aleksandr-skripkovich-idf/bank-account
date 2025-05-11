package com.interview.aleksandr.skripkovich.model.request

import com.interview.aleksandr.skripkovich.util.EmailUtil
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class EmailUpdateRequest(
    @field:NotBlank(message = EmailUtil.NOT_BLANK)
    @field:Pattern(
        regexp = EmailUtil.EMAIL_REGEXP,
        message = EmailUtil.WRONG_FORMAT
    )
    val newEmail: String,
    @field:NotBlank(message = EmailUtil.NOT_BLANK)
    @field:Pattern(
        regexp = EmailUtil.EMAIL_REGEXP,
        message = EmailUtil.WRONG_FORMAT
    )
    val oldEmail: String,
)
