package com.portfolio.dio.facade;

import java.util.Map;

public class LocalCepApi implements CepApi {
    private final Map<String, Address> addresses = Map.of(
            "01001000", new Address("Praça da Sé", "São Paulo", "SP"),
            "20040002", new Address("Rua da Assembleia", "Rio de Janeiro", "RJ")
    );

    @Override
    public Address findAddress(String cep) {
        Address address = addresses.get(normalize(cep));
        if (address == null) {
            throw new IllegalArgumentException("CEP não encontrado no catálogo local: " + cep);
        }
        return address;
    }

    private String normalize(String cep) {
        return cep.replaceAll("\\D", "");
    }
}
