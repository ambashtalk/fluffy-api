package com.ambashtalk.devops.services;

import com.ambashtalk.devops.models.Person;
import com.ambashtalk.devops.models.PersonDetails;
import com.ambashtalk.devops.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonDetailService implements UserDetailsService {
    private final PersonRepository personRepository;

    public PersonDetailService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public PersonDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        }

        return PersonDetails.build(person);
    }
}
