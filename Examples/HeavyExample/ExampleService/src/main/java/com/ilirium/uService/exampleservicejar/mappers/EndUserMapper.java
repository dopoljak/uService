package com.ilirium.uService.exampleservicejar.mappers;

import com.example.demo.client.dto.EndUserDTO;
import com.example.demo.database.entities.EndUser;

import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author dpoljak
 */
@ApplicationScoped
public class EndUserMapper {

    public EndUserDTO map(EndUser endUser) {
        EndUserDTO dto = new EndUserDTO();
        dto.setId(endUser.getId());
        dto.setUsername(endUser.getUsername());
        dto.setPassword(endUser.getPassword());
        //dto.setEmail(email);
        return dto;
    }

    public Collection<EndUserDTO> map(Collection<EndUser> endUsers) {
        Collection<EndUserDTO> endUsersDTO = new ArrayList<>(endUsers.size());
        for (EndUser endUser : endUsers) {
            endUsersDTO.add(map(endUser));
        }
        return endUsersDTO;
    }

    public EndUser map(EndUserDTO endUserDTO) {
        EndUser endUser = new EndUser();
        if (endUserDTO.getId() != null) {
            endUser.setId(endUserDTO.getId());
        }
        endUser.setUsername(endUserDTO.getUsername());
        endUser.setPassword(endUserDTO.getPassword());
        return endUser;
    }

}
