package com.api.project.controller

import com.api.project.controller.dto.request.LoginRequest
import com.api.project.controller.dto.request.SignUpRequest
import com.api.project.controller.dto.response.JwtResponse
import com.api.project.controller.dto.response.MessageResponse
import com.api.project.models.RoleModel
import com.api.project.models.UserModel
import com.api.project.models.enum.ERole
import com.api.project.repository.user.RoleRepository
import com.api.project.repository.user.UserRepository
import com.api.project.service.jwt.JwtUtils
import com.api.project.service.jwt.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class AuthController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @RequestMapping(method = [RequestMethod.POST], value = ["/signin"])
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils.generateJwtToken(authentication)
        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
        val roles: List<String> = userDetails.authorities.stream()
            .map { item -> item.authority }
            .collect(Collectors.toList())
        return JwtResponse(
            jwt,
            userDetails.username,
            userDetails.email,
            roles
        )
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/signup"])
    fun registerUser(@RequestBody signUpRequest: @Valid SignUpRequest): ResponseEntity<*> {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user = UserModel(
            username = signUpRequest.username,
            email = signUpRequest.email,
            password = encoder.encode(signUpRequest.password)
        )
        val strRoles: Set<String> = signUpRequest.roles
        val roles: MutableSet<RoleModel> = HashSet()
        strRoles.forEach(Consumer { role: String? ->
            when (role) {
                "mod" -> {
                    val modRole: RoleModel? = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    if (modRole != null) {
                        roles.add(modRole)
                    }
                }
                else -> {
                    val userRole: RoleModel? = roleRepository.findByName(ERole.ROLE_USER)
                    if (userRole != null) {
                        roles.add(userRole)
                    }
                }
            }
        })
        user.roles = roles
        userRepository.save(user)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/signup/admin"])
    @PreAuthorize("hasRole('ADMIN')")
    fun registerAdmin(@RequestBody signUpRequest: @Valid SignUpRequest): ResponseEntity<*> {

        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Email is already in use!"))
        }

        // Create new admin's account
        val admin = UserModel(
            username = signUpRequest.username,
            email = signUpRequest.email,
            password = encoder.encode(signUpRequest.password)
        )

        val roles: MutableSet<RoleModel> = HashSet()

        val adminRole: RoleModel? = roleRepository.findByName(ERole.ROLE_ADMIN)
        val modRole: RoleModel? = roleRepository.findByName(ERole.ROLE_MODERATOR)
        val userRole: RoleModel? = roleRepository.findByName(ERole.ROLE_USER)

        if (adminRole != null) {
            roles.add(adminRole)
        }
        if (modRole != null) {
            roles.add(modRole)
        }

        if (userRole != null) {
            roles.add(userRole)
        }


        admin.roles = roles
        userRepository.save(admin)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }
}