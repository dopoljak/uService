package com.example.demo.database.repositories;

import com.example.demo.database.entities.EndUser;
import com.ilirium.database.templates.AbstractRepository;
import com.mysema.query.types.EntityPath;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import static com.example.demo.database.entities.QEndUser.endUser;

/**
 *
 * @author dpoljak
 */
@RequestScoped
public class EndUserRepository extends AbstractRepository<EndUser> {

    public Collection<EndUser> getAll() {
        Collection<EndUser> endUsers = entityManager.createNamedQuery("EndUser.findAll", EndUser.class).getResultList();
        return endUsers;
    }

    public EndUser findByUsername(String username) {
        return query()
                .from(endUser)
                .where(endUser.username.eq(username))
                .singleResult(endUser);
    }

    @Override
    protected EntityPath<EndUser> getQClass() {
        return endUser;
    }
}
