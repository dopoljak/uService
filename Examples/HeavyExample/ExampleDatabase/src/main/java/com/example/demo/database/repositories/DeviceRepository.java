package com.example.demo.database.repositories;

import com.example.demo.database.entities.Device;
import com.ilirium.database.commons.AbstractRepository;
import com.mysema.query.types.EntityPath;
import javax.enterprise.context.RequestScoped;
import static com.example.demo.database.entities.QDevice.device;

/**
 *
 * @author dpoljak
 */
@RequestScoped
public class DeviceRepository extends AbstractRepository<Device> {

    @Override
    protected EntityPath<Device> getQClass() {
        return device;
    }
}
