import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MainTest {

    @Test
    public void testInsuranceClientsByRUT() {
        Map<String, List<String>> expected = new HashMap<>();
        expected.put("SEGURO COMPLEMENTARIO DE SALUD", List.of(
            "94020190", //	ALEJANDRO ZELADA
            "86620855", //	DANIEL BUSTOS
            "73826497", //	ERNESTO GRANADO
            "88587715", //	JORDAN MARTINEZ
            "7317855K" //	NICOLAS PEREZ
        ));

        Map<String, List<String>> result = Main.insuranceClientsByRUT();

        assertEquals(expected.get("SEGURO COMPLEMENTARIO DE SALUD"), result.get("SEGURO COMPLEMENTARIO DE SALUD"));
    }

    @Test
    public void testHigherClientsBalances() {
        List<Integer> result = Main.higherClientsBalances();

        // Verificar que todos los saldos sean mayores a 30,000
        for (Integer balance : result) {
            assertTrue(balance > 30000);
        }

        // Verificar que los saldos estén en orden decreciente
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i) >= result.get(i + 1));
        }
    }

    @Test
    public void testInsuranceSortedByHighestBalance() {
        List<Integer> result = Main.insuranceSortedByHighestBalance();

        assertEquals(Arrays.asList(2, 1, 3), result); // Verifica que los IDs de los seguros se devuelvan en el orden correcto basado en el total de dinero administrado
    }
}

