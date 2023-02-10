package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.BuildingDao;
import com.techelevator.tenmo.model.Building;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/building/")
public class BuildingController {
    private final BuildingDao buildingDao;
   
    public BuildingController(BuildingDao buildingDao) {
        this.buildingDao = buildingDao;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List <Building> getAllBuildings () {
        return buildingDao.getAllBuidings();
    }
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Building getBuildingById (@PathVariable int id) {
        return buildingDao.getBuildingById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createBuilding (@RequestBody Building building) {
        buildingDao.createBuilding(building);
    }
    
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateBuilding (@RequestBody Building building) {
        buildingDao.updateBuilding(building);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public void deleteBuilding (@PathVariable int id) {
        buildingDao.deleteBuilding(id);
    }

   





}
