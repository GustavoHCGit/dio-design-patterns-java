package com.portfolio.dio;

import com.portfolio.dio.facade.CepApi;
import com.portfolio.dio.facade.CrmService;
import com.portfolio.dio.facade.CustomerMigrationFacade;
import com.portfolio.dio.facade.LocalCepApi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FacadeTest {
    @Test
    void migrationCoordinatesCepLookupAndCrmRegistration() {
        CrmService crm = new CrmService();
        CepApi cepApi = new LocalCepApi();
        CustomerMigrationFacade facade = new CustomerMigrationFacade(crm, cepApi);

        CrmService.CustomerProfile result = facade.migrate("  Ana Silva  ", "01001-000");

        assertEquals("Ana Silva", result.name());
        assertEquals("São Paulo", result.address().city());
        assertEquals(1, crm.findAll().size());
    }

    @Test
    void unknownCepFailsWithoutRegisteringCustomer() {
        CrmService crm = new CrmService();
        CustomerMigrationFacade facade = new CustomerMigrationFacade(crm, new LocalCepApi());

        assertThrows(IllegalArgumentException.class, () -> facade.migrate("Ana Silva", "99999999"));
        assertEquals(0, crm.findAll().size());
    }
}
