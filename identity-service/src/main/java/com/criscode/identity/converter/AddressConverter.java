package com.criscode.identity.converter;

import com.criscode.exceptionutils.NotFoundException;
import com.criscode.identity.dto.AddressDto;
import com.criscode.identity.entity.Address;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddressConverter {

    private final UserRepository userRepository;

    public AddressDto mapToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        BeanUtils.copyProperties(address, addressDto);
        addressDto.setUserId(address.getUser().getId());
        return addressDto;
    }

    public List<AddressDto> mapToDto(List<Address> addresses) {
        return addresses.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Address mapToEntity(AddressDto addressDto) {
        User user = userRepository.findById(addressDto.getUserId()).orElseThrow(
                () -> new NotFoundException("User does not exist with id: " + addressDto.getUserId())
        );
        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        address.setUser(user);
        return address;
    }
}
