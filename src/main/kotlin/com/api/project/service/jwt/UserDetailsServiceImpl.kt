package com.api.project.service.jwt

import com.api.project.models.UserModel
import com.api.project.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetailsImpl {
        val user: UserModel = userRepository.findByUsername(username).orElseThrow {
            UsernameNotFoundException(
                "User Not Found with username: $username"
            )
        }
        return UserDetailsImpl.build(user)
    }

}