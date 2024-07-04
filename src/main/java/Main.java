import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import resources.Cliente;
import resources.Cuenta;
import resources.Seguro;

public class Main {

    private static  List<Cliente> clients = new ArrayList<>(List.of(
            new Cliente(1, "86620855", "DANIEL BUSTOS"),
            new Cliente(2, "7317855K", "NICOLAS PEREZ"),
            new Cliente(3, "73826497", "ERNESTO GRANADO"),
            new Cliente(4, "88587715", "JORDAN MARTINEZ"),
            new Cliente(5, "94020190", "ALEJANDRO ZELADA"),
            new Cliente(6, "99804238", "DENIS ROJAS")));

    private static List<Cuenta> accounts = new ArrayList<>(List.of(
            new Cuenta(6, 1, 15000),
            new Cuenta(1, 3, 18000),
            new Cuenta(5, 3, 135000),
            new Cuenta(2, 2, 5600),
            new Cuenta(3, 1, 23000),
            new Cuenta(5, 2, 15000),
            new Cuenta(3, 3, 45900),
            new Cuenta(2, 3, 19000),
            new Cuenta(4, 3, 51000),
            new Cuenta(5, 1, 89000),
            new Cuenta(1, 2, 1600),
            new Cuenta(5, 3, 37500),
            new Cuenta(6, 1, 19200),
            new Cuenta(2, 3, 10000),
            new Cuenta(3, 2, 5400),
            new Cuenta(3, 1, 9000),
            new Cuenta(4, 3, 13500),
            new Cuenta(2, 1, 38200),
            new Cuenta(5, 2, 17000),
            new Cuenta(1, 3, 1000),
            new Cuenta(5, 2, 600),
            new Cuenta(6, 1, 16200),
            new Cuenta(2, 2, 10000)));

    private static final List<Seguro> insurances = List.of(
            new Seguro(1, "SEGURO APV"),
            new Seguro(2, "SEGURO DE VIDA"),
            new Seguro(3, "SEGURO COMPLEMENTARIO DE SALUD"));

