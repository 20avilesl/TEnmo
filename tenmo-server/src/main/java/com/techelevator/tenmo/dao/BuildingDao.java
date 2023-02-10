package com.techelevator.tenmo.dao;
import java.util.List;

import com.techelevator.tenmo.model.Building;

public interface BuildingDao {
    //GET
    //getAllBuildings
    List<Building> getAllBuidings ();
    //getBuidling by Id
    Building getBuildingById(int id);
    
    //POST
    //Create buidling
    void createBuilding (Building building);
    
    //PUT
    //update building
    void updateBuilding (Building building);
    
    //DELETE
    //delete building
    void deleteBuilding (int id);
}
