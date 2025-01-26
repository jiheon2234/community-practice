package com.community.domain.user.dto.res;

import com.community.domain.user.entity.Provider;

public record SimpleUserRes(Long userId, Provider provider, String nickName) {
}