    // Método para listar los IDs de clientes
    public static List<Integer> listClientsIds() {
        try {
            List<Integer> listaID = new ArrayList<>();
            for (Cliente cliente : clients) {
                listaID.add(cliente.getId());
            }
            return listaID;
        } catch (Exception e) {
            System.err.println("Error al listar IDs de clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Método para listar los IDs de clientes ordenados por RUT
    public static List<Integer> listClientsIdsSortedByRUT() {
        try {
            List<Cliente> listaOrdenadaRut = new ArrayList<>(clients);
            listaOrdenadaRut.sort(Comparator.comparing(Cliente::getRut));

            List<Integer> listaID = new ArrayList<>();
            for (Cliente cliente : listaOrdenadaRut) {
                listaID.add(cliente.getId());
            }
            return listaID;
        } catch (Exception e) {
            System.err.println("Error al listar IDs de clientes ordenados por RUT: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Método para listar los nombres de clientes ordenados de mayor a menor por la
    // suma TOTAL de los saldos de cada cliente en los seguros que participa
    public static List<String> sortClientsTotalBalances() {
        try {
            List<Cuenta> SumaCuentas = new ArrayList<>();
            List<String> ListaNombres = new ArrayList<>();
            Integer suma = 0;

            for (Cliente cliente : clients) {
                Cuenta nuevaCuenta = new Cuenta(0, 0, 0);
                for (Cuenta cuenta : accounts) {
                    if (cuenta.getClientId() == cliente.getId()) {
                        suma += cuenta.getBalance();
                    }
                }

                nuevaCuenta.setClientId(cliente.getId());
                nuevaCuenta.setBalance(suma);
                SumaCuentas.add(nuevaCuenta);
                suma = 0;
            }
            SumaCuentas.sort(Comparator.comparing(Cuenta::getBalance));
            for (Cuenta cuenta : SumaCuentas) {
                for (Cliente cliente : clients) {
                    if (cuenta.getClientId() == cliente.getId()) {
                        ListaNombres.add(cliente.getName());
                    }
                }
            }

            return ListaNombres;
        } catch (Exception e) {
            System.err.println("Error al ordenar clientes por saldos totales: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Método para generar un objeto en que las claves sean los nombres de los
    // seguros y los valores un arreglo con los RUTs de sus clientes ordenados
    // alfabéticamente por nombre
    public static Map<String, List<String>> insuranceClientsByRUT() {
        try {
            List<Cliente> listaCliente = new ArrayList<>();
            Map<String, List<String>> seguros = new HashMap<>();

            for (Seguro seguro : insurances) {
                listaCliente.clear();
                for (Cuenta cuenta : accounts) {
                    if (seguro.getId() == cuenta.getInsuranceId()) {
                        for (Cliente cliente : clients) {
                            if (cuenta.getClientId() == cliente.getId()) {
                                if (!listaCliente.contains(cliente)) {
                                    listaCliente.add(cliente);
                                }
                            }
                        }
                    }
                }

                listaCliente.sort(Comparator.comparing(Cliente::getName));

                List<String> listaClienteOrdenado = new ArrayList<>();
                for (Cliente ClienteOrdenado : listaCliente) {
                    listaClienteOrdenado.add("" + ClienteOrdenado.getRut());
                }

                seguros.put(seguro.getName(), listaClienteOrdenado);
            }

            return seguros;
        } catch (Exception e) {
            System.err.println("Error al generar clientes por RUT en seguros: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Método para generar un arreglo ordenado decrecientemente con los saldos de
    // clientes que tengan más de 30.000 en el "Seguro APV"
    public static List<Integer> higherClientsBalances() {
        try {
            String nombre_seguro = "SEGURO APV";
            Integer Saldo = 30000;
            Integer suma = 0;
            List<Integer> ListSaldos = new ArrayList<>();

            for (Cliente cliente : clients) {
                for (Cuenta cuenta : accounts) {
                    if (cuenta.getClientId() == cliente.getId()) {
                        for (Seguro seguro : insurances) {
                            if ((cuenta.getInsuranceId() == seguro.getId()) & (seguro.getName().equals(nombre_seguro))) {
                                suma += cuenta.getBalance();
                            }
                        }
                    }
                }
                if (suma >= Saldo) {
                    ListSaldos.add(suma);
                }
                suma = 0;
            }
            ListSaldos.sort(Comparator.reverseOrder());

            return ListSaldos;
        } catch (Exception e) {
            System.err.println("Error al generar saldos altos de clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Método para generar un arreglo con IDs de los seguros ordenados
    // crecientemente por la cantidad TOTAL de dinero que administran
    public static List<Integer> insuranceSortedByHighestBalance() {
        try {
            Integer suma = 0;
            List<Integer> ListaId = new ArrayList<>();
            Map<Integer, Integer> ListaTotal = new HashMap<>();

            for (Seguro seguro : insurances) {
                for (Cuenta cuenta : accounts) {
                    if (seguro.getId() == cuenta.getInsuranceId()) {
                        suma += cuenta.getBalance();
                    }
                }
                ListaTotal.put(seguro.getId(), suma);
                suma = 0;
            }
            Map<Integer, Integer> OrdenaPorValor = ListaTotal.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Integer, Integer>comparingByValue()) // Orden creciente
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            OrdenaPorValor.forEach((key, value) -> ListaId.add(key));

            return ListaId;
        } catch (Exception e) {
            System.err.println("Error al ordenar seguros por balance más alto: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Método para generar un objeto en que las claves sean los nombres de los
    // Seguros y los valores el número de clientes que solo tengan cuentas en ese
    // seguro
    public static Map<String, Long> uniqueInsurance() {
        try {
            List<Integer> listaClienteID = new ArrayList<>();
            Map<String, Long> seguros = new HashMap<>();

            for (Seguro seguro : insurances) {
                listaClienteID.clear();
                for (Cuenta cuenta : accounts) {
                    if (seguro.getId() == cuenta.getInsuranceId()) {
                        for (Cliente cliente : clients) {
                            if (cuenta.getClientId() == cliente.getId()) {
                                if (!listaClienteID.contains(cliente.getId())) {
                                    listaClienteID.add(cliente.getId());
                                }
                            }
                        }
                    }
                }
                seguros.put(seguro.getName(), (long) listaClienteID.size());
            }
            return seguros;
        } catch (Exception e) {
            System.err.println("Error al generar mapa de seguros únicos: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Método para generar un objeto en que las claves sean los nombres de los
    // Seguros y los valores el ID de su cliente con menos fondos
    public static Map<String, Integer> clientWithLessFunds() {
        try {
            Map<String, Integer> seguros = new HashMap<>();
            Map<Integer, Map<Integer, Integer>> balancePorClienteYSeguro = new HashMap<>();

            // Calcular la suma de balances por cliente y seguro
            for (Cliente cliente : clients) {
                for (Seguro seguro : insurances) {
                    int suma = 0;
                    for (Cuenta cuenta : accounts) {
                        if (cuenta.getClientId() == cliente.getId() && seguro.getId() == cuenta.getInsuranceId()) {
                            suma += cuenta.getBalance();
                        }
                    }
                    if (suma > 0) { // Omitir las sumas de 0
                        balancePorClienteYSeguro
                                .computeIfAbsent(seguro.getId(), k -> new HashMap<>())
                                .put(cliente.getId(), suma);
                    }
                }
            }

            // Encontrar el cliente con la menor suma de balances para cada seguro
            for (Seguro seguro : insurances) {
                int minBalance = Integer.MAX_VALUE;
                int clienteConMenorBalance = -1;

                Map<Integer, Integer> balancesDeClientes = balancePorClienteYSeguro.get(seguro.getId());
                if (balancesDeClientes != null) {
                    for (Map.Entry<Integer, Integer> entry : balancesDeClientes.entrySet()) {
                        if (entry.getValue() < minBalance) {
                            minBalance = entry.getValue();
                            clienteConMenorBalance = entry.getKey();
                        }
                    }
                }

                if (clienteConMenorBalance != -1) { // Omitir seguros sin clientes con balances > 0
                    seguros.put(seguro.getName(), clienteConMenorBalance);
                }
            }

            return seguros;
        } catch (Exception e) {
            System.err.println("Error al encontrar cliente con menos fondos: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Método para agregar un nuevo cliente con datos ficticios y una cuenta en el
    // "SEGURO COMPLEMENTARIO DE SALUD" con un saldo de 15000 para este nuevo
    // cliente, luego devolver el lugar que ocupa este cliente en el ranking de la
    // pregunta 2
    public static int newClientRanking() {
        try {
            int Posicion = 0;

            Cliente NuevoCliente = new Cliente(clients.size() + 1, "12345678", "JUAN PEREZ");
            clients.add(NuevoCliente);

            Cuenta NuevaCuenta = new Cuenta(NuevoCliente.getId(), 3, 15000);
            accounts.add(NuevaCuenta);

            List<Integer> ListaClientes = listClientsIdsSortedByRUT();
            Posicion = ListaClientes.indexOf(NuevoCliente.getId());

            return Posicion + 1;
        } catch (Exception e) {
            System.err.println("Error al agregar nuevo cliente y encontrar ranking: " + e.getMessage());
            return -1;
        }
    }

    public static void main(String[] args) {
        // Crear una instancia de la clase principal
    
        System.out.println("IDs de clientes:");
        System.out.println(listClientsIds());
    
        System.out.println("\nIDs de clientes ordenados por RUT:");
        System.out.println(listClientsIdsSortedByRUT());
    
        System.out.println("\nNombres de clientes ordenados por saldo total:");
        System.out.println(sortClientsTotalBalances());
    
        System.out.println("\nClientes por RUT en seguros:");
        System.out.println(insuranceClientsByRUT());
    
        System.out.println("\nSaldos altos de clientes en SEGURO APV:");
        System.out.println(higherClientsBalances());
    
        System.out.println("\nSeguros ordenados por balance más alto:");
        System.out.println(insuranceSortedByHighestBalance());
    
        System.out.println("\nNúmero de clientes únicos por seguro:");
        System.out.println(uniqueInsurance());
    
        System.out.println("\nCliente con menos fondos por seguro:");
        System.out.println(clientWithLessFunds());
    
        System.out.println("\nRanking del nuevo cliente:");
        System.out.println(newClientRanking());
    }
    
}