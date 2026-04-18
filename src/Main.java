import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 Inserir funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000,10,18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990,5,12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961,5,2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988,10,14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995,1,5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999,11,19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993,3,31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994,7,8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003,5,24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996,9,2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 Remover João
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.4 Aplicar aumento de 10%
        funcionarios.forEach(f ->
                f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")))
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 3.3 Imprimir funcionários
        System.out.println("\n--- Funcionários ---");
        funcionarios.forEach(f -> System.out.println(
                f.getNome() + " | " +
                        f.getDataNascimento().format(formatter) + " | " +
                        formatarSalario(f.getSalario()) + " | " +
                        f.getFuncao()
        ));

        // 3.5 Agrupar por função
        Map<String, List<Funcionario>> grupo =
                funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 Imprimir agrupados
        System.out.println("\n--- Por função ---");
        grupo.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });

        // 3.8 Aniversariantes mês 10 e 12
        System.out.println("\n--- Aniversariantes (Outubro e Dezembro) ---");
        funcionarios.stream()
                .filter(f -> {
                    int mes = f.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(f -> System.out.println(
                        f.getNome() + " - " + f.getDataNascimento().format(formatter)
                ));

        // 3.9 Mais velho
        Funcionario maisVelho = Collections.min(funcionarios,
                Comparator.comparing(Funcionario::getDataNascimento));

        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();

        System.out.println("\nMais velho: " + maisVelho.getNome() + " - " + idade + " anos");

        // 3.10 Ordem alfabética
        System.out.println("\n--- Ordem alfabética ---");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // 3.11 Total salários
        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("\nTotal salários: " + formatarSalario(total));

        // 3.12 Salários mínimos
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        System.out.println("\n--- Salários mínimos ---");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + ": " + qtd);
        });
    }

    private static String formatarSalario(BigDecimal valor) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }
}