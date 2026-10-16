package com.portfolio.dio.facade;

import java.util.Objects;

/** Interface simples que esconde a colaboração entre dois subsistemas. */
public class CustomerMigrationFacade {
    private final CrmService crmService;
    private final CepApi cepApi;

    public CustomerMigrationFacade(CrmService crmService, CepApi cepApi) {
        this.crmService = Objects.requireNonNull(crmService);
        this.cepApi = Objects.requireNonNull(cepApi);
    }

    public CrmService.CustomerProfile migrate(String name, String cep) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        }
        Address address = cepApi.findAddress(cep);
        return crmService.register(name.trim(), cep, address);
    }
}
