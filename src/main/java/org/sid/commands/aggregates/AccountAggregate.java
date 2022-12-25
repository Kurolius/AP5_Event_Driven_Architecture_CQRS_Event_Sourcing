package org.sid.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.commonapi.commands.CreateAccountCommand;
import org.sid.commonapi.commands.CreditAccountCommand;
import org.sid.commonapi.commands.DebitAccountCommand;
import org.sid.commonapi.enums.AccountStatus;
import org.sid.commonapi.events.AccountActivatedEvent;
import org.sid.commonapi.events.AccountCreatedEvent;
import org.sid.commonapi.events.AccountCreditedEvent;
import org.sid.commonapi.events.AccountDebitedEvent;
import org.sid.commonapi.exceptions.BalanceInSufficientException;
import org.sid.commonapi.exceptions.NegativeAmountException;
import org.sid.commonapi.exceptions.NegativeInitialBalanceException;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier // this is the id of the aggregate
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus accountStatus;

    public AccountAggregate() {
        // required by AXON => no-arg constructor
    }

    @CommandHandler  // listener on CreateAccountCommand
   //=>comme je fais un subscribe sur le bus d'evenement, je vais recevoir un message de type CreateAccountCommand
    // le fct de décision est le constructeur de la classe
    public AccountAggregate(CreateAccountCommand command) {
        // code metier
        if(command.getInitialeBalance() <0) throw new NegativeInitialBalanceException("Balance should not be négatif !");


        // if all logic is good ==> we emmit the event
        AggregateLifecycle.apply(new AccountCreatedEvent(
            command.getId(),
            command.getInitialeBalance(),
            command.getCurrency(),
            AccountStatus.CREATED
        ));
    }

     // evoulution function for AccountCreatedEvent
    @EventSourcingHandler //=> je vais recevoir un message de type AccountCreatedEvent
    public void on(AccountCreatedEvent event) {
       this.accountId = event.getId();
       this.balance = event.getInitialeBalance();
       this.currency = event.getCurrency();
       this.accountStatus = event.getAccountStatus();

       //=> je vais emettre un message de type AccountActivatedEvent
       AggregateLifecycle.apply(new AccountActivatedEvent(
           event.getId(),
           AccountStatus.ACTIVATED
       ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        this.accountStatus = event.getAccountStatus();

    }

    /// credit operation
    @CommandHandler  // listener on CreateAccountCommand
    public void handle(CreditAccountCommand command) {
        // code metier
        if(command.getAmount() <0) throw new NegativeAmountException("Amount should not be negative");

        // si tous se passe bien = ok ==> we emmit the event
        AggregateLifecycle.apply(new AccountCreditedEvent(
            command.getId(),
            command.getAmount(),
            command.getCurrency()
        ));
    }

    // evoulution function
    @EventSourcingHandler //
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    /// debit operation
    @CommandHandler
    public void handle(DebitAccountCommand command) {
        // code metier
        if(command.getAmount() <0) throw new NegativeAmountException("Amount should not be negative");
        if(balance < command.getAmount() ) throw new BalanceInSufficientException("Balance is InSufficient !");

        // si ts se passent bien = ok ==> we emmit the event
        AggregateLifecycle.apply(new AccountDebitedEvent(
            command.getId(),
            command.getAmount(),
            command.getCurrency()
        ));
    }
    // evoulution function
    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }

}
