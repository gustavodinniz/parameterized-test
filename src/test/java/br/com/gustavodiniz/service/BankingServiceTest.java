package br.com.gustavodiniz.service;

import br.com.gustavodiniz.enums.AccountType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BankingServiceTest {

    private final BankingService service = new BankingService();

    @ParameterizedTest
    @ValueSource(strings = {"500.00", "1000.00", "1500.00"})
    void testIsSufficientBalanceWithValueSource(String amountStr) {
        BigDecimal amount = new BigDecimal(amountStr);
        assertTrue(service.isSufficientBalance("123456", amount));
    }

    @ParameterizedTest
    @CsvSource({
            "123456, true",
            "65432, false",
            "1234567, false"
    })
    void testIsValidAccountNumberWithCsvSource(String accountNumber, boolean expected) {
        assertEquals(expected, service.isValidAccountNumber(accountNumber));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/transfer-data.csv", numLinesToSkip = 1)
    void testTransferWithCsvFileSource(String fromAccount, String toAccount, String amountStr, boolean expected) {
        BigDecimal amount = new BigDecimal(amountStr);
        assertEquals(expected, service.transfer(fromAccount, toAccount, amount));
    }

    @ParameterizedTest
    @MethodSource("provideAccountTypes")
    void testIsValidAccountTypeWithMethodSource(String accountNumber, boolean expected) {
        assertEquals(expected, service.isValidAccountType(accountNumber));
    }

    @ParameterizedTest
    @EnumSource(value = AccountType.class, names = {"CURRENT", "SAVINGS"})
    void testIsValidAccountTypeWithEnumSource(AccountType accountType) {
        assertNotNull(accountType);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountArgumentsProvider.class)
    void testTransferWithCustomArgumentsProvider(String fromAccount, String toAccount, BigDecimal amount, boolean expected) {
        assertEquals(expected, service.transfer(fromAccount, toAccount, amount));
    }

    @ParameterizedTest
    @NullSource
    void testIsValidAccountNumberWithNullSource(String accountNumber) {
        assertFalse(service.isValidAccountNumber(accountNumber));
    }

    @ParameterizedTest
    @EmptySource
    void testIsValidAccountNumberWithEmptySource(String accountNumber) {
        assertFalse(service.isValidAccountNumber(accountNumber));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testIsValidAccountNumberWithNullAndEmptySource(String accountNumber) {
        assertFalse(service.isValidAccountNumber(accountNumber));
    }

    static Stream<Arguments> provideAccountTypes() {
        return Stream.of(
                Arguments.of("123456", true),
                Arguments.of("654321", false)
        );
    }

}