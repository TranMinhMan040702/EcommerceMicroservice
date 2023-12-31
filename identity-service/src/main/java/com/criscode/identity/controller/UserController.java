package com.criscode.identity.controller;

import com.criscode.clients.user.dto.AddressDto;
import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.clients.user.dto.UserResponse;
import com.criscode.identity.constants.ApplicationConstants;
import com.criscode.identity.dto.ResetPasswordRequest;
import com.criscode.identity.service.IAddressService;
import com.criscode.identity.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity-service/user/")
@CrossOrigin({"https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/"})
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IAddressService addressService;
    private final ObjectMapper objectMapper;

    @GetMapping("user-check/{userid}")
    public UserExistResponse existed(@PathVariable("userid") Integer userid) {
        return userService.checkUserExisted(userid);
    }

    @GetMapping("admin/users")
    public ResponseEntity<?> findAllUser(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE, required = false) Integer limit,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY, required = false) String sortBy,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(userService.findAll(page, limit, sortBy, search));
    }

    @GetMapping("admin/get-all-user")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOneByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findOneByUserId(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> updateUser(
            @RequestParam("model") String JsonObject,
            @RequestParam(name = "file", required = false) MultipartFile file) throws Exception {

        UserDto user = objectMapper.readValue(JsonObject, UserDto.class);
        return ResponseEntity.ok(userService.updateUser(user, file));
    }

    // Address
    @GetMapping("/addresses/{userId}")
    public ResponseEntity<?> findAddresses(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(addressService.findAddressByUserId(userId));
    }

    @PostMapping("/addresses")
    public ResponseEntity<?> uploadAddresses(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.saveAddressByUserId(addressDto));
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("addressId") Integer addressId) {
        return ResponseEntity.ok(addressService.deleteAddressById(addressId));
    }

    @GetMapping("/get-user/{userId}")
    public UserResponse getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(userService.resetPassword(request));
    }

    @PostMapping("/send-mail/forgot-password")
    public void forgotPassword(@RequestParam("email") String email) {
        userService.forgotPassword(email);
    }

    @PostMapping("/save/new-password")
    public ResponseEntity<?> saveNewPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(userService.saveNewPassword(resetPasswordRequest));
    }
}
