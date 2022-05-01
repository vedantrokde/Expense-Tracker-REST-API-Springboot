package com.code.et.service;

import java.util.List;

import com.code.et.exception.EtNotFoundException;
import com.code.et.model.Category;
import com.code.et.model.Tracker;
import com.code.et.model.Transaction;
import com.code.et.repo.TrackerRepo;
import com.code.et.repo.TransactionRepo;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    private TrackerRepo trackerRepo;
    private TransactionRepo transactionRepo;
    private CategoryService categoryService;

    public List<Transaction> getAllTransactionsByCategory(int userId, int categoryId) {
        Category category = categoryService.getCategory(userId, categoryId);
        return category.getTransactions();
    }

    public Transaction getTransaction(int userId, int categoryId, int id) throws EtNotFoundException {
        Category category = categoryService.getCategory(userId, categoryId);
        for (Transaction transaction : category.getTransactions()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        throw new EtNotFoundException("Transaction " + id + " not found");
    }

    public Transaction saveTransaction(int userId, int categoryId, Transaction transaction) {
        Tracker tracker = trackerRepo.getById(userId);
        Category category = categoryService.getCategory(userId, categoryId);

        if (transaction.getId() == 0) {
            transaction.setTransactionDate(System.currentTimeMillis());
            tracker.addTransaction(transaction);
            category.addTransaction(transaction);
        }

        return transactionRepo.save(transaction);
    }

    public void deleteTransaction(int userId, int categoryId, int id) throws EtNotFoundException {
        getTransaction(userId, categoryId, id);
        transactionRepo.deleteById(id);
    }
}
