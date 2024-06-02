package org.aspire.dao.repository;

import org.aspire.data.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {

    @Query("{ 'status': ?0 }")
    List<Loan> findLoansByStatus(String status);

    @Query("{ 'loanId': ?0 }")
    Loan getLoanById(String loanId);

    @Query("{ 'applicant': ?0 }")
    List<Loan> getLoansByApplicantName(String applicantName);
}
