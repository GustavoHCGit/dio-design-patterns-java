package com.portfolio.dio.facade;

import java.util.ArrayList;
import java.util.List;

public class CrmService {
    private final List<CustomerProfile> customers = new ArrayList<>();

    public CustomerProfile register(String name, String cep, Address address) {
        CustomerProfile profile = new CustomerProfile(name, cep, address);
        customers.add(profile);
        return profile;
    }

    public List<CustomerProfile> findAll() {
        return List.copyOf(customers);
    }

    public record CustomerProfile(String name, String cep, Address address) {
    }
}
