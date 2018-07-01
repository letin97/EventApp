package com.uit.letrongtin.eventapp.database.DataSource;

import com.uit.letrongtin.eventapp.database.LocalDatabase.TypeNewsDAO;
import com.uit.letrongtin.eventapp.database.TypeNews;

import java.util.List;

import io.reactivex.Flowable;

public class TypeNewsRepository implements TypeNewsDAO {

    private TypeNewsDAO typeNewsDAO;
    private static TypeNewsRepository instance;

    public static TypeNewsRepository getInstance(TypeNewsDAO typeNewsDAO) {
        if (instance == null) instance = new TypeNewsRepository(typeNewsDAO);
        return instance;
    }

    public TypeNewsRepository(TypeNewsDAO typeNewsDAO) {
        this.typeNewsDAO = typeNewsDAO;
    }

    @Override
    public Flowable<List<TypeNews>> getAllType() {
        return typeNewsDAO.getAllType();
    }

    @Override
    public void insertType(TypeNews... typeNews) {
        typeNewsDAO.insertType(typeNews);
    }

    @Override
    public void updateType(TypeNews... typeNews) {
        typeNewsDAO.updateType(typeNews);
    }

    @Override
    public void deleteType(TypeNews... typeNews) {
        typeNewsDAO.deleteType(typeNews);
    }

    @Override
    public void deleteAllType() {
        typeNewsDAO.deleteAllType();
    }

    @Override
    public TypeNews getTypeByTypeChose(String type) {
        return typeNewsDAO.getTypeByTypeChose(type);
    }

    @Override
    public void deleteTypeByTypeChose(String type) {
        typeNewsDAO.deleteTypeByTypeChose(type);
    }
}
