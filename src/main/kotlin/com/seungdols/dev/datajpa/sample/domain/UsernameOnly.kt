package com.seungdols.dev.datajpa.sample.domain

import org.springframework.beans.factory.annotation.Value

interface UsernameOnly {
    // open projections = 대상 객체를 다 가져와서, 조합해서 반환
    // close projections = 대상 객체의 필요한 것만 가져와서 반환
    @Value("#{target.username + ' ' + target.age}")
    fun getUsername(): String
}
