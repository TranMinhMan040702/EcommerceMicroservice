package com.criscode.identity.service.impl;

import com.criscode.clients.user.dto.AddressDto;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.identity.converter.AddressConverter;
import com.criscode.identity.entity.Address;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.AddressRepository;
import com.criscode.identity.repository.UserRepository;
import com.criscode.identity.service.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressConverter addressConverter;

    @Override
    public List<AddressDto> findAddressByUserId(Integer userId) {
        // todo: check user id with user current
        return addressConverter.mapToDto(userRepository.findById(userId).get().getAddresses());
    }

    @Override
    public List<AddressDto> saveAddressByUserId(AddressDto addressDto) {
        // todo: check user id with user current
        User user = userRepository.findById(addressDto.getUserId()).orElseThrow(
                () -> new NotFoundException("User does not exist with id: " + addressDto.getUserId())
        );
        List<Address> addresses = user.getAddresses();
        if (addressDto.getStatus() && !addresses.isEmpty()) {
            updateStatusAddress(user);
        }
        if (addresses.isEmpty()) {
            addressDto.setStatus(true);
        }
        for (Address address : addresses) {
            if (address.getId() == addressDto.getId()) {
                BeanUtils.copyProperties(addressDto, address, "createdAt", "createdBy", "id");
                addressRepository.save(address);
                return addressConverter.mapToDto(addresses);
            }
        }

        addresses.add(addressConverter.mapToEntity(addressDto));
        return addressConverter.mapToDto(userRepository.save(user).getAddresses());
    }

    @Override
    public void updateStatusAddress(User user) {
        Address address = addressRepository.findByStatusAndUser(true, user);
        if (address != null) {
            address.setStatus(false);
            addressRepository.save(address);
        }
    }

    @Override
    public List<AddressDto> deleteAddressById(Integer addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new NotFoundException("Address does not exist with id: " + addressId)
        );
        User user = address.getUser();
        if (address.getStatus()) {
            return addressConverter.mapToDto(userRepository.findById(user.getId()).get().getAddresses());
        }
        user.getAddresses().remove(address);
        userRepository.save(user);
        return addressConverter.mapToDto(userRepository.findById(user.getId()).get().getAddresses());
    }

}
