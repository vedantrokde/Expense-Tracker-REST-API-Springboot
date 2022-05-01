package com.code.et.service;

import java.util.List;

import com.code.et.exception.EtNotFoundException;
import com.code.et.model.Category;
import com.code.et.model.Tracker;
import com.code.et.repo.CategoryRepo;
import com.code.et.repo.TrackerRepo;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepo categoryRepo;
    private TrackerRepo trackerRepo;

    public List<Category> getAllCategories(int userId) {
        Tracker tracker = trackerRepo.getById(userId);
        return tracker.getCategories();
    }

    public Category getCategory(int userId, int id) throws EtNotFoundException {
        Category category=null;
        Tracker tracker = trackerRepo.getById(userId);
        List<Category> categories = tracker.getCategories();
        for(Category tempCategory: categories) {
            if(tempCategory.getId()==id) {
                category = tempCategory;
                break;
            }
        }
        if(category == null) throw new EtNotFoundException("Category " + id + " not found");
        return category;
    }

    public Category saveCategory(int userId, Category category) {
        Tracker tracker = trackerRepo.getById(userId);
        if(category.getId()==0) tracker.addCategory(category);
        return categoryRepo.save(category);
    }

    public void deleteCategory(int userId, int id) throws EtNotFoundException {
        Tracker tracker = trackerRepo.getById(userId);
        List<Category> categories = tracker.getCategories();
        for(Category tempCategory: categories) {
            if(tempCategory.getId()==id) {
                categories.remove(tempCategory);
                categoryRepo.delete(tempCategory);
                return;
            }
        }
        throw new EtNotFoundException("Category " + id + " not found");
    }
}
