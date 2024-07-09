package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.dto.request.*;
import com.example.EasyWalletApplication.dto.response.CreateWalletResponse;
import com.example.EasyWalletApplication.dto.response.PerformTransactionResponse;
import com.example.EasyWalletApplication.dto.response.ProfileResponse;
import com.example.EasyWalletApplication.exceptions.AccountAlreadyExist;
import com.example.EasyWalletApplication.exceptions.AuthorizationException;
import com.example.EasyWalletApplication.exceptions.InvalidTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Test
    public void testThatCreateAccountCreateAndReturnsAResponse() throws AccountAlreadyExist {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setEmail("olawale@gmail.com");
        request.setFirstName("olawale");
        request.setLastName("ogunbiyi");
        request.setPhoneNumber("08129810794");
        request.setPin("1234");
        CreateWalletResponse response = walletService.createAccount(request);
        assertNotNull(response);
        assertThat(response.getAccountNumber()).isEqualTo(request.getPhoneNumber());
    }

    @Test
    public void testThatCreateAccountGetCreatedAndTheCountOfUserIncreaseByOne() throws AccountAlreadyExist {
        int numberOfAccounts = walletService.findAllAccounts().size();
        CreateAccountRequest request = new CreateAccountRequest();
        request.setEmail("olawale@gmail.com");
        request.setFirstName("olawale");
        request.setLastName("ogunbiyi");
        request.setPhoneNumber("08129810794");
        request.setPin("1234");
        CreateWalletResponse response = walletService.createAccount(request);
        assertNotNull(response);
        assertThat(response.getAccountNumber()).isEqualTo(request.getPhoneNumber());
        assertThat(walletService.findAllAccounts().size()).isGreaterThan(numberOfAccounts);
    }

    @Test
    @Sql(scripts = "/script/insert.sql")
    public void testThatAccountWhichAlreadyExistBasedOnThePhoneNumberCanNotBeCreatedTwice(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setEmail("olawale@gmail.com");
        request.setFirstName("olawale");
        request.setLastName("ogunbiyi");
        request.setPhoneNumber("08032389457");
        request.setPin("1234");
        assertThrows(AccountAlreadyExist.class, () -> walletService.createAccount(request));
    }

    @Test
    @Sql(scripts = "/script/insert.sql")
    public void testThatAccountWhichAlreadyExistBasedOnTheEmailCanNotBeCreatedTwice(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setEmail("oluwatobi@gmail.com");
        request.setFirstName("olawale");
        request.setLastName("ogunbiyi");
        request.setPhoneNumber("08032389456");
        request.setPin("1234");
        assertThrows(AccountAlreadyExist.class, () -> walletService.createAccount(request));
    }

    @Test
    public void testThatAccountBeenCreatedAProfileIsCreatedForSuchPerson() throws AccountAlreadyExist, AuthorizationException {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setEmail("ojot630@gmail.com");
        request.setFirstName("olawale");
        request.setLastName("ogunbiyi");
        request.setPhoneNumber("08032389456");
        request.setPin("1234");
        CreateWalletResponse response = walletService.createAccount(request);
        assertThat(response).isNotNull();
        assertThat(response.getAccountNumber()).isEqualTo(request.getPhoneNumber());
        ProfileResponse profileResponse = walletService.getProfile(response.getAccountNumber(), request.getPin());
        assertThat(profileResponse).isNotNull();
        assertThat(profileResponse.getEmail()).isEqualTo(request.getEmail());
        assertThat(profileResponse.getPhoneNumber()).isEqualTo(request.getPhoneNumber());
    }

    @Test
    @Sql("/script/insert.sql")
    public void testThatAClientCanPerformATransactionToAClient() throws AccountAlreadyExist, InvalidTransaction {
        PerformTransactionRequest request = new PerformTransactionRequest();
        request.setAmount(BigDecimal.valueOf(1000));
        request.setDescription("Buy food");
        request.setAccount_number("08032389457");
        request.setCurrency_change("NGN");
        request.setRecipient_name("Ola");
        request.setPayment_means("Paystack");
        PerformTransactionResponse response = walletService.performTransaction(request);
        System.out.println(response);
        assertNotNull(response);
        assertThat(response.getUrl()).isNotNull();
        assertThat(response.getMessage()).isNotNull();
    }

    @Test
    @Sql("/script/insert.sql")
    public void testThatATransactionCanBeUpdatedAndAlsoTheAccountBalanceCanBeUpdatedAlsoIfSuccessful() throws AccountAlreadyExist, InvalidTransaction, AuthorizationException {
        ProfileResponse profileResponse = walletService.getProfile("08032389457", "1234");
        PayStackFundWalletRequest request = new PayStackFundWalletRequest();
        PayStackData data = new PayStackData();
        data.setReference("e558ab7c-d536-45ac-9209-7c5b43cded7c");
        request.setData(data);
        request.setEvent("charge.success");
        walletService.fundWallet(new FundWalletRequest(request));
        assertThat(walletService.getProfile("08032389457", "1234").getAmount()).isGreaterThan(profileResponse.getAmount());
    }


}
