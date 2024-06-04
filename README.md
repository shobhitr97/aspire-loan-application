### Setup Mongo [Prerequisite]
* Create folder for mongo data : `mongo-data`
* Run 
```mongod --dbpath mongo-data --port 12312```
* [Mongo Reference](https://spring.io/guides/gs/accessing-data-mongodb)

### Run application
```./gradlew build && ./gradlew bootRun```

---

### Authentication and Authorization
We are currently only supporting basic authorization in this application. 
Clients who wish to access the server need to register a user using `/auth/register` stating the type of the user: `applicant` / `admin`.
Once registered, clients will generate a basic authorization header using username and password. 
[Reference](https://www.debugbear.com/basic-auth-header-generator)

This authorization header needs to be included in every request to API server
* `/admin/**` APIs are only accessible to `admin` users
* `/applicant/**` APIs are only accessible to `applicant` users

### User flow

* Step 1 : Applicant creates a loan
* Step 2 : Admin gets all the loans in pending status
* Step 3 : Admin approves or rejects each loan request using loanId 1 by 1
* Step 4 : Applicant will check the status of all their loans
* Step 5 : Applicant will make the repayment if the loan is approved
* Step 6 : Once all the repayments are done, the loan's status will change from approved to paid