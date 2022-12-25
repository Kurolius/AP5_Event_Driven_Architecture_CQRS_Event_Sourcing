# AP5 Event Driven Architecture CQRS Event Sourcing
 
 ## Architecture de l'activité pratique
 
![](https://i.imgur.com/DCbtJ98.png)

## Command Query Responsability Segregation Architecture et terminologie

![](https://i.imgur.com/Bh7ydqO.png)

## Commandes 

### Classe générique de command
	
```java!
public abstract class BaseCommand <T> {
    @TargetAggregateIdentifier
    @Getter private T id;
    public BaseCommand(T id) {
        this.id = id;
    }
}
```

### Classe de commande de création de compte

```java!
public class CreateAccountCommand extends BaseCommand<String>{
    @Getter private double initialeBalance;
    @Getter private  String currency;
    public CreateAccountCommand(String id, double initialeBalance, String currency) {
        super(id);
        this.initialeBalance = initialeBalance;
        this.currency = currency;
    }
}
```
	
### Classe de Commande de crédit

```java!
public class CreditAccountCommand extends BaseCommand<String>{
    @Getter private double amount;
    @Getter private  String currency;
    public CreditAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
```

### Classe de commande de débit
	
```java!
public class DebitAccountCommand extends BaseCommand<String>{
    @Getter private double amount;
    @Getter private  String currency;
    public DebitAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
```

## Query 

### Classe Account
```java!
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private String id;
    private double balance;
    private Instant createdAt;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @OneToMany(mappedBy = "account")
    private Collection<AccountTransaction> transactions;
}
```
	
### Classe Transaction
```java!
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date timestamp;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
```

## Test with Swagger

### Create Account

#### Request
![image](https://user-images.githubusercontent.com/84138772/209481347-01cb9f2d-cc2c-463c-923e-28ea1a11b1fe.png)

#### Response
![image](https://user-images.githubusercontent.com/84138772/209481360-4a903a27-8f27-4bdc-afd7-4cc5e41966fb.png)


### Debit Account

#### Request

![image](https://user-images.githubusercontent.com/84138772/209481428-a83b2a2a-7cf5-45a9-8951-c24c911bc2dd.png)

#### Response

![image](https://user-images.githubusercontent.com/84138772/209481436-fc3e73db-228b-430a-95be-a157c63b2510.png)


### Credit Account

#### Request

![image](https://user-images.githubusercontent.com/84138772/209481475-0a4a17e6-0e7f-431a-b6e2-4a77c5d18864.png)

#### Response

![image](https://user-images.githubusercontent.com/84138772/209481484-83420509-005c-4835-bf2a-91265de26e75.png)

### EventStore Account

![image](https://user-images.githubusercontent.com/84138772/209481518-9407e58d-ca03-4514-8af0-77bee294dc5e.png)

### Get Account

#### Request

![image](https://user-images.githubusercontent.com/84138772/209481548-b3e9acce-adb4-42ce-b560-3fe1adf1f013.png)


#### Response

![image](https://user-images.githubusercontent.com/84138772/209481552-f0019f9b-6b7a-438d-89b2-a47fade1ac1a.png)

### Get All Account

#### Request

![image](https://user-images.githubusercontent.com/84138772/209481562-fee38e72-fef4-4afe-929f-de8bd3edd4b3.png)


#### Response

![image](https://user-images.githubusercontent.com/84138772/209481574-81910cfc-3c6d-41de-9ebc-4d5eeb955537.png)






