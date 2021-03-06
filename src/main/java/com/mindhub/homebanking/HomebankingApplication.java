package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //Creación de Clients
        Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
        Client client2 = new Client("Nicolás", "Nahuelvil", "nicolasnahuelvil@gmail.com", passwordEncoder.encode("nicolas123"));
        clientRepository.save(client1);
        clientRepository.save(client2);
        //Creación de cuentas bancarias
        Account account1 = new Account("VIN-001", LocalDate.now(), 5000, client1);
        Account account2 = new Account("VIN-002", LocalDate.now(), 7500, client2);
        Account account3 = new Account("VIN-003", LocalDate.now(), 7500, client1);
        Account account4 = new Account("VIN-004", LocalDate.now(), 7500, client1);
        //Se guardan los clientes
        accountRepository.save(account1);
        accountRepository.save(account2);
        accountRepository.save(account3);
        accountRepository.save(account4);
        //Creación de el objeto cliente
        Transaction transaction1 = new Transaction(TransactionType.DEBIT,-5000,"Transacción realizada", LocalDate.now(), account1);
        Transaction transaction2 = new Transaction(TransactionType.CREDIT, 7000,"Transacción realizada", LocalDate.now(), account2);
        Transaction transaction3 = new Transaction(TransactionType.CREDIT, 7000,"Transacción realizada", LocalDate.now(), account1);
        Transaction transaction4 = new Transaction(TransactionType.DEBIT, -5562,"Transacción realizada", LocalDate.now(), account4);
        //Se guardan las transacciones
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);
        transactionRepository.save(transaction4);
        //Se crean los prestamos
        Loan loan1 = new Loan("Hipotecario",500000, List.of(12,24,36,48,60));
        Loan loan2 = new Loan("Personal",100000, List.of(6,12,24));
        Loan loan3 = new Loan("Automotriz",300000, List.of(6,12,24,36));
        //Se guardan los prestamos
        loanRepository.save(loan1);
        loanRepository.save(loan2);
        loanRepository.save(loan3);
        //Crear ClientLoan
        ClientLoan clientLoan1 = new ClientLoan(400000,60,client1,loan1);// <---- Client1 Melva
        ClientLoan clientLoan2 = new ClientLoan(500000,12,client1,loan2);// <---- Client1 Melva
        ClientLoan clientLoan3 = new ClientLoan(100000,60,client2,loan2);// <---- Client1 Nicolas
        ClientLoan clientLoan4 = new ClientLoan(200000,12,client2,loan3);// <---- Client1 Nicolas
        //Se guarda los prestamos en los clientes correspondientes
        clientLoanRepository.save(clientLoan1);
        clientLoanRepository.save(clientLoan2);
        clientLoanRepository.save(clientLoan3);
        clientLoanRepository.save(clientLoan4);
        //Crear CARDS
        Card card1 = new Card(client1.getFirstName()+" "+client1.getLastName(), CardType.DEBIT, CardColor.GOLD, "4352-7543-9453-4512", "254", LocalDate.now(), LocalDate.now().plusYears(5), client1);
        Card card2 = new Card(client1.getFirstName()+" "+client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "8785-6354-9971-8412", "331", LocalDate.now(), LocalDate.now().plusYears(5), client1);
        Card card3 = new Card(client2.getFirstName()+" "+client2.getLastName(), CardType.CREDIT, CardColor.SILVER, "8876-4521-9654-8541", "945", LocalDate.now(), LocalDate.now().plusYears(5), client2);
        cardRepository.save(card1);
        cardRepository.save(card2);
        cardRepository.save(card3);

    }


}