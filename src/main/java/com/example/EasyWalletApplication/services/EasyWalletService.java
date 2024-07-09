package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.config.BeanConfig;
import com.example.EasyWalletApplication.data.models.Account;
import com.example.EasyWalletApplication.data.models.Profile;
import com.example.EasyWalletApplication.data.models.Status;
import com.example.EasyWalletApplication.data.models.Transaction;
import com.example.EasyWalletApplication.data.repositories.WalletAccountRepository;
import com.example.EasyWalletApplication.dto.request.*;
import com.example.EasyWalletApplication.dto.response.*;
import com.example.EasyWalletApplication.exceptions.AccountAlreadyExist;
import com.example.EasyWalletApplication.exceptions.AuthorizationException;
import com.example.EasyWalletApplication.exceptions.InvalidTransaction;
import com.example.EasyWalletApplication.util.ApiUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.EasyWalletApplication.util.ApiUtil.*;

@Service
public class EasyWalletService implements WalletService {

    @Autowired
    private WalletAccountRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProfileService profileService;
    @Autowired
    @Qualifier("monnifyPayment")
    private PaymentService monnifyPaymentService;
    @Autowired
    @Qualifier("payStackService")
    private PaymentService paystackPaymentService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BeanConfig beanConfig;

    @Override
    public CreateWalletResponse createAccount(CreateAccountRequest request) throws AccountAlreadyExist {
        Profile profile = profileService.createProfile(modelMapper.map(request, CreateProfileRequest.class));
        Account newAccount = new Account();
        newAccount.setAccountNumber(request.getPhoneNumber());
        newAccount.setAccountName(request.getFirstName() +" "+ request.getLastName());
        newAccount.setPin(request.getPin());
        newAccount.setProfile(profile);
        Account savedAccount = repository.save(newAccount);
        CreateWalletResponse response = new CreateWalletResponse();
        response.setMessage(ACCOUNT_CREATED_SUCCESSFULLY);
        response.setAccountNumber(savedAccount.getAccountNumber());
        return response;
    }


    @Override
    public List<WalletAccountResponse> findAllAccounts() {
        if (repository.findAll().isEmpty()) return new ArrayList<>();
        return repository
                .findAll()
                .stream()
                .map(account -> modelMapper.map(account, WalletAccountResponse.class))
                .toList();
    }

    @Override
    public ProfileResponse getProfile(String accountNumber, String pin) throws AccountAlreadyExist, AuthorizationException {
        Account account = findAccount(accountNumber);
        if (!account.getPin().equals(pin)) throw new AuthorizationException(AUTHORIZATION_MESSAGE);
        return new ProfileResponse(account);
    }

    @Override
    public PerformTransactionResponse performTransaction(PerformTransactionRequest request) throws AccountAlreadyExist, InvalidTransaction {
        Account account = findAccount(request.getAccount_number());
        boolean isAmountInValid = request.getAmount().compareTo(BigDecimal.valueOf(20)) < 0;
        if (isAmountInValid) throw new InvalidTransaction(AMOUNT_LESS_THAN_FIVE);
        request.setPayment_means(request.getPayment_means().toUpperCase());
        if (!request.getPayment_means().equals(MONNIFY) && !request.getPayment_means().equals(ApiUtil.PAYSTACK)) throw new InvalidTransaction(TRANSACTION_MEANS_NOT_EXIST);
        PerformTransactionResponse response = new PerformTransactionResponse();
        CreateTransactionResponse transactionResponse = transactionService.createTransaction(new CreateTransactionRequest(request, account));
        if (request.getPayment_means().equals(ApiUtil.PAYSTACK)){
            fundPayStackWallet(request, transactionResponse, account, response);
        }else {
            fundMonnifyWallet(request, account, transactionResponse, response);
        }
        response.setMessage(TRANSACTION_SUCCESSFULLY);
        return response;
    }

    private void fundMonnifyWallet(PerformTransactionRequest request, Account account, CreateTransactionResponse transactionResponse, PerformTransactionResponse response) {
        InitializePayment<MonnifyInitializePayment> monnifyPayment = new InitializePayment<>();
        monnifyPayment.setData(createMonnifyRequest(request, account.getAccountName(), account.getProfile().getEmail(), transactionResponse.getId()));
        InitializePaymentResponse initializePaymentResponse = monnifyPaymentService.initializeTransaction(monnifyPayment);
        response.setUrl(initializePaymentResponse.getUrl());
    }

    private void fundPayStackWallet(PerformTransactionRequest request, CreateTransactionResponse transactionResponse, Account account, PerformTransactionResponse response) {
        request.setAmount(request.getAmount().multiply(BigDecimal.valueOf(100)));
        InitializePayment<PaystackInitializePayment> initializePayment = new InitializePayment<>();
        initializePayment.setData(createPayStackRequest(request.getAmount(), transactionResponse.getId(), account.getProfile().getEmail()));
        InitializePaymentResponse initializePaymentResponse = paystackPaymentService.initializeTransaction(initializePayment);
        response.setUrl(initializePaymentResponse.getUrl());
    }

    @Async
    @Override
    public void fundWallet(FundWalletRequest request) throws InvalidTransaction {
        if (request.getStatus().equals(PAYSTACK_SUCCESS) || request.getStatus().equals(MONNIFY_SUCCESS)) {
            Transaction transaction = transactionService.updateTransaction(request.getReference(), Status.SUCCESSFUL);
            Account account = transaction.getAccount();
            account.setAccountBalance(account.getAccountBalance().add(transaction.getAmount()));
            repository.save(account);
        }else {
            transactionService.updateTransaction(request.getReference(), Status.FAILED);
        }
    }

    @Override
    public List<TransactionResponse> findAllTrasanctions(String accountNumber, String pin) throws AccountAlreadyExist, AuthorizationException {
        Account account = findAccount(accountNumber);
        if (!account.getPin().equals(pin)) throw new AuthorizationException(AUTHORIZATION_MESSAGE);
        return transactionService.findAllTransactionByAccount(account);
    }

    private MonnifyInitializePayment createMonnifyRequest(PerformTransactionRequest request, String name, String email, String reference) {
        MonnifyInitializePayment monnifyPayment = new MonnifyInitializePayment();
        monnifyPayment.setAmount(request.getAmount());
        monnifyPayment.setPaymentDescription(request.getDescription());
        monnifyPayment.setPaymentReference(reference);
        monnifyPayment.setCurrencyCode(request.getCurrency_change());
        monnifyPayment.setCustomerEmail(email);
        monnifyPayment.setCustomerName(name);
        monnifyPayment.setContractCode(beanConfig.getMonnifyContractCode());
        return monnifyPayment;
    }

    private PaystackInitializePayment createPayStackRequest(BigDecimal amount, String refrence, String email) {
        PaystackInitializePayment paystackInitializePayment = new PaystackInitializePayment();
        paystackInitializePayment.setEmail(email);
        paystackInitializePayment.setAmount(amount);
        paystackInitializePayment.setReference(refrence);
        return paystackInitializePayment;
    }

    private Account findAccount(String accountNumber) throws AccountAlreadyExist {
        return repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountAlreadyExist(ACCOUNT_DOES_NOT_EXIST));
    }


}
