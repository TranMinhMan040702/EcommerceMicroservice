package com.criscode.identity.controller;

import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.identity.dto.AddressDto;
import com.criscode.identity.service.AddressService;
import com.criscode.identity.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    @GetMapping("user-check/{userid}")
    public UserExistResponse existed(@PathVariable("userid") Integer userid) {
        return userService.checkUserExisted(userid);
    }

    // Address
    @GetMapping("users/addresses/{userId}")
    public ResponseEntity<?> findAddresses(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(addressService.findAddressByUserId(userId));
    }

    @PostMapping("users/addresses")
    public ResponseEntity<?> uploadAddresses(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.saveAddressByUserId(addressDto));
    }

    @PutMapping("users/addresses/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("addressId") Integer addressId) {
        return ResponseEntity.ok(addressService.deleteAddressById(addressId));
    }

}
