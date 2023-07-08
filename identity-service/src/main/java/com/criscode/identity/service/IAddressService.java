package com.criscode.identity.service;

import com.criscode.clients.user.dto.AddressDto;
import com.criscode.identity.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAddressService {
    List<AddressDto> findAddressByUserId(Integer userId);

    List<AddressDto> saveAddressByUserId(AddressDto addressDto);

    void updateStatusAddress(User user);

    List<AddressDto> deleteAddressById(Integer addressId);
}
